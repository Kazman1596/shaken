package org.example;

import org.example.dao.JdbcRecipeDao;
import org.example.model.Recipe;

import java.util.List;

public class SearchResultsMenu {
    private static final String SEARCH_RESULT_MENU_BACK = "Go back";
    private static final Object[] searchResultMenuOptions = {SEARCH_RESULT_MENU_BACK};
    Menu menu = new Menu(System.in, System.out);
    JdbcRecipeDao jdbcRecipeDao = new JdbcRecipeDao();

    public void runSearchResultsMenu(List<Recipe> cocktailSearchResults) {
        while(true) {
            Recipe choice = menu.getRecipeChoiceFromOptions(cocktailSearchResults);
            runResult(choice);
            break;
        }
    }

    public void runResult(Recipe recipeChoice) {

        while(true) {
            System.out.println(recipeChoice.recipeResultText());
            Object choice = menu.getChoiceFromOptions(searchResultMenuOptions);
            if (choice.equals(SEARCH_RESULT_MENU_BACK)) {
                break;
            }
        }
    }
}
