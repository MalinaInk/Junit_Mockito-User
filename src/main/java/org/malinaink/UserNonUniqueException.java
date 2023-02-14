package org.malinaink;

public class UserNonUniqueException extends IllegalArgumentException{
    public UserNonUniqueException(String message) {
        super(message);
    }
}
