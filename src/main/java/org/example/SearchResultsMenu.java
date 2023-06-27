package org.example;

import org.example.model.Recipe;
import org.example.model.RecipeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchResultsMenu {
    private static final String SEARCH_RESULT_MENU_BACK = "Go back";
    private static final Object[] searchResultMenuOptions = {SEARCH_RESULT_MENU_BACK};
    Menu menu = new Menu(System.in, System.out);
    RecipeList recipeList = new RecipeList();
    HashMap<String, Recipe> recipeMap = recipeList.getRecipeMap();

    public void runSearchResultsMenu(Object[] cocktailSearchResults) {
        while(true) {
            Object[] searchOptions = createResultsOptions(cocktailSearchResults);
            Object choice = menu.getChoiceFromOptions(searchOptions);
            if (choice.equals(SEARCH_RESULT_MENU_BACK)) {
                break;
            }
            runResult(choice.toString());
        }
    }

    public void runResult(String searchResult) {

        while(true) {
            Recipe recipe = recipeMap.get(searchResult.toLowerCase());
            System.out.println(recipe.recipeResultText());
            Object choice = menu.getChoiceFromOptions(searchResultMenuOptions);
            if (choice.equals(SEARCH_RESULT_MENU_BACK)) {
                break;
            }
        }
    }

    public Object[] createResultsOptions(Object[] searchResults) {
        ArrayList<Object> menuOptions = new ArrayList<>(Arrays.asList(searchResults));
        menuOptions.add(SEARCH_RESULT_MENU_BACK);

        return menuOptions.toArray();
    }
}
