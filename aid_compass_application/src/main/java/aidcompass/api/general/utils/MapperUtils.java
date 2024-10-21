package aidcompass.api.general.utils;

import aidcompass.api.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

@Named("MapperUtils")
@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final PasswordEncoder passwordEncoder;

    @Named("formatCreatedDate")
    public String formatDate(Instant unformattedDate){
        Date date = Date.from(unformattedDate);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy", Locale.ENGLISH);
        return formatter.format(date);
    }

    @Named("encodePassword")
    public String encode(String password){
        return passwordEncoder.encode(password);
    }
}
