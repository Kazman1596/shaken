package org.example.dao;

import org.example.exception.DaoException;
import org.example.model.Review;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcReviewDao implements ReviewDao{
    //TODO: Integration Tests for ReviewDao

    private static final String REVIEW_SELECT_STRING = "SELECT title, recipe_id, account_id, description, rating, post_date, post_time FROM review ";
    private final JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Review> getReviews() {
        List<Review> returnedReviews = new ArrayList<>();
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(REVIEW_SELECT_STRING);
            while (results.next()) {
                Review review = mapRowToReview(results);
                returnedReviews.add(review);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        }

        return returnedReviews;
    }

    @Override
    public Review getReviewById(int id) {
        Review review = null;
        String sql = REVIEW_SELECT_STRING + "WHERE review_id = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                review = mapRowToReview(results);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return review;
    }

    @Override
    public List<Review> getReviewsByRecipe(int recipeId) {
        List<Review> returnedReviews = new ArrayList<>();
        String sql = REVIEW_SELECT_STRING + "WHERE recipe_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
            while (results.next()) {
                Review review = mapRowToReview(results);
                returnedReviews.add(review);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return returnedReviews;
    }

    @Override
    public List<Review> getReviewsByUser(int userId) {
        List<Review> returnedReviews = new ArrayList<>();
        String sql = REVIEW_SELECT_STRING + "WHERE account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                Review review = mapRowToReview(results);
                returnedReviews.add(review);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return returnedReviews;
    }

    @Override
    public List<Review> getReviewsByRating(int rating) {
        List<Review> returnedReviews = new ArrayList<>();
        String sql = REVIEW_SELECT_STRING + "WHERE review = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, rating);
            while (results.next()) {
                Review review = mapRowToReview(results);
                returnedReviews.add(review);
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return returnedReviews;
    }

    @Override
    public Review createReview(Review newReview) {
        Review review = null;
        String sql = "INSERT INTO review(title, recipe_id, account_id, description, rating, post_date, post_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING recipe_id;";
        try {
            int newId = jdbcTemplate.queryForObject(sql, int.class, newReview.getTitle(), newReview.getRecipeId(), newReview.getUserId(),
                    newReview.getDescription(), newReview.getRating(), newReview.getPostDate(), newReview.getPostTime());
            review = getReviewById(newId);
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return review;
    }

    @Override
    public Review updateReview(Review review) {
        Review updatedReview = null;
        String sql = "UPDATE review SET title=?, description=?, rating=? WHERE review_id=?;";
        try {
            int rowCount = jdbcTemplate.update(sql, review.getTitle(), review.getDescription(), review.getRating(), review.getReviewId());
            if (rowCount == 0) {
                throw new DaoException("Expected 1 updated review, but found 0");
            } else {
                updatedReview = getReviewById(review.getReviewId());
            }
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return updatedReview;
    }

    @Override
    public boolean deleteReview(int id) {
        boolean deleted = false;
        String sql = "DELETE FROM recipe WHERE review_id = ?;";

        try{
            jdbcTemplate.update(sql, id);
            deleted = true;
        } catch (CannotGetJdbcConnectionException ex) {
            throw new DaoException("Unable to connect to the server or database", ex);
        } catch (DataIntegrityViolationException ex) {
            throw new DaoException("Data Integrity Violation", ex);
        }

        return deleted;
    }

    private Review mapRowToReview(SqlRowSet results) {
        Review review = new Review();
        review.setReviewId(results.getInt("review_id"));
        review.setUserId(results.getInt("user_id"));
        review.setTitle(results.getString("title"));
        review.setDescription(results.getString("description"));
        review.setRating(results.getInt("rating"));
        try{
            review.setPostDate(results.getDate("post_date"));
            review.setPostTime(results.getTime("post_time"));
        } catch (NullPointerException e) {
            throw new DaoException("Problem converting dates/times", e);
        }


        return review;
    }
}
