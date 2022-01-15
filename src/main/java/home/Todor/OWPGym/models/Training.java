package home.Todor.OWPGym.models;

import java.time.LocalDateTime;

public class Training {

	private String name;
	private String instructor;
	private String description;
	private TypeOfTraining typeOfTraining;
	private int price;
	private TrainingType trainingType;
	private TrainingLVL trainingLVL;
	private LocalDateTime startDate;
	private int duration;
	private double averageRating;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public TypeOfTraining getTypeOfTraining() {
		return typeOfTraining;
	}
	
	public void setTypeOfTraining(TypeOfTraining type) {
		this.typeOfTraining = type;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public TrainingType getTrainingType() {
		return trainingType;
	}
	
	public void setTrainingType(TrainingType etype) {
		this.trainingType = etype;
	}
	
	public TrainingLVL getTrainingLVL() {
		return trainingLVL;
	}
	
	public void setTrainingLVL(TrainingLVL trainingLVL) {
		this.trainingLVL = trainingLVL;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public double getAverageRating() {
		return averageRating;
	}
	
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public Training(String name, String instructor, String description, TypeOfTraining typeOfTraining, int price,
			TrainingType trainingType, TrainingLVL trainingLVL, LocalDateTime startDate, int duration, double averageRating) {
		super();
		this.name = name;
		this.instructor = instructor;
		this.description = description;
		this.typeOfTraining = typeOfTraining;
		this.price = price;
		this.trainingType = trainingType;
		this.trainingLVL = trainingLVL;
		this.startDate = startDate;
		this.duration = duration;
		this.averageRating = averageRating;
	}
	
}
