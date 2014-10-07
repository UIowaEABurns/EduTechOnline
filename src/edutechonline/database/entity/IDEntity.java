package edutechonline.database.entity;

public abstract class IDEntity {
	private int ID; //represents an ID that is unique to this entity among all other entities of the same type

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
}
