package com.carebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carebridge.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByPatientId(Long patientId);

}