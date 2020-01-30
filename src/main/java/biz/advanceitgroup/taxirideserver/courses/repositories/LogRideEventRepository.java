package biz.advanceitgroup.taxirideserver.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.LogRideEvent;

@Repository
public interface LogRideEventRepository extends JpaRepository<LogRideEvent, Long> {

}
