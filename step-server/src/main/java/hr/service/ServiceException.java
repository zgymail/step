package hr.service;

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Integer code=0;
	public ServiceException(Integer code) {
		super();
		this.code=code;
	}

	public ServiceException(Integer code,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.code=code;
	}

	public ServiceException(Integer code,String message, Throwable cause) {
		super(message, cause);
		this.code=code;
	}

	public ServiceException(Integer code,String message) {
		super(message);
		this.code=code;
	}

	public ServiceException(Integer code,Throwable cause) {
		super(cause);
		this.code=code;
	}

}
