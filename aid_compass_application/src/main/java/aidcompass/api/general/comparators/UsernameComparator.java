package aidcompass.api.general.comparators;

import aidcompass.api.user.models.dto.UserResponseDto;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class UsernameComparator implements Comparator<UserResponseDto> {

    @Override
    public int compare(UserResponseDto o1, UserResponseDto o2) {
        return o1.getUsername().compareTo(o2.getUsername());
    }
}
