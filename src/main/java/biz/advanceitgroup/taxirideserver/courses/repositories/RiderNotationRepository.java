package biz.advanceitgroup.taxirideserver.courses.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biz.advanceitgroup.taxirideserver.courses.entities.RiderNotation;

@Repository
public interface RiderNotationRepository  extends JpaRepository<RiderNotation, Long> {

}
