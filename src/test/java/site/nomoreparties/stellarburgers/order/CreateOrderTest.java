package site.nomoreparties.stellarburgers.order;

import com.github.javafaker.Faker;
import site.nomoreparties.stellarburgers.http.IngredientsApi;
import site.nomoreparties.stellarburgers.http.UserApi;
import site.nomoreparties.stellarburgers.model.ingredients.IngredientsResponse;
import site.nomoreparties.stellarburgers.DefaultTest;
import io.qameta.allure.junit4.DisplayName;
import site.nomoreparties.stellarburgers.model.user.User;
import site.nomoreparties.stellarburgers.model.user.UserResponse;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends DefaultTest {

    @Test
    @DisplayName("Creating an order with authorization and ingredients")
    public void createOrderWithAuthAndIngredients() {
        Faker faker = new Faker();
        UserApi userApi = new UserApi();
        User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());

        userApi.registrationResponse(user);
        UserResponse response = userApi.authResponse(user)
                .body().as(UserResponse.class);

        IngredientsApi ingredientsApi = new IngredientsApi();
        IngredientsResponse ingredientsResponse = ingredientsApi.getIngredientsResponse()
                .body().as(IngredientsResponse.class);

        int newIndex = faker.number().numberBetween(0, 14);

        List<String> ingredients = List.of(ingredientsResponse.getData().get(newIndex).get_id());
        order.setIngredients(ingredients);

        orderApi.createResponse(response, order)
                .then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));

        userApi.removeResponse(response)
                .then()
                .statusCode(202);
    }

    @Test
    @DisplayName("Creating an order without authorization and ingredients")
    public void createOrderWithoutAuthAndIngredients() {
        orderApi.createResponse(order)
                .then()
                .assertThat()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message",equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Creating an order with incorrect ingredients")
    public void createOrderWithIncorrectIngredients() {
        Faker faker =new Faker();
        List<String> ingredients = List.of(String.valueOf(faker.hashCode()));
        order.setIngredients(ingredients);

        orderApi.createResponse(order)
                .then()
                .assertThat()
                .statusCode(500); // падает из-за не верной ошибки 400 вместо 500
    }
}
