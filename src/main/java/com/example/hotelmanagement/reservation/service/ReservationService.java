package com.example.hotelmanagement.reservation.service;

import com.example.hotelmanagement.reservation.dto.request.ReservationRequest;
import com.example.hotelmanagement.reservation.exception.ReservationException;
import com.example.hotelmanagement.reservation.model.Reservation;
import com.example.hotelmanagement.reservation.model.repository.ReservationRepository;
import com.example.hotelmanagement.user.exception.UserException;
import com.example.hotelmanagement.user.model.User;
import com.example.hotelmanagement.user.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public Reservation getReservation(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationException("Reservation not found"));
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
//        TODO: a room shouldnt be able to be in 2 active reservations at the same time.  

        User user = userRepository.findById(reservationRequest.getOwnerId()).orElseThrow(() -> new UserException("User not found"));
        Reservation reservation = new Reservation(reservationRequest.getCheckIn(), reservationRequest.getCheckOut(), reservationRequest.getTotalPrice(), user);
        
        Reservation newReservation = reservationRepository.save(reservation);
        return newReservation;
    }
    
    public Reservation updateReservation(long id, ReservationRequest reservationRequest) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow(() -> new ReservationException("Reservation not found"));
        reservationToUpdate.setCheckIn(reservationRequest.getCheckIn());
        reservationToUpdate.setCheckOut(reservationRequest.getCheckOut());
        reservationToUpdate.setTotalPrice(reservationRequest.getTotalPrice());
        
        return reservationRepository.save(reservationToUpdate);
    }
    
    public void deleteReservation(long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ReservationException("Reservation not found"));
        reservationRepository.delete(reservation);
    }
}
