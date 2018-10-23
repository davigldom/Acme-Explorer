
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Integer> {

	@Query("select r from Ranger r where r.userAccount.id = ?1")
	Ranger findByUserAccountId(int id);

	@Query("select r.userAccount.username from Ranger r where r.banned = true")
	Collection<String> findAllRangersBanned();

	@Query("select sum(case when(r.curriculum is not null) then 1.0 else 0.0 end)/count(r) from Ranger r")
	Double getRatioRangersWithCurriculaRegistered();

	@Query("select sum(case when (a.curriculum.endorserRecords is not empty) then 1.0 else 0.0 end)/count(a) FROM Ranger a")
	Double getRatioRangersWithCurriculumEndorsed();

	@Query("select sum(case when (a.suspicious=1) then 1.0 else 0.0 end)/count(a) FROM Ranger a")
	Double getRatioRangersSuspicious();

	/*
	 * An actor who is authenticated as a ranger must be able to:
	 * Manage his or her curriculum, which includes displaying, editing, and deleting it
	 */
	@Query("select c from Curriculum c where c.ranger=?1")
	Curriculum getCurriculum(int rangerId);

	/*
	 * The average, the minimum, the maximum,
	 * and the standard deviation of the number trips guided per ranger.
	 */
	@Query("select avg(r.trips.size) from Ranger r")
	Double getAverageTripsPerRanger();

	@Query("select min(r.trips.size) from Ranger r")
	Integer getMinimumTripsPerRanger();

	@Query("select max(r.trips.size) from Ranger r")
	Integer getMaximumTripsPerRanger();

	@Query("select sqrt(sum(r.trips.size*r.trips.size)/count(r.trips.size)-(avg(r.trips.size)*avg(r.trips.size)))" + " from Ranger r")
	Double getStandardDeviationTripsPerRanger();
}
