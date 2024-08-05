package com.example.finance.mapper;

import com.openapi.finance.model.ApplicationRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationMapperTest {

    private final ApplicationMapper target = Mappers.getMapper(ApplicationMapper.class);

    @Test
    void testToFastBankApplicationRequest() {
        var financeAppRequest = new ApplicationRequest();
        financeAppRequest.setPhoneNumber("123456789");
        financeAppRequest.setEmail("test@example.com");
        financeAppRequest.setMonthlyIncomeAmount(new BigDecimal("5000"));
        financeAppRequest.setAmount(new BigDecimal("100000"));
        financeAppRequest.setMonthlyCreditLiabilities(new BigDecimal("1500"));
        financeAppRequest.setDependents(2);
        financeAppRequest.setAgreeToDataSharing(true);
        financeAppRequest.setMonthlyExpenses(new BigDecimal("2000"));
        financeAppRequest.setMaritalStatus(ApplicationRequest.MaritalStatusEnum.MARRIED);
        financeAppRequest.setAgreeToBeScored(true);

        var fastBankAppRequest = target.toFastBankApplicationRequest(financeAppRequest);

        assertEquals(financeAppRequest.getPhoneNumber(), fastBankAppRequest.getPhoneNumber());
        assertEquals(financeAppRequest.getEmail(), fastBankAppRequest.getEmail());
        assertEquals(financeAppRequest.getMonthlyIncomeAmount(), fastBankAppRequest.getMonthlyIncomeAmount());
        assertEquals(financeAppRequest.getAmount(), fastBankAppRequest.getAmount());
        assertEquals(financeAppRequest.getMonthlyCreditLiabilities(), fastBankAppRequest.getMonthlyCreditLiabilities());
        assertEquals(financeAppRequest.getDependents(), fastBankAppRequest.getDependents());
        assertEquals(financeAppRequest.getAgreeToDataSharing(), fastBankAppRequest.getAgreeToDataSharing());
    }

    @Test
    void testToSolidBankApplicationRequest() {
        var financeAppRequest = new ApplicationRequest();
        financeAppRequest.setPhoneNumber("123456789");
        financeAppRequest.setEmail("test@example.com");
        financeAppRequest.setMonthlyIncomeAmount(new BigDecimal("5000"));
        financeAppRequest.setAmount(new BigDecimal("100000"));
        financeAppRequest.setMonthlyCreditLiabilities(new BigDecimal("1500"));
        financeAppRequest.setDependents(2);
        financeAppRequest.setAgreeToDataSharing(true);
        financeAppRequest.setMonthlyExpenses(new BigDecimal("2000"));
        financeAppRequest.setMaritalStatus(ApplicationRequest.MaritalStatusEnum.MARRIED);
        financeAppRequest.setAgreeToBeScored(true);

        var solidBankAppRequest = target.toSolidBankApplicationRequest(financeAppRequest);

        assertEquals(financeAppRequest.getPhoneNumber(), solidBankAppRequest.getPhone());
        assertEquals(financeAppRequest.getEmail(), solidBankAppRequest.getEmail());
        assertEquals(financeAppRequest.getMonthlyIncomeAmount(), solidBankAppRequest.getMonthlyIncome());
        assertEquals(financeAppRequest.getAmount(), solidBankAppRequest.getAmount());
        assertEquals(financeAppRequest.getMonthlyExpenses(), solidBankAppRequest.getMonthlyExpenses());
        assertEquals(financeAppRequest.getMaritalStatus().getValue(), solidBankAppRequest.getMaritalStatus().getValue());
        assertEquals(financeAppRequest.getAgreeToBeScored(), solidBankAppRequest.getAgreeToBeScored());
    }
}