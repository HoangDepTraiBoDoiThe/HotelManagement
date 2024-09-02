package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAllByFloorNumber(Pageable pageable);
    
    @Query("SELECT DISTINCT r FROM Room r join fetch r.roomTypes where r.id = :id")
    Optional<Room> findWithRoomTypesById(@Param("id") Long id);
    
    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.roomReservations")
    List<Room> findAllWithReservations();
    
    @EntityGraph(attributePaths = "roomTypes")
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.roomReservations where r.id = :id")
    Optional<Room> findAllWithReservations(@Param("id") long id);
}
