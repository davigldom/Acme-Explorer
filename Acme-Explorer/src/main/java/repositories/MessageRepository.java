
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select f.messages from Folder f where f.id=?1")
	Collection<Message> findAllInAFolder(int folderId);

	@Query("select f.messages from Actor a join a.folders f where a.id=?1")
	Collection<Message> findAllOfActor(int actorId);

	@Query("select actors from Message m join m.actors actors where m.id=?1 order by INDEX(actors)")
	List<Actor> findActors(int messageId);

	//@Query("select m from Message m where m.id=?1")
	//Message findOne(int messageId);

	//	@Query("select m.sender from Message m where m.id=?1")
	//	Actor findSender(int messageId);
	//
	//	@Query("select m.recipient from Message m where m.id=?1")
	//	Actor findRecipient(int messageId);

}
