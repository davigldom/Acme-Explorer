
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.TripService;
import domain.Category;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService		tripService;

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public TripController() {
		super();
	}

	//Display ---------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		boolean isOver = false;
		boolean canApplyDate = false;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		result = new ModelAndView("trip/display");
		Sponsorship randomSponsorship;
		final int numberOfSponsorships = trip.getSponsorships().size();
		final Random rand = new Random();

		if (numberOfSponsorships != 0) {
			final int random = rand.nextInt(numberOfSponsorships);
			randomSponsorship = (Sponsorship) trip.getSponsorships().toArray()[random];
		} else
			randomSponsorship = null;

		if (trip.getEndDate().before(new Date(System.currentTimeMillis())))
			isOver = true;

		if (trip.getStartDate().after(new Date(System.currentTimeMillis())))
			canApplyDate = true;

		result.addObject("trip", trip);
		result.addObject("randomSponsorship", randomSponsorship);
		result.addObject("isOver", isOver);
		result.addObject("canApplyDate", canApplyDate);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findPublished();
		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");

		return result;
	}

	@RequestMapping(value = "/search-word", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam final String keyword) {
		ModelAndView result;

		final Collection<Trip> trips = this.tripService.findByKeyword(keyword);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/search-word.do");

		return result;
	}

	@RequestMapping(value = "/search-category", method = RequestMethod.POST, params = "category")
	public ModelAndView searchByCategory(@RequestParam final int categoryId) {
		ModelAndView result;
		final Category category = this.categoryService.findOne(categoryId);
		final Collection<Trip> trips = this.tripService.findByCategory(category);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/search-category.do");

		return result;
	}

}
