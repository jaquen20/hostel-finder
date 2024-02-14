package com.example.best.service;

import com.example.best.dto.HostelDto;
import com.example.best.exception.HostelsNotFoundException;
import com.example.best.exception.UserNotFoundException;
import com.example.best.model.Hostels;
import com.example.best.model.Users;
import com.example.best.repository.HostelRepository;
import com.example.best.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HostelService {
    @Autowired
    HostelRepository hostelRepository;
    @Autowired
    UserRepository userRepository;
    public List<Hostels> getAllData(String auth) {
        Users user = userRepository.findByEmail(auth);
        if (user != null) {
            return hostelRepository.findByUser(user);
        } else {
            return Collections.emptyList();
        }
    }

    public Optional<Hostels> getRoomById(String auth, Long id) {
        Users user = userRepository.findByEmail(auth);

        if (user != null) {
            return hostelRepository.findByIdAndUser(id, user);
        } else {
            return Optional.empty();
        }
        //return hostelRepository.findById(id);
    }

    public void saveRoom(String username, Hostels roomInfo) {
        Users user = userRepository.findByEmail(username);
        if (user!=null) {
            Hostels room = new Hostels();
            room.setName(roomInfo.getName());
            room.setDescription(roomInfo.getDescription());
           room.setCity(roomInfo.getCity());
            room.setType(roomInfo.getType());
            room.setMobileNo(roomInfo.getMobileNo());
            room.setAddress(roomInfo.getAddress());
            room.setRent(roomInfo.getRent());
            room.setProfileImage(roomInfo.getProfileImage());
            room.setUser(user);

             hostelRepository.save(room);

        }else {
            // Handle the case when the user is not found
            throw new UserNotFoundException("User with email " + username + " not found");
        }


    }

    public void updateRoom(String username, Hostels roomInfo) {
        Users user = userRepository.findByEmail(username);

        if (user != null) {

            Hostels existingRoom = hostelRepository.findById(roomInfo.getId())
                    .orElseThrow(() -> new HostelsNotFoundException("Room with id " + roomInfo.getId() + " not found"));


            existingRoom.setName(roomInfo.getName());
            existingRoom.setDescription(roomInfo.getDescription());
            existingRoom.setCity(roomInfo.getCity());
            existingRoom.setType(roomInfo.getType());
            existingRoom.setMobileNo(roomInfo.getMobileNo());
            existingRoom.setAddress(roomInfo.getAddress());
            existingRoom.setRent(roomInfo.getRent());
            existingRoom.setProfileImage(roomInfo.getProfileImage());


            hostelRepository.save(existingRoom);
        } else {

            throw new UserNotFoundException("User with email " + username + " not found");
        }
    }





    public void deleteRoom(Long id) {
        hostelRepository.deleteById(id);
    }
    public byte[] getImageDataByName(String imageName) {
        try {
            Path imagePath = Paths.get("src/main/resources/static/images/" + imageName);
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            // Handle exceptions appropriately
            return null;
        }
    }

    public List<Hostels> getData() {
      return   hostelRepository.findAll();
    }

    public Optional<Hostels> getDataById(Long id) {
        return hostelRepository.findById(id);
    }




    public HostelDto convertToDto(Hostels hostels) {
        HostelDto dto = new HostelDto();
        dto.setId(hostels.getId());
        dto.setName(hostels.getName());
        dto.setAddress(hostels.getAddress());
        dto.setCity(hostels.getCity());
        dto.setRent(hostels.getRent());
        dto.setType(hostels.getType());
        dto.setDescription(hostels.getDescription());
        dto.setProfileImage(hostels.getProfileImage());

       // dto.setReviews(hostels.getReviews());

        return dto;
    }

    public void saveReviews(Hostels details) {
        hostelRepository.save(details);
    }
}
