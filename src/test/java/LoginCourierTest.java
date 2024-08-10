

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.example.CourierSteps;
import io.qameta.allure.Description;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    String loginCourier = randomAlphabetic(12);
    String passwordCourier = randomAlphabetic(10);

    private CourierSteps courier = new CourierSteps();

    @Test
    @DisplayName("Успешная авторизация курьером")

    public void loginCourierTrue() {


        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier(loginCourier, passwordCourier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Попытка авторизации с несуществующим логином")
       public void loginCourierFalseLogin() {
        String loginCourierFalse = randomAlphabetic(12);



        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));


        courier
                .loginCourier(loginCourierFalse, passwordCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")

    public void loginCourierFalsePassword() {
        String passwordCourierFalse = randomAlphabetic(10);


        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));




        courier
                .loginCourier(loginCourier, passwordCourierFalse)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизации без логина")

    public void loginCourierFalseWithoutLogin() {


        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier("", passwordCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Создаем курьера, но авторизуемся без указания пароля")
    public void loginCourierFalseWithoutPassword() {


        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier(loginCourier, "")
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void Cleaning() {
        Integer idCourier = courier.loginCourier(loginCourier, passwordCourier)
                .extract()
                .body()
                .path("id");
        if (idCourier != null) {
            courier.deleteCourier(idCourier.toString());
        }
    }
}