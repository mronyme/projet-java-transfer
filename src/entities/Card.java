package entities;

import enums.EntityEnum;

public class Card extends Entity{
	int FC1Crown;
	String FC1Type;
	int FC2Crown;
	String FC2Type;
	int CardId;
	public Card(int fC1Crown, String fC1Type, int fC2Crown, String fC2Type,int cardId) {
		super(EntityEnum.Card);
		CardId = cardId; // numéro de la carte
		FC1Crown = fC1Crown; // nombre de couronnes sur face 1
		FC1Type = fC1Type; // type de la face 1
		FC2Crown = fC2Crown; // nombre de couronnes sur face 2
		FC2Type = fC2Type; // type de la face 2
	}
}
