
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.FinderService;
import controllers.AbstractController;
import domain.Finder;

@Controller
@RequestMapping("/finder/explorer")
public class FinderExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FinderService	finderService;

	@Autowired
	private ExplorerService	explorerService;


	// Constructors -----------------------------------------------------------

	public FinderExplorerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create() {
	//		ModelAndView result;
	//		SocialIdentity socialIdentity;
	//
	//		socialIdentity = this.socialIdentityService.create();
	//		result = this.createEditModelAndView(socialIdentity);
	//
	//		return result;
	//	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Finder finder;

		finder = this.finderService.getFinderByExplorerId(this.explorerService.findByPrincipal().getId());
		Assert.notNull(finder);
		result = this.createEditModelAndView(finder);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {
				this.finderService.save(finder);
				result = new ModelAndView("redirect:/trip/explorer/search.do?finderId=" + finder.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String message) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", message);

		return result;
	}
}
