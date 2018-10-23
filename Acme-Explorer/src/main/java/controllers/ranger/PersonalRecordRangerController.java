
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

import controllers.AbstractController;

import services.CurriculumService;
import services.PersonalRecordService;
import services.SystemConfigService;
import domain.Curriculum;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/ranger")
public class PersonalRecordRangerController extends AbstractController {

	// Services ---------------------------------------------------

	//	@Autowired
	//	private CurriculumService		curriculumService;

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private SystemConfigService		systemConfigService;


	// Create new personal Record (and Curriculum) ----------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView createNew() {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();
		result = this.createNewModelAndView(personalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveNew")
	public ModelAndView saveNew(@Valid final PersonalRecord personalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createNewModelAndView(personalRecord);
		else
			try {
				final PersonalRecord newPersonalRecord = this.personalRecordService.save(personalRecord);
				final Curriculum curriculum;
				curriculum = this.curriculumService.create(newPersonalRecord);
				final Curriculum curriculumSaved = this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumSaved.getId());
			} catch (final Throwable oops) {
				result = this.createNewModelAndView(personalRecord, "personalRecord.commit.error");
			}

		return result;
	}
	//	@RequestMapping(value = "/create", method = RequestMethod.GET)
	//	public ModelAndView create(@RequestParam final int curriculumId) {
	//		ModelAndView result;
	//		PersonalRecord personalRecord;
	//
	//		personalRecord = this.personalRecordService.create();
	//		result = this.createEditModelAndView(personalRecord, curriculumId);
	//
	//		return result;
	//	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int personalRecordId, @RequestParam final int curriculumId) {
		ModelAndView result;
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.findOneToEdit(personalRecordId);
		Assert.notNull(personalRecord);
		result = new ModelAndView("personalRecord/ranger/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("curriculumId", curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord, curriculumId);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId=" + curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error", curriculumId);
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final int curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null, curriculumId);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String message, final int curriculumId) {

		ModelAndView result;
		result = new ModelAndView("personalRecord/ranger/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("defaultCountry", this.systemConfigService.findConfig().getCountryCode());
		result.addObject("message", message);

		return result;
	}

	//Methods to create a new curriculum
	protected ModelAndView createNewModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createNewModelAndView(personalRecord, null);

		return result;
	}

	protected ModelAndView createNewModelAndView(final PersonalRecord personalRecord, final String message) {

		ModelAndView result;
		result = new ModelAndView("personalRecord/ranger/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("defaultCountry", this.systemConfigService.findConfig().getCountryCode());
		result.addObject("message", message);

		return result;
	}

}
