package com.example.demo.repository;

import com.example.demo.model.Doctor;
import com.example.demo.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByPhone(String phone);
    List<Doctor> findBySpeciality(Speciality speciality);
    List<Doctor> findByCity(String city);
}

