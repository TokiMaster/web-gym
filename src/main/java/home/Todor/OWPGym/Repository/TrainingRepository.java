package home.Todor.OWPGym.Repository;

import java.util.ArrayList;

import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TypeOfTraining;

public interface TrainingRepository {
	Training findOne(int id);
	TypeOfTraining findOneByTypeOfTraining(String name);
	ArrayList<Training> findAll();
	ArrayList<TypeOfTraining> findAllTypes();
	void addTraining(Training training);
	void editTraining(Training training);
}
