package sp.spring.io.shopping.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class ProductCategory {
	 @Id
	    @GeneratedValue
	    private Integer categoryId;

	    private String categoryName;

	    @NaturalId
	    private Integer categoryType;

	    private Date createTime;

	    private Date updateTime;

	    public ProductCategory() {

	    }

	    public ProductCategory(String categoryName, Integer categoryType) {
	        this.categoryName = categoryName;
	        this.categoryType = categoryType;
	    }

}
