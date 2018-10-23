
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.auditor.id=?1 and n.trip.manager.id=?2")
	Collection<Note> findByAuditorIdAndManagerId(int idAuditor, int idManager);

	@Query("select n from Note n where n.auditor.id=?1")
	Collection<Note> findByAuditorId(int idAuditor);

	@Query("select n from Note n where n.trip.id=?1")
	Collection<Note> findByTripId(int idTrip);

	@Query("select avg(t.notes.size) from Trip t")
	double findAverage();

	@Query("select max(t.notes.size) from Trip t")
	double findMax();

	@Query("select min(t.notes.size) from Trip t")
	double findMin();

	@Query("select sqrt(sum(t.notes.size*t.notes.size)/count(t.notes.size)-(avg(t.notes.size)*avg(t.notes.size))) from Trip t")
	double findStandardDeviation();

}
