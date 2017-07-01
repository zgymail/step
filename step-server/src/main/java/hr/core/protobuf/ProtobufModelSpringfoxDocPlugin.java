package hr.core.protobuf;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

public class ProtobufModelSpringfoxDocPlugin  implements ModelPropertyBuilderPlugin{

	@Override
	public boolean supports(DocumentationType delimiter) {
		return true;
	}

	@Override
	public void apply(ModelPropertyContext context) {
	}

}
