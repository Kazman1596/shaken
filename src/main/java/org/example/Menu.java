package org.example;

import org.example.model.Recipe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final PrintWriter out;
    private final Scanner in;

    public Menu(InputStream input, OutputStream output) {
        this.out = new PrintWriter(output);
        this.in = new Scanner(input);
    }

    public Recipe getRecipeChoiceFromOptions(List<Recipe> recipes) {
        Recipe choice = null;
        while(choice == null) {
            displayRecipeSearchOptions(recipes);
            choice = getRecipeChoiceFromUserInput(recipes);
        }
        return choice;
    }
    public Object getChoiceFromOptions(Object[] options) {
        Object choice = null;
        while(choice == null) {
            displayMenuOptions(options);
            choice = getChoiceFromUserInput(options);
        }
        return choice;
    }

    public Recipe getRecipeChoiceFromUserInput(List<Recipe> options) {
        String input = in.nextLine();
        Recipe choice = null;
        try {
            int inputChoice = Integer.parseInt(input);
            if (inputChoice > 0 && inputChoice <= options.size()) {
                choice = options.get(inputChoice -1 );
            }
        } catch (NumberFormatException ex) {
            //choice will be null
        }
        if (choice == null) {
            System.out.println("*** " + input + " IS NOT AN OPTION ***");
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
        if (options.length == 0) {
            System.out.println("Your drink of choice isn't available");
        }
        for (int i=0; i < options.length; i++) {
            int optionNum = i + 1;
            out.println("(" + optionNum + ") " + options[i]);
        };

        out.println(System.lineSeparator() + "Please select an option >>> ");
        out.flush();
    }

    public void displayRecipeSearchOptions(List<Recipe> searchResults) {
        if (searchResults.size() == 0) {
            System.out.println("No recipes match this search");
        }
        for (int i=0; i< searchResults.size(); i++) {
            int optionNum = i + 1;
            out.println("(" + optionNum + ") " + searchResults.get(i).getTitle());
        }

        out.println(System.lineSeparator() + "Please select an option >>> ");
        out.flush();
    }

}
