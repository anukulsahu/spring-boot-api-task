package com.example.demo.dto;

import com.example.demo.model.Speciality;
import lombok.Data;

@Data
public class DoctorDTO {
    private String name;
    private String city;
    private String email;
    private String phone;
    private Speciality speciality;
}
