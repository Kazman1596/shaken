package org.example.controller;

import org.example.dao.RecipeDao;
import org.example.exception.DaoException;
import org.example.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    public RecipeDao recipeDao;

    @RequestMapping(path="", method = RequestMethod.GET)
    public List<Recipe> getRecipes(@RequestParam(defaultValue = "")String title) {
        if (!title.equals("")) {
            return recipeDao.getRecipesByTitle(title, true);
        }
        return recipeDao.getRecipes();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public Recipe getRecipeById(@PathVariable int id) {
        Recipe recipe = recipeDao.getRecipeById(id);
        if (recipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe was not found");
        } else {
            return recipe;
        }
    }

    @RequestMapping(path="/user/{id}/collection", method = RequestMethod.GET)
    public List<Recipe> getRecipesByAccountId(@PathVariable int id) {
        List<Recipe> recipes = recipeDao.getRecipesByAccountId(id);
        if (recipes.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        } else {
            return recipes;
        }
    }

    @RequestMapping(path="/ingredient/{id}", method = RequestMethod.GET)
    public List<Recipe> getRecipesByIngredientId(@PathVariable int id) {
        List<Recipe> recipes = recipeDao.getRecipesByIngredientId(id);
        if (recipes.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient does not exist");
        } else {
            return recipes;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="", method = RequestMethod.POST)
    public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeDao.createRecipe(recipe);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public Recipe updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe) {
        try {
            recipe.setRecipeId(id);
            return recipeDao.updateRecipe(recipe);
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteRecipe(@PathVariable int id) {
        recipeDao.deleteRecipe(id);
    }
}
