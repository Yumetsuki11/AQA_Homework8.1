package ru.netology.sql.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.sql.data.DataHelper;
import ru.netology.sql.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @BeforeEach
    public void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @AfterAll
    public static void shouldDelInfo(){
        DataHelper.deleteOldData();
    }

    @Test
    public void shouldLoginIfValidCredentials() {
        var loginPage = new LoginPage();

        loginPage
                .validLogin(DataHelper.getAuthInfo())
                .validVerify(DataHelper.getAuthCode());
    }

    @Test
    public void shouldBlockIfInvalidPassword() {
        var loginPage = new LoginPage();

        loginPage.tooManyAttemptsAssert(DataHelper.getAuthInfo());
    }
}
