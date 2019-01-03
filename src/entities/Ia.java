package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import enums.ColorEnum;
import enums.FaceEnum;

public class Ia extends Player{
	public Ia(ColorEnum color,int numberKings) {
		super(color,numberKings);
	}
	public void startTurn()
	{
		// Gestion du tour par ia
		
	}
	public void pickCard() {
		List<Card> cards = new ArrayList<Card>();
		Card bestCard = cards.get(0);
		for(Card card : cards) {
			selectBestMove(card);
		}
	}
	public void selectBestMove(Card card) {
		Board tempBoard;
		for(int x = 0;x < this.getBoard().getCoords().length;x++)
		{
			for(int y = 0;y <  this.getBoard().getCoords()[x].length;y++)
			{
				if( this.getBoard().getCoords()[x][y] instanceof Empty)
				{
					for(int i = 0;i < 8;i++)
					{
						tempBoard = new Board(this.getBoard());
						switch(i) {
							case 0:
								tempBoard.setCard(x, y, x+1, y, card);
								break;
							case 1:
								tempBoard.setCard(x, y, x-1, y, card);
								break;
							case 2:
								tempBoard.setCard(x, y, x, y+1, card);
								break;
							case 3:
								tempBoard.setCard(x, y, x, y-1, card);
								break;
							case 4:
								tempBoard.setCard(x+1, y, x, y, card);
								break;
							case 5:
								tempBoard.setCard(x-1, y, x, y, card);
								break;
							case 6:
								tempBoard.setCard(x, y+1, x, y, card);
								break;
							case 7:
								tempBoard.setCard(x, y-1, x, y, card);
								break;						
						}
					}
				}
			}
		}
	}
	public void pickBestCard() {
		List<Card> cards = new ArrayList<Card>();
		Card bestCard = cards.get(0);
		HashMap<String,Integer> bestHypotheticalScore = new HashMap<String,Integer>();
		bestHypotheticalScore.put("totalScore", 0);
		bestHypotheticalScore.put("largestDomain",0);
		bestHypotheticalScore.put("nbCrown",0);
		for(Card card : cards) {
			List<Integer> regionScoreInfo = new ArrayList<Integer>();
			HashMap<String,Integer> hypotheticalScore = new HashMap<String,Integer>();
			hypotheticalScore.put("totalScore", 0);
			hypotheticalScore.put("largestDomain",0);
			hypotheticalScore.put("nbCrown",0);
			for (FaceEnum faceType : FaceEnum.values()) {
				 	regionScoreInfo =calculateRegion(this,faceType);
					if((int)regionScoreInfo.get(0) > hypotheticalScore.get("largestDomain"))
					{
						hypotheticalScore.put("largestDomain",(int)regionScoreInfo.get(0));
					}
					hypotheticalScore.put("nbCrown",hypotheticalScore.get("nbCrown")+(int)regionScoreInfo.get(0));
					hypotheticalScore.put("finalScore",hypotheticalScore.get("finalScore")+(int)regionScoreInfo.get(0) * (int)regionScoreInfo.get(1));			 
			}
			if(betterScore(hypotheticalScore,bestHypotheticalScore))
			{
				bestHypotheticalScore.put("totalScore", hypotheticalScore.get("totalScore"));
				bestHypotheticalScore.put("largestDomain",hypotheticalScore.get("largestDomain"));
				bestHypotheticalScore.put("nbCrown",hypotheticalScore.get("nbCrown"));			
			}
		}
	}
	public Boolean betterScore(HashMap<String,Integer> hypotheticalScore,HashMap<String,Integer> bestHypotheticalScore) {
		if(hypotheticalScore.get("finalScore") > bestHypotheticalScore.get("finalScore"))
		{
			return true;
		}
		else if(hypotheticalScore.get("finalScore") == bestHypotheticalScore.get("finalScore"))
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
	public List<Integer> calculateRegion(Player player,FaceEnum faceType)
	{
		List<Map<String,Integer>> sameType = player.getBoard().getSameType(faceType);
		Iterator<Map<String, Integer>> itr = sameType.iterator();
		List<Entity> faceCounted = new ArrayList<Entity>();
		List<Integer> regionScoreInfo = new ArrayList<Integer>();
		int regionScore = 0;
		int regionLargestDomain = 0;
		int regionNbCouronnes = 0;
		while(itr.hasNext())
		{
			Map<String,Integer> caseRef = itr.next();
			List<Object> domainCounted = checkAdjacent(player,faceType,caseRef.get("x"),caseRef.get("y"),faceCounted,null);
			System.out.println("DOmaine counted" +domainCounted);
			//faceCounted = ((List<Entity>)domainCounted.get(2));
			if((int)domainCounted.get(0) > regionLargestDomain)
			{
				regionLargestDomain = (int)domainCounted.get(0);
			}
			regionNbCouronnes+= (int)domainCounted.get(0);
			regionScore+= (int)domainCounted.get(0) * (int)domainCounted.get(1);
		}
		regionScoreInfo.add(regionScore);
		regionScoreInfo.add(regionLargestDomain);
		regionScoreInfo.add(regionNbCouronnes);
		return regionScoreInfo;
	}
	public List<Object> checkAdjacent(Player player,FaceEnum faceRef,int x,int y,List<Entity> faceCounted,Entity previous)
	{
		List<Object> temp;
		Entity entity = player.getBoard().getEntity(x, y);
		List<Object> count = new ArrayList<Object>();
		count.add(0);
		count.add(0);
		count.add(faceCounted);
		//System.out.println("Entity "+entity+" / Previous "+previous);
		if(entity instanceof Face && !faceCounted.contains(entity))
		{
			Entity entityUp = player.getBoard().getEntity(x, y+1);
			Entity entityDown = player.getBoard().getEntity(x, y-1);
			Entity entityLeft = player.getBoard().getEntity(x-1, y);
			Entity entityRight = player.getBoard().getEntity(x+1, y);
			if(entityUp != null && entityUp instanceof Face && ((Face) entityUp).getFaceType() == faceRef && entityUp != previous)
			{
				temp = checkAdjacent(player,faceRef,x,y+1,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityDown != null && entityDown instanceof Face && ((Face) entityDown).getFaceType() == faceRef && entityDown != previous)
			{
				temp = checkAdjacent(player,faceRef,x,y-1,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityLeft != null && entityLeft instanceof Face && ((Face) entityLeft).getFaceType() == faceRef && entityLeft != previous)
			{
				temp = checkAdjacent(player,faceRef,x-1,y,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			if(entityRight != null && entityRight instanceof Face && ((Face) entityRight).getFaceType() == faceRef && entityRight != previous)
			{
				temp = checkAdjacent(player,faceRef,x+1,y,faceCounted,entity);
				count.set(0,(int)temp.get(0));
				count.set(1,(int)temp.get(1));
				count.set(2,temp.get(2));
			}
			count.set(0,1+(int)count.get(0));
			count.set(1,((Face)player.getBoard().getEntity(x, y)).getCrown() + (int)count.get(1));
			((List<Entity>)count.get(2)).add(entity);
		}
		return count;
	}
}
