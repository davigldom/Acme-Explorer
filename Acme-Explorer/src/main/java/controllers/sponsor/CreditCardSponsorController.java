
package controllers.sponsor;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CreditCardService;
import services.SponsorService;
import domain.CreditCard;
import domain.Explorer;
import domain.Sponsor;

@Controller
@RequestMapping("/credit-card")
public class CreditCardSponsorController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private SponsorService		sponsorService;


	// Constructors -----------------------------------------------

	public CreditCardSponsorController() {
		super();
	}

	// Listing ----------------------------------------------------

	@RequestMapping(value = "/sponsor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<CreditCard> creditCards = new HashSet<CreditCard>();
		final Sponsor sponsor = this.sponsorService.findByPrincipal();

		creditCards.add(this.creditCardService.findBySponsorId(sponsor.getId()));

		result = new ModelAndView("credit-card/list");
		result.addObject("creditCards", creditCards);
		result.addObject("requestURI", "credit-card/sponsor/list.do");

		return result;
	}

	// Ancillary methods -------------------------------------------

	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String messageCode) {
		ModelAndView result;
		final String brandName = creditCard.getBrandName();
		final String holderName = creditCard.getHolderName();
		final String number = creditCard.getNumber();
		final int cvv = creditCard.getCVV();
		final int expirationMonth = creditCard.getExpirationMonth();
		final int expirationYear = creditCard.getExpirationYear();
		final Explorer explorer = creditCard.getExplorer();

		result = new ModelAndView("credit-card/create");

		result.addObject("creditCard", creditCard);
		result.addObject("brandName", brandName);
		result.addObject("holderName", holderName);
		result.addObject("number", number);
		result.addObject("cvv", cvv);
		result.addObject("expirationMonth", expirationMonth);
		result.addObject("expirationYear", expirationYear);
		result.addObject("explorer", explorer);
		result.addObject("message", messageCode);

		return result;
	}

}
