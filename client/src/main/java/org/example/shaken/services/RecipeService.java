package org.example.shaken.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.example.shaken.model.Recipe;
import org.example.util.BasicLogger;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeService {
    private static final String API_BASE_URL = "http://localhost:8080/recipes/";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Recipe> getRecipes(String title) {
        Recipe[] recipes = null;

        try{
            String url = API_BASE_URL + "?title=" + title;
            recipes = restTemplate.getForObject(url, Recipe[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        if (recipes != null) {
            return Arrays.asList(recipes);
        } else {
            return new ArrayList<>();
        }
    }

    public Recipe getRecipeById(int id) {
        Recipe recipe = null;
        try{
            recipe = restTemplate.getForObject(API_BASE_URL + id, Recipe.class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        return recipe;
    }

    //TODO: We might want to move this one to user controller when the time comes
    public List<Recipe> getRecipesByAccountId(int id) {
        Recipe[] recipes = null;
        try{
            String url = API_BASE_URL + "user/" + id + "/collection";
            recipes = restTemplate.getForObject(url, Recipe[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        if (recipes != null) {
            return Arrays.asList(recipes);
        } else {
            return new ArrayList<>();
        }
    }

    //TODO: Fix createRecipe (probably has to do with date/time tbh)
    public Recipe createRecipe(Recipe newRecipe) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Recipe> entity = new HttpEntity<>(newRecipe, headers);
        Recipe result = null;

        try {
            result = restTemplate.postForObject(API_BASE_URL, entity, Recipe.class);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return result;
    }

    public Recipe updateRecipe(Recipe updatedRecipe) {
        String url = API_BASE_URL + updatedRecipe.getRecipeId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Recipe> entity = new HttpEntity<>(updatedRecipe, headers);
        boolean success = false;

        try{
            restTemplate.put(url, entity);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return getRecipeById(updatedRecipe.getRecipeId());
    }

    public void deleteRecipe(Recipe recipe) {
        String title = recipe.getTitle();
        String url = API_BASE_URL + recipe.getRecipeId();

        try{
            restTemplate.delete(url);
            System.out.println("Successfully deleted " + title);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }
}
