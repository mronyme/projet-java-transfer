package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import entities.Card;
import entities.Castle;
import entities.Empty;
import entities.Entity;
import entities.Face;
import enums.FaceEnum;


public class Board {
	Entity[][] coords;
	public Board() {
		// Crï¿½er un tableau 9*9 remplis d'object "Empty" (case vide)
		this.coords = new Entity[9][9];
		Empty emptyCase = new Empty();
		for (Entity[] row : this.coords)
		    Arrays.fill(row, emptyCase);
	}
	// Retourne l'entitï¿½ prï¿½sente sur ces coordonneï¿½s : Soit une Carte, soit un chï¿½teaux
	// Pour savoir si on est faï¿½e un l'un ou l'autre , on compare l'entitï¿½e au ï¿½numeration : Exemple -> if(Entity.getType() == EntityEnum.Card)
	// Ne pas oublier d'importer la classe Entity et la classe EntityEnum
	public Entity getEntity(int coordsX,int coordsY)
	{
		if(coordsX >= 0 && coordsX < 9 && coordsY >= 0 && coordsY < 9)
		{
			return this.coords[coordsX][coordsY];
		}
		else
		{
			return null;
		}
	}
	// Place la carte sur ces coordonnï¿½es
	public void setCard(int coordsXFC1,int coordsYFC1,int coordsXFC2,int coordsYFC2,Card card)
	{
		this.coords[coordsXFC1][coordsYFC1] = card.getFace1();
		this.coords[coordsXFC2][coordsYFC2] = card.getFace2();
	}
	// Place le chï¿½teaux sur ces coordonnï¿½es
	public void setCastle(int coordsX,int coordsY)
	{
		Castle castle = new Castle();
		this.coords[coordsX][coordsY] = castle;
	}
	// Place le châteaux sur ces coordonnées
	public void setCastle(int coordsX,int coordsY,Castle castle)
	{
		this.coords[coordsX][coordsY] = castle;
	}
	// Récupère tout le plateaux du joueur
	public Entity[][] getBoard()
	{
		return this.coords;
	}
	public List<Map<String, Integer>> getSameType(FaceEnum faceType)
	{
		List<Map<String, Integer>> sameTypeList = new ArrayList<>();
		Map<String,Integer> map;
		for(int x = 0;x < this.coords.length;x++)
		{
			for(int y = 0;y < this.coords[x].length;y++)
			{
				if(this.coords[x][y] instanceof Face)
				{
					if(((Face)this.coords[x][y]).getFaceType() == faceType)
					{
						map = new HashMap<String, Integer>();
						map.put("x",x);
						map.put("y",y);
						sameTypeList.add(map);
					}
				}
			}
		}
		return sameTypeList;		
	}
}
