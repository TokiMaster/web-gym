package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.LoyaltyCardRepository;
import home.Todor.OWPGym.models.LoyaltyCard;
import home.Todor.OWPGym.service.LoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCardServiceImpl implements LoyaltyCardService {

    @Autowired
    LoyaltyCardRepository loyaltyCardRepository;

    @Override
    public LoyaltyCard requestLoyaltyCard(LoyaltyCard loyaltyCard) {
        if(loyaltyCard.getUser() == null || loyaltyCardRepository.findOneAccepted(loyaltyCard) != null){
            return null;
        }
        loyaltyCardRepository.requestLoyaltyCard(loyaltyCard);
        return loyaltyCard;
    }

    @Override
    public LoyaltyCard acceptLoyaltyCard(LoyaltyCard loyaltyCard) {
        if(loyaltyCard == null){
            return null;
        }
        loyaltyCardRepository.acceptLoyaltyCard(loyaltyCard);
        return loyaltyCard;
    }

    @Override
    public LoyaltyCard rejectLoyaltyCard(LoyaltyCard loyaltyCard) {
        if(loyaltyCard == null){
            return null;
        }
        loyaltyCardRepository.rejectLoyaltyCard(loyaltyCard);
        return loyaltyCard;
    }

    @Override
    public void addPoints(LoyaltyCard loyaltyCard) {
        loyaltyCardRepository.addPoints(loyaltyCard);
    }
}
