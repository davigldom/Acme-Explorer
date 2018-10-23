
package domain;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Stage extends DomainEntity {

	//Attributes
	private String	title;
	private String	description;
	private double	price;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public double getPrice() {
		final NumberFormat formatter = new DecimalFormat("#0.00");
		return Double.parseDouble(formatter.format(this.price));
	}

	public void setPrice(final double price) {
		this.price = price;
	}

}
