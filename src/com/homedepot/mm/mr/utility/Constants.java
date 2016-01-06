/*
 * 
 *
 * This program is proprietary to The Home Depot and is not to be reproduced,
 * used, or disclosed without permission of:
 *    The Home Depot
 *    2455 Paces Ferry Road, NW
 *    Atlanta, GA 30339-4024
 *
 * File Name:Constants.java
 */

package com.homedepot.mm.mr.utility;

public final class Constants {

	public static final String ERROR = "ERROR";	
	public static final String ERROR_PARSE_DATE = "Error occurred when parsing the date";	
	public static final int STORE_LENGTH = 4;		
	public static final String APP_USER_ID = "";	
	public static final int GENERIC_ERROR_IN_MSG_TABLE = 100;		
	public static final String LOCAL = "local";
	public static final String LIFE_CYCLE_PHASE = "host.LCP";	
	public static final String SUB_SYS_CODE = "MR";
	public static final String THD_PRO_LOGO = "THDProLogo" ;
	public static final String CALENDER_QUARTER = "calendarQuarter" ;
	public static final String SPEND = "spend" ;
	public static final String CASH_BACK_LEVEL = "cashBackLevel" ;
	public static final String CASH_BACK_EARNED = "cashBackEarned" ;
	public static final String CUSTOMER_TRANSACTION_LIST = "TransactionList";	
	public static final String DOWNLOAD_STATEMENT_DATA_PATH = "/downloadStatementData";
	
	public static final String SALES_DATE = "salesDate" ;
	public static final String ADDED_DATE = "addedDate" ;
	public static final String JOB_NAME = "jobName" ;
	public static final String RECEIPT_NUMBER = "receiptNumber" ;
	public static final String PRE_TAX_AMOUNT = "preTaxAmount" ;
	public static final String TOTAL_AMOUNT = "totalAmount" ;
	public static final String STORE_NAME = "storeName" ;
	public static final String CREDIT_CARD_ACCOUNTS = "creditCardsAccounts" ;
	public static final String CARD_ACCOUNT_NAME = "cardAccountName" ;
	public static final String ORDER_NUMBER = "orderNbr" ;
	
	public static final String LCP_AD = "AD";
	public static final String LCP_ST = "ST";
	public static final String LCP_QA = "QA";
	public static final String LCP_PR = "PR";
	public static final String LCP_QP = "QP";
	public static final String LCP_LOCAL = "LOCAL";
	public static final String LCP_Q1 = "Q1";
	public static final String LCP_Q2 = "Q2";
	public static final String LCP_Q3 = "Q3";
		
	public static final String HOST_URL_AD = "http://localhost:8085/ProTransactions";
	public static final String HOST_URL_ST = "https://webapps-st.homedepot.com/ProTransactions";
	public static final String HOST_URL_QA = "https://webapps-qa.homedepot.com/ProTransactions";
	public static final String HOST_URL_Q1 = "https://webapps-q1.homedepot.com/ProTransactions";
	public static final String HOST_URL_Q2 = "https://webapps-q2.homedepot.com/ProTransactions";
	public static final String HOST_URL_Q3 = "https://webapps-q3.homedepot.com/ProTransactions";
	public static final String HOST_URL_QP = "https://webapps-qp.homedepot.com/ProTransactions";
	public static final String HOST_URL_PR = "https://webapps.homedepot.com/ProTransactions";
	
	public static final String templateURL = "/templates/cashBackStatement.html" ;
    public static final String proXtralogoUrl = "/images/ProXtra_Logo_WHITE_LARGER.png" ;
 
    public static final String QuartelyCashBack_fileName = "ProXtra_Quarterly_Cash_Back_Statement_" ;
    public static String contentType = "application/pdf";
    
    public static final int ZERO_INT = 0 ;
    public static final String ZERO_STRING = "0" ;
    
	public static final short PHONE_NUMBER_TYPE_CODE = 100;
	public static final short CREDIT_OR_DEBIT_CARD_TYPE_CODE = 101;
	public static final short CHECKING_ACCOUNT_TYPE_CODE = 102;
	public static final short THD_GIFT_CARD_TYPE_CODE = 103;
	public static final short KEYTAG_TYPE_CODE = 104;

	public static final String PHONE_CODE = "PHONE";
	public static final String CREDIT_OR_DEBIT_CODE = "CREDIT";
	public static final String CHECKING_ACCOUNT_CODE = "CHKACT";
	public static final String THD_GIT_CODE = "THDGIFT";
	public static final String KEYTAG_CODE = "KEYTAG";
	
	public static final String QUARTER_1 = "Jan 01 to Mar 31" ; 
	public static final String QUARTER_2 = "Apr 01 to June 30";
	public static final String QUARTER_3 = "July 01 to Sept 30";
	public static final String QUARTER_4 = "Oct 01 to Dec 31";
	
		
	public static final Short PVL_CONSUMER_CARD_TYPE_CODE = 3;
	public static final Short PVL_CRC_CARD_TYPE_CODE = 4;
	public static final Short PVL_COMMERCIAL_CARD_TYPE_CODE = 5;
	public static final Short AMERICAN_EXPRESS_CARD_TYPE_CODE = 11;
	public static final Short DISCOVER_CARD_TYPE_CODE = 12;
	public static final Short MASTER_CARD_TYPE_CODE = 13;
	public static final Short VISA_CARD_TYPE_CODE = 14;
	public static final Short HOME_DEPOT_CARD_TYPE_CODE = 15;
	public static final Short EXPO_CONSUMER_CARD_TYPE_CODE = 16;
	public static final Short EXPO_HIL_CARD_TYPE_CODE = 17;
	public static final Short THD_HIL_CARD_TYPE_CODE = 18;
	
	
	public static final String PVL_CONSUMER_CARD = "RPL CONSUMER" ; //3; RPL CONSUMER
	public static final String PVL_CRC_CARD  = "CRC" ; // 4; CRC
	public static final String PROX_CARD =  "PROX" ; //5; PROX
	public static final String AMERICAN_EXPRESS = "AMEX" ; //(11, "AMERICAN EXPRESS"), 
	public static final String DISCOVER = "DISCOVER" ; //(12, "DISCOVER"), 
	public static final String MASTERCARD = "MASTER" ;//(13, "MASTERCARD"), 
	public static final String VISA = "VISA" ; //(14, "VISA"), 
	public static final String HOME_DEPOT =  "HD" ; //(15, "HOME DEPOT");
	
	public static final String  Q = "Q";
	
	public static final String _BEGIN_DATE = "_BEGIN_DATE" ;
	public static final String _END_DATE = "_END_DATE" ;
	
	public static final String PR4_JNDI_NM = "java:comp/env/jdbc/DB2Z.PR4.001";
	
}
