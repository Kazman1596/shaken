//import org.example.dao.JdbcRecipeDao;
//import org.example.model.Recipe;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class JdbcRecipeDaoTests extends BaseDaoTests {
//
//    private Recipe testRecipe;
//    private JdbcRecipeDao sut;
//
//    @Before
//   public void setup() throws SQLException {
//        sut = new JdbcRecipeDao(dataSource);
//        testRecipe = new Recipe("TEST COCKTAIL", "Test ingredients", "Test instructions", "Test Glass", 1);
//    }
//
//    @Test
//    public void getCityById_returns_null_when_id_not_found() {
//        Recipe recipe = sut.getRecipeById(99);
//        Assert.assertNull(recipe);
//    }
//
//
//
//   private void assertRecipesMatch(Recipe expected, Recipe result) {
//       Assert.assertEquals(expected.getRecipeId(), result.getRecipeId());
//       Assert.assertEquals(expected.getTitle(), result.getTitle());
//       Assert.assertEquals(expected.getIngredients(), result.getIngredients());
//       Assert.assertEquals(expected.getInstructions(), result.getInstructions());
//       Assert.assertEquals(expected.getGlass(), result.getGlass());
//       Assert.assertEquals(expected.getRating(), result.getRating());
//       Assert.assertEquals(expected.getAccountId(), result.getAccountId());
//   }
//}
