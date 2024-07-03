package com.example.demo.controller;

import com.example.demo.dto.PatientDTO;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Speciality;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
@Tag(name = "Patient API", description = "API for managing patients and suggesting doctors")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    private static final Map<String, Speciality> symptomSpecialityMap = new HashMap<>();

    static {
        symptomSpecialityMap.put("arthritis", Speciality.ORTHO);
        symptomSpecialityMap.put("back pain", Speciality.ORTHO);
        symptomSpecialityMap.put("tissue injuries", Speciality.ORTHO);
        symptomSpecialityMap.put("dysmenorrhea", Speciality.GYNE);
        symptomSpecialityMap.put("skin infection", Speciality.DERMA);
        symptomSpecialityMap.put("skin burn", Speciality.DERMA);
        symptomSpecialityMap.put("ear pain", Speciality.ENT);
    }

    @Operation(summary = "Add a new patient",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the patient to be added",
                    content = @Content(schema = @Schema(implementation = PatientDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Patient added successfully", content = @Content(schema = @Schema(implementation = Patient.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping
    public Patient addPatient(@RequestBody PatientDTO patientDTO) {
        return patientService.addPatient(patientDTO);
    }

    @Operation(summary = "Suggest doctors for a patient by ID",
            parameters = {
                    @Parameter(name = "patientId", description = "ID of the patient", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Suggested doctors based on patient's symptom", content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Patient not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/suggest-doctors")
    public String suggestDoctors(@RequestParam Long patientId) {
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            return "Patient With the Given ID not found.";
        }

        Speciality speciality = symptomSpecialityMap.get(patient.getSymptom().toLowerCase());
        if (speciality == null) {
            return "Invalid Symptom";
        }

        List<Doctor> localDoctors = doctorService.findDoctorsByCity(patient.getCity());
        if (localDoctors.isEmpty()) {
            return "We are still waiting to expand to your city.";
        }

        List<Doctor> filteredLocalDoctors = localDoctors.stream()
                .filter(doctor -> doctor.getSpeciality().equals(speciality))
                .collect(Collectors.toList());

        if (filteredLocalDoctors.isEmpty()) {
            return "There isn't any doctor present at your location for your symptom.";
        }

        return filteredLocalDoctors.toString();
    }
}
