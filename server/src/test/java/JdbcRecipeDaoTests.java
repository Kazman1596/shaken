import org.example.dao.JdbcRecipeDao;
import org.example.model.Recipe;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class JdbcRecipeDaoTests extends BaseDaoTests {

    private static final Recipe MARGARITA = new Recipe(1, "MARGARITA", "lime + salt + simple syrup + triple sec + tequila", "Salt rim of glass, add lime juice, simple syrup, triple sec, and tequila. Shake well. Strain into glass", "Cocktail Glass", 1);
    private static final Recipe OLD_FASHIONED = new Recipe(2, "OLD FASHIONED", "bitters + peel of orange + whiskey + maraschino cherry (optional) + sugar cube", "Place sugar cube in glass, and add bitters. Crush sugar cube with bitters, until partially dissolved. Add whiskey and ice, stir well. Add cherry and orange peel as garnish.", "Cocktail Glass", 1);
    private static final Recipe VODKA_MULE = new Recipe(3, "VODKA MULE", "lime + ginger beer + vodka + simple syrup", "Mix all ingredients into a copper mug. Garnish with lime wedge.", "Copper Mug", 1);
    private static final Recipe PALOMA = new Recipe(4, "PALOMA", "lime + grapefruit juice (or Squirt) + simple syrup + tequila", "Add all ingredients into glass and garnish with lime wedge.", "Collins Glass", 1);

    private Recipe testRecipe;
    private JdbcRecipeDao sut;

    @Before
   public void setup() throws SQLException {
        sut = new JdbcRecipeDao(dataSource);
        testRecipe = new Recipe("TEST COCKTAIL", "Test ingredients", "Test instructions", "Test Glass", 5, 1);
    }

    @Test
    public void getRecipeById_returns_null_when_id_not_found() {
        Recipe recipe = sut.getRecipeById(99);
        Assert.assertNull(recipe);
    }

    @Test
    public void getRecipeById_returns_correct_recipe() {
        Recipe recipe = sut.getRecipeById(2);
        assertRecipesMatch(OLD_FASHIONED, recipe);
    }

    @Test
    public void getRecipesByTitle_returns_like_recipes_when_found() {
        List<Recipe> recipes = sut.getRecipesByTitle("marg", true);
        Assert.assertEquals(1, recipes.size());
    }

    @Test
    public void getRecipesByTitle_returns_empty_when_not_found() {
        List<Recipe> recipes = sut.getRecipesByTitle("lorem ipsum", true);
        Assert.assertEquals(0, recipes.size());
    }

    @Test
    public void getRecipesByAccountId_returns_list() {
        List<Recipe> recipes = sut.getRecipesByAccountId(1);
        Assert.assertEquals(4, recipes.size());
    }

    @Test
    public void getRecipesByAccountId_returns_empty_if_account_does_not_exist(){
        List<Recipe> recipes = sut.getRecipesByAccountId(2);
        Assert.assertEquals(0, recipes.size());
    }

    @Test
    public void createRecipe_returns_recipe_after_created() {
        Recipe newRecipe = sut.createRecipe(testRecipe);

        int newId = newRecipe.getRecipeId();
        Assert.assertTrue(newId > 0);

        Recipe retrievedRecipe = sut.getRecipeById(newId);
        assertRecipesMatch(newRecipe, retrievedRecipe);
    }

    @Test
    public void updateRecipe_has_expected_values() {
        Recipe recipeToUpdate = sut.getRecipeById(1);

        recipeToUpdate.setInstructions("These are updated");
        recipeToUpdate.setGlass("Copper Mug");
        recipeToUpdate.setIngredients("These ingredients are updated");

        sut.updateRecipe(recipeToUpdate);

        Recipe retrievedRecipe = sut.getRecipeById(1);
        assertRecipesMatch(recipeToUpdate, retrievedRecipe);
    }

    @Test
    public void deleted_recipe_cant_be_retrieved() {
        sut.deleteRecipe(4);

        Recipe retreivedRecipe = sut.getRecipeById(4);
        Assert.assertNull(retreivedRecipe);
    }

   private void assertRecipesMatch(Recipe expected, Recipe result) {
       Assert.assertEquals(expected.getRecipeId(), result.getRecipeId());
       Assert.assertEquals(expected.getTitle(), result.getTitle());
       Assert.assertEquals(expected.getIngredients(), result.getIngredients());
       Assert.assertEquals(expected.getInstructions(), result.getInstructions());
       Assert.assertEquals(expected.getGlass(), result.getGlass());
       Assert.assertEquals(expected.getAccountId(), result.getAccountId());
   }
}
