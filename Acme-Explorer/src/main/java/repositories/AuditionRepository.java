
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audition;

@Repository
public interface AuditionRepository extends JpaRepository<Audition, Integer> {

	//Requirement 30.2
	@Query("select t.auditions from Trip t where t.id=?1")
	Collection<Audition> findByTripId(int tripId);

	//Requirement 33.2
	@Query("select a.auditions from Auditor a where a.id=?1")
	Collection<Audition> findByAuditorId(int auditorId);

	//Requirement 35.4
	@Query("select avg(t.auditions.size) from Trip t")
	Double averageAuditsPerTrip();

	@Query("select min(t.auditions.size) from Trip t")
	Double minimumAuditsPerTrip();

	@Query("select max(t.auditions.size) from Trip t")
	Double maximumAuditsPerTrip();

	@Query("select sqrt(sum(t.auditions.size*t.auditions.size)/count(t.auditions.size)-(avg(t.auditions.size)*avg(t.auditions.size))) from Trip t")
	Double standardDerivationAuditsPerTrip();

}
