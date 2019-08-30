package com.example.genericrecycleradapter;

class NoTagsFoundedException extends Exception {

    private static final long serialVersionUID = -3724209362984251230L;

    NoTagsFoundedException(String message) {
        super(message);
    }
}