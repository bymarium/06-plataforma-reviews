package org.example.services.review;

import org.example.models.Dish;
import org.example.models.Review;
import org.example.models.ReviewFactory;
import org.example.services.interfaces.ICommand;
import org.example.services.menu.SelectDish;
import org.example.utils.Validator;


public class CreateDishReview implements ICommand<Boolean> {
	private final Validator validator;
	private final SelectDish selectDish;

	public CreateDishReview(Validator validator, SelectDish selectDish) {
		this.validator = validator;
		this.selectDish = selectDish;
	}

	@Override
	public Boolean execute() {
		Dish dish = selectDish.execute();
		if (dish == null) {
			return false;
		}

		Review review = createDishReview();

		dish.addReview(review);
		return true;
	}

	private Review createDishReview() {
		String comment = validator.readString("Ingrese un comentario para el plato: ");
		Float tasteRating = validator.readRating("Ingrese una calificación para el sabor (0-5): ");
		Float presentationRating = validator.readRating("Ingrese una calificación para la presentación (0-5): ");

		return ReviewFactory.createReview(comment, tasteRating, presentationRating);
	}
}