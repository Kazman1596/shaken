package org.example.dao;

import org.example.model.Recipe;

import java.util.List;

public interface RecipeDao {

    //Returns all recipes
    List<Recipe> getRecipes();

    // Returns a list of recipes that contain userInput
    List<Recipe> getRecipesByTitle(String userInput, boolean wild);

    // Returns a specific recipe
    Recipe getRecipeById(int id);

    // Returns a list of recipes made by a user
    List<Recipe> getRecipesByAccountId(int accountId);

    //Creates a new recipe and adds to the database, returned for testing
    Recipe createRecipe(Recipe newRecipe);

    //Updates an existing recipe, returned for testing
    //updatedRecipe will be the recipe returned by getRecipeById, and then can be updated through there
    Recipe updateRecipe(Recipe updatedRecipe);

    //Deletes an existing recipe by id
    //Returns number of deleted items
    int deleteRecipe(int id);
    List<Recipe> getRecipesByIngredientId(int ingredientId);

}
