
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Folder;
import domain.SocialIdentity;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository

	@Autowired
	private SponsorRepository	sponsorRepository;


	// Supporting Services

	// Constructors

	public SponsorService() {
		super();
	}

	// Simple CRUD methods

	public Sponsor create() {
		Sponsor result;
		final Collection<Sponsorship> sponsorships = new HashSet<Sponsorship>();
		final Collection<SocialIdentity> socialIdentities = new ArrayList<SocialIdentity>();
		final Collection<Folder> folders = new ArrayList<Folder>();
		result = new Sponsor();
		result.setSponsorships(sponsorships);
		result.setSocialIdentities(socialIdentities);
		result.setFolders(folders);
		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Assert.isTrue(sponsorId != 0);
		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;
		result = this.sponsorRepository.findAll();
		Assert.notNull(result); //No puedo obtener una colección nula
		return result;
	}

	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(this.findByPrincipal().equals(sponsor));
		Sponsor result;
		result = this.sponsorRepository.save(sponsor);
		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		Assert.isTrue(this.sponsorRepository.exists(sponsor.getId()));
		this.sponsorRepository.delete(sponsor);
	}

	//OTHER METHODS

	public Sponsor findByPrincipal() {
		Sponsor result;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);

		return result;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor result;
		result = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}
}
