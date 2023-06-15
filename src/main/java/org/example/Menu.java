package org.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
    private PrintWriter out;
    private Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    public Object getChoiceFromUserInput(Object[] options) {
        String input = in.nextLine();
        Object choice = null;
        try {
            int inputChoice = Integer.parseInt(input);
            if (inputChoice > 0 && inputChoice <= options.length) {
                choice = options[inputChoice - 1];
            }
        } catch (NumberFormatException ex) {
            //choice will be null
        }
        if (choice == null) {
            System.out.println("*** " + input + " IS NOT AN OPTION ***");
        }
        return choice;
    }

    public void displayMenuOptions(Object[] options) {
        System.out.println("Welcome to Shaken!" + System.lineSeparator());
        for (int i=0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println("(" + optionNum + ") " + options[i]);
        };

        out.println(System.lineSeparator() + "Please select an option >>> ");
        out.flush();
    }

    //not adding out.flush() yet
}
