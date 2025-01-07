package aidcompass.api;

import aidcompass.api.doctor.DoctorRepository;
import aidcompass.api.doctor.appointment.DoctorAppointmentRepository;
import aidcompass.api.doctor.appointment.models.DoctorAppointmentEntity;
import aidcompass.api.doctor.models.DoctorEntity;
import aidcompass.api.security.models.Role;
import aidcompass.api.user.UserRepository;
import aidcompass.api.user.models.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Instant;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class AidCompassApplication {

    public static void main(String[] args) {
        SpringApplication.run(AidCompassApplication.class, args);
    }

    @Bean
    public CommandLineRunner testSet(UserRepository userRepository, DoctorRepository doctorRepository,
                                     DoctorAppointmentRepository doctorAppointmentRepository) {
        return args -> {
            List<UserEntity> userEntityList = List.of(
                    new UserEntity("john.doe@example.com", "passwordJohn123", "Іван Іванович Петренко",
                            "+380123456789", Role.ROLE_USER),
                    new UserEntity("jane.smith@example.com", "passwordJane456", "Олена Сергіївна Коваленко",
                            "+380123456790", Role.ROLE_USER),
                    new UserEntity("michael.jones@example.com", "passwordMichael789", "Сергій Олександрович Маленко",
                            null, Role.ROLE_USER),
                    new UserEntity("susan.adams@example.com", "passwordSusan101", "Наталія Василівна Бондаренко",
                            "+380123456791", Role.ROLE_USER),
                    new UserEntity("robert.brown@example.com", "passwordRobert102", "Олександр Миколайович Таран",
                            "+380123456792", Role.ROLE_USER),
                    new UserEntity("emma.white@example.com", "passwordEmma103", "Світлана Вікторівна Іванова",
                            null, Role.ROLE_USER),
                    new UserEntity("charles.wilson@example.com", "passwordCharles104", "Дмитро Олегович Павленко",
                            "+380123456793", Role.ROLE_USER),
                    new UserEntity("linda.clark@example.com", "passwordLinda105", "Юлія Петрівна Гаврилюк",
                            null, Role.ROLE_USER),
                    new UserEntity("david.miller@example.com", "passwordDavid106", "Віталій Олексійович Денисенко",
                            "+380123456794", Role.ROLE_USER),
                    new UserEntity("elizabeth.davis@example.com", "passwordElizabeth107", "Тетяна Сергіївна Миколаївна",
                            "+380123456795", Role.ROLE_USER)
            );
            userRepository.saveAll(userEntityList);

            List<DoctorEntity> doctorEntityList = List.of(
                    new DoctorEntity("ivan.petrenko@example.com", "Іван Іванович Петренко",
                            "+380123456789", true, Instant.now(), userRepository.findById(1L)
                            .orElseThrow(EntityNotFoundException::new),
                            "LIC12345", "Neurologist", 15, "Kyiv, Ukraine",
                            "Заслужений лікар України"),
                    new DoctorEntity("olena.kovalenko@example.com", "Олена Сергіївна Коваленко",
                            "+380123456790", true, Instant.now(), userRepository.findById(2L)
                            .orElseThrow(EntityNotFoundException::new),
                            "LIC23456", "Neurologist", 10, "Lviv, Ukraine",
                            "Автор декількох наукових публікацій"),
                    new DoctorEntity("serhiy.malenko@example.com", "Сергій Олександрович Маленко",
                            "+380123456791", false, Instant.now(), userRepository.findById(3L)
                            .orElseThrow(EntityNotFoundException::new),
                            "LIC34567", "Neurologist", 8, "Odesa, Ukraine",
                            "Лікар року 2021"),
                    new DoctorEntity("oleksandr.taran@example.com", "Олександр Миколайович Таран",
                            "+380123456792", false, Instant.now(), userRepository.findById(5L)
                            .orElseThrow(EntityNotFoundException::new),
                            "LIC45678", "Neurologist", 12, "Kharkiv, Ukraine",
                            "Професор хірургії"),
                    new DoctorEntity("svitlana.ivanova@example.com", "Світлана Вікторівна Іванова",
                            "+380123456793", true, Instant.now(), userRepository.findById(6L)
                            .orElseThrow(EntityNotFoundException::new),
                            "LIC56789", "Pediatrician", 9, "Dnipro, Ukraine",
                            "Найкращий дитячий лікар області")
            );
            doctorRepository.saveAll(doctorEntityList);

            List<DoctorAppointmentEntity> appointmentDtoList = List.of(
                    new DoctorAppointmentEntity(Instant.now().plusSeconds(3600), "Annual Check-Up",
                            "Routine annual health check.", userRepository.findById(3L).orElseThrow(EntityNotFoundException::new),
                            doctorRepository.findById(1L).orElseThrow(EntityNotFoundException::new)),
                    new DoctorAppointmentEntity(Instant.now().plusSeconds(7200), "Neurology Consultation",
                            "Consultation for neurological symptoms.", userRepository.findById(3L).orElseThrow(EntityNotFoundException::new),
                            doctorRepository.findById(2L).orElseThrow(EntityNotFoundException::new)),
                    new DoctorAppointmentEntity(Instant.now().plusSeconds(10800), "Follow-Up Visit",
                            "Follow-up visit for medication review.", userRepository.findById(3L).orElseThrow(EntityNotFoundException::new),
                            doctorRepository.findById(1L).orElseThrow(EntityNotFoundException::new)),
                    new DoctorAppointmentEntity(Instant.now().plusSeconds(14400), "Pediatric Consultation",
                            "Consultation regarding child's health.", userRepository.findById(3L).orElseThrow(EntityNotFoundException::new),
                            doctorRepository.findById(5L).orElseThrow(EntityNotFoundException::new)),
                    new DoctorAppointmentEntity(Instant.now().plusSeconds(18000), "Emergency Visit",
                            "Emergency visit for acute symptoms.", userRepository.findById(3L).orElseThrow(EntityNotFoundException::new),
                            doctorRepository.findById(2L).orElseThrow(EntityNotFoundException::new))
            );
            doctorAppointmentRepository.saveAll(appointmentDtoList);
        };
    }

}
