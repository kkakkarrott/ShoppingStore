package walmart.hackathon.shopapi.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@Table(name = "product_info")
public class ProductInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String productId;

	@NotNull
	private String productName;

	@NotNull
	private BigDecimal productPrice;

	@NotNull
	@Min(0)
	private Integer productStock;

	private String productDescription;

	private String productIcon;

	@ColumnDefault("0")
	private Integer productStatus;

	@ColumnDefault("0")
	private Integer categoryType;

	private Date createTime;
	private Date updateTime;

	public ProductInfo() {
	}
}
