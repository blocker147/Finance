package com.example.finance.mapper;

import com.openapi.client.fastbank.model.Application;
import com.openapi.client.fastbank.model.Offer;
import com.openapi.finance.model.Application.StatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FastBankMapperTest {
    private final FastBankMapper target = new FastBankMapperImpl();

    @Test
    void testFromClientApplication() {
        var testOffer = new Offer();
        testOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        testOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        testOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        testOffer.setNumberOfPayments(12);
        testOffer.setFirstRepaymentDate("2022-01-01");

        var fastBankApp = new Application();
        fastBankApp.setId("123");
        fastBankApp.setStatus(Application.StatusEnum.DRAFT);
        fastBankApp.setOffer(testOffer);

        var financeApp = target.fromClient(fastBankApp);

        assertNotNull(financeApp);
        assertEquals("123", financeApp.getId());
        assertEquals(StatusEnum.DRAFT, financeApp.getStatus());
        assertTrue(financeApp.getOffer().isPresent());
        assertEquals(fastBankApp.getOffer().getMonthlyPaymentAmount(), financeApp.getOffer().get().getMonthlyPaymentAmount());
    }

    @Test
    void testFromClientOffer() {
        var fastBankOffer = new Offer();
        fastBankOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        fastBankOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        fastBankOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        fastBankOffer.setNumberOfPayments(12);
        fastBankOffer.setFirstRepaymentDate("2022-01-01");

        var financeOffer = target.fromClient(fastBankOffer);

        assertNotNull(financeOffer);
        assertEquals(fastBankOffer.getMonthlyPaymentAmount(), financeOffer.getMonthlyPaymentAmount());
        assertEquals(fastBankOffer.getAnnualPercentageRate(), financeOffer.getAnnualPercentageRate());
        assertEquals(fastBankOffer.getTotalRepaymentAmount(), financeOffer.getTotalRepaymentAmount());
        assertEquals(fastBankOffer.getNumberOfPayments(), financeOffer.getNumberOfPayments());
        assertEquals(fastBankOffer.getFirstRepaymentDate(), financeOffer.getFirstRepaymentDate());
    }

    @Test
    void testOfferToJsonNullableOffer() {
        var fastBankOffer = new Offer();
        fastBankOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        fastBankOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        fastBankOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        fastBankOffer.setNumberOfPayments(12);
        fastBankOffer.setFirstRepaymentDate("2022-01-01");

        var financeOffer = target.offerToJsonNullableOffer(fastBankOffer);

        assertNotNull(financeOffer);
        assertTrue(financeOffer.isPresent());
        assertEquals(fastBankOffer.getMonthlyPaymentAmount(), financeOffer.get().getMonthlyPaymentAmount());
        assertEquals(fastBankOffer.getAnnualPercentageRate(), financeOffer.get().getAnnualPercentageRate());
        assertEquals(fastBankOffer.getTotalRepaymentAmount(), financeOffer.get().getTotalRepaymentAmount());
        assertEquals(fastBankOffer.getNumberOfPayments(), financeOffer.get().getNumberOfPayments());
        assertEquals(fastBankOffer.getFirstRepaymentDate(), financeOffer.get().getFirstRepaymentDate());
    }
}