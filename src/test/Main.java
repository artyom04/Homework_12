package test;

import service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMenuActive = true;
        while (isMenuActive) {
            System.out.println("------------MENU------------");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Registering User");
                    UserService.register();
                    break;
                case 2:
                    System.out.println("Login");
                    UserService.login();
                    break;
                case 3:
                    System.out.println("Exiting Menu");
                    isMenuActive = false;
                    break;
                default:
                    System.out.println("Wrong value chosen, please try again!");
                    break;
            }
        }
    }
}