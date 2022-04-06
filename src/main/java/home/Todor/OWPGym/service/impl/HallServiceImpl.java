package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.HallRepository;
import home.Todor.OWPGym.models.Hall;
import home.Todor.OWPGym.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {

    @Autowired
    HallRepository hallRepository;

    @Override
    public Hall addHall(Hall hall) {
        for(Hall hall1: hallRepository.findAll()){
            if(hall1.getHallName().equals(hall.getHallName())){
                return null;
            }
        }
        if(hall.getHallName().equals("") || hall.getCapacity() < 1){
            return null;
        }
        hallRepository.addHall(hall);
        return hall;
    }

    @Override
    public Hall editHall(Hall hall) {
        if (hall.getCapacity() < 1) {
            return null;
        }
        hallRepository.editHall(hall);
        return hall;
    }
}
