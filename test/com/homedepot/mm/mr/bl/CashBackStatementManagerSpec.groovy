package com.homedepot.mm.mr.bl

import com.homedepot.mm.mr.dao.interfaces.IMembershipAmountDAO
import com.homedepot.mm.mr.dao.interfaces.IMembershipLevelDAO
import com.homedepot.mm.mr.dao.interfaces.IMembershipTransactionsDAO
import com.homedepot.mm.mr.dto.MembershipAmountDTO
import com.homedepot.mm.mr.dto.MembershipLevelDTO
import com.homedepot.mm.mr.dto.ReadCustomerTransactionsDTO
import com.homedepot.mm.mr.recognition.utility.RAPUtil
import com.homedepot.mm.mr.bl.CashBackStatementManager


import spock.lang.Specification

class CashBackStatementManagerSpec extends Specification {


	def " Retrieve the membership summary level and the transaction list " () {

		given: "Given values"
			IMembershipAmountDAO iMembershipAmountDAO = Mock()
			IMembershipLevelDAO iMembershipLevelDAO = Mock()
			IMembershipTransactionsDAO iMembershipTransactionsDAO = Mock()
			
			def membershipLevelDTO = new MembershipLevelDTO()
			def cashBackStatementManager = new CashBackStatementManager()		
			cashBackStatementManager.setMembershipAmount(iMembershipAmountDAO)
			cashBackStatementManager.setMembershipLevel(iMembershipLevelDAO)
			cashBackStatementManager.setMembershipTransactions(iMembershipTransactionsDAO)
			
			def quarterSummary = new MembershipAmountDTO(endDate:'2015-06-12',beginDate:'2015-06-09', spend:'5000', cashBackEarned:'50')
			def transactionList	= new ArrayList<ReadCustomerTransactionsDTO>([new ReadCustomerTransactionsDTO(salesDate:'2015-08-07', 
				registerNumber:'11',transactionId:'43210', storeNumber:'9710', receiptAddedDate:'2015-06-12', jobName:'JobName', 
				preTaxAmount:'400', totalAmount:'440',accountNumber:'555544444321', extnlIdTypCd:101, paymentCardTypeCode:14, 
				storeName:'Austell Store', cardAccountName:'John Doe')])    
			def customerNumber = 1700081
			def programID = 971
			def membershipAmountTypeCD = (short) 10
		
					
		when: "Reading CashBack Statement"
			def returnCashBackDTO = cashBackStatementManager.readCashbackStatementData(customerNumber, programID, membershipAmountTypeCD, false)
		
				
		then: "Get all Sales for the Quarter"
			1 * iMembershipAmountDAO.getQuarterlyMembershipSummary(customerNumber, programID, membershipAmountTypeCD, String.valueOf(RAPUtil.getCurrentYear())) >> quarterSummary
			1 * iMembershipLevelDAO.getMembershipLevel(customerNumber, programID,   RAPUtil.addDaysToDate(quarterSummary.getEndDate(), 1)) >> membershipLevelDTO
			1 * iMembershipTransactionsDAO.getMembershipTransactions(customerNumber, programID, RAPUtil.getSQLDateinYYYYMMDDFromString(quarterSummary.getBeginDate()), RAPUtil.getSQLDateinYYYYMMDDFromString(quarterSummary.getEndDate())) >> transactionList
			
		and:
			returnCashBackDTO.quarterSummary.endDate == quarterSummary.endDate
			returnCashBackDTO.quarterSummary.beginDate == quarterSummary.beginDate
			

	}
}

