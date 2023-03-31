package com.dlowji.simple;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class KitchenServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(KitchenServiceApplication.class, args);
	}

	@Component
	@Order(1)
	class setupCategory implements CommandLineRunner {
		@Autowired
		private ICategoryRepository iCategoryRepository;
		@Override
		public void run(String... args) throws Exception {
			try{
				if (!iCategoryRepository.findById("C01").isPresent()) {
					Category drink = Category.builder().categoryId("C01").categoryName("Drink").link("?q=drink").icon("fas fa-cocktail").build();
					iCategoryRepository.save(drink);
				}

				if (!iCategoryRepository.findById("C02").isPresent()) {
					Category desert = Category.builder().categoryId("C02").categoryName("Dessert").link("?q=dessert").icon("fas fa-cookie-bite").build();
					iCategoryRepository.save(desert);
				}

				if (!iCategoryRepository.findById("C03").isPresent()) {
					Category pizza = Category.builder().categoryId("C03").categoryName("Pizza").link("?q=pizza").icon("fas fa-pizza-slice").build();
					iCategoryRepository.save(pizza);
				}

				if (!iCategoryRepository.findById("C04").isPresent()) {
					Category hamburger = Category.builder().categoryId("C04").categoryName("Hamburger").link("?q=hamburger").icon("fas fa-hamburger").build();
					iCategoryRepository.save(hamburger);
				}

				if (!iCategoryRepository.findById("C05").isPresent()) {
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
}
