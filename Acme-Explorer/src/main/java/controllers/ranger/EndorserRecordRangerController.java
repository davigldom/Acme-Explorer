
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
import services.EndorserRecordService;
import services.RangerService;
import services.SystemConfigService;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.Ranger;

@Controller
@RequestMapping("/endorserRecord/ranger")
public class EndorserRecordRangerController extends AbstractController{

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService curriculumService;

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private SystemConfigService		systemConfigService;
	
	@Autowired
	private RangerService rangerService;


	// Display ----------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int curriculumId) {
		ModelAndView result;
		EndorserRecord endorserRecord = this.endorserRecordService.create();
		result = this.createEditModelAndView(endorserRecord, curriculumId);
		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId, @RequestParam final int curriculumId) {
		ModelAndView result;
		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.findOneToEdit(endorserRecordId);
		Assert.notNull(endorserRecord);
		result = this.createEditModelAndView(endorserRecord, curriculumId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;
		Ranger r = this.rangerService.findByPrincipal();
		String[] comments = endorserRecord.getComments().toLowerCase().split(" ");

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord, curriculumId);
		else
			try {
				Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.endorserRecordService.save(endorserRecord, curriculum);
				
				for(String s : this.systemConfigService.findConfig().getSpamWords()){
					for(String c : comments){
						if(c.contains(s)){
							r.setSuspicious(true);
							this.rangerService.save(r);
						}
					}
				}
				
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId="+curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, curriculumId, "endorserRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete( final EndorserRecord endorserRecord, final BindingResult binding, @RequestParam final int curriculumId) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord, curriculumId);
		else
			try {
				Curriculum curriculum = this.curriculumService.findOneToEdit(curriculumId);
				this.endorserRecordService.delete(endorserRecord, curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId="+curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, curriculumId, "endorserRecord.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final int curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, curriculumId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final int curriculumId, final String message) {

		ModelAndView result;
		result = new ModelAndView("endorserRecord/ranger/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("message", message);
		result.addObject("defaultCountry", this.systemConfigService.findConfig().getCountryCode());

		return result;
	}

}
