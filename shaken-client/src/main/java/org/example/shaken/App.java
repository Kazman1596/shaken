package org.example.shaken;

import org.example.shaken.model.*;
import org.example.shaken.services.AuthenticationService;
import org.example.shaken.services.ConsoleService;
import org.example.shaken.services.IngredientService;
import org.example.shaken.services.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    //TODO: Create getIngredientsByRecipeId() SERVICE

    //TODO: Create getRecipesByIngredient() SERVICE

    //TODO: Update createRecipe() to automatically add Ingredients and add to associative table

    //TODO: Integration tests for all new DAO's

    //TODO: createRecipe() should now accept currentUser.getId() as userId in Recipe class

    //TODO: Hook up services and console application completely (add review, searchIngredients, etc)

    //TODO: Go through and address comments, other TODO's, and anything else before starting Front-End


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

    IngredientService ingredientService = new IngredientService();
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        App cli = new App(menu);
        cli.run();
    }

    public App(Menu menu) {
        this.menu = menu;
    }

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

    public List<Recipe> searchForIngredients(String userInput) {
        List<Recipe> results = new ArrayList<>();
        String[] userIngredientsArray = userInput.split(",");
        return results;
    }

    private void disgustingMessToAddNonDuplicateIngredients() {
        List<String> ingredientList = recipeService.getIngredientList();
        List<Ingredient> ingredientDB = ingredientService.getIngredients();
        List<Recipe> results = new ArrayList<>();
        for (String ingredientString : ingredientList) {
            System.out.println(ingredientString);
            String[] ingredientArray = ingredientString.split(" \\+ ");
            for (String ingredient : ingredientArray) {
                System.out.println(ingredient);
                int quantityIndex = 1;
                String measurement = null;

                //FINDING QUANTITY
                for (int i = 1; i < ingredient.length(); i++) {

                    if (!Character.isDigit(ingredient.charAt(i)) && !Character.isDigit(ingredient.charAt(i + 1)) && !Character.isDigit(ingredient.charAt(i + 2))) {
                        quantityIndex = i;
                        break;
                    }
                }

                //FINDING MEASUREMENT
                if (ingredient.contains("ounce")) {
                    measurement = "ounce";
                } else if (ingredient.contains("dash")) {
                    measurement = "dash";
                } else if (ingredient.contains("teaspoon")) {
                    measurement = "teaspoon";
                } else if (ingredient.contains("tablespoon")) {
                    measurement = "tablespoon";
                } else if (ingredient.contains("cup")) {
                    measurement = "cup";
                } else if (ingredient.contains("drop")) {
                    measurement = "drop";
                } else if (ingredient.contains("pinch")) {
                    measurement = "pinch";
                } else if (ingredient.contains("scoop")) {
                    measurement = "scoop";
                }


                String ingredientTitle = "";
                int measurementIndex = 0;
                if (measurement == null) {
                    ingredientTitle = ingredient.substring(quantityIndex + 1);
                } else {
                    String[] array = ingredient.split(" ");
                    boolean pastMeasurement = false;
                    for (int i = 1; i < array.length; i++) {
                        if (pastMeasurement) {
                            ingredientTitle += array[i] + " ";
                        }
                        if (array[i].contains(measurement)) {
                            pastMeasurement = true;
                        }
                    }
                }

                ingredientDB = ingredientService.getIngredients();

                System.out.println(ingredient.substring(0, quantityIndex)); //QUANTITY
                System.out.println(measurement); //MEASUREMENT
                System.out.println(ingredientTitle.trim()); //TITLE

                boolean duplicate = false;

                if(ingredient.contains("Crushed Ice")) {
                    Ingredient newIngredient = new Ingredient("1/2", "cup", "crushed ice");
                    if (ingredientDB.size() == 0) {
                        ingredientService.createIngredient(newIngredient);
                    } else {
                        for (Ingredient ing : ingredientDB) {
                            if (ing.getMeasurement() != null && newIngredient.getMeasurement() != null) {
                                if (ing.getMeasurement().equals(newIngredient.getMeasurement()) && ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
                                    duplicate = true;
                                }
                            } else {
                                if (ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
                                    duplicate = true;
                                }
                            }
                        }
                        if (!duplicate) {
                            ingredientService.createIngredient(newIngredient);
                        }
                    }
                } else {
                    Ingredient newIngredient = new Ingredient(ingredient.substring(0, quantityIndex), measurement, ingredientTitle.trim().toLowerCase());
                    if(ingredientDB.size() == 0) {
                        ingredientService.createIngredient(newIngredient);
                    } else {
                        for (Ingredient ing : ingredientDB) {
                            if (ing.getMeasurement() != null && newIngredient.getMeasurement() != null) {
                                if (ing.getMeasurement().equals(newIngredient.getMeasurement()) && ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
                                    duplicate = true;
                                    break;
                                }
                            } else {
                                if (ing.getQuantity().equals(newIngredient.getQuantity()) && ing.getName().equals(newIngredient.getName())) {
                                    duplicate = true;
                                    break;
                                }
                            }
                        }
                        if (!duplicate) {
                            ingredientService.createIngredient(newIngredient);
                        }
                    }
                }

            }
        }
    }

    public void addingIngredientsToRecipe() {
        List<Recipe> recipes = recipeService.getRecipes("");
        List<Ingredient> ingredients = ingredientService.getIngredients();

        for (Recipe recipe : recipes) {
            String recipeIngredientsString = recipe.getIngredients();
            String[] recipeIngredients = recipeIngredientsString.split(" \\+ ");
            for (Ingredient ingredient : ingredients) {
                //Checking for null getMeasurements()
                String ingString = "";
                String ingPluralString = "";
                String ingPluralString2 = "";
                if (ingredient.getMeasurement() != null) {
                    //Checking for instances of plural
                    ingString = ingredient.getQuantity() + " " + ingredient.getMeasurement() + " " + ingredient.getName();
                    ingPluralString = ingredient.getQuantity() + " " + ingredient.getMeasurement() + "s " + ingredient.getName();
                    ingPluralString2 = ingredient.getQuantity() + " " + ingredient.getMeasurement() + "es " + ingredient.getName();
                } else {
                    //Checking for instances of plural
                    ingString = ingredient.getQuantity() + " " + ingredient.getName();
                    ingPluralString = ingredient.getQuantity() + " " + ingredient.getName();
                    ingPluralString2 = ingredient.getQuantity() + " " + ingredient.getName();
                }
                for (String recipeIng : recipeIngredients) {
                    if (recipeIng.toLowerCase().equals(ingString.toLowerCase()) || recipeIng.toLowerCase().equals(ingPluralString.toLowerCase()) || recipeIng.toLowerCase().equals(ingPluralString2.toLowerCase())) {
                        IngredientRecipeDto ingredientRecipeDto = new IngredientRecipeDto(recipe.getRecipeId(), ingredient.getIngredientId());
                        ingredientService.mapIngredientToRecipe(ingredientRecipeDto);
                    }
                }
            }
        }
    }

}