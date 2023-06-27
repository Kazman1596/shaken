package org.example.dao;

import org.example.model.Recipe;

public interface RecipeDao {

    // Returns a list of recipes that contain userInput
    Recipe[] getRecipesByTitle(String userInput);

    // Returns a specific recipe
    Recipe getRecipeById(int id);

    // Returns a list of recipes made by a user
    Recipe[] getRecipesByAccountId(int accountId);

    //Returns a list of recipes that contain the users ingredients
    Recipe[] getRecipesByIngredients(String userIngredients);

    //Creates a new recipe and adds to the database, returned for testing
    Recipe createRecipe(Recipe newRecipe);

    //Updates an existing recipe, returned for testing
    //updatedRecipe will be the recipe returned by getRecipeById, and then can be updated through there
    Recipe updateRecipe(Recipe updatedRecipe);

    //Deletes an existing recipe by id
    //Returns number of deleted items
    int deleteRecipe(int id);

}
