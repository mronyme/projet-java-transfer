package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import core.CoreGame;
import core.ScoreManagement;
import enums.ColorEnum;

public class IaBeta extends Player{
	HashMap<String,Integer> bestMove;
	Card cardChosen;
	Card bestNextCard;
	public IaBeta(CoreGame game,ColorEnum color,int numberKings) {
		super(game,color,numberKings);
		this.bestMove = null;
		this.cardChosen = null;
		this.bestNextCard = null;
	}
	public void initTurn()
	{
		this.getBoard().setCastle(2, 2);
	}
	public Card firstTurn()
	{
		pickBestTurn(1);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), cardChosen);
		}
		this.game.pickCard(this, 0, cardChosen);
		return this.bestNextCard;
	}
	public void lastTurn(int nbRound,Card lastCard)
	{
		cardChosen = lastCard;
		pickBestTurn(3);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), lastCard);
		}
	}
	public Card startTurn(int nbRound,Card card)
	{
		cardChosen = card;
		pickBestTurn(2);
		if(bestMove.size() > 0)
		{
			this.getBoard().setCard(bestMove.get("FC1X"), bestMove.get("FC1Y"), bestMove.get("FC2X"), bestMove.get("FC2Y"), card);
		}
		return bestNextCard;
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
	public void pickBestTurn(int gameState){
		Board tempBoard;
		HashMap<String,Integer> localScoreInfo = new HashMap<String,Integer>();
		HashMap<String,Integer> globalScoreInfo = new HashMap<String,Integer>();
		globalScoreInfo.put("totalScore", 0);
		globalScoreInfo.put("largestDomain", 0);
		globalScoreInfo.put("nbCrown", 0);
		List<Card> cardsColumn1 = new ArrayList<Card>();
		List<Card> cardsColumn2 = new ArrayList<Card>();
		if(gameState == 1)
		{
			cardsColumn1 = game.getCardsAvailable(0);
			cardsColumn2 = game.getCardsAvailable(1);
			bestNextCard = cardsColumn2.get(0);
		}
		else if(gameState == 2)
		{
			cardsColumn1 =	new ArrayList<Card>(Arrays.asList(this.cardChosen));
			cardsColumn2 = game.getCardsAvailable(1);
			bestNextCard = cardsColumn2.get(0);
			//System.out.println(cardsColumn2);
		}
		else if(gameState == 3)
		{
			pickLastTurn();
		}
		for(Card cardCurrent : cardsColumn1) { 
			for(Card nextCard : cardsColumn2) {
				for(int x = 0;x < this.getBoard().getCoords().length;x++)
				{
					for(int y = 0;y <  this.getBoard().getCoords()[x].length;y++)
					{
						if( this.getBoard().getCoords()[x][y] instanceof Empty)
						{
							tempBoard = new Board(this.getBoard());
							Boolean valid = true;
							List<HashMap<String,Integer>> cardCurrentPositions = get8Positions(x,y);
							List<HashMap<String,Integer>> nextCardPositions = get8Positions(x,y);
							for(HashMap<String,Integer> cardCurrentPosition : cardCurrentPositions)
							{
								valid = tempBoard.setCard(cardCurrentPosition.get("FC1X"), cardCurrentPosition.get("FC1Y"), cardCurrentPosition.get("FC2X"), cardCurrentPosition.get("FC2Y"), cardCurrent);
								if(valid)
								{
									for(HashMap<String,Integer> nextCardPosition : nextCardPositions)
									{
										valid = tempBoard.setCard(nextCardPosition.get("FC1X"), nextCardPosition.get("FC1Y"), nextCardPosition.get("FC2X"), nextCardPosition.get("FC2Y"), nextCard);
										if(valid)
										{
											localScoreInfo = ScoreManagement.testBoard(tempBoard,game.getGameOptions());
											if(ScoreManagement.betterScore(localScoreInfo,globalScoreInfo)) 
											{
												globalScoreInfo.putAll(localScoreInfo);
												bestMove = cardCurrentPosition;
												if(gameState == 1)
												{
													cardChosen = cardCurrent;
												}
												bestNextCard = nextCard;
											}
										}
									}	
								}
							}
						}
					}
				}
			}
		}
	}
	public void pickLastTurn() {
		Board tempBoard;
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
					tempBoard = new Board(this.getBoard());
					Boolean valid = true;
					List<HashMap<String,Integer>> cardCurrentPositions = get8Positions(x,y);
					for(HashMap<String,Integer> cardCurrentPosition : cardCurrentPositions)
					{
						valid = tempBoard.setCard(cardCurrentPosition.get("FC1X"), cardCurrentPosition.get("FC1Y"), cardCurrentPosition.get("FC2X"), cardCurrentPosition.get("FC2Y"), cardChosen);
						if(valid)
						{
							localScoreInfo = ScoreManagement.testBoard(tempBoard,game.getGameOptions());
							if(ScoreManagement.betterScore(localScoreInfo,globalScoreInfo)) 
							{
								globalScoreInfo.putAll(localScoreInfo);
								bestMove = cardCurrentPosition;
							}
						}
					}
				}
			}
		}
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
	public List<HashMap<String,Integer>> get8Positions(int x,int y)
	{
		int FC1X,FC1Y,FC2X,FC2Y;
		List<HashMap<String,Integer>> movesList = new ArrayList<HashMap<String,Integer>>();
		HashMap<String,Integer> move ;
		for(int i = 0;i < 8;i++)
		{
			move = new HashMap<String,Integer>();
			FC1X = FC2X = x;
			FC1Y = FC2Y = y;
			switch(i) {
			case 0:
				FC2X = x+1;
				break;
			case 1:
				FC2X = x-1;
				break;
			case 2:
				FC2Y = y+1;
				break;
			case 3:
				FC2Y = y-1;
				break;
			case 4:
				FC1X = x+1;
				break;
			case 5:
				FC1X = x-1;
				break;
			case 6:
				FC1Y = y+1;
				break;
			case 7:
				FC1Y = y-1;
				break;		
			}
			move.put("FC1X", FC1X);
			move.put("FC1Y", FC1Y);
			move.put("FC2X", FC2X);
			move.put("FC2Y", FC2Y);
			movesList.add(move);
		}
		return movesList;
	}
}
