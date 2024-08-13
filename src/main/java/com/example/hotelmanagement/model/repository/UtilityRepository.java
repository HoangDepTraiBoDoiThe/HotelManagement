package com.example.hotelmanagement.model.repository;

import com.example.hotelmanagement.model.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {
}
