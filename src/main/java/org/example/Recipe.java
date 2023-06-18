package org.example;

public class Recipe {
    private String title;
    private String ingredients;
    private String instructions;
    private String glassWare;

    public Recipe(String title, String ingredients, String instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    //TODO: Make a method to determine glassware(for eventual picture)

    public String setGlassware() {
        return "";
    }

    public String recipeResultText() {
        return title + System.lineSeparator() + ingredients + System.lineSeparator() + instructions + System.lineSeparator();
    }
}
