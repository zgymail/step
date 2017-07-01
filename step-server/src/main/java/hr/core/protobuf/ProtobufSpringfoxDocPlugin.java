package hr.core.protobuf;

import static com.google.common.collect.Maps.uniqueIndex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.introspect.BasicClassIntrospector;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.google.common.base.Optional;
import com.google.protobuf.UnknownFieldSet;

import springfox.documentation.schema.property.BeanPropertyDefinitions;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER+ 1000)
public class ProtobufSpringfoxDocPlugin implements ModelPropertyBuilderPlugin{
	public static void updateObjectMapperWithConverter(List<HttpMessageConverter<?>> converters){
        ObjectMapper objectMapper = new ProtobufObjectMapper(); 
        for (HttpMessageConverter converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter =
                        ((MappingJackson2HttpMessageConverter) converter);
                	jacksonConverter.setObjectMapper(objectMapper);
                
            }
        }
	}
	
	public static List<Class<?>> getIgnoredParameterTypes(){
		List<Class<?>> ignoredParameterTypes=new ArrayList<Class<?>>();
		ignoredParameterTypes.add(UnknownFieldSet.class);
		ignoredParameterTypes.add(com.google.protobuf.ByteString.class);
		ignoredParameterTypes.add(com.google.protobuf.GeneratedMessageV3.FieldAccessorTable.class);
		ignoredParameterTypes.add(com.google.protobuf.Descriptors.Descriptor.class);
		return ignoredParameterTypes;
	}
	
	

	
		private TypeResolver resolver;
		
	
		  public ProtobufSpringfoxDocPlugin(TypeResolver resolver) {
			    this.resolver = resolver;
		  }
		

		@Override
		public void apply(ModelPropertyContext context) {
	    	Optional<BeanPropertyDefinition> def=context.getBeanPropertyDefinition();
	    	if(def.isPresent()){
	    		context.getBuilder().required(true);
	    	}

		}
		
		@Override
		  public boolean supports(DocumentationType delimiter) {
		    return true;
		  }
}

class ProtobufObjectMapper extends ObjectMapper {
	protected ClassIntrospector defaultClassIntrospector() {
        return new ProtobufBasicClassIntrospector();
    }
}

@SuppressWarnings("serial")
class ProtobufBasicClassIntrospector extends BasicClassIntrospector {
	private static Set<String> protobufIgnoreFields=new HashSet<String>();
	static{
		protobufIgnoreFields.add("allFields");
		protobufIgnoreFields.add("defaultInstanceForType");
		protobufIgnoreFields.add("descriptorForType");
		protobufIgnoreFields.add("initializationErrorString");
		protobufIgnoreFields.add("initialized");
	    protobufIgnoreFields.add("parserForType");
	    protobufIgnoreFields.add("serializedSize");
	    protobufIgnoreFields.add("unknownFields");
	    
	}
	 @Override
	    public BasicBeanDescription forSerialization(SerializationConfig cfg,
	            JavaType type, MixInResolver r)
	    {
	        BasicBeanDescription desc = _findStdTypeDesc(type);
	        if (desc == null) {
	            desc = _findStdJdkCollectionDesc(cfg, type);
	            if (desc == null) {
	                desc = BasicBeanDescription.forSerialization(collectProperties(cfg,
	                        type, r, true, "set"));
	                this.updateProperty(this.collectProperty(desc), desc);
	            }
	            _cachedFCA.putIfAbsent(type, desc);
	        }
	        return desc;
	    }
	@Override
	    public BasicBeanDescription forDeserialization(DeserializationConfig cfg,
	            JavaType type, MixInResolver r)
	    {
		  	BasicBeanDescription desc = _findStdTypeDesc(type);
	        if (desc == null) {
	            desc = _findStdJdkCollectionDesc(cfg, type);
	            if (desc == null) {
	            	desc = BasicBeanDescription.forSerialization(collectProperties(cfg,
	                        type, r, true, "set"));
	                this.updateProperty(this.collectProperty(desc), desc);
	            }
	            _cachedFCA.putIfAbsent(type, desc);
	        }
	        return desc;
	   }
	
	   private Set<ProtobufProperty> collectProperty(BasicBeanDescription desc){
		   List<BeanPropertyDefinition> properties=desc.findProperties();
		   Set<String> propertiesList=new HashSet<String>();
		   for(BeanPropertyDefinition propertyDefinition:properties){
			   String name=propertyDefinition.getName();
			   propertiesList.add(name);
           }
		   Set<ProtobufProperty> ret=new HashSet<ProtobufProperty>();
		   for(String property:propertiesList){
			   
			   
			   if(protobufIgnoreFields.contains(property)|| property.endsWith("Value") 
	    				|| property.endsWith("Bytes")
	    				|| property.indexOf("OrBuilder")>0
	    				){
				   ProtobufProperty p=new ProtobufProperty();
				   p.originName=property;
				   p.remove=true;
				   ret.add(p);
			   }else{
				   if(property.endsWith("Count")){
					   String propertyName=property.substring(0, property.length()-5);
					   String propertyNameList=propertyName+"List";
					   if(propertiesList.contains(propertyNameList)){
						   ProtobufProperty p=new ProtobufProperty();
						   p.originName=property;
						   p.remove=true;
						   ret.add(p);
						   
						   p=new ProtobufProperty();
						   p.originName=propertyNameList;
						   p.modifyName=propertyName;
						   p.remove=false;
						   ret.add(p);
						   continue;
					   }
				   }
			   }
		   }
		   return ret;
	   }
	
	    private void updateProperty(Set<ProtobufProperty> updateProperties,BasicBeanDescription desc){
	    	 for(ProtobufProperty updatePropertie:updateProperties){
	    		 if(updatePropertie.remove){
	    			 desc.removeProperty(updatePropertie.originName);
	    		 }else{
	    			 BeanPropertyDefinition beanPropertyDefinition=desc.findProperty(PropertyName.construct(updatePropertie.originName));
	    			 desc.removeProperty(updatePropertie.originName);
	    			 POJOPropertyBuilder pb=new POJOPropertyBuilder((POJOPropertyBuilder)beanPropertyDefinition,PropertyName.construct(updatePropertie.modifyName));
	    			 desc.addProperty(pb);
	    		 }
	    	 }
	    }
}

class ProtobufProperty{
public String originName;
public String modifyName;
public boolean remove;
}