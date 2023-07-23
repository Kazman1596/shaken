package org.example.shaken;

import org.example.shaken.model.AuthenticatedUser;
import org.example.shaken.model.Recipe;
import org.example.shaken.model.UserCredentials;
import org.example.shaken.services.AuthenticationService;
import org.example.shaken.services.ConsoleService;
import org.example.shaken.services.RecipeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String MAIN_MENU_OPTION_SEARCH = "Search Cocktail";
    private static final String MAIN_MENU_OPTION_INGREDIENTS = "Find A Drink With Ingredients You Have";
    private static final String MAIN_MENU_OPTION_COCKTAIL = "Cocktail Database";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit Application";
    private static final String GO_BACK = "Go back";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_SEARCH, MAIN_MENU_OPTION_INGREDIENTS, MAIN_MENU_OPTION_COCKTAIL, MAIN_MENU_OPTION_EXIT};
    private static final String[] GO_BACK_OPTION = {GO_BACK};
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final Menu menu;
    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    CocktailMenu cocktailMenu = new CocktailMenu();
    RecipeService recipeService = new RecipeService();
    Scanner userInput = new Scanner(System.in);
    ConsoleService consoleService = new ConsoleService();

    AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        App cli = new App(menu);
        cli.run();
    }

    public App(Menu menu) {
        this.menu = menu;
    }

    //TODO: login works without edge cases
    // register does NOT, typing in a login that doesn't exist does NOT
    // will pick up tomorrow
    /*
        Messages found in server-side, register needs firstName, lastName, and email as well
        Forgot what the error said for login
     */
    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForRegisterCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            System.out.println("Username already taken.");
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForLoginCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            System.out.println("Invalid login credentials");
        }
    }

    public void mainMenu() {

        label:
        while (true) {
            String MAIN_MENU_WELCOME = "Welcome to Shaken!";
            System.out.println(MAIN_MENU_WELCOME + " Logged in as: " + currentUser.getUser().getUsername());
            System.out.println();
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


            switch (choice) {
                case MAIN_MENU_OPTION_SEARCH: {
                    List<Recipe> recipeSearch = searchTitlePrompt(userInput);
                    Recipe recipe = searchResultsMenu.runSearchResultsMenu(recipeSearch);
                    searchResults(recipe);
                    break;
                }
                case MAIN_MENU_OPTION_INGREDIENTS: {
                    List<Recipe> ingredientSearch = searchIngredientsPrompt(userInput);
                    Recipe recipe = searchResultsMenu.runSearchResultsMenu(ingredientSearch);
                    searchResults(recipe);
                    break;
                }
                case MAIN_MENU_OPTION_COCKTAIL:
                    cocktailMenu.runCocktailDatabaseMenu();
                    break;
                case MAIN_MENU_OPTION_EXIT:
                    System.out.println("Goodbye!");
                    break label;
            }
        }
    }

    public List<Recipe> searchTitlePrompt(Scanner in) {
        System.out.println(System.lineSeparator() + "Please type in a cocktail:");
        String search = in.nextLine();
        return recipeService.getRecipes(search);
    }

    public void searchResults(Recipe recipe) {
        System.out.println(recipe.recipeResultText());
        while(true) {
            Object choice = menu.getChoiceFromOptions(GO_BACK_OPTION);
            if (choice.equals(GO_BACK)) {
                break;
            }
        }
    }

    public List<Recipe> searchIngredientsPrompt(Scanner in) {
        System.out.println(System.lineSeparator() + "Please list the ingredients you have in your house, separated by commas:");
        String search = in.nextLine();
        return searchForIngredients(search);
    }

    //TODO: Amazing, and the logic is there. However, this now only works if you say EXACTLY '1 1/2 ounce tequila'
    // - Perhaps another data structure will help, OR now I'm thinking maybe another SQL table for ingredients so we can separate measurement from ingredient and create the map that way
    public List<Recipe> searchForIngredients(String userInput) {
        HashMap<String, List<Recipe>> ingredientMap = recipeService.getIngredientMap();
        List<Recipe> results = new ArrayList<>();
        String[] userIngredientsArray = userInput.split(",");
        for (String ingredient : userIngredientsArray) {
            List<Recipe> recipes = ingredientMap.get(ingredient);
            results.addAll(recipes);
        }
        return results;
    }
}