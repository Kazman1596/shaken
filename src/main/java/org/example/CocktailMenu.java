package org.example;

import org.example.dao.JdbcRecipeDao;
import org.example.model.Recipe;

import java.util.List;
import java.util.Scanner;

public class CocktailMenu {

    private static final String CREATE_COCKTAIL = "Create cocktail";
    private static final String UPDATE_COCKTAIL_MENU = "Update cocktail";
    private static final String DELETE_COCKTAIL_MENU = "Delete cocktail";
    private static final String BACK_MENU = "Go back";
    private static final Object[] cocktailMenuOptions = {CREATE_COCKTAIL, UPDATE_COCKTAIL_MENU, DELETE_COCKTAIL_MENU, BACK_MENU};

    Menu menu = new Menu(System.in, System.out);
    Scanner userInput = new Scanner(System.in);
    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();

    public void runCocktailDatabaseMenu() {
        while(true) {
            Object choice = menu.getChoiceFromOptions(cocktailMenuOptions);
            if(choice.equals(CREATE_COCKTAIL)) {
                Recipe newRecipe = createRecipePrompt(userInput);
                jdbcRecipeDao.createRecipe(newRecipe);
            } else if (choice.equals(UPDATE_COCKTAIL_MENU)) {
                updateRecipePrompt(userInput);
            } else if (choice.equals(DELETE_COCKTAIL_MENU)) {
                System.out.println("Unconnected Delete Option");
            } else if (choice.equals(BACK_MENU)) {
                break;
            }
        }
    }

    public Recipe createRecipePrompt(Scanner in) {
        System.out.println("Title:");
        String title = in.nextLine().toUpperCase();
        System.out.println("Ingredients (separated by a ' + ')");
        String ingredients = in.nextLine();
        System.out.println("Instructions");
        String instructions = in.nextLine();

        return new Recipe(title, ingredients, instructions, "Cocktail Glass", 1);
    }

    //TODO: UpdateRecipePrompt should return a recipe, and ultimately be put into the jdbcRecipeDao.updateRecipeById()
    // might need to do a bit of rearranging in the SearchResultsMenu
    public void updateRecipePrompt(Scanner in) {
        System.out.println("Please search for a recipe you'd like to update");
        String recipeSearch = in.nextLine();
        List<Recipe> recipeResults = jdbcRecipeDao.getRecipesByTitle(recipeSearch, true);
        searchResultsMenu.runSearchResultsMenu(recipeResults);
    }
}
