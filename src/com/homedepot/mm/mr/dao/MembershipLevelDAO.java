package com.homedepot.mm.mr.dao;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.dao.interfaces.IMembershipLevelDAO;
import com.homedepot.mm.mr.dto.MembershipLevelDTO;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public final class MembershipLevelDAO implements IMembershipLevelDAO {

	private static final Logger LOGGER = Logger.getLogger(MembershipLevelDAO.class);
	
	/*
	 * Query to get the membership level of a customer on a given date.
	 * 
	 * For e.g. consider the following entries in MBRSHP_LVL (listed in increasing order of begin timestamp).
	 *  
	 * 	BEGIN DATE			ENDDATE			LEVEL
	 *	----------			-------		    -----
	 *  01/01/2015			03/15/2015		Member
	 *  03/15/2015			04/30/2015		Silver
	 *  05/01/2015			10/12/2015		Gold
	 *  10/12/2015			NULL			Platinum
	 *  
	 *  For this data set, depending on the date provided as input, the query result will be as follows:
	 *  
	 *  INPUT DATE   		RESULT
	 *  --------------		-------
	 *  03/01/2015			Member
	 *  03/15/2015			Silver
	 *  03/30/2015			Silver
	 *  06/31/2015			Gold
	 *  09/30/2015			Gold
	 *  10/12/2015			Platinum
	 *  12/31/2015			Platinum
	 *  05/01/2016			Platinum
	 * 
	 */
	private static final String SELECT_CUST_MBRSHP_LVL_FOR_DATE = 
			"SELECT ML.PGM_LVL_TYP_CD, PR.MAX_SPEND_REQD_AMT, PR.DISC_PCT, NPLT.TYP_DESC "
			+ "FROM   MBRSHP_LVL ML "
			+ "JOIN N_PGM_LVL_TYP NPLT ON NPLT.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD AND NPLT.LANG_CD = 'en_US' "
			+ "JOIN PGM_RUL PR ON PR.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD AND PR.CMDTY_RUL_IND='Y' "
			+ "WHERE ML.CUST_ID = ? "
			+ "AND ML.CPGM_ID = ? "
			+ "AND ("
			+ "   (? BETWEEN DATE(ML.MBRSHP_LVL_BGN_DT) AND ML.MBRSHP_LVL_END_DT) " //filter records where the requested date is between the begin and end dates on the MBRSHP_LVL table.
			           																//i.e. 
			+ "   OR (? >= DATE(ML.MBRSHP_LVL_BGN_DT) AND ML.MBRSHP_LVL_END_DT IS NULL) " // filter records where the membership level begin date is prior to the requested date and the end date is null. 
																						  // i.e. the customer's tier has changed before the requested date and has not changed since then (end time is null).  
			+ " ) "
			//If the customer's tier changed on the requested date, both the conditions listed above will be met and the query will return 2 records. This is addressed by the statements below.
			+ "ORDER BY ML.MBRSHP_LVL_BGN_DT DESC " //This will order the results so that the most recent level will be returned first. 
			+ "FETCH FIRST 1 ROWS ONLY  " //This will filter the results so that only the most recent level is returned.
			+ "WITH UR;";
	
	public MembershipLevelDAO() {
		super();
	}
	
	/**
	 * Returns the membership level information (code, max spend required, discount percent, level name) for the given customer, program and date.
	 * @param customerId
	 * @param programId
	 * @param date
	 * @return MembershipLevelDTO
	 * @throws QueryException
	 */
	public MembershipLevelDTO getMembershipLevel(final int customerId, final int programId, final Date date) 
			throws QueryException {
		AtomicInteger index = new AtomicInteger(1);
		//Set query params and populate results 
		return DAO.useJNDI(com.homedepot.mm.mr.utility.Constants.PR4_JNDI_NM)
			.setSQL(SELECT_CUST_MBRSHP_LVL_FOR_DATE)
			.input(index.getAndIncrement(), customerId)
			.input(index.getAndIncrement(), programId)
			.input(index.getAndIncrement(), date)
			.input(index.get(), date)
			.debug(LOGGER)
			.displayAs("getMembershipLevel")
			.get(MembershipLevelDTO.class);
	}
	
	

}
