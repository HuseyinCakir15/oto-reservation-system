package com.otores.api.repository;

import com.otores.api.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.time.*;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
List<Reservation> findByUser_Id(int userId);
List<Reservation> findByVehicle_Id(int vehicleId);
boolean existsByVehicle_IdAndResDateAndResTime(int vehicleId, LocalDate resDate, LocalTime resTime);
List<Reservation> findByResDate(LocalDate resDate);
List<Reservation> findByResDateBetween(LocalDate startDate, LocalDate endDate);
List<Reservation> findByUser_IdAndVehicle_Id(int userId, int vehicleId);
}
