package biz.advanceitgroup.taxirideserver.carpooling.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.PickUpBookingInformations;

public interface PickUpBookingInformationsRepository extends JpaRepository<PickUpBookingInformations, Long> {

}
