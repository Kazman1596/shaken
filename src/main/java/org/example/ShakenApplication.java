package org.example;

import org.example.dao.JdbcRecipeDao;
import org.example.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShakenApplication {
    //TODO: Once C.R.U.D. DAO connections are finished, main focus is working on Unit & Integration Tests!

    private static final String MAIN_MENU_WELCOME = "Welcome to Shaken!";
    private static final String MAIN_MENU_OPTION_SEARCH = "Search Cocktail";
    private static final String MAIN_MENU_OPTION_INGREDIENTS = "Find A Drink With Ingredients You Have";
    private static final String MAIN_MENU_OPTION_COCKTAIL = "Cocktail Database";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit Application";
    private static final String GO_BACK = "Go back";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_SEARCH, MAIN_MENU_OPTION_INGREDIENTS, MAIN_MENU_OPTION_COCKTAIL, MAIN_MENU_OPTION_EXIT};
    private static final String[] GO_BACK_OPTION = {GO_BACK};

    private final Menu menu;
    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    CocktailMenu cocktailMenu = new CocktailMenu();
    Scanner userInput = new Scanner(System.in);

    JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();

    public ShakenApplication(Menu menu) {
        this.menu = menu;
    }

    public void run() {

        while (true) {
            System.out.println(MAIN_MENU_WELCOME);
            String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


            if (choice.equals(MAIN_MENU_OPTION_SEARCH)) {
                List<Recipe> recipeSearch = searchTitlePrompt(userInput);
                Recipe recipe = searchResultsMenu.runSearchResultsMenu(recipeSearch);
                searchResults(recipe);
            } else if (choice.equals(MAIN_MENU_OPTION_INGREDIENTS)) {
                List<Recipe> ingredientSearch = searchIngredientsPrompt(userInput);
                Recipe recipe = searchResultsMenu.runSearchResultsMenu(ingredientSearch);
                searchResults(recipe);
            } else if (choice.equals(MAIN_MENU_OPTION_COCKTAIL)) {
                cocktailMenu.runCocktailDatabaseMenu();
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public List<Recipe> searchTitlePrompt(Scanner in) {
        System.out.println(System.lineSeparator() + "Please type in a cocktail:");
        String search = in.nextLine();
        return jdbcRecipeDao.getRecipesByTitle(search, true);
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
        List<Recipe> recipeList = jdbcRecipeDao.getRecipes();

        for (Recipe recipe : recipeList) {
            String searchRecipeIngredients = recipe.getIngredients();
            int counter = 0;
            for (String ingredient : userIngredientsArray) {
                if (searchRecipeIngredients.contains(ingredient.trim())) {
                    counter++;
                }
                if (counter == userIngredientsArray.length) {
                    results.add(recipe);
                    break;
                }
            }
        }
        return results;
    }

    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        ShakenApplication cli = new ShakenApplication(menu);
        cli.run();

        //TODO: To prevent lossy encoding and to be able to properly backup your DB,
        // edit recipe-list one last time to remove non-UTF-8 characters
        // *** REMEMBER to run jdbcRecipeDao.createRecipe below through recipeList() ***
//        RecipeList recipeList = new RecipeList();
//        JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();
//        List<Recipe> recipes = recipeList.getRecipeList();
//        for (Recipe recipe : recipes) {
//            jdbcRecipeDao.createRecipe(recipe);
//        }
    }
}