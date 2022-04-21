package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.LoyaltyCard;

import java.util.ArrayList;

public interface LoyaltyCardRepository {
    LoyaltyCard findOne(int id);
    ArrayList<LoyaltyCard> findAll();
    LoyaltyCard findOneByUsername(String username);
    LoyaltyCard findOneAccepted(LoyaltyCard loyaltyCard);
    void requestLoyaltyCard(LoyaltyCard loyaltyCard);
    void acceptLoyaltyCard(LoyaltyCard loyaltyCard);
    void rejectLoyaltyCard(LoyaltyCard loyaltyCard);
}
