package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Application;
import domain.EmergencyContact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.SocialIdentity;
import domain.Story;
import domain.SurvivalClass;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class ExplorerServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService explorerService;

	// Supporting services


	@Autowired
	private SurvivalClassService survivalClassService;

	@Autowired
	private FinderService finderService;

	// Tests

	@Test
	public void testCreateExplorer() {

		final List<EmergencyContact> emergencyContacts = new ArrayList<>();
	
		Finder finderToSave = this.finderService.create();
		Finder finder = this.finderService.save(finderToSave);
		final Explorer e = this.explorerService.create(finder);
		Assert.isTrue(e.getId() == 0);
		Assert.notNull(e);
		Assert.notNull(emergencyContacts);
		Assert.isNull(e.getApplications());
		Assert.isNull(e.getStories());
		Assert.isNull(e.getSurvivalClasses());

	}

	@Test
	public void testRegisterExplorer() {

		
		Finder finderToSave = this.finderService.create();
		Finder finder = this.finderService.save(finderToSave);
		final Explorer e = this.explorerService.create(finder);

		final List<Application> applications = new ArrayList<>();
		final List<Story> stories = new ArrayList<>();
		final List<SurvivalClass> survivalClasses = new ArrayList<>();
		e.setApplications(applications);
		e.setStories(stories);
		e.setSurvivalClasses(survivalClasses);
		e.setName("Paco");
		e.setSurname("Perez");
		e.setEmail("example@gmail.com");
		e.setSocialIdentities(new HashSet<SocialIdentity>());
		e.setFolders(new HashSet<Folder>());
		e.getUserAccount().setUsername("user123");
		e.getUserAccount().setPassword("password");

		final Explorer eRegistered = this.explorerService.save(e);
		Assert.isTrue(this.explorerService.findAll().contains(eRegistered));
		Assert.notNull(eRegistered.getApplications());
		Assert.notNull(eRegistered.getStories());
		Assert.notNull(eRegistered.getSurvivalClasses());
		Assert.notNull(eRegistered.getEmergencyContacts());
		Assert.notNull(eRegistered.getFinder());
	}

	@Test
	public void testModifyExplorer() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		explorer.setName("example35");
		final Explorer explorerModified = this.explorerService.save(explorer);
		Assert.isTrue(explorer.getName().equals(explorerModified.getName()));

		super.authenticate(null);
	}

	@Test
	public void testFindByPrincipal() {
		super.authenticate("explorer1");
		final int explorerId = super.getEntityId("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		Assert.isTrue(explorer.equals(this.explorerService.findOne(explorerId)));

		super.authenticate(null);
	}

	@Test
	public void testFindByUserAccount() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final UserAccount userAccount = explorer.getUserAccount();

		final Explorer explorer2 = this.explorerService
				.findByUserAccount(userAccount);

		Assert.isTrue(explorer.equals(explorer2));

		super.authenticate(null);
	}

	@Test
	public void testFindByUserAccountId() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final int userAccountId = explorer.getUserAccount().getId();

		final Explorer explorer2 = this.explorerService
				.findByUserAccountId(userAccountId);

		Assert.isTrue(explorer.equals(explorer2));

		super.authenticate(null);
	}

	@Test
	public void testEnrolSurvivalClass() {

		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final SurvivalClass survivalClass = (SurvivalClass) this.survivalClassService
				.findAvailableToEnrol().toArray()[0];
		this.explorerService.enrolSurvivalClass(survivalClass);

		Assert.isTrue(explorer.getSurvivalClasses().contains(survivalClass));

		super.authenticate(null);

	}
}
