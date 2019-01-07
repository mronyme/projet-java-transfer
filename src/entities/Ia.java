package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import core.CoreGame;
import core.ScoreManagement;
import enums.ColorEnum;
import enums.FaceEnum;

public class Ia extends Player{
	List<Entity> faceAlreadyCounted;
	public Ia(CoreGame game,ColorEnum color,int numberKings) {
		super(game,color,numberKings);
	}
	public void initTurn()
	{
		this.getBoard().setCastle((int)(this.getBoard().getCoords().length/2),(int)(this.getBoard().getCoords()[0].length/2));
	}
	public Card firstTurn()
	{
		List<Card> cards;
		cards = game.getCardsAvailable(0);
		Card card = pickBestCard(cards);
		HashMap<String,Integer> bestMove = pickBestMove(card);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), card);
		}
		this.game.pickCard(this, 0, card);
		cards = game.getCardsAvailable(1);
		Card nextCard = pickBestCard(cards);
		return nextCard;
	}
	public void lastTurn(int nbRound,Card lastCard)
	{
		HashMap<String,Integer> bestMove = pickBestMove(lastCard);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), lastCard);
		}
	}
	public Card startTurn(int nbRound,Card card)
	{
		List<Card> cards;
		HashMap<String,Integer> bestMove = pickBestMove(card);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), card);
		}
		cards = game.getCardsAvailable(1);
		Card nextCard = pickBestCard(cards);
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
			if(ScoreManagement.betterScore(globalScoreInfo,finalScoreInfo)) 
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
							localScoreInfo = ScoreManagement.testBoard(tempBoard,game.getGameOptions());
							if(ScoreManagement.betterScore(localScoreInfo,globalScoreInfo)) 
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
}
