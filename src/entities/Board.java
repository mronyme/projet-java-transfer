package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.CoreGame;

import java.util.Arrays;

import enums.FaceEnum;


public class Board {
	CoreGame game;
	Entity[][] coords;
	int maxSize;
	Player owner;
	public Board(CoreGame game,Player player) {
		// Crï¿½er un tableau 9*9 remplis d'object "Empty" (case vide)
		this.coords = new Entity[9][9];
		if(game.getGameOptions().get("bigDuelOption"))
		{
			this.maxSize = 7;
		}
		else
		{
			this.maxSize = 5;
		}
		this.owner = player;
		this.game = game;
		Empty emptyCase = new Empty();
		for (Entity[] row : this.coords)
		    Arrays.fill(row, emptyCase);
	}
	public Board(Board board) {
		this.coords = Arrays.stream(board.getCoords()).map(Entity[]::clone).toArray(Entity[][]::new);
		this.owner = board.owner;
		this.game = board.game;
	}
	// Retourne l'entitï¿½ prï¿½sente sur ces coordonneï¿½s : Soit une Carte, soit un chï¿½teaux
	// Pour savoir si on est faï¿½e un l'un ou l'autre , on compare l'entitï¿½e au ï¿½numeration : Exemple -> if(Entity.getType() == EntityEnum.Card)
	// Ne pas oublier d'importer la classe Entity et la classe EntityEnum
	public Entity getEntity(int coordsX,int coordsY)
	{
		if(coordsX >= 0 && coordsX < this.coords.length && coordsY >= 0 && coordsY < this.coords[0].length)
		{
			return this.coords[coordsX][coordsY];
		}
		else
		{
			return null;
		}
	}
	public FaceEnum[][] getFaceEnum(){
		FaceEnum[][] faceEnum = new FaceEnum[this.coords.length][this.coords[0].length];
		for(int x = 0;x < this.coords.length;x++)
		{
			for(int y = 0;y < this.coords[x].length;y++)
			{
				if(this.coords[x][y] instanceof Face)
				{
					faceEnum[x][y] = ((Face)this.coords[x][y]).type;
				}
				if(this.coords[x][y] instanceof Castle)
				{
					faceEnum[x][y] = FaceEnum.Castle;
				}
			}
		}
		return faceEnum;
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
	public int getBoardMaxSize()
	{
		return this.maxSize;
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
		if(!(coordsXFC1 >= 0 && coordsXFC1 < player.getBoard().getCoords().length && coordsYFC1 >= 0 && coordsYFC1 < player.getBoard().getCoords()[0].length && coordsXFC2 >= 0 && coordsXFC2 < player.getBoard().getCoords().length && coordsYFC2 >= 0 && coordsYFC2 < player.getBoard().getCoords()[0].length))
		{
			return false;
		}
		if(!isInRange(player,coordsXFC1,coordsYFC1,coordsXFC2,coordsYFC2))
		{
			return false;
		}
		if(!(player.getBoard().getCoords()[coordsXFC1][coordsYFC1] instanceof Empty && player.getBoard().getCoords()[coordsXFC2][coordsYFC2] instanceof Empty))
		{
			//System.out.println("Erreur : "+coordsXFC1+" "+coordsYFC1+" "+coordsXFC2+" "+coordsYFC2);
			//System.out.println(player.getBoard().getCoords()[coordsXFC1][coordsYFC1]);
			return false;
		}
		if(checkAdjacentType(player,coordsXFC1,coordsYFC1,card.face1))
		{
			return true;
		}
		if(checkAdjacentType(player,coordsXFC2,coordsYFC2,card.face2))
		{
			//System.out.println(coordsXFC2+" "+coordsYFC2+" "+card.face2.getFaceType()+" "+card.face1.getFaceType());
			return true;
		}
		return false;
	}
	public static Boolean checkAdjacentType(Player player,int x,int y,Face face) {
		int adjacentEmpty = 0;
		// Check if Castle Adjacent
		Boolean castleAdjacent = false;
		Boolean valid = true;
		/*
		 *  Check Right Square not the same type
		 */
		if((x+1) < player.getBoard().getCoords().length && player.getBoard().getCoords()[x+1][y] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x+1][y]).type)
		{
			valid = false;
		}
		// Else Check if Right Square Empty or null
		else if((x+1) >= player.getBoard().getCoords().length || (x+1) < player.getBoard().getCoords().length && player.getBoard().getCoords()[x+1][y] instanceof Empty)
		{
			adjacentEmpty+=1;
		}
		else if((x+1) < player.getBoard().getCoords().length && player.getBoard().getCoords()[x+1][y] instanceof Castle)
		{
			castleAdjacent = true;
		}
		/*
		 * / Check Left Square
		 */
		// Not same type
		if((x-1) >= 0 && player.getBoard().getCoords()[x-1][y] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x-1][y]).type)
		{
			valid = false;
		}
		// Empty || null
		else if((x-1) < 0 || (x-1) >= 0 && player.getBoard().getCoords()[x-1][y] instanceof Empty)
		{
			adjacentEmpty+=1;		
		}
		// Castle adjacent
		else if((x-1) >= 0 && player.getBoard().getCoords()[x-1][y] instanceof Castle)
		{
			castleAdjacent = true;
		}
		/*
		 * / Check Down Square
		 */
		if((y+1) < player.getBoard().getCoords()[0].length && player.getBoard().getCoords()[x][y+1] instanceof Face  && face.type != ((Face)player.getBoard().getCoords()[x][y+1]).type)
		{
			valid = false;
		}
		else if((y+1) >= player.getBoard().getCoords()[0].length || (y+1) < player.getBoard().getCoords()[0].length && player.getBoard().getCoords()[x][y+1] instanceof Empty)
		{
			adjacentEmpty+=1;	
		}
		else if((y+1) < player.getBoard().getCoords()[0].length && player.getBoard().getCoords()[x][y+1] instanceof Castle)
		{
			castleAdjacent = true;
		}
		/*
		 * / Check Upper Square
		 */
		if((y-1) >= 0 && player.getBoard().getCoords()[x][y-1] instanceof Face && face.type != ((Face)player.getBoard().getCoords()[x][y-1]).type)
		{
			valid = false;
		}
		else if((y-1) < 0 || (y-1) >= 0 && player.getBoard().getCoords()[x][y-1] instanceof Empty)
		{
			adjacentEmpty+=1;	
		}
		else if((y-1) >= 0 && player.getBoard().getCoords()[x][y-1] instanceof Castle)
		{
			castleAdjacent = true;
		}
		if(adjacentEmpty == 4)
		{
			valid = false;
		}
		if(castleAdjacent)
		{
			return castleAdjacent;
		}
		return valid;
	}
	public static void printMatrix(Object[][] grid) {
		System.out.println("--------------------");
	    for(int r=0; r<grid.length; r++) {
	       for(int c=0; c<grid[r].length; c++)
	           System.out.print(grid[r][c] + "  |  ");
	       System.out.println();
	    }
		System.out.println("--------------------");
	}
	public static Boolean isInRange(Player player,int FC1X,int FC1Y,int FC2X,int FC2Y)
	{
		int[] limit = getPlayerBoardLimit(player.getBoard());
		if(!(limit[1] - player.getBoard().getBoardMaxSize() < FC1X && limit[0] + player.getBoard().getBoardMaxSize() > FC1X && limit[3] - player.getBoard().getBoardMaxSize() < FC1Y && limit[2] + player.getBoard().getBoardMaxSize() > FC1Y))
		{
			return false;
		}
		else
			
		if(!(limit[1] - player.getBoard().getBoardMaxSize() < FC2X && limit[0] + player.getBoard().getBoardMaxSize() > FC2X && limit[3] - player.getBoard().getBoardMaxSize() < FC2Y && limit[2] + player.getBoard().getBoardMaxSize() > FC2Y))
		{
			return false;
		}
		return true;
	 }
	public static int[]  getPlayerBoardLimit(Board board)
	{
		int xMin,xMax,yMin,yMax;
		xMin = xMax = yMin = yMax = (int)(board.getCoords().length/2);
		for(int x = 0;x < board.getCoords().length;x++)
		{
			for(int y = 0;y < board.getCoords()[0].length;y++)
			{
				if(board.getEntity(x, y) instanceof Face)
				{
					xMin = x < xMin?x:xMin;
					xMax = x > xMax?x:xMax;
					yMin = y < yMin?y:yMin;
					yMax = y > yMax?y:yMax;
				}
			}
		}
		return new int[]{xMin,xMax,yMin,yMax};
	}
}
