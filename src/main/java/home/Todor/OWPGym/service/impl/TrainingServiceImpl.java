package home.Todor.OWPGym.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.service.TrainingService;

@Service
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	TrainingRepository trainingRepository;
	
	@Override
	public Training addTraining(Training training) {
		if(training.getName().equals("") || training.getInstructor().equals("")
				|| training.getDescription().equals("") || training.getPrice() < 1){
			return null;
		}
		trainingRepository.addTraining(training);
		return training;
	}
	
	@Override
	public Training editTraining(Training training) {
		if(training.getName().equals("") || training.getInstructor().equals("")
		|| training.getDescription().equals("") || training.getPrice() < 1
		|| training.getDuration() < 1){
			return null;
		}
		trainingRepository.editTraining(training);
		return training;
	}
}
