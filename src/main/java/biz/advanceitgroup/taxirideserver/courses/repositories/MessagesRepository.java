package biz.advanceitgroup.taxirideserver.courses.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import biz.advanceitgroup.taxirideserver.courses.entities.Messages;

@Repository
public interface MessagesRepository  extends JpaRepository<Messages, Long> {
	@Query(value ="SELECT * FROM messages WHERE MSG_TYPE=?", nativeQuery = true)
	Optional<Messages> findAllNotificationMessageForTrip(int type);
}
