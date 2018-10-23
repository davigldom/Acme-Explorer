
package controllers.explorer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.SurvivalClassService;
import domain.SurvivalClass;

@Controller
@RequestMapping("/survival-class/explorer")
public class SurvivalClassExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassExplorerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;
		Collection<SurvivalClass> enrolledClasses;

		survivalClasses = this.survivalClassService.findAvailableToEnrol();
		enrolledClasses = this.survivalClassService.findEnrolledClasses();

		result = new ModelAndView("survival-class/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("enrolledClasses", enrolledClasses);
		result.addObject("requestURI", "survival-class/explorer/list.do");

		return result;
	}

	// Enrol ------------------------------------------------------------------

	@RequestMapping(value = "/enrol", method = RequestMethod.GET)
	public ModelAndView enrol(@RequestParam final int survivalClassId) {
		ModelAndView result;

		try {
			this.survivalClassService.enrol(survivalClassId);
			result = this.list();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/unenrol", method = RequestMethod.GET)
	public ModelAndView unenrol(@RequestParam final int survivalClassId) {
		ModelAndView result;

		try {
			this.survivalClassService.unenrol(survivalClassId);
			result = this.list();
			result.addObject("message", "survivalClass.commit.ok");
		} catch (final Throwable oops) {
			result = this.list();
			result.addObject("message", "survivalClass.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClass, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String message) {
		ModelAndView result;

		result = new ModelAndView("survival-class/enrol");
		result.addObject("survivalClass", survivalClass);
		result.addObject("message", message);

		return result;
	}

}
