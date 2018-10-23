
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	// Attributes

	private String	title;
	private String	description;
	private Date	publication;
	private String	ticker;
	private String	cancelationReason;
	private Date	startDate;
	private Date	endDate;
	private String	requirements;
	private boolean	published;
	private boolean	cancelled;

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

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublication() {
		return this.publication;
	}

	public void setPublication(final Date publication) {
		this.publication = publication;
	}

	@Pattern(regexp = "\\d{6}[-][A-Z]{4}")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public String getCancelationReason() {
		return this.cancelationReason;
	}

	public void setCancelationReason(final String cancelationReason) {
		this.cancelationReason = cancelationReason;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return this.published;
	}

	public void setPublished(final boolean published) {
		this.published = published;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(final boolean cancelled) {
		this.cancelled = cancelled;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	@NotBlank
	public String getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}


	// Relationships

	private Ranger						ranger;
	private Manager						manager;
	private LegalText					legalText;
	private Collection<Stage>			stages;
	private Category					category;
	private Collection<Application>		applications;
	private Collection<Note>			notes;
	private Collection<Audition>		auditions;
	private Collection<Sponsorship>		sponsorships;
	private Collection<SurvivalClass>	survivalClasses;
	private Collection<TagValue>		tagValues;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public LegalText getLegalText() {
		return this.legalText;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}

	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> notes) {
		this.notes = notes;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Audition> getAuditions() {
		return this.auditions;
	}

	public void setAuditions(final Collection<Audition> auditions) {
		this.auditions = auditions;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<Sponsorship> getSponsorships() {
		return this.sponsorships;
	}

	public void setSponsorships(final Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClasses) {
		this.survivalClasses = survivalClasses;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<TagValue> getTagValues() {
		return this.tagValues;
	}

	public void setTagValues(final Collection<TagValue> tagValues) {
		this.tagValues = tagValues;
	}

}
