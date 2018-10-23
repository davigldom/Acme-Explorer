
package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Folder;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService	explorerService;

	@Autowired
	private FolderService	folderService;


	@Test
	public void testCreate() {
		super.authenticate("explorer1");

		final Folder folder = this.folderService.create();

		Assert.isNull(folder.getName());
		Assert.isTrue(!folder.isSysFolder());
		Assert.isNull(folder.getRoot());
		Assert.isTrue(folder.getChildren().isEmpty());
		Assert.isTrue(folder.getMessages().isEmpty());

		super.authenticate(null);
	}

	@Test
	public void testCreateChild() {
		super.authenticate("explorer1");

		final Folder root = this.folderService.create();
		final Folder child = this.folderService.create();
		child.setRoot(root);

		Assert.isNull(root.getName());
		Assert.isTrue(!root.isSysFolder());
		Assert.isNull(root.getRoot());

		Assert.isNull(child.getName());
		Assert.isTrue(!child.isSysFolder());
		Assert.isTrue(child.getRoot() == root);
		Assert.isTrue(child.getChildren().isEmpty());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("explorer1");
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Folder folder = this.folderService.create();

		folder.setName("Folder's name 1");
		folder.setMessages(new HashSet<Message>());
		folder.setRoot(this.folderService.getInBox(explorer));
		final Folder folderSaved = this.folderService.save(folder);
		final Collection<Folder> folders = this.folderService.findByPrincipal();
		final Folder root = folderSaved.getRoot();
		Assert.isTrue(root.getChildren().contains(folderSaved));
		Assert.isTrue(folders.contains(folderSaved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("explorer1");

		final Folder folder = this.folderService.create();

		folder.setName("Folder's name 2");
		folder.setMessages(new HashSet<Message>());

		final Folder folderSaved = this.folderService.save(folder);
		this.folderService.delete(folderSaved);

		final Collection<Folder> folders = this.folderService.findByPrincipal();
		Assert.isTrue(!folders.contains(folderSaved));

		super.authenticate(null);
	}

	@Test
	public void testEdit() {
		super.authenticate("explorer1");

		//		final Explorer explorer = this.explorerService.findByPrincipal();
		final Folder folder = this.folderService.create();

		folder.setName("Name 1wer3");
		folder.setMessages(new HashSet<Message>());

		final Folder folderSaved = this.folderService.save(folder);

		final Folder folderToEdit = this.folderService.findOneToEdit(folderSaved.getId());

		folderToEdit.setName("Name 2");

		final Folder folderEdited = this.folderService.save(folderToEdit);

		final Collection<Folder> folders = this.folderService.findByPrincipal();
		Assert.isTrue(folders.contains(folderEdited));
		Assert.isTrue(folderEdited.getName().equals("Name 2"));

		super.authenticate(null);
	}

}
