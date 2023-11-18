package pl.rybczynski.trip.evaluator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.rybczynski.trip.evaluator.model.Trip;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {
}
