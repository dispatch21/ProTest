package com.homedepot.mm.mr.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.mm.mr.utility.Util;
import com.homedepot.ta.aa.dao.builder.DAOElement;

public class ReadCustomerTransactionsDTO implements Serializable {

	//serialVersionUID
	private static final long serialVersionUID = -2190162001293640142L;

	@DAOElement({"SLS_DT"})
	private String salesDate ;

	@DAOElement({"RGSTR_NBR"})
	private String registerNumber;
	
	@DAOElement({"POS_TRANS_ID"})
	private	String transactionId ;
	
	@DAOElement({"STR_NBR"})
	private	String storeNumber ;
	
	@DAOElement({"RECPT_ADD_DT"})
	private String receiptAddedDate ;
	
	@DAOElement({"PBC_JOB_NM"})
	private String jobName ;

	@DAOElement({"TRANS_TOT_MER_AMT"})
	private	String preTaxAmount ;

	@DAOElement({"GRS_SLS_AMT"})
	private	String totalAmount ;

	@DAOElement({"EXTNL_ID_VAL"})
	private	String accountNumber ;

	@DAOElement({"EXTNL_ID_TYP_CD"})
	private short extnlIdTypCd;

	@DAOElement({"PAYMT_CARD_TYP_CD"})
	private	short paymentCardTypeCode ;
	
	@DAOElement({"STR_NM"})
	private String storeName;
	
	@DAOElement({"EXTNL_ID_ACCT_NM"})
	private	String cardAccountName ;

	/**
	 * @return the salesDate
	 */
	public String getSalesDate() {
		return salesDate;
	}
	
	/**
	 * @return the salesDate formatted for display
	 */
	public String getSalesDateDisplay() {
		return RAPUtil.convertDashesDateTOSlashes(salesDate);
	}

	/**
	 * @param salesDate the salesDate to set
	 */
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	/**
	 * @return the registerNumber
	 */
	public String getRegisterNumber() {
		return registerNumber;
	}

	/**
	 * @param registerNumber the registerNumber to set
	 */
	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the receiptNumber
	 */
	public String getReceiptNumber() {
		return Util.frameReceiptDetails(registerNumber, transactionId);
	}

	/**
	 * @return the storeNumber
	 */
	public String getStoreNumber() {
		return storeNumber;
	}

	/**
	 * @param storeNumber the storeNumber to set
	 */
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	/**
	 * @return the receiptAddedDate
	 */
	public String getReceiptAddedDate() {
		return receiptAddedDate;
	}

	/**
	 * @return the receiptAddedDate formatted for display
	 */
	public String getReceiptAddedDateDisplay() {
		return RAPUtil.convertDashesDateTOSlashes(receiptAddedDate);
	}
	
	
	/**
	 * @param receiptAddedDate the receiptAddedDate to set
	 */
	public void setReceiptAddedDate(String receiptAddedDate) {
		this.receiptAddedDate = receiptAddedDate;
	}

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	
	/**
	 * @return the jobName formatted for display
	 */
	public String getJobNameDisplay() {
		return StringUtils.substring(jobName, 0, 10);
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the preTaxAmount
	 */
	public String getPreTaxAmount() {
		return Util.roundOfTwoDecimalPlaces(preTaxAmount);
	}

	/**
	 * @param preTaxAmount the preTaxAmount to set
	 */
	public void setPreTaxAmount(String preTaxAmount) {
		this.preTaxAmount = preTaxAmount;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return Util.roundOfTwoDecimalPlaces(totalAmount);
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the accountNumber formatted for display
	 */
	public String getAccountNumberDisplay() {
		String returnVal = null;
		if (accountNumber != null ) {
			if (Constants.CREDIT_OR_DEBIT_CARD_TYPE_CODE == extnlIdTypCd) {
				if (paymentCardTypeCode != 0) {
					String cardType = Util.getPaymentCardType(paymentCardTypeCode);
					returnVal = cardType +  Util.getMaskNumber(accountNumber);
				} else {
					returnVal = Util.getMaskNumber(accountNumber);
				}
			} else {
				returnVal = Util.getMaskNumber(accountNumber);
			}	
		}
		return returnVal;
	}
	
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the extnlIdTypCd
	 */
	public short getExtnlIdTypCd() {
		return extnlIdTypCd;
	}

	/**
	 * @param extnlIdTypCd the extnlIdTypCd to set
	 */
	public void setExtnlIdTypCd(short extnlIdTypCd) {
		this.extnlIdTypCd = extnlIdTypCd;
	}

	/**
	 * @return the paymentCardTypeCode
	 */
	public short getPaymentCardTypeCode() {
		return paymentCardTypeCode;
	}

	/**
	 * @param paymentCardTypeCode the paymentCardTypeCode to set
	 */
	public void setPaymentCardTypeCode(short paymentCardTypeCode) {
		this.paymentCardTypeCode = paymentCardTypeCode;
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}
	
	/**
	 * @return the storeName formatted for display
	 */
	public String getStoreNameDisplay() {
		return StringUtils.upperCase(StringUtils.join("#", storeNumber,"-", storeName));
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * @return the cardAccountName
	 */
	public String getCardAccountName() {
		return cardAccountName;
	}
	
	/**
	 * @return the cardAccountName formatted for display
	 */
	public String getCardAccountNameDisplay() {
		return StringUtils.substring(cardAccountName, 0, 12);
	}

	/**
	 * @param cardAccountName the cardAccountName to set
	 */
	public void setCardAccountName(String cardAccountName) {
		this.cardAccountName = cardAccountName;
	}
	
}