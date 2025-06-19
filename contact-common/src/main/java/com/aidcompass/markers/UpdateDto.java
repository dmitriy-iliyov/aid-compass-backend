package com.aidcompass.markers;


import com.aidcompass.enums.ContactType;

public interface UpdateDto {
    Long id();
    ContactType type();
    String contact();
    boolean isPrimary();
}
