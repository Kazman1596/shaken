package org.example.shaken.services;


import org.example.shaken.model.UserCredentials;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to Shaken! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForRegisterCredentials() {
        String firstName = promptForString("First Name: ");
        String lastName = promptForString("Last Name: ");
        String email = promptForString("Email: ");
        String username = promptForString("Username: ").toLowerCase(); //Keep all usernames lowercase, and make toLowerCase() when logging in
        String password = promptForString("Password: ");
        return new UserCredentials(firstName, lastName, email, username, password);
    }

    public UserCredentials promptForLoginCredentials() {
        String username = promptForString("Username: ").toLowerCase();
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    //TODO: create a promptForValidEmail to make sure @ and .com/.org/.net is real

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
}
