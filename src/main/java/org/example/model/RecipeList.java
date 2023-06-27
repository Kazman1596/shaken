package org.example.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RecipeList {
    File recipeFile = new File("/Users/stephenkaczmarowski/workspace/stephen-kaczmarowski-code/bartenders-friend/recipe-list.txt");
    HashMap<String, Recipe> recipeMap = new HashMap<>();

    public RecipeList() {

        try(Scanner scanner = new Scanner(recipeFile)) {
            while (scanner.hasNextLine()) {
                String recipeLine = scanner.nextLine();
                String[] recipeArr = recipeLine.split(" \\| ");
                String title = recipeArr[0];
                Recipe recipe = new Recipe(recipeArr[0], recipeArr[1], recipeArr[2]);
                recipeMap.put(title.toLowerCase(), recipe);
            }
        } catch(Exception ex) {
            System.out.println("Problem fetching recipe");
        }
    }

    public HashMap<String, Recipe> getRecipeMap() {
        return recipeMap;
    }

    public Object[] searchOptionsOutput(Scanner in) {
        ArrayList<String> searchResults = new ArrayList<>();
        System.out.println(System.lineSeparator() + "Please type in a cocktail:");
        String search = in.nextLine();

        for (Map.Entry<String, Recipe> recipe : recipeMap.entrySet()) {
            String recipeKey = recipe.getKey();
            if (recipeKey.contains(search.toLowerCase())) {
                searchResults.add(recipe.getValue().getTitle());
            }
        }
        return searchResults.toArray();
    }

    public Object[] searchIngredientOptionsOutput(Scanner in) {
        ArrayList<String> searchResults = new ArrayList<>();
        System.out.println(System.lineSeparator() + "Please list the ingredients you have in your house, separated by commas:");
        String search = in.nextLine();
        String[] ingredientsArray = search.split(",");

        for (Map.Entry<String, Recipe> recipe : recipeMap.entrySet()) {
            Recipe searchRecipe = recipe.getValue();
            String searchRecipeIngredients = searchRecipe.getIngredients();

            int counter = 0;
            for (String ingredient : ingredientsArray) {
                if (searchRecipeIngredients.contains(ingredient.trim())) {
                    counter++;
                }
                if (counter == ingredientsArray.length) {
                    searchResults.add(recipe.getValue().getTitle());
                    break;
                }
            }

        }
        return searchResults.toArray();
    }

    //TODO: Fix --> If you create a recipe then search for recipe right after, it will throw NullPointerException Error

    public void createRecipe(String title, String ingredients, String instructions) {
        Recipe newRecipe = new Recipe(title.toUpperCase(), ingredients, instructions);
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(recipeFile,true))) {
            writer.println(title.toUpperCase() + " | " + ingredients + " | " + instructions);
        } catch (Exception ex) {
            System.out.println("Problem creating new recipe");
        }
        recipeMap.put(title.toLowerCase(), newRecipe);
    }


}
