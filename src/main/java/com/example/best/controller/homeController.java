package com.example.best.controller;

import com.example.best.dto.HostelDto;
import com.example.best.model.Hostels;
import com.example.best.model.Review;
import com.example.best.model.Users;
import com.example.best.service.HostelService;
import com.example.best.service.ReviewService;
import com.example.best.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class homeController {


    @Autowired
    UserService userService;

    @Autowired
    HostelService roomService;



    @GetMapping("/index")
    public String home(){
        return "index";
    }
    @GetMapping("/login")
    public String AdminLogin(){
        return "AdminLogin";
    }

    @GetMapping("/registration")
    public String AdminRegistration(Model model){
        Users adminDto=new Users();
        model.addAttribute("user",adminDto);
        return "registration";
    }


    @PostMapping("/registration/save")
    public String AdminRegistration(@Validated @ModelAttribute("user") Users user,
                                    BindingResult result,
                                    Model model){
        Users existingUser=userService.findUserByEmail(user.getEmail());
        if (existingUser!=null && existingUser.getEmail()!=null &&!existingUser.getEmail().isEmpty()){
            result.rejectValue("email",null,"user with this email already registered");

        }
        if (result.hasErrors()){
            model.addAttribute("user",user);
            return "registration";
        }

        userService.saveUser(user);
        return "redirect:/registration?success";
    }

    @GetMapping("/AdminHome")
    public String AdminHome(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.getName();
        model.addAttribute("roomList",roomService.getAllData(auth));
        return "AdminHome";
    }

    @GetMapping("/edit/{id}")
    public String UpdateRoomInfo(@PathVariable Long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.getName();
        Optional<Hostels> roomInfo=roomService.getRoomById(auth,id);
        model.addAttribute("roomInfo",roomInfo);


        return "RoomUpdate";
    }
    @GetMapping("/edit/AddRoom")
    public String RoomForm(Model model){
        model.addAttribute("rooms", new Hostels());
        return "RoomAdd";
    }

    @PostMapping(value = "/saveRoom",consumes = {"multipart/form-data"})
    public String AddRoom(Hostels roomInfo, @RequestParam("imageFile") MultipartFile multipartFile, Model model)throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.getName();
        if (!multipartFile.isEmpty()) {
            String fileName = saveImage(multipartFile);
            roomInfo.setProfileImage(fileName);

            roomService.saveRoom(auth,roomInfo);
            return "redirect:/AdminHome";
        }
        else {

            model.addAttribute("error", "Please select an image file.");
            return "RoomAdd";
        }
    }

    @GetMapping("/delete/{id}")
    public String DeleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
        return "redirect:/AdminHome";
    }

    @PostMapping(value = "/saveUpdatedRoom", consumes = {"multipart/form-data"})
    public String UpdateRoom(Hostels roomInfo, @RequestParam("imageFile") MultipartFile multipartFile, Model model) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String auth = authentication.getName();


        Optional<Hostels> existingRoomOptional = roomService.getRoomById(auth, roomInfo.getId());

        if (existingRoomOptional.isPresent()) {
            Hostels existingRoom = existingRoomOptional.get();


            existingRoom.setName(roomInfo.getName());
            existingRoom.setCity(roomInfo.getCity());
            existingRoom.setAddress(roomInfo.getAddress());
            existingRoom.setMobileNo(roomInfo.getMobileNo());
            existingRoom.setDescription(roomInfo.getDescription());
            existingRoom.setType(roomInfo.getType());
            existingRoom.setRent(roomInfo.getRent());

            if (!multipartFile.isEmpty()) {
                String fileName = saveImage(multipartFile);
                existingRoom.setProfileImage(fileName);
            }


            roomService.updateRoom(auth, existingRoom);

            return "redirect:/AdminHome";
        } else {
            model.addAttribute("error", "Room not found");
            return "RoomUpdate";
        }
    }



    private String saveImage(MultipartFile imageFile) throws IOException {

        String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        String imagePath = "src/main/resources/static/images" + File.separator + filename;

        Files.copy(imageFile.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }





}
