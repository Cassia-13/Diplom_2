package site.nomoreparties.stellarburgers;

import com.github.javafaker.Faker;
import site.nomoreparties.stellarburgers.http.OrderApi;
import site.nomoreparties.stellarburgers.http.UserApi;
import site.nomoreparties.stellarburgers.model.order.Order;
import site.nomoreparties.stellarburgers.model.user.User;
import site.nomoreparties.stellarburgers.model.user.UserResponse;

public abstract class DefaultTest {
    protected final Faker faker = new Faker();
    
    protected UserResponse userResponse = new UserResponse();


    protected final UserApi userApi = new UserApi();

    protected final OrderApi orderApi = new OrderApi();
    protected final Order order = new Order();
    
    
    protected User user = new User(faker.name().username() + "@piu.ru", faker.random().hex(), faker.name().firstName());
}
