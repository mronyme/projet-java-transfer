package entities;

import enums.EntityEnum;
import enums.FaceEnum;

public class Face extends Entity{
	int crown;
	FaceEnum type;
	public Face(int crown, FaceEnum type) {
		super(EntityEnum.Face);
		this.crown = crown;
		this.type = type;
	}
	public int getCrown() {
		return crown;
	}
	public FaceEnum getFaceType() {
		return type;
	}
}
