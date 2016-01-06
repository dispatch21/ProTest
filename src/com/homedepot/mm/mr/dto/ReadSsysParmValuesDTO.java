package com.homedepot.mm.mr.dto;

import java.io.Serializable;

import com.homedepot.ta.aa.dao.builder.DAOElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Ssysparm")
public class ReadSsysParmValuesDTO implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8341593733984324283L;

	@DAOElement({"SSYS_PARM_NM"})
	private String ssysParmNm;

	@DAOElement({"SSYS_PARM_CHAR_VAL"})
	private String ssysParmCharVal;
	
	
	
	/**
	 * @return the ssysParmNm
	 */
	public String getSsysParmNm() {
		return ssysParmNm;
	}
	/**
	 * @param ssysParmNm
	 */
	public void setSsysParmNm(String ssysParmNm) {
		this.ssysParmNm = ssysParmNm;
	}
	/**
	 * @return the ssysParmCharVal
	 */
	public String getSsysParmCharVal() {
		return ssysParmCharVal;
	}
	/**
	 * @param ssysParmCharVal
	 */
	public void setSsysParmCharVal(String ssysParmCharVal) {
		this.ssysParmCharVal = ssysParmCharVal;
	}
}