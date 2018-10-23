
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialIdentity extends DomainEntity {

	//Attributes

	private String	nick;
	private String	nameOfSocialNetwork;
	private String	link;
	private String	photo;


	@NotBlank
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	public String getNameOfSocialNetwork() {
		return this.nameOfSocialNetwork;
	}

	public void setNameOfSocialNetwork(final String nameOfSocialNetwork) {
		this.nameOfSocialNetwork = nameOfSocialNetwork;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}


	//Relationships

}
