package com.otores.api.repository;

import com.otores.api.entity.VehicleStatus;
import com.otores.api.entity.VehicleStatus;
import com.otores.api.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
List<Vehicle> findByUser_Id(int userId);
Optional<Vehicle> findByPlate(String Plate);
boolean existsByPlate(String plate);
boolean existsByChassisNo(String chassisNo);
List<Vehicle> findByBrand(String brand);
List<Vehicle> findByBrandAndModel(String brand, String model);
List<Vehicle> findByYearGreaterThanEqual(int year);
List<Vehicle> findByYearLessThanEqual(int year);
List<Vehicle> findByYearBetween(int start, int end);
List<Vehicle> findByUser_IdAndStatus(int id, VehicleStatus status);
}
