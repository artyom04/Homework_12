package model;

import service.EncryptionService;
import service.FileService;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private static final String EMAIL_VERIFICATION = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String PASSWORD_VERIFICATION = "^(?=(?:.*?[A-Z]){2})(?=(?:.*?[0-9]){3}).*$";

    private FullName fullName;
    private String username;
    private String email;
    private String password;

    private static class FullName {
        private String name;
        private String surname;

        public FullName(String name, String surname) throws EmptyNameException {
            setName(name);
            setSurname(surname);
        }

        public void setName(String name) throws EmptyNameException {
            if (!name.isEmpty()) {
                this.name = name;
            } else {
                throw new EmptyNameException();
            }
        }

        public void setSurname(String surname) throws EmptyNameException {
            if (!surname.isEmpty()) {
                this.surname = surname;
            } else {
                throw new EmptyNameException();
            }
        }

        @Override
        public String toString() {
            return this.name + " " + this.surname;
        }
    }

    public User() {

    }

    public void setFullName(String name, String surname) throws EmptyNameException {
        this.fullName = new FullName(name, surname);
    }

    public void setUsername(String username) throws Exception {
        String[] userInformation = FileService.read("src/user_database.txt");
        if (username.length() <= 10) {
            throw new InvalidInputException("Username should have at least 11 digits!");
        }
        for (int i = 1, userInformationLength = userInformation.length; i < userInformationLength; i++) {
            String information = userInformation[i];
            String[] splitInfo = information.split(",");
            if (splitInfo[1].equals(username)) {
                throw new InvalidInputException("Username has been already taken, please use another!");
            }
        }
        this.username = username;
    }

    public void setEmail(String email) throws InvalidInputException {
        Pattern emailPattern = Pattern.compile(EMAIL_VERIFICATION);
        Matcher matcher = emailPattern.matcher(email);
        if (matcher.matches()) {
            this.email = email;
        } else {
            throw new InvalidInputException("Email should be like: something@example.com");
        }
    }

    public void setPassword(String password) throws InvalidInputException, NoSuchAlgorithmException {
        Pattern passwordPattern = Pattern.compile(PASSWORD_VERIFICATION);
        Matcher matcher = passwordPattern.matcher(password);
        if (matcher.matches() && password.length() >= 8) {
            this.password = EncryptionService.getMd5(password);
        } else {
            throw new InvalidInputException("Password should contain at least 2 uppercase letters and 3 " +
                    "numbers and has min 8 digit length");
        }
    }

    public FullName getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return this.getFullName() + "," + this.getUsername() + "," + this.getEmail() + "," + this.getPassword();
    }
}