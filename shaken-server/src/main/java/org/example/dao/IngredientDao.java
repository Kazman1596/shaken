package org.example.dao;

import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;

import java.util.List;

public interface IngredientDao {
    List<Ingredient> getIngredients();
    List<Ingredient> getIngredientsByRecipe(int recipeId);
    Ingredient getIngredientById(int ingredientId);
    Ingredient createIngredient(Ingredient newIngredient);
    Ingredient updateIngredient(Ingredient updatedIngredient);
    int deleteIngredient(int ingredientId);
    List<Ingredient> getIngredientsByRecipeId(int recipeId);
    void mapIngredientToRecipe(IngredientRecipeDto ingredientRecipeDto);
}
