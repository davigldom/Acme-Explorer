
package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	//Attributes

	private Date			moment;
	private String			subject;
	private String			body;
	private PriorityLevel	priorityLevel;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Valid
	public PriorityLevel getPriorityLevel() {
		return this.priorityLevel;
	}

	public void setPriorityLevel(final PriorityLevel priorityLevel) {
		this.priorityLevel = priorityLevel;
	}


	//Relationships

	//	private Actor	sender;
	//	private Actor	recipient;

	//	private Folder		folder;
	private List<Actor>	actors;


	@NotEmpty
	@Valid
	@ManyToMany
	@OrderColumn(name = "POSITION")
	public List<Actor> getActors() {
		return this.actors;
	}

	public void setActors(final List<Actor> actors) {
		this.actors = actors;
	}

	//	@NotNull
	//	@Valid
	//	@ManyToOne(optional = false)
	//	public Folder getFolder() {
	//		return this.folder;
	//	}
	//
	//	public void setFolder(final Folder folder) {
	//		this.folder = folder;
	//	}

	//	@NotNull
	//	@Valid
	//	@ManyToOne(optional = false)
	//	public Actor getSender() {
	//		return this.sender;
	//	}
	//
	//	public void setSender(final Actor sender) {
	//		this.sender = sender;
	//	}
	//
	//	@NotNull
	//	@Valid
	//	@ManyToOne(optional = false)
	//	public Actor getRecipient() {
	//		return this.recipient;
	//	}
	//
	//	public void setRecipient(final Actor recipient) {
	//		this.recipient = recipient;
	//	}

}
