package entity;

public abstract class AbstractButton {
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;

	public AbstractButton(String name) {
		super();
		this.name = name;
	}
}
