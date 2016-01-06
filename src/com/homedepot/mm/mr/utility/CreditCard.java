package com.homedepot.mm.mr.utility;

public enum CreditCard {
	PROX(5,"PROX"),AMEX(11, "AMEX"), DISCOVER(12, "DISCOVER"), MASTERCARD6(6, "MASTER"), MASTERCARD7(7, "MASTER"), MASTERCARD13(13, "MASTER"), 
	VISA(14, "VISA"), HD15(15, "HD"), HD3(3, "HD"),HD4(4, "HD"),HD17(17, "HD");

	private int cardID;
	private String cardType;

	private CreditCard(int cardID, String cardType) {
		this.cardID = cardID;
		this.cardType = cardType;
	}

	public static CreditCard getCreditCard(short cardID)
	{
		switch (cardID) {
			case 5:
				return PROX;
			case 11:
				return AMEX;
			case 12:
				return DISCOVER;
			case 6:
				return MASTERCARD6;
			case 7:
				return MASTERCARD7;
			case 13:				
				return MASTERCARD13;
			case 14:
				return VISA;
			case 3: // HOME_DEPOT_CARD_TYPE_CODE
				return HD3;
			case 4: // EXPO_CONSUMER_CARD_TYPE_CODE
				return HD4;
			case 17: // EXPO_HIL_CARD_TYPE_CODE
				return HD17;
			case 15: // HD				
			default:
				return HD15;
		}

	}

	public int getCardID()
	{
		return cardID;
	}

	public void setCardID(int cardID)
	{
		this.cardID = cardID;
	}

	public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}
}

