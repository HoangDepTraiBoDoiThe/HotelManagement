package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.RoomReservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    
    @Query("SELECT CASE WHEN COUNT (rr) > 0 THEN true ELSE FALSE END " +
    "FROM RoomReservation rr WHERE rr.room = :id AND rr.checkout > current date"
    )
    public boolean isRoomOccupied(@Param("id") long id);    
    
    @EntityGraph(attributePaths = "reservation")
    @Query("SELECT rr FROM RoomReservation rr JOIN FETCH rr.room r JOIN FETCH rr.reservation res WHERE rr.room = :id")
    public Optional<RoomReservation> getRoomReservationByRoomId(@Param("id") long id);
}
