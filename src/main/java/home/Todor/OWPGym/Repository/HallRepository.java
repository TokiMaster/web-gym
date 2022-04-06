package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.Hall;

import java.util.ArrayList;

public interface HallRepository {
    Hall findOne(int id);
    ArrayList<Hall> findAll();
    void addHall(Hall hall);
    void editHall(Hall hall);
}
