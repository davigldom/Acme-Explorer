
package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CreditCardService;
import services.SponsorService;
import services.SponsorshipService;
import services.TripService;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/sponsorship")
public class SponsorshipSponsorController extends AbstractController{

	// Services ---------------------------------------------------

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private TripService			tripService;
	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------

	public SponsorshipSponsorController() {
		super();
	}

	// Listing ----------------------------------------------------

	// Creation ---------------------------------------------------

	@RequestMapping(value = "/sponsor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		final ModelAndView result;
		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		final CreditCard creditCard = this.creditCardService.findBySponsorId(sponsor.getId());
		final Trip trip = this.tripService.findOne(tripId);
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.create(sponsor, creditCard, trip);

		result = this.createEditModelAndView(sponsorship);

		return result;
	}

	// Edition ----------------------------------------------------
	//Can't be edited nor deleted

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(sponsorship);
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:/trip/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(sponsorship, "sponsorship.commit.error");
			}

		return result;
	}

	// Ancillary methods -------------------------------------------

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.createEditModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;
		final Sponsor sponsor = sponsorship.getSponsor();
		final Trip trip = sponsorship.getTrip();
		final CreditCard creditCard = sponsorship.getCreditCard();
		final String banner = sponsorship.getBanner();
		final String link = sponsorship.getLink();

		result = new ModelAndView("sponsorship/create");

		result.addObject("sponsorship", sponsorship);
		result.addObject("sponsor", sponsor);
		result.addObject("trip", trip);
		result.addObject("creditCard", creditCard);
		result.addObject("banner", banner);
		result.addObject("link", link);

		return result;
	}
}
