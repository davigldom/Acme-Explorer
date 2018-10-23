
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	userAccountRepository;


	public UserAccount create() {

		final UserAccount ua = new UserAccount();

		return ua;

	}

	public UserAccount save(final UserAccount ua) {
		Assert.notNull(ua);
		Assert.notNull(ua.getUsername());
		Assert.notNull(ua.getPassword());

		final UserAccount savedUa = this.userAccountRepository.save(ua);

		Assert.isTrue(this.userAccountRepository.findAll().contains(savedUa));

		return savedUa;
	}
}
