package uz.ilmnajot.card_generator.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.card_generator.entity.CreditCard;
import uz.ilmnajot.card_generator.model.ApiResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    @Override
    public ApiResponse validateCreditCard(CreditCard creditCard) {
        if (!isValidExpiryDate(creditCard.getExpiryDate())) {
            return new ApiResponse("fail", false, "failed", HttpStatus.BAD_GATEWAY);
        }
        if (!isValidCVV(creditCard.getCardNumber(), creditCard.getCvv())) {
            return new ApiResponse("failed", false, "cvv is not valid", HttpStatus.BAD_REQUEST);
        }
        if (!isValidCreditCard(creditCard.getCardNumber()))
            return new ApiResponse("failed", false, "credit card is not valid", HttpStatus.BAD_REQUEST);
        return new ApiResponse("success", true, "success", HttpStatus.OK);
    }


    private static boolean isValidExpiryDate(String expiryDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
            Date expDate = dateFormat.parse(expiryDate);
            Date currentDate = new Date();
            return expDate.after(currentDate);
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isValidCVV(String cardNumber, String cvv) {
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            return cvv.length() == 4;
        } else {
            return cvv.length() == 3;
        }
    }

    private static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber.startsWith("34") || cardNumber.startsWith("37")) {
            if (cardNumber.length() < 15 || cardNumber.length() > 19) {
                return false;
            }
        } else {
            if (cardNumber.length() < 16 || cardNumber.length() > 19) {
                return false;
            }
        }
        if(!isValidCard(cardNumber)){
            return false;
        }
        return true;
    }

    private static boolean isValidCard(String cardNumber) {
        int[] digits = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            digits[i] = Character.getNumericValue(cardNumber.charAt(i));
        }

        for (int i = digits.length - 2; i >= 0; i -= 2) {
            int doubleDigit = digits[i] * 2;
            if (doubleDigit > 9) {
                doubleDigit -= 9;
            }
            digits[i] = doubleDigit;

        }
        int sum = 0;

        for (int digit : digits) {
            sum += digit;
        }
        return sum % 10 == 0;

    }
}
