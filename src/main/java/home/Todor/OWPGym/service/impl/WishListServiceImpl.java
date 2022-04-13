package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.WishListRepository;
import home.Todor.OWPGym.models.WishList;
import home.Todor.OWPGym.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Override
    public WishList addToWishList(WishList wishList) {
//        WishList wishList = new WishList(user, training);
        wishListRepository.addToWishList(wishList);
        return wishList;
    }
}
