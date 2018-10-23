
package services;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Managed repository------------------------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;


	// Supporting services-----------------------------------------------------------

	// CRUD methods ---------------------------------------------------
	public CreditCard create() {
		CreditCard result;
		result = new CreditCard();
		return result;
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);

		return result;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.isTrue(creditCard.getId() == 0);

		creditCard.setNumber(creditCard.getNumber().replaceAll("\\s", ""));
		Assert.isTrue(!this.creditCardRepository.findAllNumbers().contains(creditCard.getNumber()));

		final LocalDate now = LocalDate.now();
		if (2000 + creditCard.getExpirationYear() == now.getYear())
			Assert.isTrue(creditCard.getExpirationMonth() > now.getMonthOfYear());
		else if (2000 + creditCard.getExpirationYear() < now.getYear())
			Assert.isTrue(false);

		if (creditCard.getSponsor() == null)
			Assert.notNull(creditCard.getExplorer());
		else
			Assert.isNull(creditCard.getExplorer());

		CreditCard result;
		result = this.creditCardRepository.save(creditCard);
		return result;
	}

	//Credit cards cant't be modified
	//	public void delete(final CreditCard creditCard) {
	//		Assert.notNull(creditCard);
	//		Assert.isTrue(creditCard.getId() != 0);
	//
	//		this.creditCardRepository.delete(creditCard);
	//	}

	//Other business methods

	public Collection<CreditCard> findByExplorerId(final int explorerId) {
		final Collection<CreditCard> creditCards = this.creditCardRepository.findByExplorerId(explorerId);

		return creditCards;
	}

	public CreditCard findBySponsorId(final int sponsorId) {
		final CreditCard creditCard = this.creditCardRepository.findBySponsorId(sponsorId);

		return creditCard;
	}
}
