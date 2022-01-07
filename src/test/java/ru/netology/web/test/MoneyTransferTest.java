package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void transferFromSecondToFirstCard() {
        val dashboardPage = new DashboardPage();
        int amountValue = 1200;
        val expectedResultSecondCard = dashboardPage.getSecondCardBalance() - amountValue;
        val expectedResultFirstCard = dashboardPage.getFirstCardBalance() + amountValue;
        val transferPage = dashboardPage.firstCardDeposit();
        transferPage.updateBalance(amountValue, DataHelper.getSecondCard());
        val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
        val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedResultFirstCard, actualResultsFirstCard);
        assertEquals(expectedResultSecondCard, actualResultsSecondCard);
    }

    @Test
    void transferFromFirstToSecondCard() {

        val dashboardPage = new DashboardPage();
        int amountValue = 26000;
        val expectedResultFirstCard = dashboardPage.getFirstCardBalance() - amountValue;
        val expectedResultSecondCard = dashboardPage.getSecondCardBalance() + amountValue;
        val transferPage = dashboardPage.secondCardDeposit();
        //   проверка условия

        if (expectedResultFirstCard < 0) {
            transferPage.amountExceedsBalance(amountValue, DataHelper.getFirstCard());
            val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
            val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
        } else {

            transferPage.updateBalance(amountValue, DataHelper.getFirstCard());
            val actualResultsFirstCard = dashboardPage.getFirstCardBalance();
            val actualResultsSecondCard = dashboardPage.getSecondCardBalance();
            assertEquals(expectedResultFirstCard, actualResultsFirstCard);
            assertEquals(expectedResultSecondCard, actualResultsSecondCard);
        }
    }
}
