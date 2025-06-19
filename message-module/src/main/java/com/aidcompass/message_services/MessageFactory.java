package com.aidcompass.message_services;

import com.aidcompass.contracts.ScheduledAppointmentInfo;
import com.aidcompass.contracts.UserInfo;
import com.aidcompass.message_services.configs.MessageConfig;
import com.aidcompass.message_services.models.MessageDto;
import org.springframework.stereotype.Service;

@Service
public class MessageFactory {

    public static MessageDto accountConfirmation(String resource, String code) {
        return new MessageDto(resource, "Підтвердження акаунту",
                MessageConfig.ACCOUNT_CONFIRMATION.formatted(code)
        );
    }

    public static MessageDto resourceConfirmation(String resource, String code) {
        return new MessageDto(resource, "Підтвердження пошти",
                MessageConfig.RESOURCE_CONFIRMATION.formatted(code, "вашої електронної пошти")
        );
    }

    public static MessageDto passwordRecovery(String resource, String code) {
        return new MessageDto(resource, "Відновлення паролю",
                MessageConfig.PASS_RECOVERY.formatted(code)
        );
    }

    public static MessageDto greeting(UserInfo info) {
        return new MessageDto(info.contact(), "Акаунт підтверджено!",
                MessageConfig.GREETING.formatted(info.firstName(), info.secondName())
        );
    }

    public static MessageDto customerAppointmentScheduled(ScheduledAppointmentInfo info) {
        return new MessageDto(info.customerContact(), "Запис на консультацію створено!",
                MessageConfig.CUSTOMER_APPOINTMENT_SCHEDULED_INFORMATION.formatted(
                        info.customerFirstName(), info.customerLastName(), info.appointmentType(),
                        info.volunteerType(), info.volunteerFirstName(), info.volunteerSecondName(),
                        info.appointmentDate()
                )
        );
    }

    public static MessageDto volunteerAppointmentScheduled(ScheduledAppointmentInfo info) {
        return new MessageDto(info.customerContact(), "У Вас новий запис на консультацію!",
                MessageConfig.VOLUNTEER_APPOINTMENT_SCHEDULED_INFORMATION.formatted(
                        info.volunteerFirstName(), info.volunteerLastName(),
                        info.customerFirstName(), info.customerSecondName(),
                        info.appointmentType(), info.appointmentDate(), info.appointmentDescription()
                )
        );
    }
}
