
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.FolderService;
import services.MessageService;
import services.SystemConfigService;
import domain.Actor;
import domain.Folder;
import domain.Manager;
import domain.Message;
import domain.PriorityLevel;
import domain.Ranger;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------

	public MessageController() {
		super();
	}

	// Display
	// ---------------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageId) {
		ModelAndView result;
		Message ourMessage;

		ourMessage = this.messageService.findOneToEdit(messageId);
		Assert.notNull(ourMessage);

		final Actor sender = this.messageService.findActors(messageId).get(0);
		final Actor recipient = this.messageService.findActors(messageId).get(1);
		final int rootId = this.folderService.findByMessage(ourMessage).getId();
		final PriorityLevel high = PriorityLevel.HIGH;
		final PriorityLevel med = PriorityLevel.MED;
		final PriorityLevel low = PriorityLevel.LOW;

		result = new ModelAndView("message/display");
		result.addObject("ourMessage", ourMessage);
		result.addObject("sender", sender);
		result.addObject("recipient", recipient);
		result.addObject("rootId", rootId);
		result.addObject("high", high);
		result.addObject("med", med);
		result.addObject("low", low);

		return result;
	}

	// Creation ---------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Message ourMessage;

		ourMessage = this.messageService.create();
		result = this.createEditModelAndView(ourMessage);

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		ModelAndView result;
		Message ourMessage;

		ourMessage = this.messageService.findOneToEdit(messageId);
		Assert.notNull(ourMessage);
		result = this.createEditModelAndView(ourMessage);

		return result;
	}

	@RequestMapping(value = "/change-folder", method = RequestMethod.GET)
	public ModelAndView changeFolder(@RequestParam final int messageId) {
		ModelAndView result;
		result = new ModelAndView("message/change-folder");
		final Collection<Folder> folders = this.folderService.findByPrincipal();
		result.addObject("folders", folders);
		result.addObject("messageId", messageId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("ourMessage") @Valid final Message ourMessage, final BindingResult binding) {
		ModelAndView result;

		final Message message = ourMessage;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				// First we look for spamwords.
				final List<String> spamWordsList = new ArrayList<String>(this.systemConfigService.findConfig().getSpamWords());
				boolean isSpam = false;
				for (int i = 0; i < spamWordsList.size(); i++)
					if (message.getBody().toLowerCase().contains(spamWordsList.get(i).toLowerCase()) || message.getSubject().toLowerCase().contains(spamWordsList.get(i).toLowerCase())) {
						// Found a spamword, so we mark the message as spam.
						isSpam = true;
						// If the sender is a ranger or a manager, we must mark
						// him or her as suspicious.
						if (message.getActors().get(0).getUserAccount().getAuthorities().contains(Authority.RANGER)) {
							final Ranger sender = (Ranger) message.getActors().get(0);
							sender.setSuspicious(true);
						} else if (message.getActors().get(0).getUserAccount().getAuthorities().contains(Authority.MANAGER)) {
							final Manager sender = (Manager) message.getActors().get(0);
							sender.setSuspicious(true);
						}
						break;
					}
				final Message messageSaved = this.messageService.save(message, false, isSpam);
				result = new ModelAndView("redirect:display.do?messageId=" + messageSaved.getId());
			} catch (final ObjectOptimisticLockingFailureException e) {
				result = this.createEditModelAndView(message, "ourMessage.commit.test");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "ourMessage.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Message ourMessage, final BindingResult binding) {
		ModelAndView result;

		try {
			ourMessage.setActors(this.messageService.findActors(ourMessage.getId()));
			this.messageService.delete(ourMessage);
			result = new ModelAndView("redirect:/folder/list.do?folderId=0");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(ourMessage, "ourMessage.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "changeFolder")
	public ModelAndView changeFolder(@RequestParam final int messageId, @RequestParam final int folderId) {
		ModelAndView result;

		try {
			final Message message = this.messageService.findOneToEdit(messageId);
			final Folder folder = this.folderService.findOneToList(folderId);
			final List<Actor> actors = this.messageService.findActors(messageId);
			message.setActors(actors);
			this.messageService.changeFolder2(message, folder);
			result = new ModelAndView("redirect:/folder/list.do?folderId="
					+ folderId);
		} catch (final Throwable oops) {
			result = new ModelAndView("message/change-folder");
			final Collection<Folder> folders = this.folderService.findByPrincipal();
			result.addObject("folders", folders);
			result.addObject("messageId", messageId);
			result.addObject("message", "ourMessage.commit.error");
		}

		return result;
	}

	// Ancillary methods -------------------------------------------

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;
		final Collection<Actor> recipients;

		recipients = this.actorService.findAll();

		
		if (message.getId() == 0)
			result = new ModelAndView("message/create");
		else
			result = new ModelAndView("message/edit");
		result.addObject("ourMessage", message);
		result.addObject("recipients", recipients);
		result.addObject("message", messageCode);

		return result;
	}

}
