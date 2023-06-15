package org.example;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class RecipeList {
    File inputFile = new File("recipe-list.txt");
    HashMap<String, Recipe> recipeMap = new HashMap<>();

    public RecipeList() {

        try(Scanner scanner = new Scanner(inputFile)) {
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

    public void searchRecipe(Scanner in) {
        System.out.println(System.lineSeparator() + "Please type in a cocktail:");
        String search = in.nextLine();

        if (recipeMap.containsKey(search.toLowerCase())) {
            Recipe recipe = recipeMap.get(search.toLowerCase());
            System.out.println("This is the cocktail: " + recipe.getTitle());
            System.out.println("INGREDIENTS:");
            System.out.println(recipe.getIngredients());
            System.out.println("INSTRUCTIONS:");
            System.out.println(recipe.getInstructions());
        } else {
            System.out.println("Could not find your drink of choice");
        }
    }


}
