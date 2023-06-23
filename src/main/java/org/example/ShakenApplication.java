package org.example;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class ShakenApplication {

    private static final String MAIN_MENU_WELCOME = "Welcome to Shaken!";
    private static final String MAIN_MENU_OPTION_SEARCH = "Search Cocktail";
    private static final String MAIN_MENU_OPTION_INGREDIENTS = "Find A Drink With Ingredients You Have";
    private static final String MAIN_MENU_OPTION_CREATE = "Create New Cocktail";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit Application";
    private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_SEARCH, MAIN_MENU_OPTION_INGREDIENTS, MAIN_MENU_OPTION_CREATE, MAIN_MENU_OPTION_EXIT};

    private Menu menu;

    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    RecipeList recipeList = new RecipeList();
    Scanner userInput = new Scanner(System.in);

    public ShakenApplication(Menu menu) {
        this.menu = menu;
    }
    public void run() {

        while (true) {
            System.out.println(MAIN_MENU_WELCOME);
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


            if (choice.equals(MAIN_MENU_OPTION_SEARCH)) {
                Object[] recipeSearch = recipeList.searchOptionsOutput(userInput);
                searchResultsMenu.runSearchResultsMenu(recipeSearch);
            } else if (choice.equals(MAIN_MENU_OPTION_INGREDIENTS)) {
                Object[] ingredientSearch = recipeList.searchIngredientOptionsOutput(userInput);
                searchResultsMenu.runSearchResultsMenu(ingredientSearch);
            } else if (choice.equals(MAIN_MENU_OPTION_CREATE)) {
                System.out.println("Title:");
                String userInputTitle = userInput.nextLine();
                System.out.println("Ingredients (please do not separate using commas):");
                String userInputIngredients = userInput.nextLine();
                System.out.println("Instructions:");
                String userInputInstructions = userInput.nextLine();
                recipeList.createRecipe(userInputTitle, userInputIngredients, userInputInstructions);
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        ShakenApplication cli = new ShakenApplication(menu);
        cli.run();

        //Clean up
        File file = new File("recipe-list.txt");
        File outputFile = new File("recipe-list2.txt");
        try(Scanner scanner = new Scanner(file); PrintWriter writer = new PrintWriter(outputFile)) {
            while (scanner.hasNextLine()) {
                //Setting up scanner / writer
                String line = scanner.nextLine();
                String[] lineArray = line.split(" \\| ");
                String title = lineArray[0];
                String ingredients = lineArray[1];
                String instructions = lineArray[2];

                String newIngredients = " " + ingredients + " ";
                //TODO: Splitting ingredients by comma

                //Putting it back together
                    System.out.println(newIngredients.trim());
                //writer.println(title + " | " + newIngredients + " | " + instructions);
            }
        } catch (Exception ex) {
            System.out.println("Trouble reading recipe");
        }

    }

}