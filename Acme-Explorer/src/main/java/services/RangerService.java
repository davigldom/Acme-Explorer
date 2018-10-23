
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Folder;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repository

	@Autowired
	private RangerRepository		rangerRepository;

	// Supporting Services

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private UserAccountService		userAccountService;


	public RangerService() {
		super();
	}

	// Simple CRUD methods

	public Ranger create() {
		final UserAccount userAccount = this.userAccountService.create();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.RANGER);
		userAccount.addAuthority(authority);

		Ranger result;
		final Collection<Trip> trips = new HashSet<Trip>();
		final Collection<SocialIdentity> socialIdentities = new HashSet<SocialIdentity>();
		final Collection<Folder> folders = new HashSet<Folder>();
		result = new Ranger();
		result.setTrips(trips); //Añadido
		result.setSocialIdentities(socialIdentities);
		result.setFolders(folders);
		result.setUserAccount(userAccount);
		return result;
	}

	public Ranger findOne(final int rangerId) {
		Assert.isTrue(rangerId != 0);
		Ranger result;
		result = this.rangerRepository.findOne(rangerId);
		return result;
	}

	public Collection<Ranger> findAll() {
		Collection<Ranger> result;
		result = this.rangerRepository.findAll();
		Assert.notNull(result);
		return result;
	}


	public Ranger save(final Ranger ranger) {
		Ranger result;
		Assert.notNull(ranger.getUserAccount().getUsername());
		Assert.notNull(ranger.getUserAccount().getPassword());
		Assert.notNull(ranger);
		if (ranger.getId() != 0){
			final Ranger principal = this.findByPrincipal();
			Assert.isTrue(principal.equals(ranger));
		}else {

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

			ranger.getFolders().addAll(sysFolders);

			//Hashing password
			String passwordHashed = null;
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			passwordHashed = encoder.encodePassword(ranger.getUserAccount().getPassword(), null);
			ranger.getUserAccount().setPassword(passwordHashed);
		}

		result = this.rangerRepository.save(ranger);
		return result;
	}

	public void delete(final Ranger ranger) {
		Assert.notNull(ranger);
		Assert.isTrue(this.rangerRepository.exists(ranger.getId()));
		Assert.isTrue(ranger.getId() != 0);
		this.rangerRepository.delete(ranger);
	}

	//Other business methods

	public Ranger findByPrincipal() {
		Ranger result;
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		return result;
	}

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Ranger result;

		result = this.rangerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<String> findAllRangersBanned() {

		Collection<String> result;

		result = this.rangerRepository.findAllRangersBanned();
		Assert.notNull(result);

		return result;
	}

	public Double getRatioRangersWithCurriculaRegistered() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getRatioRangersWithCurriculaRegistered();
	}

	public Double getRatioRangersWithCurriculumEndorsed() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getRatioRangersWithCurriculumEndorsed();
	}

	public Double getRatioRangersSuspicious() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getRatioRangersSuspicious();
	}

	public Double getAverageTripsPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getAverageTripsPerRanger();
	}

	public Integer getMinimumTripsPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getMinimumTripsPerRanger();
	}

	public Integer getMaximunTripsPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getMaximumTripsPerRanger();
	}

	public Double getStandardDeviationTripsPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		return this.rangerRepository.getStandardDeviationTripsPerRanger();
	}

}
