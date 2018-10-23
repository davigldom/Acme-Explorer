package controllers.admin;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ActorService;
import services.AdministratorService;
import services.FolderService;
import services.MessageService;
import services.SystemConfigService;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message/administrator")
public class MessageAdministratorController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private SystemConfigService systemConfigService;

	// Constructors -----------------------------------------------

	public MessageAdministratorController() {
		super();
	}

	// Notification ---------------------------------------------------

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ModelAndView createNotification() {
		ModelAndView result;
		final Message notification;

		notification = this.messageService.create();
		result = this.createEditModelAndView(notification);

		return result;
	}

	@RequestMapping(value = "/notification", method = RequestMethod.POST, params = "broadcast")
	public ModelAndView broadcast(@ModelAttribute("notification") @Valid final Message notification,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(notification);
		else
			try {
				final List<Actor> allActors = new ArrayList<Actor>(
						this.actorService.findAll());
				final Administrator admin = this.administratorService
						.findByPrincipal();
				allActors.remove(admin);

				// First we look for spamwords in the message
				final List<String> spamWordsList = new ArrayList<String>(
						this.systemConfigService.findConfig().getSpamWords());
				boolean isSpam = false;
				for (int i = 0; i < spamWordsList.size(); i++)
					if (notification.getBody().toLowerCase()
							.contains(spamWordsList.get(i).toLowerCase())
							|| notification
									.getSubject()
									.toLowerCase()
									.contains(
											spamWordsList.get(i).toLowerCase())) {
						isSpam = true;
						break;
					}

				// Then, we send the message to every actor
				for (int i = 0; i < allActors.size(); i++) {
					final List<Actor> actors = new ArrayList<Actor>();
					actors.add(admin);
					actors.add(allActors.get(i));
					notification.setActors(actors);
					this.messageService.save(notification, true, isSpam);
				}
				final Folder notificationBox = this.folderService
						.getNotificationBox(admin);
				result = new ModelAndView("redirect:/folder/list.do?folderId="
						+ notificationBox.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(notification,
						"ourMessage.commit.error");
			}

		return result;
	}

	// Ancillary methods -------------------------------------------

	protected ModelAndView createEditModelAndView(final Message notification) {
		ModelAndView result;

		result = this.createEditModelAndView(notification, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message notification,
			final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("message/notification");
		result.addObject("notification", notification);

		result.addObject("message", messageCode);

		return result;
	}

}
