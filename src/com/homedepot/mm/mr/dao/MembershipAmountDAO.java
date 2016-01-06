/**
 * 
 */
package com.homedepot.mm.mr.dao;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.dao.interfaces.IMembershipAmountDAO;
import com.homedepot.mm.mr.dto.MembershipAmountDTO;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

/**
 * @author gxk8688
 *
 */
public final class MembershipAmountDAO implements IMembershipAmountDAO {
	
	private static final Logger LOGGER = Logger.getLogger(MembershipAmountDAO.class);

	//Query to retrieve the accrued spend, cash back and the begin and end dates for a given quarter
	private static final String SELECT_QTRLY_MBRSHP_SUMMARY = 
			"SELECT  CB.MBRSHP_AMT AS QUARTERLY_CASHBACK, "+
			        "AMTSPND.MBRSHP_AMT AS QUARTERLY_SPEND, "+
			        "CB.TM_PRD_BGN_DT, "+
			        "CB.TM_PRD_END_DT "+
			"FROM  MBRSHP_AMT_SUM CB  "+ 
			  "JOIN MBRSHP_AMT_SUM AMTSPND ON AMTSPND.CUST_ID = CB.CUST_ID AND AMTSPND.CPGM_ID = CB.CPGM_ID "+ //Inner join on the same table to get the accrued spend  
			"WHERE  CB.CUST_ID = ? "+
			       "AND CB.CPGM_ID = ? "+
			       "AND CB.MBRSHP_AMT_TYP_CD = ? "+ // Supply the amount type code that corresponds to accrued cash-back for the quarter
			       "AND CB.TM_PRD_BGN_DT = AMTSPND.TM_PRD_BGN_DT "+ //These date conditions will restrict the results to the same quarter thats denoted by the membership amount type specified above. 
			       "AND CB.TM_PRD_END_DT = AMTSPND.TM_PRD_END_DT "+
			       "AND AMTSPND.MBRSHP_AMT_TYP_CD <> ? "+ // Supply the sane type code again to filter our accrued cash-back. 
			       "AND CB.TM_PRD_YR = ? "+
			"WITH UR";	
	
	/**
	 * 
	 */
	public MembershipAmountDAO() {
		super();
	}
	
	/**
	 * Returns the quarterly accrued spend and cash back for the given inputs.
	 * @param custId
	 * @param programId
	 * @param mbrshpAmtTypCd
	 * @param year
	 * @return MembershipAmountDTO
	 * @throws QueryException
	 */
	public MembershipAmountDTO getQuarterlyMembershipSummary(final int custId, final int programId, int mbrshpAmtTypCd, final String year) 
			throws QueryException {
		AtomicInteger index = new AtomicInteger(1);
		//Set query params and populate results 
		return DAO.useJNDI(Constants.PR4_JNDI_NM)
			.setSQL(SELECT_QTRLY_MBRSHP_SUMMARY)
			.input(index.getAndIncrement(), custId)
			.input(index.getAndIncrement(), programId)
			.input(index.getAndIncrement(), mbrshpAmtTypCd)
			.input(index.getAndIncrement(), mbrshpAmtTypCd)
			.input(index.get(),  year)
			.debug(LOGGER)
			.displayAs("getQuarterlyMembershipAmountSummary")
			.get(MembershipAmountDTO.class);
	}


}
