package entities;

import enums.EntityEnum;
import enums.FaceEnum;

public class Face extends Entity{
	int crown;
	FaceEnum type;
	String	image;
	public Face(int crown, FaceEnum type) {
		super(EntityEnum.Face);
		this.crown = crown;
		this.type = type;
		this.image = "images/"+type;
	}
	public int getCrown() {
		return crown;
	}
	public FaceEnum getFaceType() {
		return type;
	}
	public String getImage() {
		return image;
	}
}
