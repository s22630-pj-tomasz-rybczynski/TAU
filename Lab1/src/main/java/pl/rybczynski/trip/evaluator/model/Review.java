package pl.rybczynski.trip.evaluator.model;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private Integer rating;

    public Review(Long id, String content, User user, Integer rating) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.rating = rating;
    }

    public Review(String content, User user, Integer rating) {
        this.id = null;
        this.content = content;
        this.user = user;
        this.rating = rating;
    }

    public Review() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
