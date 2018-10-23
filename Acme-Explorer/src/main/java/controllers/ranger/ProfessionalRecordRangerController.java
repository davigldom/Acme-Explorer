
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
import services.ProfessionalRecordService;
import services.RangerService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.ProfessionalRecord;
import domain.Ranger;

@Controller
@RequestMapping("/professionalRecord/ranger")
public class ProfessionalRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private SystemConfigService			systemConfigService;

	@Autowired
	private RangerService				rangerService;


	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		final ModelAndView result;
		final ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordService.create();

		result = this.createEditModelAndView(professionalRecord, curriculumId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId, final int curriculumId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOneToEdit(professionalRecordId);
		Assert.notNull(professionalRecord);
		result = this.createEditModelAndView(professionalRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding, final int curriculumId) {

		ModelAndView result;
		final Ranger r = this.rangerService.findByPrincipal();
		final String[] comments = professionalRecord.getComments().toLowerCase().split(" ");

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.professionalRecordService.save(professionalRecord, curriculum);

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String c : comments)
						if (c.contains(s)) {
							r.setSuspicious(true);
							this.rangerService.save(r);
						}

				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculum.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, curriculumId, "professionalRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding, final int curriculumId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.professionalRecordService.delete(professionalRecord, curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculum.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, curriculumId, "professionalRecord.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final Integer curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final Integer curriculumId, final String message) {

		ModelAndView result;
		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("message", message);

		return result;
	}

}
