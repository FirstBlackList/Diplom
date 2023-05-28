package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataCard;
import ru.netology.data.DbHelper;
import ru.netology.page.CardPage;
import ru.netology.page.FirstPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.page.CardPage.getErrorNotification;
import static ru.netology.page.CardPage.getSuccessNotification;

public class CreditUITest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open(System.getProperty("sut.url"));
    }

    @AfterEach
    void clean() {
        DbHelper.cleanDb();
    }

    //HappyPath
    @Test
    void shouldBuyInCreditGate() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        new CardPage().fullData(card);
        getSuccessNotification();

        assertEquals(1, DbHelper.getCreditsRequest().size());
        assertEquals(0, DbHelper.getPayments().size());
        assertEquals(1, DbHelper.getOrders().size());
        assertTrue(DbHelper.getCreditStatus().equalsIgnoreCase("approved"));
        assertEquals(DbHelper.getCreditsRequest().get(0).getBank_id(),
                DbHelper.getOrders().get(0).getPayment_id());
    }

    @Test
    void shouldBuyInCreditLatinName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidNameInLatinLetters(),
                getValidCvc());
        new FirstPage().buyInCredit();
        new CardPage().fullData(card);
        getSuccessNotification();

        assertEquals(1, DbHelper.getCreditsRequest().size());
        assertEquals(0, DbHelper.getPayments().size());
        assertEquals(1, DbHelper.getOrders().size());
        assertTrue(DbHelper.getCreditStatus().equalsIgnoreCase("approved"));
        assertEquals(DbHelper.getCreditsRequest().get(0).getBank_id(),
                DbHelper.getOrders().get(0).getPayment_id());
    }

    @Test
    void shouldNotBuyInDeclinedCardNumber() {
        DataCard card = new DataCard(getDeclinedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        new CardPage().fullData(card);

        assertEquals("DECLINED", DbHelper.getCreditStatus());
        getErrorNotification();
    }

    //CardNumber
    @Test
    void shouldInvalidCardNumberErrorNotification() {
        DataCard card = new DataCard(getInvalidCardNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        new CardPage().fullData(card);
        getErrorNotification();
    }

    @Test
    void shouldInvalidFormatShortCardNumber() {
        DataCard card = new DataCard(getShortCardNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidFormat();
    }

    @Test
    void shouldTheFieldIsRequiredToFillInNumberCard() {
        DataCard card = new DataCard(null,
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getRequiredField();
    }

    //Month
    @Test
    void shouldInvalidMonthValue_00_CreditCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                get_OO_Month(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidDate();
    }

    @Test
    void shouldNonExistingMonth_13() {
        DataCard card = new DataCard(getApprovedNumber(),
                get_13_Month(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidDate();
    }

    @Test
    void shouldExpiredMonthCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getLastMonth(),
                getCurrentYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getExpiredDateMonth();
    }

    @Test
    void shouldTheFieldIsRequiredToFillInMonthCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                null,
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getRequiredField();
    }

    //Year
    @Test
    void shouldExpiredYearCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getLastYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getExpiredDateYear();
    }

    @Test
    void shouldNotBuyInCreditGateWithEmptyYear() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                null,
                getValidName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getRequiredField();
    }

    //Name
    @Test
    void shouldInvalidInputOnlyName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlyName(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlyNameInLatinLetters() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlyNameInLatinLetters(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlySurname() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlySurname(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlySurnameInLatinLetters() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlySurnameInLatinLetters(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidName();
    }

    @Test
    void shouldNameAndSurnameWithDash() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameAndSurnameDash(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormat();
    }

    @Test
    void shouldDoubleSurname() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getDoubleSurname(),
                getValidCvc());
        new FirstPage().buyInCredit();
        new CardPage().fullData(card);
        getSuccessNotification();
    }

    @Test
    void shouldInputDigitsInName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameWithNumbers(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidDataName();
    }

    @Test
    void shouldInputShortNameOneSymbol() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameShort(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getShortName();
    }

    @Test
    void shouldInputLongName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameLong(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidFormatLong();
    }

    @Test
    void shouldNameFieldIsNotFilledIn() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                null,
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getRequiredField();
    }

    @Test
    void shouldNotBuyInCreditGateWithSpaceInsteadOfName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameSymbol(),
                getValidCvc());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidDataName();
    }

    //CVC/CVV
    @Test
    void shouldInvalidSingleDigitCVCField() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getCvcWithOneDigit());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidCvc();
    }

    @Test
    void shouldInvalidCVCFieldTwoDigits() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getCvcWithTwoDigits());
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidCvc();
    }

    @Test
    void shouldFieldWithAnEmptyCVC() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                null);
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getInvalidCVCMessage();
    }

    //AllEmptyFields
    @Test
    void shouldAnIncompleteForm() {
        DataCard card = new DataCard(null, null, null, null, null);
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        creditPage.getAllFieldsAreRequired();
    }

    @Test
    void shouldFormIsFieldWithSpace() {
        DataCard card = new DataCard(" ", " ", " ", " ", " ");
        new FirstPage().buyInCredit();
        val creditPage = new CardPage();
        creditPage.fullData(card);
        assertEquals(0, DbHelper.getCreditsRequest().size());
        assertEquals(0, DbHelper.getPayments().size());
        assertEquals(0, DbHelper.getOrders().size());
    }
}
