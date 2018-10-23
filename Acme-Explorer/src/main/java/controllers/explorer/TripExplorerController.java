
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.FinderService;
import services.LegalTextService;
import services.RangerService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.LegalText;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;


	// Constructors -----------------------------------------------------------

	public TripExplorerController() {
		super();
	}

	@RequestMapping(value = "/explorer/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final int finderId) {
		ModelAndView result;
		final Finder finder = this.finderService.findOne(finderId);

		final Collection<Trip> trips = finder.getTrips();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/explorer/search.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String message) {
		ModelAndView result;

		final Collection<Ranger> rangers = this.rangerService.findAll();
		final Collection<LegalText> legalTexts = this.legalTextService.findAllFinal();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("trip/edit");
		result.addObject("rangers", rangers);
		result.addObject("legalTexts", legalTexts);
		result.addObject("categories", categories);
		result.addObject("trip", trip);
		result.addObject("message", message);

		return result;
	}
}
