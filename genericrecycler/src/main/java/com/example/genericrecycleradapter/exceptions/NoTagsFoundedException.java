package com.example.genericrecycleradapter.exceptions;

public class NoTagsFoundedException extends Exception {

    private static final long serialVersionUID = -3724209362984251230L;

    public NoTagsFoundedException(String message) {
        super(message);
    }
}