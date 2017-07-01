package hr.core.util;

import org.springframework.http.MediaType;

public class MediaTypeExt extends MediaType {
	public final static String APPLICATION_PROTOBUF_VALUE = "application/x-protobuf";
	public final static MediaType APPLICATION_PROTOBUF;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6781811424464883688L;

	public MediaTypeExt(String type) {
		super(type);
	}

	
	static {
		APPLICATION_PROTOBUF = valueOf(APPLICATION_PROTOBUF_VALUE);
	}
}
