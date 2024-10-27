package aidcompass.api.mail;

import lombok.experimental.UtilityClass;
import java.security.SecureRandom;

@UtilityClass
public class MailUtils {

    public String generateConfirmKey() {
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        int length = 6;
        for (int i = 0; i < length; i++)
            result.append(random.nextInt(10));
        return result.toString();
    }

    public boolean validateConfirmKey(String currentKey, String originKey){
        return currentKey.equals(originKey);
    }

}
