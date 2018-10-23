
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	//Attributes

	//Relationships
	private Collection<Application>			applications;
	private Collection<EmergencyContact>	emergencyContacts;
	private Finder							finder;
	private Collection<Story>				stories;
	private Collection<SurvivalClass>		survivalClasses;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	@OneToMany
	public Collection<EmergencyContact> getEmergencyContacts() {
		return this.emergencyContacts;
	}

	public void setEmergencyContacts(final Collection<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "explorer")
	public Collection<Story> getStories() {
		return this.stories;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@NotNull
	@Valid
	@ManyToMany
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}
}
