package com.aidcompass.enums;


public class RoleToServiceTypeMapper {
    public static ServiceType from(Authority authority) {
        return switch (authority) {
            case ROLE_DOCTOR -> ServiceType.DOCTOR_SERVICE;
            case ROLE_JURIST -> ServiceType.JURIST_SERVICE;
            case ROLE_CUSTOMER -> ServiceType.CUSTOMER_SERVICE;
            default -> throw new IllegalArgumentException("Unsupported authority: " + authority);
        };
    }
}
