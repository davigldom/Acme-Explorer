
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class EducationRecord extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	titleOfDiploma;
	private String	institution;
	private String	attachment;
	private String	comments;
	private Period	educationPeriod;


	@NotBlank
	public String getTitleOfDiploma() {
		return this.titleOfDiploma;
	}

	public void setTitleOfDiploma(final String titleOfDiploma) {
		this.titleOfDiploma = titleOfDiploma;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@URL
	public String getAttachment() {
		return this.attachment;
	}

	public void setAttachment(final String attachment) {
		this.attachment = attachment;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	@NotNull
	@Valid
	public Period getEducationPeriod() {
		return this.educationPeriod;
	}

	public void setEducationPeriod(final Period educationPeriod) {
		this.educationPeriod = educationPeriod;
	}

}
