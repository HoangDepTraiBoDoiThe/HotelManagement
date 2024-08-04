package com.example.hotelmanagement.room.service;

import com.example.hotelmanagement.room.model.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    
    
}
