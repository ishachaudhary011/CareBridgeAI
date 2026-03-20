package com.carebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carebridge.entity.Doctor;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByDepartmentId(Long departmentId);

}