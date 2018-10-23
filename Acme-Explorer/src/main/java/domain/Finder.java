
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Attributes

	private String		keyWord;
	private PriceRange	priceRange;
	private DateRange	dateRange;
	private Date		moment;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@Valid
	public PriceRange getPriceRange() {
		return this.priceRange;
	}

	public void setPriceRange(final PriceRange priceRange) {
		this.priceRange = priceRange;
	}

	@Valid
	public DateRange getDateRange() {
		return this.dateRange;
	}

	public void setDateRange(final DateRange dateRange) {
		this.dateRange = dateRange;
	}

	@NotNull
	@Past
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	//Relationships

	private Collection<Trip>	trips;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Trip> getTrips() {
		return this.trips;
	}

	public void setTrips(final Collection<Trip> trips) {
		this.trips = trips;
	}
}
