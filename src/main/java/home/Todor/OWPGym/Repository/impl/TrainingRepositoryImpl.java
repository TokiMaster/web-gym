package home.Todor.OWPGym.Repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
			
			String name = rs.getString(index++);
			String instructor = rs.getString(index++);
			String description = rs.getString(index++);
			TypeOfTraining typeOfTraining = findOneByTypeOfTraining(rs.getString(index++));
			int price = rs.getInt(index++);
			TrainingType trainingType = TrainingType.valueOf(rs.getString(index++));
			TrainingLVL trainingLVL = TrainingLVL.valueOf(rs.getString(index++));
			LocalDateTime startDate = rs.getTimestamp(index++).toLocalDateTime();
			int duration = rs.getInt(index++);
			double averageRating = rs.getDouble(index++);
			
			Training training = new Training(name, instructor, description, typeOfTraining,
					  price, trainingType, trainingLVL, startDate, duration, averageRating);
			trainings.add(training);
		}
	}
	
	private class TypeRowCallbackHandler implements RowCallbackHandler {
	
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
	public Training findOne(String name) {
		String sql = "select * from Training where name = ?";
		TrainingRowCallbackHandler callbackHandler = new TrainingRowCallbackHandler();
		jdbcTemplate.query(sql, callbackHandler, name);
		if(callbackHandler.trainings.isEmpty()) {
			return null;
		}
		return callbackHandler.trainings.get(0);
	}
	
	@Override
	public TypeOfTraining findOneByTypeOfTraining(String name) {
		String sql = "select * from TypeOfTraining where name = ?";
		TypeRowCallbackHandler callbackHandler = new TypeRowCallbackHandler();
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
	
}
