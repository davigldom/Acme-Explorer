
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.LegalTextRepository;
import domain.Administrator;
import domain.LegalText;
import domain.Trip;

@Service
@Transactional
public class LegalTextService {

	// Managed repository

	@Autowired
	private LegalTextRepository		legalTextRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public LegalTextService() {
		super();
	}

	// Simple CRUD methods

	public LegalText create() {
		LegalText result;

		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		result = new LegalText();
		result.setMoment(new Date(System.currentTimeMillis()));
		result.setTrips(new HashSet<Trip>());
		result.setDefinitive(false);

		return result;
	}

	public LegalText findOne(final int legalTextId) {
		LegalText result;

		result = this.legalTextRepository.findOne(legalTextId);
		Assert.notNull(result);

		return result;
	}

	public Collection<LegalText> findAll() {
		Collection<LegalText> result;

		result = this.legalTextRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public LegalText save(final LegalText legalText) {
		Assert.notNull(legalText);

		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		LegalText result;
		legalText.setMoment(new Date(System.currentTimeMillis() - 1000)); // Cuando se valida la fecha y es una fecha pasada
		result = this.legalTextRepository.save(legalText);
		return result;
	}

	public void delete(final LegalText legalText) {
		Assert.isTrue(legalText.getId() != 0);
		Assert.isTrue(!legalText.isDefinitive());

		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		this.legalTextRepository.delete(legalText);
	}

	// Other business methods

	public LegalText findOneToEdit(final int legalTextId) {
		LegalText result;
		result = this.findOne(legalTextId);
		Assert.notNull(result);

		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Assert.isTrue(!result.isDefinitive());

		return result;
	}

	public Collection<Object[]> timesLegalTextReferenced() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Collection<Object[]> result;
		result = this.legalTextRepository.timesLegalTextReferenced();
		return result;
	}

	public Collection<LegalText> findAllFinal() {
		Collection<LegalText> legalTexts;

		legalTexts = this.legalTextRepository.findAllFinal();

		return legalTexts;
	}

}
