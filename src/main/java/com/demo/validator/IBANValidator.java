package com.demo.validator;

public class IBANValidator {
    private final int length;

    public IBANValidator(int length) {
        this.length = length;
    }

    public boolean isValid(String iban) {
        return iban != null && iban.length() == length;
    }

}
