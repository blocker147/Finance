package com.example.finance.mapper;

import com.openapi.finance.model.Application;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolidBankMapperTest {
    private final SolidBankMapper target = new SolidBankMapperImpl();

    @Test
    void testFromClientApplication() {
        var testOffer = new com.openapi.client.solidbank.model.Offer();
        testOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        testOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        testOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        testOffer.setNumberOfPayments(12);
        testOffer.setFirstRepaymentDate("2022-01-01");

        var solidBankApp = new com.openapi.client.solidbank.model.Application();
        solidBankApp.setId("123");
        solidBankApp.setStatus(com.openapi.client.solidbank.model.Application.StatusEnum.DRAFT);
        solidBankApp.setOffer(testOffer);

        var financeApp = target.fromClient(solidBankApp);

        assertNotNull(financeApp);
        assertEquals("123", financeApp.getId());
        assertEquals(Application.StatusEnum.DRAFT, financeApp.getStatus());
        assertTrue(financeApp.getOffer().isPresent());
        assertEquals(solidBankApp.getOffer().getMonthlyPaymentAmount(), financeApp.getOffer().get().getMonthlyPaymentAmount());
    }

    @Test
    void testFromClientOffer() {
        var solidBankOffer = new com.openapi.client.solidbank.model.Offer();
        solidBankOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        solidBankOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        solidBankOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        solidBankOffer.setNumberOfPayments(12);
        solidBankOffer.setFirstRepaymentDate("2022-01-01");

        var financeOffer = target.fromClient(solidBankOffer);

        assertNotNull(financeOffer);
        assertEquals(solidBankOffer.getMonthlyPaymentAmount(), financeOffer.getMonthlyPaymentAmount());
        assertEquals(solidBankOffer.getAnnualPercentageRate(), financeOffer.getAnnualPercentageRate());
        assertEquals(solidBankOffer.getTotalRepaymentAmount(), financeOffer.getTotalRepaymentAmount());
        assertEquals(solidBankOffer.getNumberOfPayments(), financeOffer.getNumberOfPayments());
        assertEquals(solidBankOffer.getFirstRepaymentDate(), financeOffer.getFirstRepaymentDate());
    }

    @Test
    void testOfferToJsonNullableOffer() {
        var solidBankOffer = new com.openapi.client.solidbank.model.Offer();
        solidBankOffer.setMonthlyPaymentAmount(new BigDecimal("100.00"));
        solidBankOffer.setAnnualPercentageRate(new BigDecimal("5.5"));
        solidBankOffer.setTotalRepaymentAmount(new BigDecimal("1200.00"));
        solidBankOffer.setNumberOfPayments(12);
        solidBankOffer.setFirstRepaymentDate("2022-01-01");

        var financeOffer = target.offerToJsonNullableOffer(solidBankOffer);

        assertNotNull(financeOffer);
        assertTrue(financeOffer.isPresent());
        assertEquals(solidBankOffer.getMonthlyPaymentAmount(), financeOffer.get().getMonthlyPaymentAmount());
        assertEquals(solidBankOffer.getAnnualPercentageRate(), financeOffer.get().getAnnualPercentageRate());
        assertEquals(solidBankOffer.getTotalRepaymentAmount(), financeOffer.get().getTotalRepaymentAmount());
        assertEquals(solidBankOffer.getNumberOfPayments(), financeOffer.get().getNumberOfPayments());
        assertEquals(solidBankOffer.getFirstRepaymentDate(), financeOffer.get().getFirstRepaymentDate());
    }
}