package com.homedepot.mm.mr.dto;

import java.io.Serializable;
import java.util.List;


public class CashBackStatementDTO implements Serializable {

	//serialVersionUID
	private static final long serialVersionUID = 5375242452140137026L;

	//Instance variable for quarterSummary
	private MembershipAmountDTO quarterSummary;
	
	//Instance variable for membershipLevel
	private MembershipLevelDTO membershipLevel;
	
	//Instance variable for transactions
	private List<ReadCustomerTransactionsDTO> transactions;
	
	/**
	 * Constructor will all fields
	 * @param quarterSummary
	 * @param membershipLevel
	 * @param transactions
	 */
	public CashBackStatementDTO(MembershipAmountDTO quarterSummary, MembershipLevelDTO membershipLevel, List<ReadCustomerTransactionsDTO> transactions) {
		this.quarterSummary = quarterSummary;
		this.membershipLevel = membershipLevel;
		this.transactions = transactions;
	}

	/**
	 * @return the quarterSummary
	 */
	public MembershipAmountDTO getQuarterSummary() {
		return quarterSummary;
	}

	/**
	 * @param quarterSummary the quarterSummary to set
	 */
	public void setQuarterSummary(MembershipAmountDTO quarterSummary) {
		this.quarterSummary = quarterSummary;
	}

	/**
	 * @return the membershipLevel
	 */
	public MembershipLevelDTO getMembershipLevel() {
		return membershipLevel;
	}

	/**
	 * @param membershipLevel the membershipLevel to set
	 */
	public void setMembershipLevel(MembershipLevelDTO membershipLevel) {
		this.membershipLevel = membershipLevel;
	}

	/**
	 * @return the transactions
	 */
	public List<ReadCustomerTransactionsDTO> getTransactions() {
		return transactions;
	}

	/**
	 * @param transactions the transactions to set
	 */
	public void setTransactions(List<ReadCustomerTransactionsDTO> transactions) {
		this.transactions = transactions;
	}
	
	
}
