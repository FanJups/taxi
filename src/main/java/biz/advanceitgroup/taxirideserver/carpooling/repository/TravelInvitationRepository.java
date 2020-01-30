/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.repository;

import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelInvitation;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daniel
 */
@Repository
public interface TravelInvitationRepository extends JpaRepository<TravelInvitation, Long>{
    @Transactional
    @Query(value = "SELECT * FROM `travel_invitation` WHERE `travel_id`=?1 AND (`departure_date` BETWEEN ?2 AND ?3) AND `invitation_status`=?3", nativeQuery = true)
    public List<TravelInvitation> findAllLongDistanceTripInvitation(long travelID, Date startDate,Date endDate, int bookingStatus);
    
    @Transactional
    @Query(value = "SELECT * FROM `travel_invitation` WHERE `rider_id`=?1 AND `invitation_status`=?2",nativeQuery = true)
    public List<TravelInvitation> findTravelInvitationByRiderAndStatusId(long riderId,int status);
}
