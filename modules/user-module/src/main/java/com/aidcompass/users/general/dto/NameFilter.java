package com.aidcompass.users.general.dto;

import com.aidcompass.core.general.contracts.dto.PageRequest;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
public class NameFilter extends PageRequest {
    @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
    @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$", message = "First name should contain only Ukrainian!")
    protected String firstName;

    @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
    @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$", message = "Second name should contain only Ukrainian!")
    protected String secondName;

    @Size(min = 2, max = 20, message = "Should has lengths from 2 to 20 characters!")
    @Pattern(regexp = "^[а-яА-ЯєЄїЇіІґҐ]{2,20}$", message = "Last name should contain only Ukrainian!")
    protected String lastName;

    public NameFilter() {
        super(0, 10);
    }

    public NameFilter(String secondName, String firstName, String lastName, Integer page, Integer size) {
        super(page, size);
        this.secondName = secondName;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
