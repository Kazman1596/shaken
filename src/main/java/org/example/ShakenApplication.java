package org.example;

import org.example.dao.JdbcRecipeDao;
import org.example.model.Recipe;
import org.example.model.RecipeList;

import java.util.Scanner;

public class ShakenApplication {

    private static final String MAIN_MENU_WELCOME = "Welcome to Shaken!";
    private static final String MAIN_MENU_OPTION_SEARCH = "Search Cocktail";
    private static final String MAIN_MENU_OPTION_INGREDIENTS = "Find A Drink With Ingredients You Have";
    private static final String MAIN_MENU_OPTION_COCKTAIL = "Cocktail Database";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit Application";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_SEARCH, MAIN_MENU_OPTION_INGREDIENTS, MAIN_MENU_OPTION_COCKTAIL, MAIN_MENU_OPTION_EXIT};

    private Menu menu;
    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    CocktailMenu cocktailMenu = new CocktailMenu();
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
            } else if (choice.equals(MAIN_MENU_OPTION_COCKTAIL)) {
                cocktailMenu.runCocktailDatabaseMenu();
            } else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    public static void main(String[] args) {
        RecipeList recipeList = new RecipeList();
        JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();
        Menu menu = new Menu(System.in, System.out);
        ShakenApplication cli = new ShakenApplication(menu);
        cli.run();
        for (Recipe recipe : recipeList.getRecipeList()) {
            jdbcRecipeDao.createRecipe(recipe);
        }
    }
}