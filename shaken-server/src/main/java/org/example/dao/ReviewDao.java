package org.example.dao;

import org.example.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getReviews();
    Review getReviewById(int id);
    List<Review> getReviewsByRecipe(int recipeId);
    List<Review> getReviewsByUser(int userId);
    List<Review> getReviewsByRating(int rating);
    Review createReview(Review newReview);
    Review updateReview(Review updatedReview);
    boolean deleteReview(int id);

}
