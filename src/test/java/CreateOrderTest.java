

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.example.OrderStep;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderStep orderStep = new OrderStep();
    String[] color;

    @Parameterized.Parameters
    public static Object[] color() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Test
    @DisplayName("Создание заказа")
    @Step("Создание заказа")
    public void createOrderTest() {
        orderStep
                .createOrder(color)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}