
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SystemConfig extends DomainEntity {

	//Attributes

	private String				banner;
	private String				welcomeMessage;
	private String				welcomeMessageEs;
	private Collection<String>	spamWords;
	private String				countryCode;
	private double				VAT;
	private Integer				cacheHours;
	private Integer				finderResults;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessage() {
		return this.welcomeMessage;
	}

	public void setWelcomeMessage(final String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}

	@NotBlank
	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotBlank
	@Pattern(regexp = "^[+]([1-9]{1,3})$")
	public String getCountryCode() {   //Added pattern as it will be automatically used in phone numbers
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public double getVAT() {
		return this.VAT;
	}

	public void setVAT(final double VAT) {
		this.VAT = VAT;
	}

	@NotNull
	@Range(min = 1, max = 24)
	public Integer getCacheHours() {
		return this.cacheHours;
	}

	public void setCacheHours(final Integer hours) {
		this.cacheHours = hours;
	}

	@NotNull
	@Range(min = 0, max = 100)
	public Integer getFinderResults() {
		return this.finderResults;
	}

	public void setFinderResults(final Integer results) {
		this.finderResults = results;
	}

}
