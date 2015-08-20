package com.futureinst.home.eventdetail;

public class CalculateAssureUtil {
	private static final float EVENT_0 = 0;
	private static final float EVENT_1 = 100;
	
	//盈利
	public static  float calculateGain(int buyNum, float buyPrice, int sellNum, float sellPrice, float cullPrice){
		return buyNum * (cullPrice - buyPrice) + sellNum * (sellPrice - cullPrice);
	}
	//保证金
	public static float calculateAssure(int buyNum, float buyPrice, int sellNum, float sellPrice){
		float minGain = Math.min(calculateGain(buyNum, buyPrice, sellNum, sellPrice, EVENT_0),
				calculateGain(buyNum, buyPrice, sellNum, sellPrice, EVENT_1));
		return minGain >= 0 ? 0 : -minGain;
	}
	//均价
	public static  float calculateAvgPrice(int oldNum, float oldAvg, int addNum, float addAvg){
		return (oldNum * oldAvg + addNum * addAvg)/(oldNum + addNum);
	}
	//计算交易所需保证金
	public static float calculateNeedAssure(boolean attitude,int currNum,float currPrice,
			int buyNum, float buyPrice, int sellNum, float sellPrice){
		float assure = 0f;
		if(buyNum == 0 && sellNum == 0){
			if(attitude){//买
				assure = calculateAssure(currNum, currPrice, 0, 0);
			}else{//卖
				assure = calculateAssure(0,0,currNum, currPrice);
			}
			return assure;
		}else{
			if(attitude){//买
				for(int i = 0;i<currNum;i++){
					float currBuyPrice = (buyNum*buyPrice + i*currPrice)/(buyNum + i);
					assure = Math.max(assure, calculateAssure(buyNum + i, currBuyPrice, sellNum, sellPrice));
				}
			}else{//卖
				for(int i = 0;i<currNum;i++){
					float currSellPrice = (sellNum*sellPrice + i*currPrice)/(sellNum + i);
					assure = Math.max(assure, calculateAssure(buyNum, buyPrice, sellNum + i, currSellPrice));
				}
			}
			return assure - calculateAssure(buyNum, buyPrice, sellNum, sellPrice);
		}
	}
}
