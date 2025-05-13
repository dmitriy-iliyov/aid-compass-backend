package com.aidcompass.doctor;


import com.aidcompass.doctor.models.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, UUID> {

    @Modifying
    @Query("UPDATE DoctorEntity d SET d.address = :address WHERE d.id = :id")
    void updateAddressById(@Param("id") UUID id, @Param("address") String address);

    @Modifying
    @Query("UPDATE DoctorEntity d SET d.approved = :approve_status WHERE d.id = :id")
    void approveById(@Param("id") UUID id, @Param("approve_status") boolean approveStatus);

    List<DoctorEntity> findAllByApproved(boolean approved);

    @Query("SELECT d FROM DoctorEntity d JOIN FETCH d.specializations WHERE d.id = :id")
    Optional<DoctorEntity> findWithSepcsById(@Param("id") UUID id);
}
