package org.example.models;

import org.example.models.interfaces.IObservable;
import org.example.models.interfaces.IObserver;

import java.util.ArrayList;
import java.util.List;

public class Dish implements IObservable {
  private static int idCounter = 1;
  private Integer dishId;
  private String name;
  private String description;
  private Float price;
  private List<Review> reviews;

  private List<IObserver> observers = new ArrayList<>();

  public Dish() {
  }

  public Dish(String name, String description, Float price) {
    this.dishId = generateId();
    this.name = name;
    this.description = description;
    this.price = price;
    this.reviews = new ArrayList<>();
  }

  private static int generateId() {
    return idCounter++;
  }

  public Integer getDishId() {
    return dishId;
  }

  public void setDishId(Integer dishId) {
    this.dishId = dishId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public List<IObserver> getObservers() {
    return observers;
  }

  public void setObservers(List<IObserver> observers) {
    this.observers = observers;
  }

  public void addReview(Review review) {
    this.reviews.add(review);

    notifyObservers("Se ha agregado una nueva review al plato: " + this.name);

    Float newAverage = calculateAverageRating();
    notifyObservers("La calificación promedio del plato " + this.name + " ha cambiado a: " + newAverage);
  }

  public Float calculateAverageRating() {
    return (float) reviews.stream()
      .mapToDouble(Review::getRatingAverage)
      .average()
      .orElse(0.0);
  }

  @Override
  public String toString() {
    return """
        Plato {
          ID: %d
          Nombre: '%s'
          Descripción: '%s'
          Precio: %.2f
          Calificación promedio: %.2f
        }
        """.formatted(dishId, name, description, price, calculateAverageRating());
  }

  @Override
  public void addObserver(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void notifyObservers(String message) {
    observers.forEach(observer -> observer.update(message));
  }
}