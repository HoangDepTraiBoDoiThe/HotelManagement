package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT DISTINCT r FROM Room r JOIN FETCH r.reservations where r.id = :id")
    Optional<Room> findWithReservationsById(Long id);
    
    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.reservations")
    List<Room> findAllWithReservations();
}
