package org.example.controller;

import org.example.dao.IngredientDao;
import org.example.exception.DaoException;
import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    public IngredientDao ingredientDao;

    @RequestMapping(path="", method = RequestMethod.GET)
    public List<Ingredient> getIngredients() {
        return ingredientDao.getIngredients();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public Ingredient getIngredientById(@PathVariable int id) {
        Ingredient ingredient = ingredientDao.getIngredientById(id);
        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient was not found");
        } else {
            return ingredient;
        }
    }

    @RequestMapping(path="/recipe/{id}", method = RequestMethod.GET)
    public List<Ingredient> getIngredientByRecipeId(@PathVariable int id) {
        List<Ingredient> ingredients = ingredientDao.getIngredientsByRecipeId(id);
        if (ingredients.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredients were not found");
        } else {
            return ingredients;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="", method = RequestMethod.POST)
    public Ingredient createIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ingredientDao.createIngredient(ingredient);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public Ingredient updateIngredient(@PathVariable int id, @Valid @RequestBody Ingredient ingredient) {
        try {
            ingredient.setIngredientId(id);
            return ingredientDao.updateIngredient(ingredient);
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteIngredient(@PathVariable int id) {
        ingredientDao.deleteIngredient(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="/map", method = RequestMethod.POST)
    public void mapIngredientToRecipe(@Valid @RequestBody IngredientRecipeDto ingredientRecipeDto) {
        ingredientDao.mapIngredientToRecipe(ingredientRecipeDto);
    }

}
