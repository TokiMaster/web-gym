package home.Todor.OWPGym.models;

public class Training {

	private int id;
	private String name;
	private String instructor;
	private String description;
	private String photo;
	private TypeOfTraining typeOfTraining;
	private int price;
	private TrainingType trainingType;
	private TrainingLVL trainingLVL;
	private int duration;
	private double averageRating;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public Training(String name, String instructor, String description, String photo, TypeOfTraining typeOfTraining, int price,
			TrainingType trainingType, TrainingLVL trainingLVL, int duration, double averageRating) {
		super();
		this.name = name;
		this.instructor = instructor;
		this.description = description;
		this.photo = photo;
		this.typeOfTraining = typeOfTraining;
		this.price = price;
		this.trainingType = trainingType;
		this.trainingLVL = trainingLVL;
		this.duration = duration;
		this.averageRating = averageRating;
	}
	
	public Training(int id, String name, String instructor, String description, String photo, TypeOfTraining typeOfTraining, int price,
			TrainingType trainingType, TrainingLVL trainingLVL, int duration, double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.instructor = instructor;
		this.description = description;
		this.photo = photo;
		this.typeOfTraining = typeOfTraining;
		this.price = price;
		this.trainingType = trainingType;
		this.trainingLVL = trainingLVL;
		this.duration = duration;
		this.averageRating = averageRating;
	}
	
}
