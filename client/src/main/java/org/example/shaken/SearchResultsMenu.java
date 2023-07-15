package org.example.shaken;

import org.example.shaken.model.Recipe;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class SearchResultsMenu {

    Menu menu = new Menu(System.in, System.out);
    private final PrintWriter out = new PrintWriter(System.out);
    private final Scanner in = new Scanner(System.in);

    public Recipe runSearchResultsMenu(List<Recipe> cocktailSearchResults) {
        return getRecipeChoiceFromOptions(cocktailSearchResults);
    }

    public Recipe getRecipeChoiceFromOptions(List<Recipe> recipes) {
        Recipe choice = null;
        while(choice == null) {
            displayRecipeSearchOptions(recipes);
            choice = getRecipeChoiceFromUserInput(recipes);
        }
        return choice;
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

    public Recipe getRecipeChoiceFromUserInput(List<Recipe> options) {
        String input = in.nextLine();
        Recipe choice = null;
        try {
            int inputChoice = Integer.parseInt(input);
            if (inputChoice > 0 && inputChoice <= options.size()) {
                choice = options.get(inputChoice -1);
            }
        } catch (NumberFormatException ex) {
            //choice will be null
        }
        if (choice == null) {
            System.out.println("*** " + input + " IS NOT AN OPTION ***");
        }

        return choice;
    }
}
