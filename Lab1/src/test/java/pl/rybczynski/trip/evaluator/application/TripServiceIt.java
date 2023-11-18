package pl.rybczynski.trip.evaluator.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.rybczynski.trip.evaluator.model.Review;
import pl.rybczynski.trip.evaluator.model.Trip;
import pl.rybczynski.trip.evaluator.repository.TripRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TripServiceIt {

    @Autowired
    private TripService tripService;

    @MockBean
    private TripRepo tripRepository;

    @Test
    void alignRating_10() {
        Integer rating = 10;
        Integer alignedRating = tripService.alignRating(rating);

        assertThat(alignedRating).isEqualTo(5);
    }

    @Test
    void alignRating_0() {
        Integer rating = 0;
        Integer alignedRating = tripService.alignRating(rating);

        assertThat(alignedRating).isEqualTo(1);
    }

    @Test
    void alignRatingForReviews() {
        Review review1 = new Review("content", null, 100);
        Review review2 = new Review("content", null, -20);
        Review review3 = new Review("content", null, 3);
        Trip trip = new Trip("title", "destination", List.of(review1, review2, review3), BigDecimal.TEN);
        tripService.alignRatingForEachReview(trip);

        assertThat(trip.getReviews().stream().map(Review::getRating).collect(Collectors.toList())).isEqualTo(List.of(5, 1, 3));
    }

    @Test
    void shouldFindById() {
        when(tripRepository.findById(any())).thenReturn(Optional.of(new Trip()));

        Trip trip = tripService.findById(1L);

        assertThat(trip).isNotNull();
    }

    @Test
    void shouldNotFindById() {
        when(tripRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> tripService.findById(1L));
    }

    @Test
    void deleteTripById() {
        tripRepository.deleteById(1L);

        verify(tripRepository).deleteById(any());
    }

    @Test
    void getAllTrips() {
        List<Trip> mock = List.of(new Trip(), new Trip());

        when(tripRepository.findAll()).thenReturn(mock);

        List<Trip> trips = tripService.findAll();

        assertThat(trips).isEqualTo(mock);
    }

    @Test
    void getAllTripsEmpty() {
        when(tripRepository.findAll()).thenReturn(List.of());

        List<Trip> trips = tripService.findAll();

        assertThat(trips).isEqualTo(List.of());
    }

    @Test
    void tripExists() {
        when(tripRepository.existsById(1L)).thenReturn(Boolean.TRUE);
        when(tripRepository.existsById(2L)).thenReturn(Boolean.FALSE);

        Boolean trip1 = tripService.existsById(1L);
        Boolean trip2 = tripService.existsById(2L);

        assertThat(trip1).isEqualTo(Boolean.TRUE);
        assertThat(trip2).isEqualTo(Boolean.FALSE);
    }

    @Test
    void createTrip() {
        Trip trip = new Trip();
        when(tripRepository.save(trip)).thenReturn(trip);

        Trip saved = tripService.save(trip);

        assertThat(saved).isEqualTo(saved);
    }
}
