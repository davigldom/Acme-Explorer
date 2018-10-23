
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Manager;
import domain.SocialIdentity;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class ManagerService {

	// Managed repository

	@Autowired
	private ManagerRepository		managerRepository;

	// Supporting services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private FolderService			folderService;


	// Constructors

	public ManagerService() {
		super();
	}

	// Simple CRUD methods

	public Manager create() {
		Administrator admin;
		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final UserAccount userAccount = this.userAccountService.create();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		userAccount.addAuthority(authority);

		final Collection<Trip> trips = new HashSet<Trip>();
		final Collection<Folder> folders = new HashSet<Folder>();
		final Collection<SurvivalClass> survivalClasses = new HashSet<SurvivalClass>();
		final Collection<SocialIdentity> socialIdentities = new HashSet<SocialIdentity>();

		Manager result;
		result = new Manager();

		result.setSuspicious(false);
		result.setBanned(false);
		result.setTrips(trips);
		result.setSurvivalClasses(survivalClasses);
		result.setSocialIdentities(socialIdentities);
		result.setUserAccount(userAccount);
		result.setFolders(folders);

		return result;
	}

	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> result;

		result = this.managerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Manager save(final Manager manager) {
		Manager result;
		Assert.notNull(manager.getUserAccount().getUsername());
		Assert.notNull(manager.getUserAccount().getPassword());
		Assert.notNull(manager);
		if (manager.getId() != 0) {
			final Manager principal = this.findByPrincipal();
			Assert.isTrue(principal.equals(manager));
		} else {
			Administrator admin;
			admin = this.administratorService.findByPrincipal();
			Assert.notNull(admin);

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

			manager.getFolders().addAll(sysFolders);

			//Hashing password
			String passwordHashed = null;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			passwordHashed = encoder.encodePassword(manager.getUserAccount().getPassword(), null);
			manager.getUserAccount().setPassword(passwordHashed);
		}

		result = this.managerRepository.save(manager);
		return result;
	}

	// public void delete(final Manager manager) {
	// Assert.isTrue(manager.getId() != 0);
	//
	// this.managerRepository.delete(manager);
	// }

	// Other business methods

	public Manager findByPrincipal() {
		Manager result;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);

		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Manager result;

		result = this.managerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<Manager> findAllManagersSuspicious() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Collection<Manager> result;

		result = this.managerRepository.findAllManagersSuspicious();
		Assert.notNull(result);

		return result;
	}

	public Collection<String> findAllManagersBanned() {

		Collection<String> result;

		result = this.managerRepository.findAllManagersBanned();
		Assert.notNull(result);

		return result;
	}
	public Double ratioManagersSuspicious() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;

		result = this.managerRepository.ratioManagersSuspicious();

		return result;
	}

}
