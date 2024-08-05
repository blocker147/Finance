package com.example.finance.mapper;

import com.openapi.finance.model.Application;
import com.openapi.finance.model.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = "spring")
public interface SolidBankMapper {
    @Mapping(target = "offer", qualifiedByName = "offerToJsonNullableOffer")
    Application fromClient(com.openapi.client.solidbank.model.Application application);

    Offer fromClient(com.openapi.client.solidbank.model.Offer offer);

    @Named("offerToJsonNullableOffer")
    default JsonNullable<Offer> offerToJsonNullableOffer(com.openapi.client.solidbank.model.Offer offer) {
        return JsonNullable.of(fromClient(offer));
    }
}
