package edu.mum.waa.exceptions;

public class StudentException extends Exception {

    private String message;

    public StudentException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
