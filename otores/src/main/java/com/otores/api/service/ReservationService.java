package com.otores.api.service;

import com.otores.api.entity.Reservation;
import com.otores.api.entity.User;
import com.otores.api.entity.Vehicle;
import com.otores.api.entity.Status;
import com.otores.api.entity.VehicleStatus;
import com.otores.api.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final VehicleService vehicleService;

    @Transactional
    public Reservation createReservation(int userId, int vehicleId, LocalDate resDate, LocalTime resTime) {

        User user = userService.getUserById(userId);
        if (user.getStatus() != Status.ACTIVE) {
            throw new IllegalStateException("Bu kullanıcı aktif olmadığı için rezervasyon oluşturamaz.");
        }

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle.getStatus() != VehicleStatus.ACTIVE) {
            throw new IllegalStateException("Bu araç aktif olmadığı için rezerve edilemez.");
        }

        boolean alreadyBooked = reservationRepository
                .existsByVehicle_IdAndResDateAndResTime(vehicleId, resDate, resTime);
        if (alreadyBooked) {
            throw new IllegalStateException("Bu araç bu tarih ve saatte zaten rezerve edilmiş.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setVehicle(vehicle);
        reservation.setResDate(resDate);
        reservation.setResTime(resTime);

        return reservationRepository.save(reservation);
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rezervasyon bulunamadı, id: " + id));
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(int userId) {
        return reservationRepository.findByUser_Id(userId);
    }

    public List<Reservation> getReservationsByVehicle(int vehicleId) {
        return reservationRepository.findByVehicle_Id(vehicleId);
    }

    public List<Reservation> getReservationsByDate(LocalDate resDate) {
        return reservationRepository.findByResDate(resDate);
    }

    public List<Reservation> getReservationsByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }
        return reservationRepository.findByResDateBetween(startDate, endDate);
    }

    public List<Reservation> getReservationsByUserAndVehicle(int userId, int vehicleId) {
        return reservationRepository.findByUser_IdAndVehicle_Id(userId, vehicleId);
    }

    public void deleteReservation(int id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }

}