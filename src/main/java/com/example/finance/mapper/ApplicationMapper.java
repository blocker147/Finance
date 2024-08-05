package com.example.finance.mapper;

import com.openapi.finance.model.ApplicationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    com.openapi.client.fastbank.model.ApplicationRequest toFastBankApplicationRequest(ApplicationRequest applicationRequest);

    @Mapping(source = "phoneNumber", target = "phone")
    @Mapping(source = "monthlyIncomeAmount", target = "monthlyIncome")
    com.openapi.client.solidbank.model.ApplicationRequest toSolidBankApplicationRequest(ApplicationRequest applicationRequest);
}
