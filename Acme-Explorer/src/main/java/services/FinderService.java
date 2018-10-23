
package services;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.DateRange;
import domain.Finder;
import domain.PriceRange;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Simple CRUD methods

	public Finder create() {
		Finder result;
		result = new Finder();
		result.setPriceRange(new PriceRange());
		result.setDateRange(new DateRange());
		final Collection<Trip> trips = this.tripService.findPublished();
		result.setTrips(trips);
		return result;
	}

	public Finder findOne(final int finderId) {
		Assert.isTrue(finderId != 0);
		Finder result;
		result = this.finderRepository.findOne(finderId);
		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);
		finder.setMoment(new Date(System.currentTimeMillis() - 1000));
		Finder result;

		//There are three cases in which we save a finder.

		//Two of them only occur when we are searching for trips.
		if (finder.getId() != 0) {
			//First, we must check that the finder in the database has the same search parameters
			//and that the difference between moments is the number of hours specified in the system configuration.
			final Finder oldFinder = this.explorerService.findByPrincipal().getFinder();
			if (this.isLessThanHourFromChache(finder, oldFinder))
				//If that is the case, we return the stored finder
				result = this.explorerService.findByPrincipal().getFinder();
			else {
				//If not, we perform the search and save the finder.
				final Collection<Trip> trips = this.tripService.finderResults(finder.getKeyWord(), finder.getPriceRange().getMinPrice(), finder.getPriceRange().getMaxPrice(), finder.getDateRange().getStart(), finder.getDateRange().getEnd());
				finder.setTrips(trips);
				result = this.finderRepository.save(finder);
			}
		}else
			//The last case occurs when we are saving a finder for the first time. This only a explorer
			//registers to the system, so finder object must have a collection of all the trips (assigned in create method).
			result = this.finderRepository.save(finder);

		return result;
	}

	// Check if use cache finder or get a new results
	public boolean isLessThanHourFromChache(final Finder finder, final Finder oldFinder) {
		boolean res = false;
		//We retrieve the cached keyword and the new one.
		final String newKeyword = finder.getKeyWord();
		final String oldKeyword = oldFinder.getKeyWord();

		//We retrieve the new PriceRange and the old one
		final Double newMinPrice = finder.getPriceRange().getMinPrice();
		final Double newMaxPrice = finder.getPriceRange().getMaxPrice();

		//The database will return a priceRange with null value if its both attributes are null
		Double oldMinPrice = null;
		Double oldMaxPrice = null;
		if (oldFinder.getPriceRange() != null) {
			oldMinPrice = oldFinder.getPriceRange().getMinPrice();
			oldMaxPrice = oldFinder.getPriceRange().getMaxPrice();
		}

		//Now we retrieve the dates in form of time in milliseconds,
		//because the database returns the dateRange in Timestamp format

		Long newStartTime = null;
		Long newEndTime = null;

		if (finder.getDateRange().getStart() != null)
			newStartTime = finder.getDateRange().getStart().getTime();
		if (finder.getDateRange().getEnd() != null)
			newEndTime = finder.getDateRange().getEnd().getTime();

		Long oldStartTime = null;
		Long oldEndTime = null;
		if (oldFinder.getDateRange() != null) {
			if (oldFinder.getDateRange().getStart() != null)
				oldStartTime = oldFinder.getDateRange().getStart().getTime();
			if (oldFinder.getDateRange().getEnd() != null)
				oldEndTime = oldFinder.getDateRange().getEnd().getTime();
		}

		//At last, we retrieve the moments, in milliseconds as well
		final Long newMomentTime = finder.getMoment().getTime();
		final Long oldMomentTime = oldFinder.getMoment().getTime();
		final Integer cacheHours = this.systemConfigService.findConfig().getCacheHours();

		if (Objects.equals(newKeyword, oldKeyword) && Objects.equals(newMinPrice, oldMinPrice) && Objects.equals(newMaxPrice, oldMaxPrice) && Objects.equals(newStartTime, oldStartTime) && Objects.equals(newEndTime, oldEndTime)
			&& newMomentTime - oldMomentTime <= cacheHours * 60 * 60 * 1000)
			res = true;
		return res;
	}

	// Other business rules

	public Finder getFinderByExplorerId(final int explorerId) {
		Finder result;
		Assert.notNull(explorerId);
		result = this.finderRepository.getFinderByExplorerId(explorerId);
		return result;

	}

	public Finder findOneToEdit(final int finderId) {
		final Finder result;
		Assert.notNull(finderId);
		result = this.finderRepository.findOne(finderId);
		return result;

	}

	public Collection<Finder> findByTrip(final int tripId) {
		Collection<Finder> finders;
		Assert.notNull(tripId);
		finders = this.finderRepository.findByTrip(tripId);
		return finders;
	}
}
