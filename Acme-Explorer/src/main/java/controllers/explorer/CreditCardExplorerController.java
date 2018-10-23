
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CreditCardService;
import services.ExplorerService;
import domain.CreditCard;
import domain.Explorer;

@Controller
@RequestMapping("/credit-card")
public class CreditCardExplorerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CreditCardService	creditCardService;
	@Autowired
	private ExplorerService		explorerService;


	// Constructors -----------------------------------------------

	public CreditCardExplorerController() {
		super();
	}

	// Listing ----------------------------------------------------

	@RequestMapping(value = "/explorer/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<CreditCard> creditCards;
		final Explorer explorer = this.explorerService.findByPrincipal();

		creditCards = this.creditCardService.findByExplorerId(explorer.getId());

		result = new ModelAndView("credit-card/list");
		result.addObject("creditCards", creditCards);
		result.addObject("requestURI", "credit-card/explorer/list.do");

		return result;
	}

	// Creation ---------------------------------------------------

	@RequestMapping(value = "/explorer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final CreditCard creditCard = this.creditCardService.create();

		result = this.createEditModelAndView(creditCard);

		return result;
	}

	// Edition ---------------------------------------------------
	//Can't edit

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CreditCard creditCard, final BindingResult binding) {
		ModelAndView result = null;
		final Explorer explorer = this.explorerService.findByPrincipal();
		final LocalDate now = LocalDate.now();

		creditCard.setExplorer(explorer);

		if (binding.hasErrors())
			result = this.createEditModelAndView(creditCard);
		else if (2000 + creditCard.getExpirationYear() == now.getYear()) {
			if (creditCard.getExpirationMonth() < now.getMonthOfYear())
				result = this.createEditModelAndView(creditCard, "credit-card.expired");
		} else if (2000 + creditCard.getExpirationYear() < now.getYear())
			result = this.createEditModelAndView(creditCard, "credit-card.expired");
		else
			try {
				this.creditCardService.save(creditCard);
				result = new ModelAndView("redirect:/credit-card/explorer/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(creditCard, "credit-card.commit.error");
			}
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
