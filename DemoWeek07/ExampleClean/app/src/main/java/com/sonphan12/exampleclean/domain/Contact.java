package com.sonphan12.exampleclean.domain;

public class Contact {
    private String name;
    private boolean hasPhoneNumber;
    private String phoneNumber;

    public Contact() {
    }

    public Contact(String name, boolean hasPhoneNumber, String phoneNumber) {
        this.name = name;
        this.hasPhoneNumber = hasPhoneNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(boolean hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
