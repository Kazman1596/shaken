package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Ingredient;
import org.example.model.IngredientRecipeDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcIngredientDao implements IngredientDao {
    private final String INGREDIENT_SELECT_STRING = "SELECT ingredient_id, quantity, unit, name FROM ingredient ";
    private final JdbcTemplate jdbcTemplate;
    public JdbcIngredientDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Ingredient> getIngredients() {
        List<Ingredient> returnedIngredients = new ArrayList<>();
        try {
            String sql = INGREDIENT_SELECT_STRING + ";";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredient(results);
                returnedIngredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedIngredients;
    }

    //TODO: Fix this once we get DB populated
    @Override
    public List<Ingredient> getIngredientsByRecipe(int recipeId) {
        return null;
    }

    @Override
    public Ingredient getIngredientById(int ingredientId) {
        Ingredient ingredient = null;
        String sql = INGREDIENT_SELECT_STRING + "WHERE ingredient_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, ingredientId);
            if (results.next()) {
                ingredient = mapRowToIngredient(results);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return ingredient;
    }

    @Override
    public Ingredient createIngredient(Ingredient newIngredient) {
        Ingredient ingredient = null;
        String sql = "INSERT INTO ingredient(quantity, unit, name) " +
                "VALUES (?, ?, ?) RETURNING ingredient_id;";
        try {
            int newId = jdbcTemplate.queryForObject(sql, int.class, newIngredient.getQuantity(), newIngredient.getMeasurement(), newIngredient.getName());
            ingredient = getIngredientById(newId);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return ingredient;
    }

    @Override
    public Ingredient updateIngredient(Ingredient updatedIngredient) {
        Ingredient ingredient = null;
        String sql = "UPDATE ingredient SET quantity=?, unit=?, name=? WHERE ingredient_id = ?;";
        try {
            int rowCount = jdbcTemplate.update(sql, updatedIngredient.getQuantity(), updatedIngredient.getMeasurement(), updatedIngredient.getName(), updatedIngredient.getIngredientId());
            if (rowCount == 0) {
                throw new DaoException("Expected 1 updated ingredient, but found 0");
            } else {
                ingredient = getIngredientById(updatedIngredient.getIngredientId());
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
        return ingredient;
    }

    //TODO: This will not work until we also create a sql string to delete associated ingredient_recipe row
    @Override
    public int deleteIngredient(int ingredientId) {
        int deletedRows = 0;
        String sql = "DELETE FROM ingredient WHERE ingredient_id = ?;";

        try{
            deletedRows = jdbcTemplate.update(sql, ingredientId);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return deletedRows;
    }

    @Override
    public void mapIngredientToRecipe(IngredientRecipeDto ingredientRecipeDto) {
        String sql = "INSERT INTO recipe_ingredient(recipe_id, ingredient_id) " +
                "VALUES (?, ?) RETURNING recipe_id;";
        try {
            int recipeId = jdbcTemplate.queryForObject(sql, int.class, ingredientRecipeDto.getRecipeId(), ingredientRecipeDto.getIngredientId());
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }
    }

    @Override
    public List<Ingredient> getIngredientsByRecipeId(int recipeId) {
        List<Ingredient> returnedIngredients = new ArrayList<>();
        String sql = "SELECT i.ingredient_id, i.quantity, i.unit, i.name FROM ingredient i\n" +
                "JOIN recipe_ingredient ri\n" +
                "\tON ri.ingredient_id = i.ingredient_id\n" +
                "JOIN recipe r\n" +
                "\tON ri.recipe_id = r.recipe_id\n" +
                "WHERE r.recipe_id = 3256;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Ingredient ingredient = mapRowToIngredient(results);
                returnedIngredients.add(ingredient);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedIngredients;
    }

    private Ingredient mapRowToIngredient(SqlRowSet results) {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(results.getInt("ingredient_id"));
        ingredient.setQuantity(results.getString("quantity")); //TODO: Might cause an issue
        ingredient.setMeasurement(results.getString("unit"));
        ingredient.setName(results.getString("name"));

        return ingredient;
    }
}
