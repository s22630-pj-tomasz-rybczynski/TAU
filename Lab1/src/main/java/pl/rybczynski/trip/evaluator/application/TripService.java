package pl.rybczynski.trip.evaluator.application;

import org.springframework.stereotype.Service;
import pl.rybczynski.trip.evaluator.model.Review;
import pl.rybczynski.trip.evaluator.model.Trip;
import pl.rybczynski.trip.evaluator.repository.TripRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepo tripRepo;

    public TripService(TripRepo tripRepo) {
        this.tripRepo = tripRepo;
    }

    public Trip save(Trip trip) {
        return tripRepo.save(trip);
    }

    public void addReview(Long tripId, Review review) {
        Trip trip = findById(tripId);
        List<Review> reviews = trip.getReviews();
        reviews.add(review);
        trip.setReviews(reviews);
        tripRepo.save(trip);
    }

    public List<Trip> findAll() {
        return tripRepo.findAll();
    }

    public Trip findById(Long tripId) {
        return tripRepo.findById(tripId).orElseThrow();
    }

    public Boolean existsById(Long tripId) { return tripRepo.existsById(tripId); }

    public void delete(Long tripId) {
        tripRepo.deleteById(tripId);
    }

    public Integer alignRating(Integer rating) { //5 star rating
        if (rating >= 5) {
            return 5;
        } else if (rating <= 1) {
            return 1;
        } else {
            return rating;
        }
    }

    public void alignRatingForEachReview(Trip trip) {
        trip.getReviews().forEach(review ->
                review.setRating(alignRating(review.getRating()))
        );
    }

    public boolean isFullyReviewed(Trip trip) {
        return trip.getReviews().size() > 10;
    }

    public boolean areCustomersHappy(Trip trip) {
        List<Integer> score = trip.getReviews().stream().map(Review::getRating).collect(Collectors.toList());
        Integer sum = score.stream().reduce(0, Integer::sum);
        if (sum / score.size() >= 4) {
            return true;
        } else {
            return false;
        }
    }

    public void maskContent(Trip trip) {
        trip.getReviews().forEach(review ->
                review.setContent("<MASKED>")
        );
    }

    public void capitalizeNames(Trip trip) {
        trip.getReviews().forEach(review ->
                review.getUser().getName().toUpperCase()
        );
    }
}
