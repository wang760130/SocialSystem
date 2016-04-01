package com.bj58.socialsystem.demo;

import java.util.Random;

/**
 * 微信红包的随机算法
 * 
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月22日
 */
public class WeixinRedPaper {
	
	public static double getRandomMoney(LeftMoneyPackage leftMoneyPackage) {
		if(leftMoneyPackage.getRemainSize() == 1) {
			leftMoneyPackage.setRemainSize(leftMoneyPackage.getRemainSize() - 1);
			return (double) Math.round(leftMoneyPackage.getRemainMoney() * 100) / 100;
		}
		
		Random random = new Random();
		double min = 0.01;
		double max = leftMoneyPackage.getRemainMoney() / leftMoneyPackage.getRemainSize() * 2;
		double money = random.nextDouble() * max;
		
		money = money <= min ? 0.01 : money;
		money = Math.floor(money * 100) / 100;
		leftMoneyPackage.setRemainSize(leftMoneyPackage.getRemainSize() - 1);
		leftMoneyPackage.setRemainMoney(leftMoneyPackage.getRemainMoney() - money);
	
		return money;
	}
	
	public static void main(String[] args) {
		int money = 500;
		int size = 30;
		LeftMoneyPackage leftMoneyPackage = new LeftMoneyPackage();
		leftMoneyPackage.setRemainMoney(money);
		leftMoneyPackage.setRemainSize(size);
		for(int i = 0; i < size; i++)
			System.out.println(getRandomMoney(leftMoneyPackage));
	}
}


class LeftMoneyPackage {
	
	private int remainSize;
	private double remainMoney;
	
	public int getRemainSize() {
		return remainSize;
	}
	public void setRemainSize(int remainSize) {
		this.remainSize = remainSize;
	}
	public double getRemainMoney() {
		return remainMoney;
	}
	public void setRemainMoney(double remainMoney) {
		this.remainMoney = remainMoney;
	}
}