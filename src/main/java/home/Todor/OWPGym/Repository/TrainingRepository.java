package home.Todor.OWPGym.Repository;

import java.util.ArrayList;

import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TypeOfTraining;

public interface TrainingRepository {

	public Training findOne(String name);
	public TypeOfTraining findOneByTypeOfTraining(String name);	
	public ArrayList<Training> findAll();
	
}
