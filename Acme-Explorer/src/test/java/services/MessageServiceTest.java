
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Explorer;
import domain.Folder;
import domain.Message;
import domain.PriorityLevel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private FolderService	folderService;


	@Test
	public void testChangeFolder() {
		super.authenticate("explorer1");

		final Explorer sender = this.explorerService.findByPrincipal();
		final Explorer recipient = (Explorer) this.explorerService.findAll().toArray()[1];
		final List<Actor> actors = new ArrayList<>();
		actors.add(sender);
		actors.add(recipient);

		final Folder folder = this.folderService.create();

		folder.setName("New folder");
		Folder folderSaved = this.folderService.save(folder);
		final Message m = this.messageService.create();

		m.setSubject("Message's subject");
		m.setBody("Message's body");
		m.setPriorityLevel(PriorityLevel.HIGH);
		m.setActors(actors);

		final Message messageSaved = this.messageService.save(m, false, false);
		Assert.isTrue(this.folderService.getOutBox(sender).getMessages().contains(messageSaved));

		this.messageService.changeFolder(messageSaved, folderSaved);

		folderSaved = this.folderService.findOne(folderSaved.getId());
		Assert.isTrue(folderSaved.getMessages().contains(messageSaved));

		Assert.isTrue(!this.folderService.getOutBox(sender).getMessages().contains(messageSaved));

		super.authenticate(null);
	}

	@Test
	public void testCreate() {
		super.authenticate("explorer1");

		final Explorer sender = this.explorerService.findByPrincipal();
		final Message m = this.messageService.create();

		Assert.isTrue(m.getActors().get(0).equals(sender));
		Assert.isNull(m.getActors().get(1));
		Assert.isNull(m.getSubject());
		Assert.isNull(m.getBody());
		Assert.isNull(m.getPriorityLevel());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("explorer2");

		final Explorer sender = this.explorerService.findByPrincipal();
		final Explorer recipient = (Explorer) this.explorerService.findAll().toArray()[0];
		final Message m = this.messageService.create();

		m.setSubject("Subject 1");
		m.setBody("I love life");
		m.setPriorityLevel(PriorityLevel.HIGH);
		m.getActors().set(1, recipient);

		final Message messageSaved = this.messageService.save(m, false, false);

		Assert.isTrue(this.folderService.getOutBox(sender).getMessages().contains(messageSaved));
		Assert.isTrue(this.messageService.findAllOfActor(sender).contains(messageSaved));
		super.authenticate(null);

		super.authenticate(recipient.getUserAccount().getUsername());
		final Message received = (Message) this.folderService.getInBox(recipient).getMessages().toArray()[0];
		Assert.isTrue(received.getSubject().equals(messageSaved.getSubject()));
		super.authenticate(null);
	}

	@Test
	public void testSaveSpam() {
		super.authenticate("explorer1");

		final Explorer sender = this.explorerService.findByPrincipal();
		final Explorer recipient = (Explorer) this.explorerService.findAll().toArray()[0];
		//final Folder spamBoxRecipient = this.folderService.getSpamBox(recipient);
		final Message m = this.messageService.create();

		m.setSubject("Subject 1");
		m.setBody("I love sex");
		m.setPriorityLevel(PriorityLevel.HIGH);
		m.getActors().set(1, recipient);

		final Message messageSaved = this.messageService.save(m, false, true);

		Assert.isTrue(this.folderService.getOutBox(sender).getMessages().contains(messageSaved));
		Assert.isTrue(this.messageService.findAllOfActor(sender).contains(messageSaved));
		super.authenticate(null);

		//Now we log as the recipient to make sure that the message is in the spam box.

		super.authenticate(recipient.getUserAccount().getUsername());
		final Message spam = (Message) this.folderService.getSpamBox(recipient).getMessages().toArray()[0];
		Assert.isTrue(spam.getSubject().equals(messageSaved.getSubject()));
		Assert.isTrue(this.folderService.getInBox(recipient).getMessages().isEmpty());
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("explorer1");

		final Explorer sender = this.explorerService.findByPrincipal();
		final Explorer recipient = (Explorer) this.explorerService.findAll().toArray()[1];
		final Message m = this.messageService.create();

		m.setSubject("Subject 2");
		m.setBody("Message's body 2");
		m.setPriorityLevel(PriorityLevel.MED);
		m.getActors().set(1, recipient);

		final Message messageSaved = this.messageService.save(m, false, false);

		this.messageService.delete(messageSaved);

		Folder trashBoxSender = this.folderService.getTrashBox(sender);

		// The message should be in trash box
		Assert.isTrue(trashBoxSender.getMessages().contains(messageSaved));
		Assert.isTrue(!this.folderService.getOutBox(sender).getMessages().contains(messageSaved));
		Assert.isTrue(this.messageService.findAllOfActor(sender).contains(messageSaved));

		this.messageService.delete(messageSaved);

		trashBoxSender = this.folderService.getTrashBox(sender);

		// The message should be eliminated
		Assert.isTrue(!trashBoxSender.getMessages().contains(messageSaved));
		Assert.isTrue(!this.messageService.findAllOfActor(sender).contains(messageSaved));

		super.authenticate(null);
	}

}
