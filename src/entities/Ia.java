package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.CoreGame;
import enums.ColorEnum;
import enums.FaceEnum;

public class Ia extends Player{
	List<Entity> faceAlreadyCounted;
	public Ia(CoreGame game,ColorEnum color,int numberKings) {
		super(game,color,numberKings);
	}
	public void initTurn()
	{
		this.getBoard().setCastle(2, 2);
	}
	public Card startTurn(int nbRound,Card card)
	{
		System.out.println("Round: "+nbRound+" | Player turn :"+ this.color);
		List<Card> cards;
		if(card == null)
		{
			//System.out.println("Selecting first card :");
			cards = game.getCardsAvailable(0);
			card = pickBestCard(cards);
			//System.out.println("First card picked :"+ card);
			this.game.pickCard(this, 0, card);
		}
		else
		{
			//System.out.println("Card used :"+card);
		}
		HashMap<String,Integer> bestMove = pickBestMove(card);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), card);
		}
		cards = game.getCardsAvailable(1);
		//System.out.println("Selecting next card :");
		Card nextCard = pickBestCard(cards);
		//System.out.println("Next card picked :"+ nextCard);
		System.out.println(Arrays.deepToString(this.board.getFaceEnum()).replaceAll("],", "]," + System.getProperty("line.separator")));
		// Gestion du tour par ia
		return nextCard;
	}
	public Card pickBestCard(List<Card> cards) {
		HashMap<String,Integer> globalScoreInfo = new HashMap<String,Integer>();
		HashMap<String,Integer> finalScoreInfo = new HashMap<String,Integer>();
		finalScoreInfo.put("totalScore", 0);
		finalScoreInfo.put("largestDomain", 0);
		finalScoreInfo.put("nbCrown", 0);
		Card bestCard = cards.get(0);
		for(Card card : cards) {
			//System.out.println("Card tested : "+ card);
			globalScoreInfo = generateAllPossibleMoves(card,false);
			if(betterScore(globalScoreInfo,finalScoreInfo)) 
			{
				finalScoreInfo.putAll(globalScoreInfo);
				bestCard = card;
			}
		}
		return bestCard;
	}
	public HashMap<String,Integer> pickBestMove(Card card){
		HashMap<String,Integer> bestMove = generateAllPossibleMoves(card,true);
		return bestMove;
	}
	public HashMap<String,Integer> generateAllPossibleMoves(Card card,Boolean returnBestMove) {
		Board tempBoard;
		int FC1X,FC1Y,FC2X,FC2Y;
		HashMap<String,Integer> bestMove = new HashMap<String,Integer>();
		HashMap<String,Integer> localScoreInfo = new HashMap<String,Integer>();
		HashMap<String,Integer> globalScoreInfo = new HashMap<String,Integer>();
		globalScoreInfo.put("totalScore", 0);
		globalScoreInfo.put("largestDomain", 0);
		globalScoreInfo.put("nbCrown", 0);
		for(int x = 0;x < this.getBoard().getCoords().length;x++)
		{
			for(int y = 0;y <  this.getBoard().getCoords()[x].length;y++)
			{
				if( this.getBoard().getCoords()[x][y] instanceof Empty)
				{
					for(int i = 0;i < 8;i++)
					{
						tempBoard = new Board(this.getBoard());
						//System.out.println(Arrays.deepToString(this.getBoard().getCoords()));
						Boolean valid = true;
						FC1X = FC2X = x;
						FC1Y = FC2Y = y;
						switch(i) {
							case 0:
								valid = tempBoard.setCard(x, y, x+1, y, card);
								FC2X = x+1;
								break;
							case 1:
								valid = tempBoard.setCard(x, y, x-1, y, card);
								FC2X = x-1;
								break;
							case 2:
								valid = tempBoard.setCard(x, y, x, y+1, card);
								FC2Y = y+1;
								//System.out.println(FC1X+" "+FC1Y+" "+FC2X+" "+FC2Y);
								//System.out.println(valid);
								break;
							case 3:
								valid = tempBoard.setCard(x, y, x, y-1, card);
								FC2Y = y-1;
								break;
							case 4:
								valid = tempBoard.setCard(x+1, y, x, y, card);
								FC1X = x+1;
								break;
							case 5:
								valid = tempBoard.setCard(x-1, y, x, y, card);
								FC1X = x-1;
								break;
							case 6:
								valid = tempBoard.setCard(x, y+1, x, y, card);
								FC1Y = y+1;
								break;
							case 7:
								valid = tempBoard.setCard(x, y-1, x, y, card);
								FC1Y = y-1;
								break;		
						}
						if(valid)
						{
							//System.out.println(Arrays.deepToString(this.board.getCoords()).replaceAll("],", "]," + System.getProperty("line.separator")));
							localScoreInfo = testMove(tempBoard);
							if(betterScore(localScoreInfo,globalScoreInfo)) 
							{
								globalScoreInfo.putAll(localScoreInfo);
								bestMove.put("FC1X", FC1X);
								bestMove.put("FC1Y", FC1Y);
								bestMove.put("FC2X", FC2X);
								bestMove.put("FC2Y", FC2Y);
							}
						}
					}
				}
			}
		}
		if(returnBestMove)
		{
			return bestMove;			
		}
		return globalScoreInfo;
	}
	public HashMap<String,Integer> testMove(Board board) {
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
					regionScoreInfo = checkAdjacent(board,(FaceEnum)((Face)board.getEntity(x, y)).type,x,y,0);
					if((int)regionScoreInfo.get("domainSize") > localLargestDomain)
					{
						localLargestDomain = (int)regionScoreInfo.get("domainSize");
					}
					localNbCrown+= (int)regionScoreInfo.get("nbCrown");
					localScore+= (int)regionScoreInfo.get("domainSize") * (int)regionScoreInfo.get("nbCrown");
				}
			}
		}
		localScoreInfo.put("totalScore",localScore);
		localScoreInfo.put("largestDomain",localLargestDomain);
		localScoreInfo.put("nbCrown",localNbCrown);
		return localScoreInfo;
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
	public HashMap<String,Object> checkAdjacent(Board board,FaceEnum faceRef,int x,int y,int call)
	{
		HashMap<String,Object> temp;
		Entity entity = board.getEntity(x, y);
		HashMap<String,Object> regionScoreInfo = new HashMap<String,Object>();
		regionScoreInfo.put("domainSize", 0);
		regionScoreInfo.put("nbCrown",0);
		regionScoreInfo.put("faceAlreadyCounted",faceAlreadyCounted);
		//System.out.println(entity+" "+call);
		if(entity instanceof Face && !faceAlreadyCounted.contains(entity))
		{
			faceAlreadyCounted.add(entity);
			Entity entityUp = board.getEntity(x, y+1);
			Entity entityDown = board.getEntity(x, y-1);
			Entity entityLeft = board.getEntity(x-1, y);
			Entity entityRight = board.getEntity(x+1, y);
			if(entityUp != null && entityUp instanceof Face && ((Face) entityUp).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x,y+1,call+1);
				regionScoreInfo.put("domainSize",(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown"));
				regionScoreInfo.put("faceAlreadyCounted",temp.get("faceAlreadyCounted"));
			}
			if(entityDown != null && entityDown instanceof Face && ((Face) entityDown).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x,y-1,call+1);
				regionScoreInfo.put("domainSize",(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown"));
				regionScoreInfo.put("faceAlreadyCounted",temp.get("faceAlreadyCounted"));
			}
			if(entityLeft != null && entityLeft instanceof Face && ((Face) entityLeft).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x-1,y,call+1);
				regionScoreInfo.put("domainSize",(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown"));
				regionScoreInfo.put("faceAlreadyCounted",temp.get("faceAlreadyCounted"));
			}
			if(entityRight != null && entityRight instanceof Face && ((Face) entityRight).getFaceType() == faceRef)
			{
				temp = checkAdjacent(board,faceRef,x+1,y,call+1);
				regionScoreInfo.put("domainSize",(int)temp.get("domainSize"));
				regionScoreInfo.put("nbCrown",(int)temp.get("nbCrown"));
				regionScoreInfo.put("faceAlreadyCounted",temp.get("faceAlreadyCounted"));
			}
			regionScoreInfo.put("domainSize",1+(int)regionScoreInfo.get("domainSize"));
			regionScoreInfo.put("nbCrown",((Face)board.getEntity(x, y)).getCrown() + (int)regionScoreInfo.get("nbCrown"));
		}
		return regionScoreInfo;
	}
}
