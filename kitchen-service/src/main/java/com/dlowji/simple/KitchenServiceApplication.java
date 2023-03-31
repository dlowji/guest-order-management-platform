package com.dlowji.simple;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.Dish;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.data.IDishRepository;
import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.command.api.model.dto.DishDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class KitchenServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}

	@Component
	@Order(1)
	static
	class setupCategories implements CommandLineRunner {
		private final ICategoryRepository iCategoryRepository;

		public setupCategories(ICategoryRepository iCategoryRepository) {
			this.iCategoryRepository = iCategoryRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			try{
				if (iCategoryRepository.findById("C01").isEmpty()) {
					Category drink = Category.builder().categoryId("C01").categoryName("Drink").link("?q=drink").icon("fas fa-cocktail").build();
					iCategoryRepository.save(drink);
				}

				if (iCategoryRepository.findById("C02").isEmpty()) {
					Category desert = Category.builder().categoryId("C02").categoryName("Dessert").link("?q=dessert").icon("fas fa-cookie-bite").build();
					iCategoryRepository.save(desert);
				}

				if (iCategoryRepository.findById("C03").isEmpty()) {
					Category pizza = Category.builder().categoryId("C03").categoryName("Pizza").link("?q=pizza").icon("fas fa-pizza-slice").build();
					iCategoryRepository.save(pizza);
				}

				if (iCategoryRepository.findById("C04").isEmpty()) {
					Category hamburger = Category.builder().categoryId("C04").categoryName("Hamburger").link("?q=hamburger").icon("fas fa-hamburger").build();
					iCategoryRepository.save(hamburger);
				}

				if (iCategoryRepository.findById("C05").isEmpty()) {
					Category pasta = Category.builder().categoryId("C05").categoryName(
					"Pasta").icon("fa-solid fas fa-pastafarianism").link("?q=pasta").build();
					iCategoryRepository.save(pasta);
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@Component
	@Order(2)
	static
	class setupDishes implements CommandLineRunner {

		private final IDishRepository dishRepository;
		private final ICategoryRepository categoryRepository;

		setupDishes(IDishRepository dishRepository, ICategoryRepository categoryRepository) {
			this.dishRepository = dishRepository;
			this.categoryRepository = categoryRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			try {
				String path = "kitchen-service/src/main/resources/dishes.json";
				ObjectMapper objectMapper = new ObjectMapper();
				DishDTO[] dishes = objectMapper.readValue(new File(path), DishDTO[].class);
				for (DishDTO dish : dishes) {
					String categoryId = dish.getCategoryId();
					Optional<Category> existCategory = categoryRepository.findById(categoryId);
					if (existCategory.isPresent()) {
						Category category = existCategory.get();
						if (dishRepository.findByTitle(dish.getTitle()).isEmpty()) {
							String dishId = UUID.randomUUID().toString();
							Dish savedDish = Dish.builder()
									.dishId(dishId)
									.title(dish.getTitle())
									.image(dish.getImage())
									.price(dish.getPrice())
									.summary(dish.getSummary())
									.category(category)
									.dishStatus(DishStatus.AVAILABLE)
									.build();
							dishRepository.save(savedDish);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
