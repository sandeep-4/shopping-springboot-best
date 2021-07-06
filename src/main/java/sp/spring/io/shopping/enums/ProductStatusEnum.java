package sp.spring.io.shopping.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {
	UP(0, "Available"), DOWN(1, "Unavailable");

	private Integer code;
	private String message;

	public String getStatus(Integer code) {
		for (ProductStatusEnum statusEnum : ProductStatusEnum.values()) {
			if (statusEnum.getcode() == code) {
				return statusEnum.getMessage();
			}
		}
		return "";
	}

	@Override
	public Integer getcode() {
		// TODO Auto-generated method stub
		return null;
	}

	private ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

}
