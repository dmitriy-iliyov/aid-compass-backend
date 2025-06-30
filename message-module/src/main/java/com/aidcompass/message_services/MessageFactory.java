package com.aidcompass.message_services;

import com.aidcompass.dto.ReminderAppointmentDto;
import com.aidcompass.dto.ScheduledAppointmentDto;
import com.aidcompass.dto.UserDto;
import com.aidcompass.message_services.configs.MessageConfig;
import com.aidcompass.message_services.models.MessageDto;
import org.springframework.stereotype.Service;

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

    public static MessageDto greeting(UserDto dto) {
        return new MessageDto(dto.contact(), "Акаунт підтверджено!",
                MessageConfig.GREETING.formatted(dto.firstName(), dto.secondName())
        );
    }

    public static MessageDto customerAppointmentScheduled(ScheduledAppointmentDto dto) {
        return new MessageDto(dto.customer().contact(), "Запис на консультацію створено!",
                MessageConfig.CUSTOMER_APPOINTMENT_SCHEDULED_INFORMATION.formatted(
                        dto.customer().firstName(), dto.customer().lastName(), dto.appointment().type(),
                        dto.volunteerType(), dto.volunteer().firstName(), dto.volunteer().secondName(),
                        dto.appointment().date()
                )
        );
    }

    public static MessageDto volunteerAppointmentScheduled(ScheduledAppointmentDto dto) {
        return new MessageDto(dto.customer().contact(), "У Вас новий запис на консультацію!",
                MessageConfig.VOLUNTEER_APPOINTMENT_SCHEDULED_INFORMATION.formatted(
                        dto.volunteer().firstName(), dto.volunteer().lastName(),
                        dto.customer().firstName(), dto.customer().secondName(),
                        dto.appointment().type(), dto.appointment().date(), dto.appointment().description()
                )
        );
    }

    public static MessageDto customerReminder(ReminderAppointmentDto dto) {
        return new MessageDto(dto.customer().contact(), "Нагадування про консультацію!",
                MessageConfig.CUSTOMER_APPOINTMENT_REMINDER_INFORMATION.formatted(
                        dto.customer().firstName(), dto.customer().secondName(),
                        dto.appointment().date(), dto.appointment().type()
                )
        );
    }
}
