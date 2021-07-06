package sp.spring.io.shopping.enums;

public enum OrderStatusEnum implements CodeEnum {
	NEW(0, "New OrderMain"), FINISHED(1, "Finished"), CANCELED(2, "Canceled");

	private int code;
	private String msg;

	OrderStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public Integer getcode() {
		// TODO Auto-generated method stub
		return code;
	}

}
