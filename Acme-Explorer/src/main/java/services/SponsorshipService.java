
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository

	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;


	// Supporting Services

	//@Autowired
	//private SponsorService sponsorService;
	//@Autowired
	//private TripService tripService;

	// Constructors

	public SponsorshipService() {
		super();
	}

	// Simple CRUD methods

	public Sponsorship create(final Sponsor sponsor, final CreditCard creditCard, final Trip trip) {
		Assert.notNull(sponsor);
		Assert.notNull(creditCard);
		Assert.notNull(trip);

		final Sponsorship result = new Sponsorship();

		result.setTrip(trip);
		result.setCreditCard(creditCard);
		result.setSponsor(sponsor);

		sponsor.getSponsorships().add(result);
		trip.getSponsorships().add(result);

		return result;
	}

	public Sponsorship findOne(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);
		Sponsorship result;
		result = this.sponsorshipRepository.findOne(sponsorshipId);
		return result;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> result;
		result = this.sponsorshipRepository.findAll();
		Assert.notNull(result); //No puedo obtener una colección nula
		return result;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		Sponsorship result;

		//final Sponsor sponsor = sponsorship.getSponsor();
		//final Trip trip = sponsorship.getTrip();
		result = this.sponsorshipRepository.save(sponsorship);

		//sponsor.getSponsorships().remove(sponsorship);
		//trip.getSponsorships().remove(sponsorship);

		//sponsor.getSponsorships().add(result);
		//this.sponsorService.save(sponsor);
		//trip.getSponsorships().add(result);
		//this.tripService.save(sponsor);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		//final Sponsor sponsor = sponsorship.getSponsor();
		//final Trip trip = sponsorship.getTrip();
		Assert.notNull(sponsorship);
		Assert.isTrue(sponsorship.getId() != 0);
		Assert.isTrue(this.sponsorshipRepository.exists(sponsorship.getId()));
		//sponsor.getSponsorships().remove(result);
		//this.sponsorService.save(sponsor);
		//trip.getSponsorships().remove(result);
		//this.tripService.save(sponsor);

		this.sponsorshipRepository.delete(sponsorship);
	}

	public Collection<Sponsorship> findBySponsorPrincipal() {
		Collection<Sponsorship> result;
		final Sponsor principal = this.sponsorService.findByPrincipal();
		result = this.sponsorshipRepository.findBySponsorId(principal.getId());
		return result;
	}
}
