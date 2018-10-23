
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SystemConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SystemConfigTest extends AbstractTest {

	@Autowired
	private SystemConfigService	systemConfigService;


	@Test
	public void testSave() {
		super.authenticate("administrator");

		final SystemConfig sys = this.systemConfigService.findConfig();
		Assert.notNull(sys);

		sys.setCacheHours(2);

		final SystemConfig savedSys = this.systemConfigService.save(sys);
		Assert.notNull(savedSys);
		Assert.isTrue(sys.getId() == savedSys.getId());
		super.authenticate(null);
	}
}
