package ru.netology.sql.page;

import com.codeborne.selenide.Condition;
import ru.netology.sql.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static ru.netology.sql.data.DataHelper.getRandomPassword;

public class LoginPage {
    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(info.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }

    public void invalidPasswordAssert(DataHelper.AuthInfo info) {
        $("[data-test-id=login] input").setValue(info.getLogin());
        $("[data-test-id=password] input").setValue(getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error_notification]").shouldBe(Condition.visible);
    }

    public void tooManyAttemptsAssert(DataHelper.AuthInfo info) {
        for (int i = 0; i < 3; i++) {
            invalidPasswordAssert(info);
            if (i < 2) {
                $("[data-test-id=error_notification] .notification__content").getText().equals("Ошибка! " +
                        "Неверно указан логин или пароль");
            } else {
                $("[data-test-id=error_notification] .notification__content").getText().equals("Ошибка! " +
                        "Превышено количество попыток входа. Пользователь заблокирован");
            }
        }
    }


}
