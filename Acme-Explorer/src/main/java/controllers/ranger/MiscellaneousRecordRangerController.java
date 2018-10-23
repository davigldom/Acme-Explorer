
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
import services.MiscellaneousRecordService;
import services.RangerService;
import services.SystemConfigService;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Controller
@RequestMapping("/miscellaneousRecord/ranger")
public class MiscellaneousRecordRangerController extends AbstractController{

	// Services ---------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private SystemConfigService		systemConfigService;
	
	@Autowired
	private RangerService rangerService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord, curriculumId);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId, final int curriculumId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOneToEdit(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);
		result = this.createEditModelAndView(miscellaneousRecord, curriculumId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, final int curriculumId) {

		ModelAndView result;
		Ranger r = this.rangerService.findByPrincipal();
		String[] comments = miscellaneousRecord.getComments().toLowerCase().split(" ");
		

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
				this.miscellaneousRecordService.save(miscellaneousRecord, curriculum);
				
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
				result = this.createEditModelAndView(miscellaneousRecord, curriculumId, "miscellaneousRecord.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete( final MiscellaneousRecord miscellaneousRecord, final BindingResult binding, final int curriculumId) {

		ModelAndView result;
		

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord, curriculumId);
		else
			try {
				final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
				this.miscellaneousRecordService.delete(miscellaneousRecord, curriculum);
				result = new ModelAndView("redirect:/curriculum/display.do?curriculumId="+curriculumId);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, curriculumId, "miscellaneousRecord.commit.error");
			}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final int curriculumId) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, curriculumId, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final int curriculumId, final String message) {

		ModelAndView result;
		result = new ModelAndView("miscellaneousRecord/ranger/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("curriculumId", curriculumId);
		result.addObject("message", message);

		return result;
	}

}
