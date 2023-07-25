package org.example.controller;

import org.example.dao.ReviewDao;
import org.example.exception.DaoException;
import org.example.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    public ReviewDao reviewDao;

    @RequestMapping(path="", method= RequestMethod.GET)
    public List<Review> getReviews() {
        return reviewDao.getReviews();
    }

    @RequestMapping(path="/{id}", method = RequestMethod.GET)
    public Review getReviewById(@PathVariable int id) {
        Review review = reviewDao.getReviewById(id);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review was not found");
        } else {
            return review;
        }
    }

    @RequestMapping(path="/recipe/{id}", method = RequestMethod.GET)
    public List<Review> getReviewsByRecipeId(@PathVariable int recipeId) {
        List<Review> reviews = reviewDao.getReviewsByRecipe(recipeId);
        if (reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews were found");
        } else {
            return reviews;
        }
    }

    @RequestMapping(path="/user/{id}", method = RequestMethod.GET)
    public List<Review> getReviewsByUserId(@PathVariable int userId) {
        List<Review> reviews = reviewDao.getReviewsByUser(userId);
        if (reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews were found");
        } else {
            return reviews;
        }
    }

    @RequestMapping(path="/rating/{rating}", method = RequestMethod.GET)
    public List<Review> getReviewsByRating(@PathVariable int rating) {
        List<Review> reviews = reviewDao.getReviewsByRating(rating);
        if (reviews.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No reviews were found");
        } else {
            return reviews;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path="", method = RequestMethod.POST)
    public Review createReview(@Valid @RequestBody Review review) {
        return reviewDao.createReview(review);
    }

    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public Review updateReview(@PathVariable int id, @Valid @RequestBody Review review) {
        try {
            review.setReviewId(id);
            return reviewDao.updateReview(review);
        } catch (DaoException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void deleteReview(@PathVariable int id) {
        reviewDao.deleteReview(id);
    }
    //TODO: Test via postman!

}
