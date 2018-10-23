
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.DateRange;
import domain.Explorer;
import domain.Finder;
import domain.PriceRange;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	@Autowired
	private FinderService	finderService;

	//Supporting services´

	@Autowired
	private ExplorerService	explorerService;


	@Test
	public void testCreateFinder() {
		final Finder finder = this.finderService.create();
		Assert.notNull(finder);
		Assert.notNull(finder.getDateRange());
		Assert.notNull(finder.getPriceRange());
		
		Assert.isNull(finder.getKeyWord());
		Assert.notNull(finder.getTrips());
		Assert.isNull(finder.getMoment());
	}

	@Test
	public void testSaveFinder() {
		final Finder finder = this.finderService.create();
		final Finder finderSaved = this.finderService.save(finder);
		Assert.isTrue(this.finderService.findAll().contains(finderSaved));

	}

	@Test
	public void testModifyFinder() {
		super.authenticate("explorer1");

		final Finder finder = this.explorerService.findByPrincipal().getFinder();
		//		try {
		//			TimeUnit.SECONDS.sleep(5);
		//		} catch (final Exception e) {
		//			Assert.isTrue(false);
		//		}

		Assert.isTrue(this.finderService.findAll().contains(finder));
		finder.setKeyWord("jungle");

		finder.setPriceRange(new PriceRange());
		finder.setDateRange(new DateRange());
		final Finder finderModified = this.finderService.save(finder);
		Assert.isTrue((finder.getKeyWord().equals(finderModified.getKeyWord())));

		super.authenticate(null);
	}

	@Test
	public void testModifyFinderCache() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();

		final Finder finder = explorer.getFinder();

		finder.setPriceRange(new PriceRange());
		finder.setDateRange(new DateRange());
		final Finder finderModified = this.finderService.save(finder);

		//		try {
		//			TimeUnit.SECONDS.sleep(5);
		//		} catch (final Exception e) {
		//			Assert.isTrue(false);
		//		}

		final Finder finderModified2 = this.finderService.save(finder);

		Assert.isTrue(finderModified.getMoment().equals(finderModified2.getMoment()));

		super.authenticate(null);
	}

	@Test
	public void testGetFinderByExplorerId() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Finder finder = explorer.getFinder();
		final Finder finder2 = this.finderService.getFinderByExplorerId(explorer.getId());
		Assert.isTrue(finder.equals(finder2));

		super.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Finder finder = explorer.getFinder();
		final Finder finder2 = this.finderService.findOneToEdit(finder.getId());
		Assert.isTrue(finder.equals(finder2));
	}

}
