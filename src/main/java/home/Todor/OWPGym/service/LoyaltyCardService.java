package home.Todor.OWPGym.service;

import home.Todor.OWPGym.models.LoyaltyCard;

public interface LoyaltyCardService {
    LoyaltyCard requestLoyaltyCard(LoyaltyCard loyaltyCard);
    LoyaltyCard acceptLoyaltyCard(LoyaltyCard loyaltyCard);
    LoyaltyCard rejectLoyaltyCard(LoyaltyCard loyaltyCard);
    void addPoints(LoyaltyCard loyaltyCard);
}
