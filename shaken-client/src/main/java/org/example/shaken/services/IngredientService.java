package org.example.shaken.services;

import org.example.shaken.model.Ingredient;
import org.example.shaken.model.IngredientRecipeDto;
import org.example.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientService {
    private static final String API_BASE_URL = "http://localhost:8080/ingredients/";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Ingredient> getIngredients() {
        Ingredient[] ingredients = null;

        try{
            ingredients = restTemplate.getForObject(API_BASE_URL, Ingredient[].class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        if (ingredients != null) {
            return Arrays.asList(ingredients);
        } else {
            return new ArrayList<>();
        }
    }

    public Ingredient getIngredientById(int id) {
        Ingredient ingredient = null;
        try{
            ingredient = restTemplate.getForObject(API_BASE_URL + id, Ingredient.class);
        } catch (RestClientException e) {
            BasicLogger.log(e.getMessage());
        }

        return ingredient;
    }

    public Ingredient createIngredient(Ingredient newIngredient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Ingredient> entity = new HttpEntity<>(newIngredient, headers);
        Ingredient result = null;

        try {
            result = restTemplate.postForObject(API_BASE_URL, entity, Ingredient.class);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return result;
    }

    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        String url = API_BASE_URL + updatedIngredient.getIngredientId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Ingredient> entity = new HttpEntity<>(updatedIngredient, headers);
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

        return getIngredientById(updatedIngredient.getIngredientId());
    }

    public void deleteIngredient(Ingredient ingredient) {
        String title = ingredient.getName();
        String url = API_BASE_URL + ingredient.getIngredientId();

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

    public IngredientRecipeDto mapIngredientToRecipe(IngredientRecipeDto ingredientRecipeDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<IngredientRecipeDto> entity = new HttpEntity<>(ingredientRecipeDto, headers);
        IngredientRecipeDto result = null;

        try {
            result = restTemplate.postForObject(API_BASE_URL + "/map", entity, IngredientRecipeDto.class);
        }
        catch(RestClientResponseException e) {
            BasicLogger.log(e.getStatusText());
        }
        catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return result;
    }
}
