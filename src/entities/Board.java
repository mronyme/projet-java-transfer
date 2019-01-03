package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import enums.FaceEnum;


public class Board {
	Entity[][] coords;
	Player owner;
	public Board(Player player) {
		// Crï¿½er un tableau 9*9 remplis d'object "Empty" (case vide)
		this.coords = new Entity[8][8];
		this.owner = player;
		Empty emptyCase = new Empty();
		for (Entity[] row : this.coords)
		    Arrays.fill(row, emptyCase);
	}
	public Board(Board board) {
		this.coords = board.getCoords();
		this.owner = board.owner;
	}
	// Retourne l'entitï¿½ prï¿½sente sur ces coordonneï¿½s : Soit une Carte, soit un chï¿½teaux
	// Pour savoir si on est faï¿½e un l'un ou l'autre , on compare l'entitï¿½e au ï¿½numeration : Exemple -> if(Entity.getType() == EntityEnum.Card)
	// Ne pas oublier d'importer la classe Entity et la classe EntityEnum
	public Entity getEntity(int coordsX,int coordsY)
	{
		if(coordsX >= 0 && coordsX < 8 && coordsY >= 0 && coordsY < 8)
		{
			return this.coords[coordsX][coordsY];
		}
		else
		{
			return null;
		}
	}
	// Place la carte sur ces coordonnï¿½es
	public Boolean setCard(int coordsXFC1,int coordsYFC1,int coordsXFC2,int coordsYFC2,Card card)
	{
		if(Board.moveIsValid(owner, coordsXFC1, coordsYFC1, coordsXFC2, coordsYFC2, card))
		{
			this.coords[coordsXFC1][coordsYFC1] = card.getFace1();
			this.coords[coordsXFC2][coordsYFC2] = card.getFace2();
			return true;
		}
		return false;
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
	public Entity[][] getCoords()
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
	public static Boolean moveIsValid(Player player,int coordsXFC1,int coordsYFC1,int coordsXFC2,int coordsYFC2,Card card) {
		if(!(player.getBoard().getCoords()[coordsXFC1][coordsYFC1] instanceof Empty && player.getBoard().getCoords()[coordsXFC2][coordsYFC2] instanceof Empty))
		{
			return false;
		}
		if(!checkAdjacentType(player,coordsXFC1,coordsYFC1,card.face1,card.face2))
		{
			return false;
		}
		if(!checkAdjacentType(player,coordsXFC2,coordsYFC2,card.face2,card.face1))
		{
			return false;
		}
		return true;
	}
	public static Boolean checkAdjacentType(Player player,int x,int y,Face face,Face faceLinked) {
		if((x+1) >= player.getBoard().getCoords().length || player.getBoard().getCoords()[x+1][y] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x+1][y]).type && faceLinked != ((Face)player.getBoard().getCoords()[x+1][y]))
		{
			return false;
		}
		if((x-1) < 0 || player.getBoard().getCoords()[x-1][y] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x-1][y]).type && faceLinked != ((Face)player.getBoard().getCoords()[x-1][y]))
		{
			return false;
		}
		if((y+1) >= player.getBoard().getCoords()[0].length || player.getBoard().getCoords()[x][y+1] instanceof Face  && face.type != ((Face)player.getBoard().getCoords()[x][y+1]).type && faceLinked != ((Face)player.getBoard().getCoords()[x][y+1]))
		{
			return false;
		}
		if((y+1) < 0 || player.getBoard().getCoords()[x][y-1] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x][y-1]).type && faceLinked != ((Face)player.getBoard().getCoords()[x][y-1]))
		{
			return false;
		}
		return true;
	}
}
