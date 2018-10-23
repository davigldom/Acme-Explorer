
package controllers.ranger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CurriculumService;
import domain.Curriculum;

@Controller
@RequestMapping("/curriculum")
public class CurriculumRangerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;


	// Delete --------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult binding) {

		ModelAndView result;

		try {

			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/actor/display.do?actorId=" + curriculum.getRanger().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("curriculum/display");
			result.addObject("message", "educationRecord.commit.error");
			result.addObject("curriculum", curriculum);
			result.addObject("requestURI", "curriculum/display.do");
		}

		return result;
	}

}
