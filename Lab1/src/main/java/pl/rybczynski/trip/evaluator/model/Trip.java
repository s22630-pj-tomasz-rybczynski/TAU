package pl.rybczynski.trip.evaluator.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String destination;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews;
    private BigDecimal price;

    public Trip(Long id, String title, String destination, List<Review> reviews, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.destination = destination;
        this.reviews = reviews;
        this.price = price;
    }

    public Trip(String title, String destination, List<Review> reviews, BigDecimal price) {
        this.id = null;
        this.title = title;
        this.destination = destination;
        this.reviews = reviews;
        this.price = price;
    }

    public Trip() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
