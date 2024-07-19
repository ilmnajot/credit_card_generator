package uz.ilmnajot.card_generator.service;

import uz.ilmnajot.card_generator.entity.CreditCard;
import uz.ilmnajot.card_generator.model.ApiResponse;

public interface CreditCardService {
    ApiResponse validateCreditCard(CreditCard creditCard);
}
