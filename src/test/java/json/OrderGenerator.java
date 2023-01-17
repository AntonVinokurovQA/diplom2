package json;

import org.apache.commons.lang3.RandomStringUtils;
import steps.IngredientsClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OrderGenerator {
    public static Order getRandomOrder() {
        IngredientsClient ingredientsClient = new IngredientsClient();
        List<String> listOfIngredients = new ArrayList<>();
        listOfIngredients.add(ingredientsClient.getListOfIngredients().get(ThreadLocalRandom.current().nextInt(1, ingredientsClient.getListOfIngredients().size())));
        listOfIngredients.add(ingredientsClient.getListOfIngredients().get(ThreadLocalRandom.current().nextInt(1, ingredientsClient.getListOfIngredients().size())));

        return new Order(listOfIngredients);
    }

    public static Order getOrderWithBadIngredient() {
        IngredientsClient ingredientsClient = new IngredientsClient();
        List<String> listOfIngredients = new ArrayList<>();
        listOfIngredients.add(RandomStringUtils.randomAlphabetic(7));

        return new Order(listOfIngredients);
    }
}
