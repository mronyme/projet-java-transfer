package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import entities.Board;
import entities.Castle;
import entities.Empty;
import entities.Entity;
import entities.Face;
import entities.Player;
import enums.FaceEnum;

public class ScoreManagement {
    static List<Entity> faceAlreadyCounted;
	public static HashMap<String,Integer> testBoard(Board board,HashMap<String,Boolean> gameOptions) {
		faceAlreadyCounted = new ArrayList<Entity>();
		HashMap<String,Integer> localScoreInfo = new HashMap<String,Integer>();
		HashMap<String,Object> regionScoreInfo = new HashMap<String,Object>();
		int localScore = 0;
		int localLargestDomain = 0;
		int localNbCrown = 0;
		for(int x = 0;x < board.getCoords().length;x++)
		{
			for(int y = 0;y < board.getCoords()[0].length;y++) 
			{
				if(board.getEntity(x, y) instanceof Face)
				{
					regionScoreInfo = checkAdjacent(board,(FaceEnum)((Face)board.getEntity(x, y)).getFaceType(),x,y,0);
					if((int)regionScoreInfo.get("domainSize") > localLargestDomain)
					{
						localLargestDomain = (int)regionScoreInfo.get("domainSize");
					}
					localNbCrown+= (int)regionScoreInfo.get("nbCrown");
					localScore+= (int)regionScoreInfo.get("domainSize") * (int)regionScoreInfo.get("nbCrown");
				}
			}
		}
		if(gameOptions.get("middleKingdomOption"))
		{
			int[] limit = Board.getPlayerBoardLimit(board);
			int xMiddle = (int)((limit[1]+limit[0])/2);
			int yMiddle = (int)((limit[3]+limit[2])/2);
			if(board.getEntity(xMiddle, yMiddle) instanceof Castle)
			{
				localScore+=10;
			}
		}
		if(gameOptions.get("harmonyOption"))
		{
			int[] limit = Board.getPlayerBoardLimit(board);
			int empty = 0;
			for(int x = limit[0]; x < limit[1];x++)
			{
				for(int y = limit[2];y < limit[3];y++)
				{
					if(board.getEntity(x, y) instanceof Empty)
					{
						empty++;
					}
				}
			}
			if(empty == 0)
			{
				localScore+=5;			
			}
		}
		localScoreInfo.put("totalScore",localScore);
		localScoreInfo.put("largestDomain",localLargestDomain);
		localScoreInfo.put("nbCrown",localNbCrown);
		return localScoreInfo;
	}
	public static HashMap<String,Object> checkAdjacent(Board board,FaceEnum faceRef,int x,int y,int call)
	{
		HashMap<String,Object> temp;
		Entity entity = board.getEntity(x, y);
		HashMap<String,Object> regionScoreInfo = new HashMap<String,Object>();
		regionScoreInfo.put("domainSize", 0);
		regionScoreInfo.put("nbCrown",0);
		if(entity instanceof Face && !faceAlreadyCounted.contains(entity))
		{
			faceAlreadyCounted.add(entity);
			// UP = gauche
			Entity entityUp = board.getEntity(x, y-1);
			// Down = droite
			Entity entityDown = board.getEntity(x, y+1);
			// LEft = haut
			Entity entityLeft = board.getEntity(x-1, y);
			// Right = bas
			Entity entityRight = board.getEntity(x+1, y);
			//System.out.println(entityUp+" "+entityDown+" "+entityLeft+" "+entityRight);
			if(entityUp != null && entityUp instanceof Face && ((Face) entityUp).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x,y-1,call+1);
				regionScoreInfo.put("domainSize",(int)regionScoreInfo.get("domainSize")+(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown") + (int)regionScoreInfo.get("nbCrown"));
			}
			if(entityDown != null && entityDown instanceof Face && ((Face) entityDown).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x,y+1,call+1);
				regionScoreInfo.put("domainSize",(int)regionScoreInfo.get("domainSize")+(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown") + (int)regionScoreInfo.get("nbCrown"));
			}
			if(entityLeft != null && entityLeft instanceof Face && ((Face) entityLeft).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x-1,y,call+1);
				regionScoreInfo.put("domainSize",(int)regionScoreInfo.get("domainSize")+(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown") + (int)regionScoreInfo.get("nbCrown"));
			}
			if(entityRight != null && entityRight instanceof Face && ((Face) entityRight).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x+1,y,call+1);
				regionScoreInfo.put("domainSize",(int)regionScoreInfo.get("domainSize")+(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown") + (int)regionScoreInfo.get("nbCrown"));
			}
			regionScoreInfo.put("domainSize",1+(int)regionScoreInfo.get("domainSize"));
			regionScoreInfo.put("nbCrown",((Face)board.getEntity(x, y)).getCrown() + (int)regionScoreInfo.get("nbCrown"));
		}
		return regionScoreInfo;
	}
	public static Boolean betterScore(HashMap<String,Integer> hypotheticalScore,HashMap<String,Integer> bestHypotheticalScore) {
		if(hypotheticalScore.get("totalScore") > bestHypotheticalScore.get("totalScore"))
		{
			return true;
		}
		else if(hypotheticalScore.get("totalScore") == bestHypotheticalScore.get("totalScore"))
		{
			if(hypotheticalScore.get("largestDomain") > bestHypotheticalScore.get("largestDomain"))
			{
				return true;
			}
			else if(hypotheticalScore.get("largestDomain") == bestHypotheticalScore.get("largestDomain"))
			{
				if(hypotheticalScore.get("nbCrown") > bestHypotheticalScore.get("nbCrown"))
				{
					return true;
				}				
			}				
		}	
		return false;
	}
	// Cette fonction retourne la liste des joueurs classé par leurs scores finaux
    public static void calculateScore(List<Player> players,HashMap<String,Boolean> gameOptions)
    {
    	for(Player player : players) {
    		player.setFinalScore(ScoreManagement.testBoard(player.getBoard(),gameOptions));
    	}
    }
	public static List<Player> getLeaderBoard(List<Player> players,HashMap<String,Boolean> gameOptions)
	{
		calculateScore(players,gameOptions);
		List<Player> playersRanked = new ArrayList<Player>();
		for(Player player : players) {
			if(playersRanked.size() == 0)
			{
				playersRanked.add(player);
			}
			else
			{
				for(int i = 0;i < playersRanked.size();i++) {
					// Si true : le score de player est > à celui du joueur à la position i
					if(betterScore(player.getFinalScore(),playersRanked.get(i).getFinalScore()) && !playersRanked.contains(player))
					{
						playersRanked.add(i,player);
						break;
					}
				}
				if(!playersRanked.contains(player))
				{
					playersRanked.add(player);
				}
			}
		}
		return playersRanked; 
	}
}
