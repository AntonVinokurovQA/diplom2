import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.Credentials;
import json.User;
import json.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.AuthClient;
import steps.OrderAssertions;
import steps.OrderClient;
import steps.UserClient;

public class OrderTest {
    OrderClient orderClient = new OrderClient();
    OrderAssertions orderAssertions = new OrderAssertions();
    UserClient userClient = new UserClient();
    AuthClient authClient = new AuthClient();
    private String token;
    private User user;
    private Credentials credentials;

    @Before
    public void serUp() {
        user = UserGenerator.getRandomUser();
        credentials = new Credentials(user.getEmail(), user.getPassword());
        userClient.createUser(user);
        token = authClient.logIn(credentials).extract().path("accessToken");
    }

    @After
    public void tearDown() {
        userClient.deleteUser(token);
    }

    @Test
    @DisplayName("Create order without auth successful with ingredients")
    public void createOrderWithoutAuthWithIngredientsSuccessful() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithIngredients();
        orderAssertions.orderSuccessful(newOrderResponse);
    }

    @Test
    @DisplayName("Create order without auth without ingredients failed")
    public void createOrderWithoutAuthWithoutIngredientsFailed() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithoutIngredients();
        orderAssertions.withoutIngredientFailed(newOrderResponse);
    }

    @Test
    @DisplayName("Create order without auth successful with unknown ingredient")
    public void createOrderWithoutAuthWithUnknownIngredientFailed() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithBadIngredient();
        orderAssertions.badIngredientFailed(newOrderResponse);
    }

    @Test
    @DisplayName("Create order with auth successful with ingredients")
    public void createOrderWithAuthWithIngredientsSuccessful() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithIngredients(token);
        orderAssertions.orderSuccessful(newOrderResponse, credentials);
    }

    @Test
    @DisplayName("Get orders list without auth failed")
    public void getOrdersWithoutAuthFailed() {
        ValidatableResponse newOrderResponse = orderClient.getOrders();
        orderAssertions.unauthorizedFailed(newOrderResponse);
    }

    @Test
    @DisplayName("Get orders list with auth successful")
    public void getOrdersWithAuthSuccessful() {
        orderClient.createOrderWithIngredients(token);

        ValidatableResponse response = orderClient.getOrders(token);
        orderAssertions.getOrderSuccessful(response);
    }
}
