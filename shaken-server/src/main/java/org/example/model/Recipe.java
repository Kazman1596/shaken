package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Recipe {
    private int recipeId;
    private String title;
    private String ingredients;
    private String instructions;
    private String glass;
    private int accountId;
    private int rating;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate postDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime postTime;

    public Recipe(int recipeId, String title, String ingredients, String instructions, String glass, int accountId) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.glass = glass;
        this.accountId = accountId;
        this.postDate = LocalDate.now();
        this.postTime = LocalTime.now();
    }

    public Recipe() {};

    public Recipe(String title, String ingredients, String instructions, String glass, int rating, int accountId) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.glass = glass;
        this.accountId = accountId;
        this.rating = rating;
        this.postDate = LocalDate.now();
        this.postTime = LocalTime.now();
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

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(Date date) {
        this.postDate = date.toLocalDate();
    }

    public LocalTime getPostTime() {
        return postTime;
    }

    public void setPostTime(Time time) {
        this.postTime = time.toLocalTime();
    }

    public String recipeResultText() {
        return title + System.lineSeparator() + glass + System.lineSeparator() + ingredients + System.lineSeparator() + instructions + System.lineSeparator();
    }
}
