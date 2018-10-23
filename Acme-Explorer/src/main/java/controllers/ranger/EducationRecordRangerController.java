
package controllers.ranger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CurriculumService;
import services.EducationRecordService;
import services.RangerService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@Controller
@RequestMapping("/educationRecord/ranger")
public class EducationRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private SystemConfigService		systemConfigService;

	@Autowired
	private RangerService			rangerService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		final EducationRecord educationRecord;
		educationRecord = this.educationRecordService.create();

		result = this.createEditModelAndView(educationRecord, curriculumId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId, @RequestParam final int curriculumId) {
		ModelAndView result;
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.findOneToEdit(educationRecordId);
		Assert.notNull(educationRecord);
		result = this.createEditModelAndView(educationRecord, curriculumId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;
		final Ranger r = this.rangerService.findByPrincipal();
		final String[] comments = educationRecord.getComments().toLowerCase().split(" ");

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.educationRecordService.save(educationRecord, curriculum);

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String c : comments)
						if (c.contains(s)) {
							r.setSuspicious(true);
							this.rangerService.save(r);
						}

				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, curriculumId, "educationRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EducationRecord educationRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(educationRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.educationRecordService.delete(educationRecord, curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(educationRecord, curriculumId, "educationRecord.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final int curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final int curriculumId, final String message) {

		ModelAndView result;
		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message", message);
		result.addObject("curriculumId", curriculumId);

		return result;
	}

}
