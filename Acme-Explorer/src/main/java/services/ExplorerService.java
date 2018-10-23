package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ExplorerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.EmergencyContact;
import domain.Explorer;
import domain.Finder;
import domain.Folder;
import domain.SocialIdentity;
import domain.SurvivalClass;

@Service
@Transactional
public class ExplorerService {

	@Autowired
	private ExplorerRepository explorerRepository;

	// Supporting services

	

	@Autowired
	private SurvivalClassService survivalClassService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private UserAccountService userAccountService;

	// Simple CRUD methods

	//	public Explorer create(
	//			final Collection<EmergencyContact> emergencyContacts,
	//			final String explorerUserName, final String explorerPassword) {
	//
	//		final UserAccount userAccount = this.userAccountService.create(
	//				explorerUserName, explorerPassword);
	//		final Authority authority = new Authority();
	//		authority.setAuthority(Authority.EXPLORER);
	//		userAccount.addAuthority(authority);
	//		userAccount.setUsername(explorerUserName);
	//		userAccount.setPassword(explorerPassword);
	//
	//		Assert.notNull(emergencyContacts);
	//		Assert.isTrue(!emergencyContacts.isEmpty());
	//		Explorer result;
	//		result = new Explorer();
	//		result.setUserAccount(userAccount);
	//		result.setEmergencyContacts(emergencyContacts);
	//		return result;
	//	}

	public Explorer create(Finder finder) {

		final UserAccount userAccount = this.userAccountService.create();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.EXPLORER);
		userAccount.addAuthority(authority);
		final Collection<SocialIdentity> socialIdentities = new HashSet<SocialIdentity>();
		final Collection<Folder> folders = new HashSet<Folder>();
		final Collection<EmergencyContact> emergencyContacts = new HashSet<EmergencyContact>();

		Explorer result;
		result = new Explorer();
		result.setUserAccount(userAccount);
		result.setEmergencyContacts(emergencyContacts);
		result.setFolders(folders);
		result.setSocialIdentities(socialIdentities);
		result.setFinder(finder);
		return result;
	}

	public Explorer findOne(final int explorerId) {
		Assert.isTrue(explorerId != 0);
		Explorer result;
		result = this.explorerRepository.findOne(explorerId);
		return result;
	}

	public Collection<Explorer> findAll() {
		Collection<Explorer> result;
		result = this.explorerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	//	public Explorer modify(final Explorer explorer) {
	//		Explorer result;
	//		Assert.notNull(explorer);
	//		Assert.isTrue(explorer.getId() != 0);
	//		Assert.isTrue(this.findByPrincipal().equals(explorer));
	//		result = this.explorerRepository.save(explorer);
	//		Assert.isTrue(this.explorerRepository.findAll().contains(result));
	//		return result;
	//	}

	public Explorer save(final Explorer explorer) {
		Assert.notNull(explorer);
		Assert.notNull(explorer.getUserAccount().getUsername());
		Assert.notNull(explorer.getUserAccount().getPassword());

		if (explorer.getId() == 0) {
			//New account


			//System folders
			final Collection<Folder> sysFolders = new HashSet<>();

			//Out box
			final Folder outbox_created = this.folderService.create();
			outbox_created.setName("out box");
			outbox_created.setSysFolder(true);
			final Folder outbox = this.folderService.save(outbox_created);
			sysFolders.add(outbox);

			//In box
			final Folder inbox_created = this.folderService.create();
			inbox_created.setName("in box");
			inbox_created.setSysFolder(true);
			final Folder inbox = this.folderService.save(inbox_created);
			sysFolders.add(inbox);

			//Notification box
			final Folder notificationbox_created = this.folderService.create();
			notificationbox_created.setName("notification box");
			notificationbox_created.setSysFolder(true);
			final Folder notificationbox = this.folderService.save(notificationbox_created);
			sysFolders.add(notificationbox);

			//Trash box
			final Folder trashbox_created = this.folderService.create();
			trashbox_created.setName("trash box");
			trashbox_created.setSysFolder(true);
			final Folder trashbox = this.folderService.save(trashbox_created);
			sysFolders.add(trashbox);

			//Spam box
			final Folder spambox_created = this.folderService.create();
			spambox_created.setName("spam box");
			spambox_created.setSysFolder(true);
			final Folder spambox = this.folderService.save(spambox_created);
			sysFolders.add(spambox);

			explorer.getFolders().addAll(sysFolders);

			//Hashing password
			String passwordHashed = null;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			passwordHashed = encoder.encodePassword(explorer.getUserAccount().getPassword(), null);
			explorer.getUserAccount().setPassword(passwordHashed);

		} else
			//Editing personal data
			Assert.isTrue(this.findByPrincipal().equals(explorer));
		final Explorer explorerSaved = this.explorerRepository.save(explorer);
		return explorerSaved;
	}

	// Other business methods

	public Explorer findByPrincipal() {
		Explorer result;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);

		return result;
	}

	public Explorer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Explorer result;
		result = this.explorerRepository.findByUserAccountId(userAccount
			.getId());

		return result;
	}

	public Explorer findByUserAccountId(final int explorerId) {
		Explorer result;
		result = this.explorerRepository.findByUserAccountId(explorerId);
		return result;
	}

	public void enrolSurvivalClass(final SurvivalClass survivalClass) {
		Assert.notNull(survivalClass);
		Assert.isTrue(this.survivalClassService.findAvailableToEnrol()
			.contains(survivalClass));
		final Explorer explorer = this.findByPrincipal();
		final Collection<SurvivalClass> survivalClasses = explorer
			.getSurvivalClasses();
		survivalClasses.add(survivalClass);
		this.explorerRepository.save(explorer);
	}

}
