package uz.ilmnajot.card_generator.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.card_generator.entity.CreditCard;
import uz.ilmnajot.card_generator.model.ApiResponse;
import uz.ilmnajot.card_generator.service.CreditCardService;

@RestController
@RequestMapping("/api/creditCards")
public class CardController {

    private final CreditCardService creditCardService;

    public CardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public HttpEntity<ApiResponse> validateCreditCard(@RequestBody CreditCard creditCard) {
        ApiResponse apiResponse = creditCardService.validateCreditCard(creditCard);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
