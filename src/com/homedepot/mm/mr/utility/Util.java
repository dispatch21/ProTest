package com.homedepot.mm.mr.utility;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.homedepot.mm.mr.dao.GetCashBackStatementDataDAO;
import com.homedepot.mm.mr.dto.ReadSsysParmValuesDTO;
import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.util.TAAAResourceManager;

public class Util  {

	private final static Logger LOGGER = Logger.getLogger(Util.class);

	/**
	 * Returns the life cycle phase of the server
	 * @return the life cycle phase (AD, ST, QA, Q2, Q3, QP, PR)
	 */
	private static String getLCP()
	{
		String lcp = TAAAResourceManager.getProperty(Constants.LIFE_CYCLE_PHASE);
		if (lcp == null)
		{
			LOGGER.error("getLCP: Null value returned for \"TAAAResourceManager.getProperty(\"host.LCP\")\" LCP = \"AD\" will be used.");
			lcp = Constants.LOCAL;
		}

		lcp = lcp.toUpperCase(Locale.getDefault()).trim();

		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Printing LCP Value ...." + lcp);
		}

		return lcp;
	}
	
	
	/**
	 * Get base URL
	 * @return
	 */
	public static String getBaseURL()
	{
		String baseUrl = "";
		String lcp = null;
		lcp = getLCP();

		if (Constants.LCP_LOCAL.equals(lcp) || Constants.LCP_AD.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_AD;
		}
		else if (Constants.LCP_ST.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_ST ;
		}
		else if ( Constants.LCP_QA.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_QA ;
		}
		else if ( Constants.LCP_Q1.equals(lcp) )
		{
			baseUrl = Constants.HOST_URL_Q1 ;
		}
		else if ( Constants.LCP_Q2.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_Q2 ;
		}
		else if ( Constants.LCP_Q3.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_Q3 ;
		}
		else if (Constants.LCP_QP.equals(lcp))
		{
			baseUrl = Constants.HOST_URL_QP ;
		}
		else
		{
			baseUrl = Constants.HOST_URL_PR ;
		}

		return baseUrl;
	}
	
	
	/**
	 * This method returns the current date.
	 *
	 * @return String
	 */
	public static String getCurrentDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = new Date(System.currentTimeMillis());
		return sdf.format(currentDate);
	}

	
	/**
	 * This method is used to get the Mask number for Credit Card accounts
	 * @param extnlIdVal
	 * @return
	 */
	public static String getMaskNumber(String extnlIdVal)
	{
		StringBuffer maskNumber = new StringBuffer();
		extnlIdVal = StringUtils.trimToEmpty(extnlIdVal);
		if (StringUtils.isEmpty(extnlIdVal))
		{
			maskNumber.append("x-none");
		}
		else
		{
			maskNumber.append("x-");
			maskNumber.append(StringUtils.substring(extnlIdVal, extnlIdVal.length()-4));
			
		}
		return maskNumber.toString();

	}
	
	
	/**
	 * This method is used to get the Mask number for accounts
	 * @param extnlIdVal
	 * @return
	 */

	public static String getMaskAcctNumber(String extnlIdVal)
	{
		StringBuffer maskNumber = new StringBuffer();
		extnlIdVal = StringUtils.trimToEmpty(extnlIdVal);
		int length;
		length = extnlIdVal.length();
		maskNumber.append("x");
		maskNumber.append("-");
		if (length > 4)
		{
			maskNumber.append(extnlIdVal.substring(length - 4));
		}
		else if (length > 1)
		{
			maskNumber.append(extnlIdVal.substring(1));
		}
		else
		{
			maskNumber.append(extnlIdVal);
		}
		return maskNumber.toString();
	}
	
	
	/**
	 * 
	 * @param registerNum
	 * @param posTransId
	 * @return
	 */
	public static String frameReceiptDetails(String registerNum, String posTransId) {
		String val = "";
		try {
			String registerNumberStr = RAPUtil.prefixZeros(registerNum, 5);
			String posTransIdStr = RAPUtil.prefixZeros(posTransId, 5);
			val = StringUtils.join(new String [] {registerNumberStr, posTransIdStr}, "-");
		} catch (QueryException e) {
			//Do nothing.
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug(e);
			}
		}
		return val;
	}

	
	/**
	 * TODO revisit these case statememts.
	 * Account Type Code    
	 * @param ExtnlIdTypCd
	 * @return
	 */
	public static String getPaymentCardType(short cardTypeCode) {
		return StringUtils.upperCase( CreditCard.getCreditCard(cardTypeCode).getCardType())+" ";
	}
	
		
	/**
	 * Returns the Calendar Quarter
	 * @param value
	 * @return
	 */
	public static String getQuarterDates(int value) {
		String calendarQuarter = "";

		String _BEGIN_DATE = Constants.Q + value + Constants._BEGIN_DATE;
		String _END_DATE = Constants.Q + value + Constants._END_DATE;

		String beginMonth = getSsysParmValue(_BEGIN_DATE);
		String endMonth = getSsysParmValue(_END_DATE);

		if (!RAPUtil.isNullString(beginMonth) && !RAPUtil.isNullString(endMonth)) {
			calendarQuarter = beginMonth + " to " + endMonth;
		} else {
			calendarQuarter = getQuarter(value) ;
		}
		
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("calendarQuarter ...." + calendarQuarter);
		}
		
		return calendarQuarter;
	}
	
	
	/**
	 * This method rounds of the amount to two decimals
	 * @param string
	 * @return double
	 */
	public static String roundOfTwoDecimalPlaces(String amount) {
		String amt = null;
		Locale locale = new Locale("en", "US");      
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
	    amt = currencyFormatter.format(Double.parseDouble(amount)) ;
	    
	    return amt ;
	}
	
	/**
	 * removeTrailingZeros
	 * @param cashBackLevel
	 * @return
	 */
	public static String removeTrailingZeros(String cashBackLevel) {
		String percentage = null;
		Double cashLevel = Double.parseDouble(cashBackLevel);
		DecimalFormat format = new DecimalFormat("0.#");
		percentage = format.format(cashLevel);
		
		return percentage;
	}
	/**
	 * Returns month in short form
	 * @param month
	 * @return
	 */
	public static String theMonth(int month){
	    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};
	    return monthNames[month];
	}
	
	
	/**
	 * getSsysParmValue
	 * @param inputValue
	 * @return
	 */
	public static String getSsysParmValue(String inputValue) {
		String period = "";

		try {
			ArrayList<ReadSsysParmValuesDTO> ssysParmValuesList = (ArrayList<ReadSsysParmValuesDTO>) GetCashBackStatementDataDAO
					.getSsysParmValuesData(Constants.SUB_SYS_CODE, inputValue);

			if (!RAPUtil.isNullList(ssysParmValuesList)) {
				int month = Integer.parseInt(ssysParmValuesList.get(0).getSsysParmCharVal().substring(0, 2));
				period = theMonth(month - 1)+ " "+ ssysParmValuesList.get(0).getSsysParmCharVal().substring(3, 5);
			}

		} catch (QueryException e) {		
			LOGGER.error("QueryException: " + e);
		}
		return period;
	}
	
	
	/**
	 * Returns the Calendar Quarter
	 * @param value
	 * @return
	 */
	public static String getQuarter(int value) {
		String quarter = "";

		switch (value) {
		case 1:
			quarter = Constants.QUARTER_1 ;
			break;
		case 2:
			quarter = Constants.QUARTER_2 ;
			break;
		case 3:
			quarter = Constants.QUARTER_3 ;
			break;
		case 4:
			quarter = Constants.QUARTER_4 ;
			break;
		}
		return quarter;
	}
	
}
