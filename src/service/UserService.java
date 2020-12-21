package service;

import model.EmptyNameException;
import model.InvalidInputException;
import model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class UserService {
    public static Scanner scanner = new Scanner(System.in);

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
            String[] userInformation = FileService.read("src/user_database.txt");
            for (int i = 1; i < userInformation.length; i++) {
                String[] splitData = userInformation[i].split(",");
                if (splitData[1].equals(username)) {
                    if (splitData[3].equals(EncryptionService.getMd5(password))) {
                        System.out.println("Login Successful!");
                        return;
                    }
                }
            }
            System.out.println("Invalid Input Data");
        } catch (Exception e) {
            System.out.println("File not Found / Can't Read");
            e.printStackTrace();
        }
    }
}