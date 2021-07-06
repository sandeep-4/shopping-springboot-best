package sp.spring.io.shopping.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class User implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min=3,message="Length must be greater than 3")
	private String password;
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String phone;
	
	@NotEmpty
	private String address;
	@NotNull
	private boolean active;
	
	@NotEmpty
	private String role="ROLE_CUSTOMER";
	
	@OneToOne(mappedBy="user" ,cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JsonIgnore
	//bi drection recursion
	private Cart cart;
	
	
	
}
