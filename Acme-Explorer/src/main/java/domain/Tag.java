
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Tag extends DomainEntity {

	//Attributes

	private String	name;


	@NotBlank
	@Column(unique=true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//Relationships
	private Collection<TagValue>	tagValues;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "tag")
	public Collection<TagValue> getTagValues() {
		return this.tagValues;
	}

	public void setTagValues(final Collection<TagValue> tagValues) {
		this.tagValues = tagValues;
	}

}
