package com.otores.api.service;

import com.otores.api.repository.VehicleRepository;
import com.otores.api.entity.Vehicle;
import jakarta.transaction.Transactional;
import lombok.*;
import com.otores.api.entity.VehicleStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public Vehicle createVehicle(Vehicle vehicle){
        if(vehicleRepository.existsByPlate(vehicle.getPlate())){
            throw new IllegalArgumentException("This plate has taken by other car: " + vehicle.getPlate());
        }
        if(vehicleRepository.existsByChassisNo(vehicle.getChassisNo())){
            throw new IllegalArgumentException("This chassis no has taken by other car: " + vehicle.getChassisNo());
        }
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(int id){
        return vehicleRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Not founden any vehicle by id: " + id));
    }

    public List<Vehicle> getVehicleByUserId(int id){
        return vehicleRepository.findByUser_Id(id);
    }

    public Vehicle getVehicleByPlate(String plate){
        return vehicleRepository.findByPlate(plate).orElseThrow(() -> new IllegalArgumentException("No vehicle was found with this license plate."));
    }

    public List<Vehicle> getVehicleByBrand(String brand){
        return vehicleRepository.findByBrand(brand);
    }

    public List<Vehicle> getVehicleByBrandAndModel(String brand, String model){
        return vehicleRepository.findByBrandAndModel(brand, model);
    }

    public List<Vehicle> getVehiclesByYearGreaterThanEqual(int year) {
        return vehicleRepository.findByYearGreaterThanEqual(year);
    }

    public List<Vehicle> getVehiclesByYearLessThanEqual(int year) {
        return vehicleRepository.findByYearLessThanEqual(year);
    }

    public List<Vehicle> getVehiclesByYearBetween(int start, int end) {
        if (start > end){
            throw new IllegalArgumentException("Start year can not be greater than end year");
        }
        return vehicleRepository.findByYearBetween(start, end);
    }

    public List<Vehicle> getVehiclesByUserAndStatus(int userId, VehicleStatus status) {
        return vehicleRepository.findByUser_IdAndStatus(userId, status);
    }

    @Transactional
    public void deactivateVehiclesByUser(int userId){
        List<Vehicle> vehicles = vehicleRepository.findByUser_Id(userId);
        for (Vehicle vehicle : vehicles){
            vehicle.setStatus(VehicleStatus.PASSIVE);
        }
        vehicleRepository.saveAll(vehicles);
    }
}
