package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.SQLUtils;
import ru.netology.data.UserInfo;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.generateUserInfo;
import static ru.netology.data.DataGenerator.getInvalidUserInfo;

public class LoginTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        SQLUtils.clearTables();
    }

    @Test
    void shouldLogin() {
        val validUser = generateUserInfo();
        val loginPage = new LoginPage();
        val verificationPage = loginPage.validLogin(validUser);
        verificationPage.validVerify(SQLUtils.getVerificationCode());
    }

    @Test
    void shouldBlock() {
        val invalidUser = getInvalidUserInfo();
        val loginPage = new LoginPage();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.cleanFields();
        loginPage.login(invalidUser);
        loginPage.blockNotice();
    }
}
