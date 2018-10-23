
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Service
@Transactional
public class FolderService {

	// Managed repository

	@Autowired
	private FolderRepository	folderRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;


	// Constructors

	public FolderService() {
		super();
	}

	// Simple CRUD methods

	public Folder create() {
		// final Actor actor = this.actorService.findByPrincipal();
		// Assert.notNull(actor);
		Folder result;
		final List<Message> messages = new ArrayList<Message>();
		final List<Folder> children = new ArrayList<Folder>();

		result = new Folder();
		result.setSysFolder(false);
		result.setMessages(messages);
		result.setChildren(children);

		// actor.getFolders().add(result);

		return result;
	}

	public Folder findOne(final int folderId) {
		Folder result;

		result = this.folderRepository.findOne(folderId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Folder> findAll() {
		Collection<Folder> result;

		result = this.folderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Folder save(final Folder folder) {
		Assert.notNull(folder);
		if(folder.getRoot()!=null)Assert.isTrue(!folder.getRoot().equals(folder));
		Folder result;

		if (folder.isSysFolder() && folder.getId() == 0)
			result = this.folderRepository.save(folder);
		else {
			// User
			final Actor principal = this.actorService.findByPrincipal();

			// When editing a folder, we must remove the old version from the
			// folders of the user
			if (folder.getId() != 0)
				principal.getFolders().remove(folder);

			result = this.folderRepository.save(folder);
			principal.getFolders().add(result);

			// Root folder
			final Folder root = result.getRoot();

			if (root != null) {
				// Root folder's children
				Collection<Folder> children = root.getChildren();
				if (children == null) {
					final List<Folder> childrenList = new ArrayList<Folder>();
					children = childrenList;
				}
				// When editing a folder, we must remove the old version from
				// the children of the root folder
				if (folder.getId() != 0)
					children.remove(folder);
				children.add(result);
				root.setChildren(children);
				if(folder.getId()!=0)this.save(root);
			}

		}
		return result;
	}

	public void delete(final Folder folder) {
		Assert.isTrue(folder.getId() != 0);
		Assert.isTrue(!folder.isSysFolder());
		this.checkPrincipal(folder);
		final Actor principal = this.actorService.findByPrincipal();

//		if (!this.messageService.findAllInAFolder(folder).isEmpty()) {
//			final Collection<Message> messages = this.messageService.findAllInAFolder(folder);
//			for (int i = 0; i < messages.toArray().length; i++) {
//				final Message message = (Message) messages.toArray()[i];
//				message.setActors(this.messageService.findActors(message.getId()));
//				this.messageService.delete(message);
//			}
//		}
		
		if (!this.messageService.findAllInAFolder(folder).isEmpty()) {
			final Collection<Message> messages = this.messageService.findAllInAFolder(folder);
			for (int i = 0; i < messages.toArray().length; i++) {
				final Message message = (Message) messages.toArray()[i];
				message.setActors(this.messageService.findActors(message.getId()));
				this.messageService.changeFolder(message, this.getTrashBox(principal));
			}
		}

		principal.getFolders().remove(folder);

		//		if (folder.getChildren() != null) {
		//			final List<Folder> children = new ArrayList<Folder>(folder.getChildren());
		//			for (int i = 0; i < children.size(); i++)
		//				this.delete(this.findOne(children.get(i).getId()));
		//			folder.setChildren(null);
		//		}

		final java.util.Iterator<Folder> iter = folder.getChildren().iterator();
		while (iter.hasNext()) {
			final Folder child = iter.next();
			iter.remove();
			this.delete(child);

		}

		Folder result = this.folderRepository.findOne(folder.getId());
		this.folderRepository.delete(result);

	}

	// Other business methods

	public Collection<Folder> findByPrincipal() {
		Collection<Folder> result;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		result = this.folderRepository.findByActorId(actor.getId());

		return result;
	}

	public Folder findOneToEdit(final int folderId) {
		Folder result;
		result = this.findOne(folderId);
		Assert.notNull(result);
		Assert.isTrue(!result.isSysFolder());
		this.checkPrincipal(result);
		return result;
	}

	public Folder findOneToList(final int folderId) {
		Folder result;
		result = this.findOne(folderId);
		Assert.notNull(result);
		this.checkPrincipal(result);
		return result;
	}

	public void checkPrincipal(final Folder folder) {
		Assert.notNull(folder);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getFolders().contains(folder));
	}

	public Folder getInBox(final Actor actor) {
		Assert.notNull(actor);
		Folder result;
		result = this.folderRepository.findInBox(actor.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder getOutBox(final Actor actor) {
		Assert.notNull(actor);
		Folder result;
		result = this.folderRepository.findOutBox(actor.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder getNotificationBox(final Actor actor) {
		Assert.notNull(actor);
		Folder result;
		result = this.folderRepository.findNotificationBox(actor.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder getTrashBox(final Actor actor) {
		Assert.notNull(actor);
		Folder result;
		result = this.folderRepository.findTrashBox(actor.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder getSpamBox(final Actor actor) {
		Assert.notNull(actor);
		Folder result;
		result = this.folderRepository.findSpamBox(actor.getId());
		Assert.notNull(result);
		return result;
	}

	public Folder findByMessage(final Message message) {
		Folder result = null;
		result = this.folderRepository.findByMessage(message.getId());
		return result;
	}

	public Collection<Folder> findByRootPrincipal(final int folderId) {
		Collection<Folder> result;
		Actor actor;
		Folder root;

		actor = this.actorService.findByPrincipal();

		if (folderId == 0)
			result = this.folderRepository.findRootByActorId(actor.getId());
		else {
			root = this.findOneToList(folderId);
			result = this.folderRepository.findChildrenByActorId(actor.getId(), root);
		}

		return result;
	}
}
