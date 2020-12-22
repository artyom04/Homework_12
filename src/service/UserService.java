package service;

import model.EmptyNameException;
import model.InvalidInputException;
import model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    public static Scanner scanner = new Scanner(System.in);

    public static ArrayList<User> getUsersFromFile(String path) throws Exception {
        ArrayList<User> userArrayList = new ArrayList<>();
        String[] userInformation = FileService.read(path);
        for (int i = 1; i < userInformation.length; i++) {
            userArrayList.add(new User(userInformation[i]));
        }
        return userArrayList;
    }

    public static void register() {
        User user = new User();
        boolean indicator = true;
        while (indicator) {
            System.out.print("Enter the Name: ");
            String name = scanner.next();
            System.out.print("Enter the Surname: ");
            String surname = scanner.next();
            try {
                user.setFullName(name, surname);
                indicator = false;
            } catch (EmptyNameException e) {
                System.out.println("Please try again");
                e.printStackTrace();
            }
        }
        indicator = true;
        while (indicator) {
            System.out.print("Enter the Username: ");
            String username = scanner.next();
            try {
                user.setUsername(username);
                indicator = false;
            } catch (Exception e) {
                System.out.println("Please try again");
                e.printStackTrace();
            }
        }
        indicator = true;
        while (indicator) {
            System.out.print("Enter the Email: ");
            String email = scanner.next();
            try {
                user.setEmail(email);
                indicator = false;
            } catch (InvalidInputException e) {
                System.out.println("Please try again");
                e.printStackTrace();
            }
        }
        indicator = true;
        while (indicator) {
            System.out.print("Enter the Password: ");
            String password = scanner.next();
            try {
                user.setPassword(password);
                indicator = false;
            } catch (InvalidInputException | NoSuchAlgorithmException e) {
                System.out.println("Please try again");
                e.printStackTrace();
            }
        }
        try {
            FileService.write("src/user_database.txt", "\n" + user);
            System.out.println("Information was successfully written to user_database.txt file!");
        } catch (IOException e) {
            System.out.println("File Not Found / Can't Write");
            e.printStackTrace();
        }
    }

    public static void login() {
        System.out.print("Enter the Username: ");
        String username = scanner.next();
        System.out.print("Enter the Password: ");
        String password = scanner.next();
        try {
            ArrayList<User> userArrayList = getUsersFromFile("src/user_database.txt");
            for (User user : userArrayList) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(EncryptionService.getMd5(password))) {
                        System.out.println("Login Successful!");
                        return;
                    }
                }
            }
            System.out.println("Invalid Input Data!");
        } catch (Exception e) {
            System.out.println("Invalid File/ Can't Read");
            e.printStackTrace();
        }
    }
}