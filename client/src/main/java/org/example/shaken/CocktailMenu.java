package org.example.shaken;
import org.example.shaken.model.Recipe;
import org.example.shaken.services.RecipeService;

import java.util.List;
import java.util.Scanner;

public class CocktailMenu {

    private static final String CREATE_COCKTAIL = "Create cocktail";
    private static final String UPDATE_COCKTAIL_MENU = "Update cocktail";
    private static final String DELETE_COCKTAIL_MENU = "Delete cocktail";
    private static final String BACK_MENU = "Go back";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final Object[] cocktailMenuOptions = {CREATE_COCKTAIL, UPDATE_COCKTAIL_MENU, DELETE_COCKTAIL_MENU, BACK_MENU};
    private static final Object[] confirmMenuOptions = {YES, NO};

    Menu menu = new Menu(System.in, System.out);
    Scanner userInput = new Scanner(System.in);
    SearchResultsMenu searchResultsMenu = new SearchResultsMenu();
    RecipeService recipeService = new RecipeService();

    public void runCocktailDatabaseMenu() {
        while(true) {
            Object choice = menu.getChoiceFromOptions(cocktailMenuOptions);
            if(choice.equals(CREATE_COCKTAIL)) {
                Recipe newRecipe = createRecipePrompt(userInput);
                recipeService.createRecipe(newRecipe);
            } else if (choice.equals(UPDATE_COCKTAIL_MENU)) {
                updateRecipePrompt(userInput);
            } else if (choice.equals(DELETE_COCKTAIL_MENU)) {
                deleteRecipePrompt(userInput);
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

    public void updateRecipePrompt(Scanner in) {

        System.out.println("Please search for a recipe you'd like to update");
        String recipeSearch = in.nextLine();
        List<Recipe> recipeResults = recipeService.getRecipes(recipeSearch);
        Recipe recipeToUpdate = searchResultsMenu.runSearchResultsMenu(recipeResults);
        System.out.println("You are updating --> " + recipeToUpdate.getTitle());

        System.out.println("New Title: ");
        String title = in.nextLine();
        recipeToUpdate.setTitle(title.toUpperCase());

        System.out.println("New Ingredients (Separated by a ' + '): ");
        String ingredients = in.nextLine();
        recipeToUpdate.setIngredients(ingredients);

        System.out.println("New Instructions: ");
        String instructions = in.nextLine();
        recipeToUpdate.setInstructions(instructions);

        System.out.println("Updating...");
        Recipe recipe = recipeService.updateRecipe(recipeToUpdate);
        System.out.println("Successfully updated the cocktail, " + recipe.getTitle() + "!");

    }

    public void deleteRecipePrompt(Scanner in) {
        System.out.println("Please search for a recipe to delete:");
        String recipeSearch = in.nextLine();
        List<Recipe> recipeResults = recipeService.getRecipes(recipeSearch);
        Recipe recipeToDelete = searchResultsMenu.runSearchResultsMenu(recipeResults);

        while (true) {
            System.out.println("You are deleting --> " + recipeToDelete.getTitle());
            System.out.println("Are you sure?");
            Object choice = menu.getChoiceFromOptions(confirmMenuOptions);
            if (choice.equals(YES)) {
                recipeService.deleteRecipe(recipeToDelete);
                break;
            } else if (choice.equals(NO)) {
                break;
            }
        }
    }
}
