package aidcompass.api.doctor;


import aidcompass.api.doctor.models.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    Optional<DoctorEntity> findByUsername(String username);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByEmail(String email);

    Iterable<DoctorEntity> findAllByApproved(boolean approved);

    Iterable<DoctorEntity> findAllByApprovedAndSpecialization(boolean approved, String specialization);

    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findByNumber(String number);

    Optional<DoctorEntity> findByLicenseNumber(String number);
}
