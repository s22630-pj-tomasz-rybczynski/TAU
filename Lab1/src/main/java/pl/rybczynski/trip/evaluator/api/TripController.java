package pl.rybczynski.trip.evaluator.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rybczynski.trip.evaluator.application.TripService;
import pl.rybczynski.trip.evaluator.model.Review;
import pl.rybczynski.trip.evaluator.model.Trip;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {

    final private TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> findAll() {
        return ResponseEntity.ok(tripService.findAll());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello world");
    }

    @GetMapping
    public ResponseEntity<Trip> findById(@RequestParam Long id) {
        return ResponseEntity.ok(tripService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Trip> addTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.save(trip));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrip(@RequestParam Long id) {
        tripService.delete(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addReview(@RequestBody Review review, @RequestParam Long id) {
        tripService.addReview(id, review);
    }

}
