package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @EntityGraph(attributePaths = "reservations")
    Optional<Room> findWithReservationsById(Long id);
    @EntityGraph(attributePaths = "reservations")
    List<Room> findAllWithReservations();
}
