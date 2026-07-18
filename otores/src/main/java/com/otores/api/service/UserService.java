package com.otores.api.service;

import com.otores.api.entity.Status;
import com.otores.api.entity.User;
import com.otores.api.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import java.util.List;


import com.otores.api.entity.Role;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final VehicleService vehicleService;

    public User createUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("This email already has been taken! " + user.getEmail());
        }
        if (userRepository.existsByPhone(user.getPhone())){
            throw new IllegalArgumentException("This phone number already has been taken! " + user.getPhone());
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User was not found by Id: " + id));
    }

    public List<User> getUserByRole(Role role){
        return userRepository.findByRole(role);
    }
    public User getUserByPhone(String phone){
        return userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException("User was not found by phone: " + phone));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User was not found by email: " + email));
    }

    public List<User> getUserByStatus(Status status){
        return userRepository.findByStatus(status);
    }

    public User updateUserStatus(int id, Status status){
        User user = getUserById(id);
        user.setStatus(status);
        userRepository.save(user);

        if(status != Status.ACTIVE){
            vehicleService.deactivateVehiclesByUser(id);
        }

        return user;
    }
}
