package sp.spring.io.shopping.entity.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {

	@NotBlank
	private String username;
	@NotBlank
	private String password;

}
