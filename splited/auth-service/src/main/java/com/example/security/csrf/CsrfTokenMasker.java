package com.example.security.csrf;

public interface CsrfTokenMasker {

    String mask(String csrfToken) throws Exception;

}
