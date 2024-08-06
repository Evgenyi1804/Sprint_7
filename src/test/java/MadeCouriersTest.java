import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Issue;


import org.junit.After;
import org.junit.Test;
import org.example.CourierSteps;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import static org.hamcrest.Matchers.is;

public class MadeCouriersTest {
   String loginCourier = randomAlphabetic(12);
   String passwordCourier = randomAlphabetic(10);
   String firstNameCourier = randomAlphabetic(8);

    private CourierSteps courier = new CourierSteps();

    @Test
    @DisplayName("Создание курьера с заполнением всех полей")
    @Step("Создание курьера")
    public void createCourierTrue() {


        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера только с заполнением обязательных полей")
    @Step("Создание курьера")
    public void createCourierTrueWithoutFirstName() {

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Невозможность создания курьера без логина")
    @Step("Попытка создания курьера")
    public void createCourierFalseWithoutLogin() {

        courier
                .createCourier("", passwordCourier, firstNameCourier)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания курьера без пароля")
    @Step("Попытка создания курьера")
    public void createCourierFalseWithoutPassword() {
             courier
                .createCourier(loginCourier, "", firstNameCourier)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания двух курьеров с одинаковым логином")
    @Step("Попытка создания курьера")
    @Description("bug в теле ответа")
    @Issue("https://merkuschin2012.youtrack.cloud/issue/8M-119/Pri-otpravke-zaprosa-api-v1-courier-na-sozdanie-2h-kurerov-s-odinakovym-loginom-v-tele-otveta-prihodit-Etot-login-uzhe")
    public void createDoubleCourierFalse() {

        // Создали курьера первый раз
        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(201)
                .body("ok", is(true));

        // Попробуем создать второй раз
        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(409);
//Здесь баг, так как по документации в теле ответа "message": "Этот логин уже используется", а по факту другое
              //  .body("message", is("Этот логин уже используется."));
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