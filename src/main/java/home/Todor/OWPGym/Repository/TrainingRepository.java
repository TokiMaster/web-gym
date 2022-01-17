package home.Todor.OWPGym.Repository;

import java.util.ArrayList;

import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TypeOfTraining;

public interface TrainingRepository {

	public Training findOne(int id);
	public TypeOfTraining findOneByTypeOfTraining(String name);	
	public ArrayList<Training> findAll();
	public void addTraining(Training training);
	public void editTraining(Training training);
}
