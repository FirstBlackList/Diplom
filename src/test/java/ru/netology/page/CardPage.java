package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardPage {
    private SelenideElement cardNumberInput = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInput = $("input[placeholder='08']");
    private SelenideElement yearInput = $("input[placeholder='22']");
    private SelenideElement holderInput = $(byXpath(".//span[text()='Владелец']//ancestor::div/span/span[1]/span//input"));
    private SelenideElement cvcInput = $("input[placeholder='999']");
    private SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));

    public void fullData(DataCard card) {
        cardNumberInput.setValue(card.getNumber());
        monthInput.setValue(card.getMonth());
        yearInput.setValue(card.getYear());
        holderInput.setValue(card.getNameUser());
        cvcInput.setValue(card.getCvc());
        continueButton.click();
    }

    public static void getSuccessNotification() {
        $(withText("Успешно")).should(visible, Duration.ofSeconds(15));
        $(withText("Операция одобрена Банком.")).should(visible, Duration.ofSeconds(15));
    }

    public static void getErrorNotification() {
        $(withText("Ошибка")).should(visible, Duration.ofSeconds(15));
        $(withText("Ошибка! Банк отказал в проведении операции.")).should(visible, Duration.ofSeconds(15));
    }

    public void getInvalidFormat() {
        $(withText("Неверный формат")).shouldBe(visible);
    }

    public void getInvalidFormatLong() {
        $(withText("Значение поля не может содержать более 100 символов")).shouldBe(visible);
    }

    public void getRequiredField() {
        $(withText("Поле обязательно для заполнения")).shouldBe(visible);
    }

    public void getInvalidCVCMessage() {
        $x("//span[contains(text(),'Неверный формат')]")
                .shouldHave(text("Поле обязательно для заполнения"))
                .should(visible);
    }

    public void getInvalidDataName() {
        $(withText("Значение поля может содержать только русские или только латинские буквы и дефис")).shouldBe(visible);
    }

    public void getShortName() {
        $(withText("Значение поля должно содержать больше одной буквы")).shouldBe(visible);
    }

    public void getInvalidCvc() {
        $(withText("Значение поля должно содержать 3 цифры")).shouldBe(visible);
    }

    public void getAllFieldsAreRequired() {
        $$(".input__sub").shouldHave(CollectionCondition.size(5))
                .shouldHave(CollectionCondition
                        .texts("Поле обязательно для заполнения"));
    }

    public void getInvalidDate() {
        $(withText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void getExpiredDateMonth() {
        $(withText("Неверно указан срок действия карты")).shouldBe(visible);
    }

    public void getExpiredDateYear() {
        $(withText("Истёк срок действия карты")).shouldBe(visible);
    }

    public void getInvalidName() {
        $(withText("Введите полное имя и фамилию")).shouldBe(visible);
    }
}
