
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditionRepository;
import domain.Administrator;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@Service
@Transactional
public class AuditionService {

	//Managed Repository-------------------------------------------

	@Autowired
	private AuditionRepository		auditionRepository;

	// Supporting services-----------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AuditorService			auditorService;


	// CRUD methods ---------------------------------------------------
	public Audition create(final Trip trip, final Auditor auditor) {
		Assert.notNull(trip);
		Assert.notNull(auditor);

		final Audition result = new Audition();
		final Collection<String> attachments = new HashSet<String>();
		final boolean draft = true;

		result.setTrip(trip);
		result.setAuditor(auditor);
		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setAttachments(attachments);
		result.setDraft(draft);

		trip.getAuditions().add(result);
		auditor.getAuditions().add(result);

		return result;
	}

	public Collection<Audition> findAll() {
		Collection<Audition> result;

		result = this.auditionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Audition findOne(final int auditionId) {
		Audition result;

		result = this.auditionRepository.findOne(auditionId);
		Assert.notNull(result);

		return result;
	}

	public Audition save(final Audition audition) {
		Assert.notNull(audition);

		//		if (audition.getId() != 0)
		//			Assert.isTrue(audition.isDraft());

		this.checkPrincipal(audition);

		audition.setMoment(new Date(System.currentTimeMillis() - 1000));
		Audition result = null;

		result = this.auditionRepository.save(audition);

		return result;
	}

	public void delete(final Audition audition) {
		Assert.notNull(audition);
		Assert.isTrue(audition.getId() != 0);
		Assert.isTrue(audition.isDraft());

		audition.getTrip().getAuditions().remove(audition);
		audition.getAuditor().getAuditions().remove(audition);
		this.auditionRepository.delete(audition);
	}

	// Other business methods--------------------------------------
	public Audition findOneToEdit(final int auditionId) {
		Audition result;
		result = this.findOne(auditionId);
		Assert.notNull(result);
		this.checkPrincipal(result);
		return result;
	}

	public void checkPrincipal(final Audition audition) {
		Assert.notNull(audition);
		final Auditor principal = this.auditorService.findByPrincipal();
		Assert.isTrue(principal.equals(audition.getAuditor()));
	}

	//Requirement 30.2
	public Collection<Audition> findByTripId(final int tripId) {
		Collection<Audition> result;
		result = this.auditionRepository.findByTripId(tripId);
		return result;
	}

	//Requirement 33.2
	public Collection<Audition> findByAuditorId(final int auditorId) {
		Collection<Audition> result;
		result = this.auditionRepository.findByAuditorId(auditorId);
		return result;
	}

	//Requirement 35.4
	public Double averageAuditsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.auditionRepository.averageAuditsPerTrip();
		return result;
	}

	public Double minimumAuditsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.auditionRepository.minimumAuditsPerTrip();
		return result;
	}

	public Double maximumAuditsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.auditionRepository.maximumAuditsPerTrip();
		return result;
	}

	public Double standardDerivationAuditsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.auditionRepository.standardDerivationAuditsPerTrip();
		return result;
	}

}
