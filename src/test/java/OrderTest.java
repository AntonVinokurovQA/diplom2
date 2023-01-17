import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import json.Credentials;
import json.User;
import json.UserGenerator;
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
    }

    @Test
    @DisplayName("Create order without auth successful with ingredients")
    public void createOrderWithoutAuthWithIngredients() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithIngredients();
        orderAssertions.orderWithoutAuthSuccessful(newOrderResponse);
    }

    @Test
    @DisplayName("Create order without auth successful with unknown ingredient")
    public void createOrderWithoutAuthWithUnknownIngredient() {
        ValidatableResponse newOrderResponse = orderClient.createOrderWithBadIngredient();
        orderAssertions.orderWithBadIngredient(newOrderResponse);
    }

    @Test
    @DisplayName("Create order with auth successful with ingredients")
    public void createOrderWithAuthWithIngredients() {
        token = authClient.logIn(credentials).extract().path("accessToken");

        ValidatableResponse newOrderResponse = orderClient.createOrderWithIngredients(token);
        orderAssertions.orderWithAuthSuccessful(newOrderResponse, credentials);
    }

    @Test
    @DisplayName("Get orders list without auth failed")
    public void getOrdersWithoutAuthFailed() {
        ValidatableResponse newOrderResponse = orderClient.getOrders();
        orderAssertions.unauthorizedFailed(newOrderResponse);
    }

    @Test
    @DisplayName("Get orders list without auth failed")
    public void getOrdersWithAuthSuccessful() {
        token = authClient.logIn(credentials).extract().path("accessToken");
        orderClient.createOrderWithIngredients(token);

        ValidatableResponse response = orderClient.getOrders(token);
        orderAssertions.getOrderSuccessful(response);
    }

}
