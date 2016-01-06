package com.homedepot.mm.mr.dto;

import java.io.Serializable;



import org.apache.commons.lang3.StringUtils;

import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.mm.mr.utility.Util;
import com.homedepot.ta.aa.dao.builder.DAOElement;

public class MembershipAmountDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@DAOElement({"QUARTERLY_SPEND"})
	private	String spend;
	
	@DAOElement({"QUARTERLY_CASHBACK"})
	private	String cashBackEarned;
	
	@DAOElement({"TM_PRD_BGN_DT"})
    private String beginDate;
    
    @DAOElement({"TM_PRD_END_DT"})
    private String endDate;
	
    /**
	 * @return the spend
	 */
	public String getSpend() {
		return spend;
	}
	/**
	 * @param spend the spend to set
	 */
	public void setSpend(String spend) {
		this.spend = Util.roundOfTwoDecimalPlaces(spend);
	}
	/**
	 * @return the cashBackEarned
	 */
	public String getCashBackEarned() {
		return cashBackEarned;
	}
	/**
	 * @param cashBackEarned the cashBackEarned to set
	 */
	public void setCashBackEarned(String cashBackEarned) {
		this.cashBackEarned = Util.roundOfTwoDecimalPlaces(cashBackEarned);
	}
	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * @returns the beginDate formatted for display
	 */
	public String getBeginDateDisplay() {
		return RAPUtil.convertDashesDateTOSlashes(beginDate);
	}
	/**
	 * 
	 * @return the begin and end date formatted for display
	 */
	public String getTotalsDateRange(){
		return StringUtils.join(new String [] {getBeginDateDisplay(), getEndDateDisplay()}, " to ");
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * @return the endDate formatted for display
	 */
	public String getEndDateDisplay() {
		return RAPUtil.convertDashesDateTOSlashes(endDate);
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
    
    
    
}
