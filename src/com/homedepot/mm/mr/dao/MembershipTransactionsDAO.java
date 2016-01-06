/**
 * 
 */
package com.homedepot.mm.mr.dao;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.dao.interfaces.IMembershipTransactionsDAO;
import com.homedepot.mm.mr.dto.ReadCustomerTransactionsDTO;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

/**
 * @author gxk8688
 *
 */
public final class MembershipTransactionsDAO implements IMembershipTransactionsDAO {
	
	private static final Logger LOGGER = Logger.getLogger(MembershipTransactionsDAO.class);

	//Query to retrieve the transactions for the given customer, program and date range
	private static final String SELECT_MBRSHP_TRANSACTIONS = 
			"SELECT T.SLS_DT, "+  
		       "T.RGSTR_NBR, "+
		       "T.POS_TRANS_ID, "+ 
		       "T.STR_NBR, "+ 
		       "T.RECPT_ADD_DT, "+ 
		       "T.PBC_JOB_NM, "+ 
		       "T.TRANS_TOT_MER_AMT, "+ 
		       "T.GRS_SLS_AMT, "+  
		       "T.EXTNL_ID_VAL, "+ 
		       "T.EXTNL_ID_TYP_CD, "+
		       "F.PAYMT_CARD_TYP_CD, "+
		       "BU.STR_NM,  "+
		       "D.EXTNL_ID_ACCT_NM  "+
		"FROM  CUST_TRANS T "+
		   "JOIN MBRSHP_TRANS M ON T.SLS_DT = M.SLS_DT AND T.STR_NBR = M.STR_NBR AND T.RGSTR_NBR = M.RGSTR_NBR AND T.POS_TRANS_ID = M.POS_TRANS_ID AND T.CUST_ID=M.CUST_ID "+ 
		   "JOIN BU_MER_HIER BU ON BU.STR_NBR = T.STR_NBR  "+
		   "JOIN RGSTR_EXTNL_ID D ON T.EXTNL_ID_VAL = D.EXTNL_ID_VAL AND T.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD AND T.CUST_ID = D.CUST_ID "+
		   "JOIN CRWRD_EXTNL_ID F ON F.EXTNL_ID_VAL = D.EXTNL_ID_VAL AND F.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD   "+
		"WHERE T.CUST_ID = ? "+
		  "AND T.RECPT_ADD_DT BETWEEN ? AND ? "+
		  "AND M.CPGM_ID= ? "+
		"WITH UR ";
	
	/**
	 * 
	 */
	public MembershipTransactionsDAO() {
		super();
	}
	
	/**
	 * Retrieve customer transactions that qualify for the specified program during the specified date range.
	 * @param custId
	 * @param programId
	 * @param startDate
	 * @param endDate
	 * @return List<ReadCustomerTransactionsDTO>
	 * @throws QueryException
	 */
	public List<ReadCustomerTransactionsDTO> getMembershipTransactions(final int customerId, final int programId, final Date startDate, final Date endDate) 
			throws QueryException {
		AtomicInteger index = new AtomicInteger(1);
		//Set query params and populate results 
		return DAO.useJNDI(com.homedepot.mm.mr.utility.Constants.PR4_JNDI_NM)
			.setSQL(SELECT_MBRSHP_TRANSACTIONS)
			.input(index.getAndIncrement(), customerId)
			.input(index.getAndIncrement(), startDate)
			.input(index.getAndIncrement(), endDate)
			.input(index.get(), programId)
			.debug(LOGGER)
			.displayAs("getMembershipTransactions")
			.list(ReadCustomerTransactionsDTO.class);
	}


}
