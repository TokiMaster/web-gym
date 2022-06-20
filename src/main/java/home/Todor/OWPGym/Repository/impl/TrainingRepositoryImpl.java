package home.Todor.OWPGym.Repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TrainingLVL;
import home.Todor.OWPGym.models.TrainingType;
import home.Todor.OWPGym.models.TypeOfTraining;


@Repository
public class TrainingRepositoryImpl implements TrainingRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TrainingRowCallbackHandler implements RowCallbackHandler {
		
		private ArrayList<Training> trainings = new ArrayList<Training>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			
			int id = rs.getInt(index++);
			String name = rs.getString(index++);
			String instructor = rs.getString(index++);
			String description = rs.getString(index++);
			String photo = rs.getString(index++);
			TypeOfTraining typeOfTraining = findOneByTypeOfTraining(rs.getString(index++));
			int price = rs.getInt(index++);
			TrainingType trainingType = TrainingType.valueOf(rs.getString(index++));
			TrainingLVL trainingLVL = TrainingLVL.valueOf(rs.getString(index++));
			int duration = rs.getInt(index++);
			double averageRating = rs.getDouble(index++);
			
			Training training = new Training(id, name, instructor, description, photo, typeOfTraining,
					  	  price, trainingType, trainingLVL, duration, averageRating);
			trainings.add(training);
		}
	}
	
	private class TypeOfTrainingRowCallbackHandler implements RowCallbackHandler {
	
		private ArrayList<TypeOfTraining> typeOfTrainings = new ArrayList<TypeOfTraining>();
		
		@Override
		public void processRow(ResultSet rs) throws SQLException {
			int index = 1;
			
			String name = rs.getString(index++);
			String description = rs.getString(index++);
			
			
			TypeOfTraining typeOfTraining = new TypeOfTraining(name, description);
			typeOfTrainings.add(typeOfTraining);
		}
	}
	
	@Override
	public Training findOne(int id) {
		String sql = "select * from Training where id = ?";
		TrainingRowCallbackHandler callbackHandler = new TrainingRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler, id);
		if(callbackHandler.trainings.isEmpty()) {
			return null;
		}
		return callbackHandler.trainings.get(0);
	}
	
	@Override
	public TypeOfTraining findOneByTypeOfTraining(String name) {
		String sql = "select * from TypeOfTraining where name = ?";
		TypeOfTrainingRowCallbackHandler callbackHandler = new TypeOfTrainingRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler, name);
		if(callbackHandler.typeOfTrainings.isEmpty()) {
			return null;
		}
		return callbackHandler.typeOfTrainings.get(0);
	}
	
	@Override
	public ArrayList<Training> findAll() {
		String sql = "select * from Training";
		TrainingRowCallbackHandler callbackHandler = new TrainingRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler);
		return callbackHandler.trainings;
	}
	
	@Override
	public ArrayList<TypeOfTraining> findAllTypes() {
		String sql = "select * from TypeOfTraining";
		TypeOfTrainingRowCallbackHandler callbackHandler = new TypeOfTrainingRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler);
		return callbackHandler.typeOfTrainings;
	}
	
	@Override
	public void addTraining(Training training) {
		String sql = "insert into Training (name, instructor, description, typeOfTraining, price, "
				+ "trainingType, trainingLVL, duration, averageRating)"
				+ "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, training.getName(), training.getInstructor(), 
					training.getDescription(), training.getTypeOfTraining().getName(),
					training.getPrice(), training.getTrainingType().name(),
					training.getTrainingLVL().name(), training.getDuration(),
					training.getAverageRating());
	}
	
	@Override
	public void editTraining(Training training) {
		String sql = "update Training "
				+ "set name = ?, instructor = ?, description = ?, typeOfTraining = ?, price = ?, trainingType = ?, "
				+ "trainingLVL = ?, duration = ?, averageRating = ? where id = ?";
		jdbcTemplate.update(sql, training.getName(), training.getInstructor(), 
				training.getDescription(), training.getTypeOfTraining().getName(),
				training.getPrice(), training.getTrainingType().name(),
				training.getTrainingLVL().name(), training.getDuration(),
				training.getAverageRating(), training.getId());
	}
	
}
