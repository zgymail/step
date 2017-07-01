package hr.core.protobuf;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.TextFormat;

public class ProtobufHttpMessageConverter extends AbstractHttpMessageConverter<Message> {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	public static final MediaType PROTOBUF = new MediaType("application", "x-protobuf", DEFAULT_CHARSET);
	
	public static final MediaType JSON = MediaType.APPLICATION_JSON;

	private static final ConcurrentHashMap<Class<?>, Method> methodCache = new ConcurrentHashMap<Class<?>, Method>();
	/**
	 * Construct a new instance.
	 */
	public ProtobufHttpMessageConverter() {
		super(PROTOBUF, JSON,MediaType.TEXT_HTML);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return Message.class.isAssignableFrom(clazz);
	}

	@Override
	protected MediaType getDefaultContentType(Message message) {
		return PROTOBUF;
	}

	@Override
	protected Message readInternal(Class<? extends Message> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		MediaType contentType = inputMessage.getHeaders().getContentType();
		if (contentType == null) {
			contentType = PROTOBUF;
		}
		Charset charset = contentType.getCharset();
		if (charset == null) {
			charset = DEFAULT_CHARSET;
		}
		InputStreamReader reader=null;
		try {
			Message.Builder builder = getMessageBuilder(clazz);
			if (PROTOBUF.isCompatibleWith(contentType)) {
				builder.mergeFrom(inputMessage.getBody());
			}else if (JSON.isCompatibleWith(contentType)) {
				reader = new InputStreamReader(inputMessage.getBody(), charset);
				JsonFormat.parser().merge(reader, builder);
			}else{
				reader = new InputStreamReader(inputMessage.getBody(), charset);
				TextFormat.getParser().merge(reader, builder);
			}
			return builder.build();
		}
		catch (Exception ex) {
			throw new HttpMessageNotReadableException("Could not read Protobuf message: " + ex.getMessage(), ex);
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
	}

	/**
	 * This method overrides the parent implementation, since this HttpMessageConverter
	 * can also produce {@code MediaType.HTML "text/html"} ContentType.
	 */
	@Override
	protected boolean canWrite(MediaType mediaType) {
		return (super.canWrite(mediaType) || MediaType.TEXT_HTML.isCompatibleWith(mediaType));
	}

	@Override
	protected void writeInternal(Message message, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {

		MediaType contentType = outputMessage.getHeaders().getContentType();
		if (contentType == null) {
			contentType = getDefaultContentType(message);
		}
		Charset charset = contentType.getCharset();
		if (charset == null) {
			charset = DEFAULT_CHARSET;
		}
		OutputStreamWriter outputStreamWriter=null;
		try {
			if (PROTOBUF.isCompatibleWith(contentType)) {
					OutputStream output=outputMessage.getBody();
					output.write(message.toByteArray());
					output.flush();
			}else if(JSON.isCompatibleWith(contentType)){
					String jsonStr=JsonFormat.printer().print(message);
					outputStreamWriter = new OutputStreamWriter(outputMessage.getBody(), charset);
					outputStreamWriter.write(jsonStr);
					outputStreamWriter.flush();
			}else{
				String textStr=TextFormat.printToString(message);
				outputStreamWriter = new OutputStreamWriter(outputMessage.getBody(), charset);
				outputStreamWriter.write(textStr);
				outputStreamWriter.flush();
			}
		} catch (Exception e) {
			throw new HttpMessageNotReadableException("Could not read Protobuf message: " + e.getMessage(), e);
		}finally{
			if(outputStreamWriter!=null){
				outputStreamWriter.close();
			}
		}
	}

	
	private static Message.Builder getMessageBuilder(Class<? extends Message> clazz) throws Exception {
		Method method = methodCache.get(clazz);
		if (method == null) {
			method = clazz.getMethod("newBuilder");
			methodCache.put(clazz, method);
		}
		return (Message.Builder) method.invoke(clazz);
	}


}
