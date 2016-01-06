package com.homedepot.mm.mr.bl;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.homedepot.mm.mr.dao.interfaces.IMembershipAmountDAO;
import com.homedepot.mm.mr.dao.interfaces.IMembershipLevelDAO;
import com.homedepot.mm.mr.dao.interfaces.IMembershipTransactionsDAO;
//import com.homedepot.mm.mr.dao.MembershipAmountDAO;
//import com.homedepot.mm.mr.dao.MembershipLevelDAO;
//import com.homedepot.mm.mr.dao.MembershipTransactionsDAO;
import com.homedepot.mm.mr.dto.CashBackStatementDTO;
import com.homedepot.mm.mr.dto.MembershipAmountDTO;
import com.homedepot.mm.mr.dto.MembershipLevelDTO;
import com.homedepot.mm.mr.dto.ReadCustomerTransactionsDTO;
import com.homedepot.mm.mr.recognition.exception.ProRecognitionException;
import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.lowagie.text.DocumentException;

import freemarker.template.TemplateException;

public class CashBackStatementManager {

	private static CashBackStatementManager instance = new CashBackStatementManager();
	
	private IMembershipAmountDAO iMembershipAmountDAO;
	private IMembershipLevelDAO iMembershipLevelDAO;
	private IMembershipTransactionsDAO iMembershipTransactionsDAO;
	
	public IMembershipAmountDAO getMembershipAmount() {
		return iMembershipAmountDAO;
	}
	

	public void setMembershipAmount(IMembershipAmountDAO iMembershipAmountDAO) {
		this.iMembershipAmountDAO = iMembershipAmountDAO;
	}
	
	
	public IMembershipLevelDAO getMembershipLevel() {
		return iMembershipLevelDAO;
	}
	

	public void setMembershipLevel(IMembershipLevelDAO iMembershipLevelDAO) {
		this.iMembershipLevelDAO = iMembershipLevelDAO;
	}
	
	
	public IMembershipTransactionsDAO getMembershipTransactions() {
		return iMembershipTransactionsDAO;
	}
	


	public void setMembershipTransactions(IMembershipTransactionsDAO iMembershipTransactionsDAO) {
		this.iMembershipTransactionsDAO = iMembershipTransactionsDAO;
	}
	
	/**
	 * Hiding constructor for singleton.
	 */
	private CashBackStatementManager()  {
		super();
	}
	
	public static CashBackStatementManager getInstance() {
		return instance;
	}
	
	/**
	 * Override clone to prevent cloning.
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	} 

	/**
	 * Generates the cashback pdf for the given customer and program.
	 * @param customerId
	 * @param programId
	 * @param mbrshpAmtTypCd
	 * @return
	 * @throws QueryException
	 * @throws ProRecognitionException
	 * @throws ClassNotFoundException
	 * @throws TemplateException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws DocumentException
	 */
	public byte[] generateCashBackStatement(final int customerId, final int programId, final short mbrshpAmtTypCd, boolean isSimulateLoadTest) throws 
		QueryException, ProRecognitionException, ClassNotFoundException, TemplateException, IOException, 
			ParserConfigurationException, SAXException, DocumentException {
		CashBackStatementDTO cashbackStatementDTO = this.readCashbackStatementData(customerId, programId, mbrshpAmtTypCd, isSimulateLoadTest);
		return CashBackStatementContentCreator.createCashBackStatement(cashbackStatementDTO);
	}
	
	
	/**
	 * retrieves the membership summary,level and the transaction list for the specified input parameters
	 * @param customerId
	 * @param programId
	 * @param mbrshpAmtTypCd
	 * @return
	 * @throws QueryException
	 * @throws ProRecognitionException
	 */
	private CashBackStatementDTO readCashbackStatementData(final int customerId, final int programId, final short mbrshpAmtTypCd, boolean isSimulateLoadTest) 
			throws QueryException, ProRecognitionException {
		//Read the quarterly membership summary.
		MembershipAmountDTO quarterSummary = iMembershipAmountDAO.getQuarterlyMembershipSummary(customerId, programId, mbrshpAmtTypCd, String.valueOf(RAPUtil.getCurrentYear()));
		//Determine the last day of the quarter - since the membership reset batch runs at midnight everyday, the membership level will not change until 12:00 AM the following day. 
		//So the last day of the quarter should be the end date from the MBR_SHP_AMT_SUM table plus 1. 
		Date lastDayOfQtr = RAPUtil.addDaysToDate(quarterSummary.getEndDate(), 1);
		//Read the membership level for the customer at the end of the quarter.  
		MembershipLevelDTO customerLevel = iMembershipLevelDAO.getMembershipLevel(customerId, programId, lastDayOfQtr);
		//Read the customer transactions for the cashback period.
		List<ReadCustomerTransactionsDTO> transactionList = iMembershipTransactionsDAO.getMembershipTransactions(
				customerId, programId, 
				RAPUtil.getSQLDateinYYYYMMDDFromString(quarterSummary.getBeginDate()), 
				RAPUtil.getSQLDateinYYYYMMDDFromString(quarterSummary.getEndDate()));
		if(isSimulateLoadTest && transactionList.size() < 20000) {
			while(transactionList.size() < 20000) {
				transactionList.addAll(transactionList);
			}
		}
		return new CashBackStatementDTO(quarterSummary, customerLevel, transactionList);
	}
	
	

}
