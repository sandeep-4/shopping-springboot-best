package sp.spring.io.shopping.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class OrderMain implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long orderId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderMain")
	private Set<ProductInOrder> products = new HashSet<>();

	@NotEmpty
	private String buyerEmail;

	@NotEmpty
	private String buyerName;

	@NotEmpty
	private String buyerPhone;

	@NotEmpty
	private String buyerAddress;

	// Total order amount
	@NotNull
	private BigDecimal orderAmount;

	/**
	 * default 0 for new order
	 */
	@NotNull
	@ColumnDefault("0")
	private Integer orderStatus;

	@CreationTimestamp
	private LocalDateTime createTime;

	@UpdateTimestamp
	private LocalDateTime updateTime;

	public OrderMain(User buyer) {
		this.buyerEmail = buyer.getEmail();
		this.buyerName = buyer.getName();
		this.buyerPhone = buyer.getPhone();
		this.buyerAddress = buyer.getAddress();
		this.orderAmount = buyer.getCart().getProducts().stream()
				.map(item -> item.getProductPrice().multiply(new BigDecimal(item.getCount()))).reduce(BigDecimal::add)
				.orElse(new BigDecimal(0));
		this.orderStatus = 0;
	}

}
