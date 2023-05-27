package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class FirstPage {
    private final SelenideElement heading = $x("//div[@id='root']/div/div[contains(@class, 'card')]");
    private final SelenideElement buyButton = $x("//span[text()='Купить']//ancestor::button");
    private final SelenideElement creditButton = $x("//span[text()='Купить в кредит']//ancestor::button");

    private final SelenideElement headingPayment = $$("h3").findBy(Condition.text("Оплата по карте"));
    private final SelenideElement headingCredit = $$("h3").findBy(Condition.text("Кредит по данным карты"));

    public FirstPage() {
        heading.shouldBe(Condition.visible);
    }

    public CardPage buy() {
        buyButton.click();
        headingPayment.shouldBe(Condition.visible);
        return new CardPage();
    }

    public CardPage buyInCredit() {
        creditButton.click();
        headingCredit.shouldBe(Condition.visible);
        return new CardPage();
    }
}
