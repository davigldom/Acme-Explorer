
package repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from Trip t " + "where t.published=1 and (t.title like CONCAT('%',?1,'%')" + "or t.description like CONCAT('%',?1,'%')" + "or t.ticker like CONCAT('%',?1,'%'))")
	Collection<Trip> findByKeyword(String keyword);

	@Query("select t from Trip t where t.published=1 and (t.price >= ?1 and t.price <=?2) and (t.publication > ?3 and t.publication < ?4) and ((t.title like CONCAT('%',?5,'%')) or (t.description like CONCAT('%',?5,'%')) or (t.ticker like CONCAT('%',?5,'%')))")
	List<Trip> finderResults(Double minPrice, Double maxPrice, Date minDate, Date maxDate, String keyword, Pageable pageable);

	@Query("select t from Trip t where t.category.id = ?1 and t.published=true")
	Collection<Trip> findByCategoryId(int categoryId);

	@Query("select t from Trip t where t.manager.id =?1")
	Collection<Trip> findByManagerId(int managerId);

	@Query("select t from Trip t where t.ranger.id =?1")
	Collection<Trip> findByRangerId(int id);

	@Query("select t from Trip t where t.legalText.id =?1")
	Collection<Trip> findByLegalTextId(int id);

	@Query("select t from Application a join a.trip t where a.status=3 and a.trip.id = t.id and a.explorer.id =?1")
	Collection<Trip> findAcceptedApplicationsOfExplorer(int id);

	@Query("select avg(m.trips.size) from Manager m")
	double findAveragePerManager();

	@Query("select min(m.trips.size) from Manager m")
	double findMinPerManager();

	@Query("select max(m.trips.size) from Manager m")
	double findMaxPerManager();

	@Query("select sqrt(sum(m.trips.size*m.trips.size)/count(m.trips.size)-(avg(m.trips.size)*avg(m.trips.size))) from Manager m")
	double findStandardDeviationPerManager();

	@Query("select avg(t.price) from Trip t")
	double findPriceAverage();

	@Query("select min(t.price) from Trip t")
	double findPriceMin();

	@Query("select max(t.price) from Trip t")
	double findPriceMax();

	@Query("select sqrt(sum(t.price*t.price)/count(t.price)-(avg(t.price)*avg(t.price))) from Trip t")
	double findPriceStandardDeviation();

	@Query("select avg(r.trips.size) from Ranger r")
	double findAveragePerRanger();

	@Query("select min(r.trips.size) from Ranger r")
	double findMinPerRanger();

	@Query("select max(r.trips.size) from Ranger r")
	double findMaxPerRanger();

	@Query("select sqrt(sum(r.trips.size*r.trips.size)/count(r.trips.size)-(avg(r.trips.size)*avg(r.trips.size))) from Ranger r")
	double findStandardDeviationPerRanger();

	@Query("select sum(case when (t.cancelled = 1) then 1.0 else 0.0 end)/count(t) FROM Trip t")
	double findRatioCancelled();

	@Query("select t from Trip t where t.applications.size>=1.1*(select avg(t.applications.size) from Trip t) order by t.applications.size")
	Collection<Trip> findTenPercentApplications();

	@Query("select sum(case when(t.auditions is not empty) then 1.0 else 0.0 end)/count(t) from Trip t")
	double findRatioWithAudit();

	@Query("select t from Trip t where t.published = 1")
	Collection<Trip> findPublished();

	@Query("select t.ticker from Trip t where t.ticker like CONCAT('%',?1,'%')")
	Collection<String> findTickersLike(String ticker);

}
