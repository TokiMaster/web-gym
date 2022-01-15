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
		// TODO validirate neke podatke
		trainingRepository.addTraining(training);
		return training;
	}
	
}
