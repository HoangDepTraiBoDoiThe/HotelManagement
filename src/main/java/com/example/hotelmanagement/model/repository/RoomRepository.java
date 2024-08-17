package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT DISTINCT r FROM Room r join fetch r.roomTypes where r.id = :id")
    Optional<Room> findWithRoomTypesById(@Param("id") Long id);
    
    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.reservations")
    List<Room> findAllWithReservations();
}
