
package controllers.explorer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EmergencyContactService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.EmergencyContact;

@Controller
@RequestMapping("/emergency-contact/explorer")
public class EmergencyContactExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private EmergencyContactService	emergencyContactService;

	@Autowired
	private SystemConfigService		systemConfigService;


	// Constructors -----------------------------------------------------------

	public EmergencyContactExplorerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int explorerId) {
		ModelAndView result;
		Collection<EmergencyContact> emergencyContacts;

		emergencyContacts = this.emergencyContactService.findByExplorerId(explorerId);
		result = new ModelAndView("emergency-contact/list");
		result.addObject("emergencyContacts", emergencyContacts);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EmergencyContact emergencyContact;

		emergencyContact = this.emergencyContactService.create();
		result = this.createEditModelAndView(emergencyContact);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int contactId) {
		ModelAndView result;
		EmergencyContact emergencyContact;

		emergencyContact = this.emergencyContactService.findOne(contactId);
		Assert.notNull(emergencyContact);
		result = this.createEditModelAndView(emergencyContact);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EmergencyContact emergencyContact, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(emergencyContact);
		else
			try {
				this.emergencyContactService.save(emergencyContact);
				result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(emergencyContact, "emergencyContact.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EmergencyContact emergencyContact, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(emergencyContact);
		else
			try {
				this.emergencyContactService.delete(emergencyContact);
				result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(emergencyContact, "emergencyContact.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EmergencyContact emergencyContact) {
		ModelAndView result;

		result = this.createEditModelAndView(emergencyContact, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EmergencyContact emergencyContact, final String message) {
		ModelAndView result;

		result = new ModelAndView("emergency-contact/edit");
		result.addObject("emergencyContact", emergencyContact);
		result.addObject("message", message);
		result.addObject("defaultCountry", this.systemConfigService.findConfig().getCountryCode());

		return result;
	}
}
