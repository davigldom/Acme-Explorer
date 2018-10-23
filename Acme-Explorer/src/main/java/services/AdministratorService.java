
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;


	// Supporting services

	//CRUD METHODS

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		Assert.isTrue(this.findByPrincipal().equals(administrator));
		Administrator result;
		result = this.administratorRepository.save(administrator);

		return result;
	}

	//OTHER METHODS

	public Administrator findByPrincipal() {
		Administrator administrator;

		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);

		administrator = this.findByUserAccount(ua);
		Assert.notNull(administrator);

		return administrator;
	}

	public Administrator findByUserAccount(final UserAccount ua) {
		Assert.notNull(ua);

		Administrator result;

		result = this.administratorRepository.findByUserAccountId(ua.getId());
		Assert.notNull(result);

		return result;
	}

}
