
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Administrator;
import domain.Application;
import domain.Audition;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Note;
import domain.Ranger;
import domain.Sponsorship;
import domain.Stage;
import domain.SurvivalClass;
import domain.TagValue;
import domain.Trip;

@Service
@Transactional
public class TripService {

	@Autowired
	private TripRepository			tripRepository;

	// Supporting services------------------------------------------------------
	@Autowired
	private ManagerService			managerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SystemConfigService		systemConfigService;


	public Trip create(final Manager manager) {
		Assert.notNull(this.managerService.findByPrincipal());
		final Trip result = new Trip();

		result.setCancelled(false);
		result.setPublished(false);
		result.setTicker(this.generateTicker());
		result.setPrice(0.0);
		result.setManager(manager);
		result.setApplications(new ArrayList<Application>());
		result.setStages(new ArrayList<Stage>());
		result.setNotes(new ArrayList<Note>());
		result.setAuditions(new ArrayList<Audition>());
		result.setSponsorships(new ArrayList<Sponsorship>());
		result.setSurvivalClasses(new ArrayList<SurvivalClass>());
		result.setTagValues(new ArrayList<TagValue>());

		return result;

	}

	public Collection<Trip> findAll() {
		return this.tripRepository.findAll();
	}

	public Trip findOne(final int id) {
		return this.tripRepository.findOne(id);
	}

	public Trip save(Trip trip) {
		Assert.notNull(trip);
		this.checkManagerPrincipal(trip);
		// Check that the legal text is saved in final mode.
		Assert.isTrue(trip.getLegalText().isDefinitive());
		Assert.isTrue(trip.getStartDate().before(trip.getEndDate()));
		Assert.isTrue(trip.getStartDate().after(new Date(System.currentTimeMillis())));

		final Manager manager = trip.getManager();
		final Ranger ranger = trip.getRanger();
		final LegalText legalText = trip.getLegalText();
		final Category category = trip.getCategory();

		// A trip can only be cancelled if it has been published but has not
		// started yet,
		// and it must store a cancellation reason.

		if (trip.isCancelled()) {
			Assert.isTrue(trip.isPublished());
			Assert.isTrue(trip.getStartDate().after(new Date(System.currentTimeMillis())));
			Assert.notNull(trip.getCancelationReason());
		}

		// If it is published, the system must store the moment;
		if (trip.isPublished()) {
			if (trip.getPublication() == null)
				trip.setPublication(new Date(System.currentTimeMillis() - 1000));

			//Also, if it's published, only cancelled and cancelationReason attributes can change
			final Trip oldTrip = this.findOne(trip.getId());
			oldTrip.setCancelationReason(trip.getCancelationReason());
			oldTrip.setCancelled(trip.isCancelled());
			oldTrip.setPublication(trip.getPublication());
			oldTrip.setPublished(trip.isPublished());
			trip = oldTrip;
		}

		final Trip result = this.tripRepository.save(trip);

		if (trip.getId() == 0) {
			manager.getTrips().add(result);
			ranger.getTrips().add(result);
			category.getTrips().add(result);
			legalText.getTrips().add(result);
		}

		return result;
	}

	public void delete(final Trip trip) {
		Assert.notNull(trip);
		this.checkManagerPrincipal(trip);
		// Check that the trip hasn't been published.
		Assert.isTrue(!trip.isPublished());
		Assert.isTrue(trip.getId() != 0);
		final Manager manager = trip.getManager();
		final Ranger ranger = trip.getRanger();
		final LegalText legalText = trip.getLegalText();
		final Category category = trip.getCategory();

		manager.getTrips().remove(trip);
		ranger.getTrips().remove(trip);
		category.getTrips().remove(trip);
		legalText.getTrips().remove(trip);
		this.tripRepository.delete(trip);

	}

	// OTHER METHODS

	public Collection<Trip> findByKeyword(String keyword) {
		if (keyword == null)
			keyword = "%";
		Collection<Trip> result = null;
		result = this.tripRepository.findByKeyword(keyword);
		return result;
	}

	public List<Trip> finderResults(String keyword, Double minPrice, Double maxPrice, Date minDate, Date maxDate) {
		final List<Trip> result;

		if (keyword == null || keyword.isEmpty())
			keyword = "%";
		if (minPrice == null)
			minPrice = 0.0;
		if (maxPrice == null)
			maxPrice = Double.MAX_VALUE;
		if (minDate == null)
			minDate = new Date(0);
		if (maxDate == null)
			maxDate = new Date(Long.MAX_VALUE);
		result = this.tripRepository.finderResults(minPrice, maxPrice, minDate, maxDate, keyword, new PageRequest(0, this.systemConfigService.findConfig().getFinderResults()));
		return result;

	}

