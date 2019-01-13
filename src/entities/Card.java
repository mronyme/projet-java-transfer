package entities;

import enums.FaceEnum;

public class Card{
	Face face1;
	Face face2;
	int CardId;
	public Card(int fC1Crown, FaceEnum fC1Type, int fC2Crown, FaceEnum fC2Type,int cardId) {
		CardId = cardId; // num�ro de la carte
		face1 = new Face(fC1Crown,fC1Type);
		face2 = new Face(fC2Crown,fC2Type);
	}
	public Face getFace1() {
		return this.face1;
	}
	public Face getFace2() {
		return this.face2;
	}
	public int getCardId()
	{
		return this.CardId;
	}
}
