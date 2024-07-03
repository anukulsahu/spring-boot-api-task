package com.example.demo.controller;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public Doctor addDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.addDoctor(doctorDTO);
    }

    @DeleteMapping("/email")
    public void removeDoctorByEmail(@RequestParam String email) {
        doctorService.removeDoctorByEmail(email);
    }

    @DeleteMapping("/phone")
    public void removeDoctorByPhone(@RequestParam String phone) {
        doctorService.removeDoctorByPhone(phone);
    }
}
