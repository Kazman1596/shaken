package org.example.shaken.model;

public class Recipe {

    //TODO: Figure out how to parse Date/Time from JSON... I think this is causing issues
    //  changed to string for now, but now I think createRecipe broke because its expecting a date
    //  OTHER THAN THAT, it seems like everything works as expected!!
    private int recipeId;
    private String title;
    private String ingredients;
    private String instructions;
    private String glass;
    private int accountId;
    private int rating;

    //Change back to LocalDate/LocalTime
    private String postDate;
    private String postTime;

    public Recipe(int recipeId, String title, String ingredients, String instructions, String glass, int accountId) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.glass = glass;
        this.accountId = accountId;
    }

    public Recipe() {};

//    public Recipe(String title, String ingredients, String instructions, String glass, int accountId, String postDate, String postTime) {
//        this.title = title;
//        this.ingredients = ingredients;
//        this.instructions = instructions;
//        this.glass = glass;
//        this.accountId = accountId;
//        this.postDate = postDate;
//        this.postTime = postTime;
//    }
    public Recipe(String title, String ingredients, String instructions, String glass, int accountId) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.glass = glass;
        this.accountId = accountId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int id) {
        this.recipeId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rate) {
        rating = rate;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public String recipeResultText() {
        return title + System.lineSeparator() + glass + System.lineSeparator() + ingredients + System.lineSeparator() + instructions + System.lineSeparator();
    }
}
