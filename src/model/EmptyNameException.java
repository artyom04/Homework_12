package model;

public class EmptyNameException extends Exception {
    public EmptyNameException() {
        super("Your input can't be empty!");
    }
}