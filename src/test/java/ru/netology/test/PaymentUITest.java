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

public class PaymentUITest {
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
        DbHelper.cleanDb();
        open(System.getProperty("sut.url"));
    }

    @AfterEach
    void clean() {
        DbHelper.cleanDb();
    }

    //HappyPath
    @Test
    void shouldBuyInPaymentGate() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        new CardPage().fullData(card);
        getSuccessNotification();

        assertEquals(1, DbHelper.getPayments().size());
        assertEquals(0, DbHelper.getCreditsRequest().size());
        assertEquals(1, DbHelper.getOrders().size());

        assertTrue(DbHelper.getPaymentStatus().equalsIgnoreCase("approved"));
        assertEquals(DbHelper.getPayments().get(0).getTransaction_id()
                , DbHelper.getOrders().get(0).getPayment_id());
        assertNull(DbHelper.getOrders().get(0).getCredit_id());
    }

    @Test
    void shouldBuyInPaymentLatinLetters() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidNameInLatinLetters(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        getSuccessNotification();

        assertEquals(1, DbHelper.getPayments().size());
        assertEquals(0, DbHelper.getCreditsRequest().size());
        assertEquals(1, DbHelper.getOrders().size());
        assertTrue(DbHelper.getPaymentStatus().equalsIgnoreCase("approved"));
        assertEquals(DbHelper.getPayments().get(0).getTransaction_id()
                , DbHelper.getOrders().get(0).getPayment_id());
        assertNull(DbHelper.getOrders().get(0).getCredit_id());
    }

    @Test
    void shouldNotBuyInPaymentDeclinedCardNumber() {
        DataCard card = new DataCard(getDeclinedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);

        assertEquals("DECLINED", DbHelper.getPaymentStatus());
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
        new FirstPage().buy();
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
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormat();
    }

    @Test
    void shouldTheFieldIsRequiredToFillInNumberCard() {
        DataCard card = new DataCard(null,
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getRequiredField();
    }

    //Month
    @Test
    void shouldInvalidMonthValue_00_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                get_OO_Month(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidDate();
    }

    @Test
    void shouldNonExistingMonth_13() {
        DataCard card = new DataCard(getApprovedNumber(),
                get_13_Month(),
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidDate();
    }

    @Test
    void shouldExpiredMonthCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getLastMonth(),
                getCurrentYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getExpiredDateMonth();
    }

    @Test
    void shouldTheFieldIsRequiredToFillInMonthCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                null,
                getNextYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getRequiredField();
    }

    //Year
    @Test
    void shouldExpiredYearCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getLastYear(),
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getExpiredDateYear();
    }

    @Test
    void shouldNotBuyInPaymentGateWithEmptyYear() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                null,
                getValidName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getRequiredField();
    }

    //Name
    @Test
    void shouldInvalidInputOnlyName_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlyName(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlyNameInLatinLetters_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlyNameInLatinLetters(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlySurname_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlySurname(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidName();
    }

    @Test
    void shouldInvalidInputOnlySurnameInLatinLetters_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getOnlySurnameInLatinLetters(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidName();
    }

    @Test
    void shouldNameAndSurnameWithDash_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameAndSurnameDash(),
                getValidCvc());
        new FirstPage().buy();
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
        new FirstPage().buy();
        new CardPage().fullData(card);
        getSuccessNotification();
    }

    @Test
    void shouldInputDigitsInName_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameWithNumbers(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidDataName();
    }

    @Test
    void shouldInputShortNameOneSymbol_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameShort(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getShortName();
    }

    @Test
    void shouldInputLongName_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameLong(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidFormatLong();
    }

    @Test
    void shouldNameFieldIsNotFilledIn() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                null,
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getRequiredField();
    }

    @Test
    void shouldNotBuyInCreditGateWithSpaceInsteadOfName() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getNameSymbol(),
                getValidCvc());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidDataName();
    }

    //CVC/CVV
    @Test
    void shouldInvalidSingleDigitCVCField() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getCvcWithOneDigit());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidCvc();
    }

    @Test
    void shouldInvalidCVCFieldTwoDigits() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                getCvcWithTwoDigits());
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidCvc();
    }

    @Test
    void shouldFieldWithAnEmptyCVC_PaymentCard() {
        DataCard card = new DataCard(getApprovedNumber(),
                getCurrentMonth(),
                getNextYear(),
                getValidName(),
                null);
        new FirstPage().buy();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getInvalidCVCMessage();
    }

    //AllEmptyFields
    @Test
    void shouldAnIncompleteForm_PaymentCard() {
        DataCard card = new DataCard(null, null, null, null, null);
        new FirstPage().buyInCredit();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        paymentPage.getAllFieldsAreRequired();
    }

    @Test
    void shouldFormIsFieldWithSpace_PaymentCard() {
        DataCard card = new DataCard(" ", " ", " ", " ", " ");
        new FirstPage().buyInCredit();
        val paymentPage = new CardPage();
        paymentPage.fullData(card);
        assertEquals(0, DbHelper.getPayments().size());
        assertEquals(0, DbHelper.getCreditsRequest().size());
        assertEquals(0, DbHelper.getOrders().size());
    }
}
