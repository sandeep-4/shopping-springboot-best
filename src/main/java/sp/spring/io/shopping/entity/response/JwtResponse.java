package sp.spring.io.shopping.entity.response;

import lombok.Data;

@Data
public class JwtResponse {

	private String token;
	private String type = "Bearer";
	private String acount;
	private String name;
	private String role;

	public JwtResponse(String token, String acount, String name, String role) {
		super();
		this.token = token;
		this.acount = acount;
		this.name = name;
		this.role = role;
	}

}
