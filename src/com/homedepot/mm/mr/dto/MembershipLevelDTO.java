package com.homedepot.mm.mr.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.homedepot.mm.mr.utility.Util;
import com.homedepot.ta.aa.dao.builder.DAOElement;

public class MembershipLevelDTO implements Serializable {

	
	//serialVersionUID
	private static final long serialVersionUID = 1L;

	@DAOElement({"PGM_LVL_TYP_CD"})
	private short programLevelTypeCode;
	
	@DAOElement({"MAX_SPEND_REQD_AMT"})
	private BigDecimal maxSpend;
	
	@DAOElement({"DISC_PCT"})
	private BigDecimal discountPercent;
	
	@DAOElement({"TYP_DESC"})
	private String programLevelDescription;

	/**
	 * @return the programLevelTypeCode
	 */
	public short getProgramLevelTypeCode() {
		return programLevelTypeCode;
	}

	/**
	 * @param programLevelTypeCode the programLevelTypeCode to set
	 */
	public void setProgramLevelTypeCode(short programLevelTypeCode) {
		this.programLevelTypeCode = programLevelTypeCode;
	}

	/**
	 * @return the maxSpend
	 */
	public BigDecimal getMaxSpend() {
		return maxSpend;
	}

	/**
	 * @param maxSpend the maxSpend to set
	 */
	public void setMaxSpend(BigDecimal maxSpend) {
		this.maxSpend = maxSpend;
	}

	/**
	 * @return the discountPercent
	 */
	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}
	
	/**
	 * 
	 * @return the discountPercent formatted for display
	 */
	public String getDiscountPercentDisplay() {
		return Util.removeTrailingZeros(discountPercent.toString());
	}
	/**
	 * @param discountPercent the discountPercent to set
	 */
	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	/**
	 * @return the programLevelDescription
	 */
	public String getProgramLevelDescription() {
		return programLevelDescription;
	}

	/**
	 * @param programLevelDescription the programLevelDescription to set
	 */
	public void setProgramLevelDescription(String programLevelDescription) {
		this.programLevelDescription = programLevelDescription;
	}
	
	
	
    
    
}
