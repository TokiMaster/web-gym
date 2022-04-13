package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.WishList;

import java.util.ArrayList;

public interface WishListRepository {
    WishList findOne(int id);
    ArrayList<WishList> findAll();
    void addToWishList(WishList wishList);
    void deleteFromWishList(WishList wishList);
    ArrayList<WishList> findUsersWishList(String username);
}
