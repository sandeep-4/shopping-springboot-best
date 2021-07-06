package sp.spring.io.shopping.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	private String productId;
	@NotNull
	private String productName;
	@javax.validation.constraints.NotNull
	private BigDecimal productPrice;

	@NotNull
	@Min(0)
	private Integer productStock;

	private String productDescription;

	private String productIcon;

	@ColumnDefault(value = "0")
	private Integer productStatus;

	@ColumnDefault("0")
	private Integer categoryType;

	@CreationTimestamp
	private Date createdTime;
	@UpdateTimestamp
	private Date updatedTime;

}