	public Collection<Trip> findByCategory(final Category category) {
		Assert.notNull(category);
		Collection<Trip> result = null;
		result = this.tripRepository.findByCategoryId(category.getId());
		return result;
	}

	public Collection<Trip> findByManager(final Manager manager) {
		Assert.notNull(manager);
		Collection<Trip> result = null;
		result = this.tripRepository.findByManagerId(manager.getId());
		return result;
	}

	public Collection<Trip> findByRanger(final Ranger ranger) {
		Assert.notNull(ranger);
		Collection<Trip> result = null;
		result = this.tripRepository.findByRangerId(ranger.getId());
		return result;
	}

	public Collection<Trip> findByLegalText(final LegalText legalText) {
		Assert.notNull(legalText);
		Collection<Trip> result = null;
		result = this.tripRepository.findByLegalTextId(legalText.getId());
		return result;
	}

	public Collection<Trip> findAcceptedApplicationsOfExplorer(final int explorerId) {
		Collection<Trip> result = null;
		result = this.tripRepository.findAcceptedApplicationsOfExplorer(explorerId);
		return result;
	}

	public double findAveragePerManager() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findAveragePerManager();
		return result;
	}

	public double findMaxPerManager() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findMaxPerManager();
		return result;
	}

	public double findMinPerManager() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findMinPerManager();
		return result;
	}

	public double findStandardDeviationPerManager() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findStandardDeviationPerManager();
		return result;
	}

	public double findPriceAverage() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findPriceAverage();
		return result;
	}

	public double findPriceMax() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findPriceMax();
		return result;
	}

	public double findPriceMin() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findPriceMin();
		return result;
	}

	public double findPriceStandardDeviation() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findPriceStandardDeviation();
		return result;
	}

	public double findAveragePerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findAveragePerRanger();
		return result;
	}

	public double findMaxPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findMaxPerRanger();
		return result;
	}

	public double findMinPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findMinPerRanger();
		return result;
	}

	public double findStandardDeviationPerRanger() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findStandardDeviationPerRanger();
		return result;
	}

	public double findRatioCancelled() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.tripRepository.findRatioCancelled();
		return result;
	}

	public Collection<Trip> findTenPercentApplications() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Collection<Trip> result;
		result = this.tripRepository.findTenPercentApplications();
		return result;
	}

	public double findRatioWithAudit() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		double result;
		result = this.tripRepository.findRatioWithAudit();
		return result;
	}

	void checkManagerPrincipal(final Trip trip) {
		Assert.notNull(trip);
		final Manager principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(trip.getManager().equals(principal));
	}

	void checkTripIsOver(final Trip trip) {
		Assert.isTrue(trip.getPublication().before(new Date(System.currentTimeMillis() - 1000)));
	}

	String generateTicker() {
		String ticker = "";

		final Calendar c = Calendar.getInstance();
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1)
			day = "0" + day;

		String month = Integer.toString(c.get(Calendar.MONTH) + 1);
		if (month.length() == 1)
			month = "0" + month;

		String year = Integer.toString(c.get(Calendar.YEAR));
		year = year.substring(2);

		ticker = year + month + day;

		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random random = new Random();
		final int index1 = random.nextInt(alphabet.length());
		final int index2 = random.nextInt(alphabet.length());
		final int index3 = random.nextInt(alphabet.length());
		final int index4 = random.nextInt(alphabet.length());
		ticker = ticker + "-" + alphabet.charAt(index1) + alphabet.charAt(index2) + alphabet.charAt(index3) + alphabet.charAt(index4);

		final Collection<String> tickers = this.tripRepository.findTickersLike(ticker);
		if (!tickers.isEmpty())
			ticker = this.generateTicker();

		return ticker;

	}

	//	double generatePrice(final Trip trip) {
	//		double price = 0.0;
	//		if (trip.getStages() != null) {
	//			for (final Stage stage : trip.getStages())
	//				price = price + stage.getPrice();
	//			price = price + price
	//				* this.systemConfigService.findConfig().getVAT() / 100;
	//		}
	//
	//		return price;
	//	}

	public Trip findOneToEdit(final int tripId) {
		Trip result;
		result = this.findOne(tripId);
		Assert.notNull(result);
		Assert.isTrue(!result.isPublished());

		this.checkManagerPrincipal(result);

		return result;
	}

	public Trip findOneToCancel(final int tripId) {
		Trip result;
		result = this.findOne(tripId);
		Assert.notNull(result);

		this.checkManagerPrincipal(result);

		return result;
	}

	public Collection<Trip> findPublished() {

		Collection<Trip> trips;
		trips = this.tripRepository.findPublished();
		return trips;
	}

}
