package sp.spring.io.shopping.exception;

import sp.spring.io.shopping.enums.ResultEnum;

public class MyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;

	public MyException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());
		this.code = resultEnum.getCode();
	}

	public MyException(Integer code, String message) {
		super(message);
		this.code = code;
	}

}
