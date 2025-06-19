package com.aidcompass.security.csrf;

public interface CsrfMasker {

    String mask(String csrfToken) throws Exception;

}
