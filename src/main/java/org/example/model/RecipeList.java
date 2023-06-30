package org.example.model;

import org.example.dao.JdbcRecipeDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class RecipeList {
    File recipeFile = new File("recipe-list.txt");
    List<Recipe> recipeList = new ArrayList<>();
    JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();


    public RecipeList() {

        try(Scanner scanner = new Scanner(recipeFile)) {
            while (scanner.hasNextLine()) {
                String recipeLine = scanner.nextLine();
                String[] recipeArr = recipeLine.split(" \\| ");
                Recipe recipe = new Recipe(recipeArr[0], recipeArr[1], recipeArr[2], recipeArr[3], 1);
                recipeList.add(recipe);

            }
        } catch(Exception ex) {
            System.out.println("Problem fetching recipe");
        }
    }

    public Recipe getRecipeByTitle(String title) {
        for (Recipe recipe : recipeList) {
            if (recipe.getTitle().equalsIgnoreCase(title)) {
                return recipe;
            }
        }
        return null;
    }
//    public void createRecipe(String title, String ingredients, String instructions, String glassWare) {
//        Recipe newRecipe = new Recipe(title.toUpperCase(), ingredients, instructions, glassWare, 1 );
//        try(PrintWriter writer = new PrintWriter(new FileOutputStream(recipeFile,true))) {
//            writer.println(title.toUpperCase() + " | " + ingredients + " | " + instructions);
//        } catch (Exception ex) {
//            System.out.println("Problem creating new recipe");
//        }
//        recipeList.add(newRecipe);
//    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
