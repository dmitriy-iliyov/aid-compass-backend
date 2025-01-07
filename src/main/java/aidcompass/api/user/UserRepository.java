package aidcompass.api.user;

import aidcompass.api.user.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByNumber(String number);

    boolean existsByEmail(String email);

    boolean existsByNumber(String number);

    void deleteByEmail(String email);
}
