package com.example.demo.service;

import com.example.demo.dto.DoctorDTO;
import com.example.demo.model.Doctor;
import com.example.demo.model.Speciality;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(doctorDTO.getName());
        doctor.setCity(doctorDTO.getCity());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setSpeciality(doctorDTO.getSpeciality());
        return doctorRepository.save(doctor);
    }

    public void removeDoctorByEmail(String email) {
        doctorRepository.findByEmail(email).ifPresent(doctorRepository::delete);
    }

    public void removeDoctorByPhone(String phone) {
        doctorRepository.findByPhone(phone).ifPresent(doctorRepository::delete);
    }

    public List<Doctor> findDoctorsBySpeciality(Speciality speciality) {
        return doctorRepository.findBySpeciality(speciality);
    }

    public List<Doctor> findDoctorsByCity(String city) {
        return doctorRepository.findByCity(city);
    }
}
