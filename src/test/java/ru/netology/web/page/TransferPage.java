package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public DashboardPage updateBalance(int amountValue, DataHelper.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amountValue));
        fromField.setValue(cardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    // если сумма перевода больше, чем есть на балансе карты
    public DashboardPage amountExceedsBalance(int amountValue, DataHelper.CardInfo cardInfo) {
        amountField.setValue(String.valueOf(amountValue));
        fromField.setValue(cardInfo.getCardNumber());
        //  transferButton.click();
        $(withText("Ошибка, недостаточно средств на карте для указанной суммы перевода")).shouldBe(Condition.visible);
        return new DashboardPage();
    }


}
