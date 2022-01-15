package home.Todor.OWPGym.models;

public class TypeOfTraining {
	
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public TypeOfTraining() {
		
	}
	
	public TypeOfTraining(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
