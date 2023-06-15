package org.example;

import java.util.Scanner;

public class ShakenApplication {

    RecipeList recipeList = new RecipeList();
    Scanner userInput = new Scanner(System.in);
    public void run() {

        System.out.println("Welcome to Shaken!");
        System.out.println("Please search for a cocktail:");
        String searchedCocktail = userInput.nextLine();
        recipeList.searchRecipe(searchedCocktail);

    }
    public static void main(String[] args) {
        Menu menu = new Menu(System.in, System.out);
        ShakenApplication cli = new ShakenApplication();
        cli.run();

    }

}