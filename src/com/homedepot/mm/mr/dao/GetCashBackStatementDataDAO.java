package com.homedepot.mm.mr.dao;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.dto.ReadCustomerTransactionsDTO;
import com.homedepot.mm.mr.dto.ReadSsysParmValuesDTO;
import com.homedepot.mm.mr.recognition.utility.RAPUtil;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.ta.aa.dao.builder.DAO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;
import com.homedepot.ta.aa.log4j.OrangeLogHelper;

/**
 * File Name: GetCashBackStatementDataDAO.java
 * 
 * Description: This class contains the methods for the retrieving quarterly cash back statement data 
 * for a single customer from a given quarter.
 * 
 * @author Mark Marlow
 */

public final class GetCashBackStatementDataDAO
{
	private GetCashBackStatementDataDAO()
	{
		
	}
	private static final Logger LOGGER = Logger.getLogger(OrangeLogHelper.getLoggerNameUsingTomcatClassLoaderAndTheStack());
	
	/**
	 * getSsysParmValues 
	 */
	public static final String getSsysParmValuesData = 
						" SELECT "
						+ "SSYS_PARM_NM, "
						+ "SSYS_PARM_CHAR_VAL "
						+ "FROM SSYS_PARM "
						+ "WHERE SUB_SYS_CD = ? "
						+ "AND SSYS_PARM_NM IN ( ? )";


	/**
	 * 	getQuarterlyCashbackStatementData	
	 */
	public static final String getQuarterlyCashbackStatementData = 
			" SELECT DISTINCT "
					+ "AMTSPND.MBRSHP_AMT AS QUARTERLY_SPEND, "
					+ "CB.MBRSHP_AMT AS QUARTERLY_CASHBACK, "
					+ "ML.PGM_LVL_TYP_CD	, "
					+ "NPLT.TYP_DESC,  "
					+ "PR.MAX_SPEND_REQD_AMT, "
					+ "PR.DISC_PCT  AS QUARTERLY_CASHBACK_PCT, "
					+ "C.SLS_DT AS SALES_DATE, "
					+ "C.RECPT_ADD_DT AS ADDED_DATE, "
					+ "C.PBC_JOB_NM AS JOB_NAME, "
					+ "C.POS_TRANS_ID AS RECEIPT_NUMBER, "
					+ "C.RGSTR_NBR, "
					+ "C.TRANS_TOT_MER_AMT AS PRE_TAX_AMOUNT, "
					+ "C.GRS_SLS_AMT AS TOTAL_AMOUNT, "
					+ "C.STR_NBR AS STORE_NUMBER, "
					+ "COALESCE(BU.STR_NM,'') AS STR_NM, "
					+ "C.EXTNL_ID_VAL AS ACCOUNT_LAST_4, "
					+ "C.EXTNL_ID_TYP_CD, "
					+ "D.EXTNL_ID_ACCT_NM AS ACCOUNT_NAME, "
					+ "E.CUST_ORD_NBR AS CUST_ORD_NBR,    "
					+ "F.PAYMT_CARD_TYP_CD "
			+ "FROM 	"
				+ "MBRSHP_AMT_SUM CB  "
				+ "JOIN MBRSHP_AMT_SUM AMTSPND ON AMTSPND.CUST_ID=CB.CUST_ID "
				+ "AND AMTSPND.CPGM_ID = CB.CPGM_ID "
				+ "AND CB.D_CAL_PRD_TYP_CD ='Quarterly' "
				+ "AND CB.TM_PRD_BGN_DT = AMTSPND.TM_PRD_BGN_DT "
				+ "AND CB.TM_PRD_END_DT = AMTSPND.TM_PRD_END_DT "
				+ "AND CB.MBRSHP_AMT_TYP_CD <> AMTSPND.MBRSHP_AMT_TYP_CD "
				+ "JOIN MBRSHP_LVL ML ON ML.CPGM_ID = CB.CPGM_ID AND ML.CUST_ID = CB.CUST_ID	"
				+ "AND ML.MBRSHP_LVL_END_DT IS NULL   "
				+ "JOIN N_PGM_LVL_TYP NPLT ON NPLT.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD "
				+ "JOIN PGM_RUL PR ON PR.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD "
				+ "JOIN CUST_TRANS C ON C.CUST_ID=AMTSPND.CUST_ID "
					+ "AND C.SLS_DT >= CB.TM_PRD_BGN_DT "
					+ "AND C.SLS_DT<=AMTSPND.TM_PRD_END_DT  "
				+ "LEFT JOIN BU_MER_HIER BU ON BU.STR_NBR = C.STR_NBR	"
				+ "JOIN RGSTR_EXTNL_ID D ON C.EXTNL_ID_VAL = D.EXTNL_ID_VAL 	"
					+ "AND C.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD 	 "
					+ "AND C.CUST_ID = D.CUST_ID  "
				+ "LEFT JOIN CUST_TRANS_DTL E ON E.SLS_DT=C.SLS_DT "
					+ "AND E.STR_NBR=C.STR_NBR "
					+ "AND E.RGSTR_NBR=C.RGSTR_NBR "
					+ "AND E.POS_TRANS_ID=C.POS_TRANS_ID  "
				+ "LEFT JOIN CRWRD_EXTNL_ID F ON F.EXTNL_ID_VAL = D.EXTNL_ID_VAL "
				 	+ "AND F.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD "
			+ "WHERE CB.CUST_ID=? AND CB.MBRSHP_AMT_TYP_CD=? AND CB.TM_PRD_YR=? ";

	
	/**
	 * @param custId
	 * @param mbrshpAmtTypCd
	 * @param isCurrentYear
	 * @return List<ReadCustomerTransactionsDTO>
	 * @throws ProRecognitionException
	 * =============================================
	 * DAO Selector: getQuarterlyCashbackStatementData
	 * DAO Query:

            SELECT DISTINCT 
                AMTSPND.MBRSHP_AMT AS QUARTERLY_SPEND, 
                CB.MBRSHP_AMT AS QUARTERLY_CASHBACK, 
                 ML.PGM_LVL_TYP_CD , 
                 NPLT.TYP_DESC,  
                 PR.MAX_SPEND_REQD_AMT, 
                 PR.DISC_PCT  AS QUARTERLY_CASHBACK_PCT, 
                 C.SLS_DT AS SALES_DATE, 
                 C.RECPT_ADD_DT AS ADDED_DATE, 
                 C.PBC_JOB_NM AS JOB_NAME, 
                 C.POS_TRANS_ID AS RECEIPT_NUMBER, 
                 C.RGSTR_NBR, 
                 C.TRANS_TOT_MER_AMT AS PRE_TAX_AMOUNT, 
                 C.GRS_SLS_AMT AS TOTAL_AMOUNT, 
                 C.STR_NBR AS STORE_NUMBER, 
                 COALESCE(BU.STR_NM,'') AS STR_NM, 
                 C.EXTNL_ID_VAL AS ACCOUNT_LAST_4, 
                 C.EXTNL_ID_TYP_CD, 
                 D.EXTNL_ID_ACCT_NM AS ACCOUNT_NAME, 
                 E.CUST_ORD_NBR AS CUST_ORD_NBR,    
                 F.PAYMT_CARD_TYP_CD 
             FROM 
                 PRHDB.MBRSHP_AMT_SUM CB  
                 JOIN PRHDB.MBRSHP_AMT_SUM AMTSPND ON AMTSPND.CUST_ID=CB.CUST_ID 
                 AND AMTSPND.CPGM_ID = CB.CPGM_ID 
                 AND CB.D_CAL_PRD_TYP_CD ='Quarterly' 
                 AND CB.TM_PRD_BGN_DT = AMTSPND.TM_PRD_BGN_DT 
                 AND CB.TM_PRD_END_DT = AMTSPND.TM_PRD_END_DT 
                 AND CB.MBRSHP_AMT_TYP_CD <> AMTSPND.MBRSHP_AMT_TYP_CD 
                 JOIN PRHDB.MBRSHP_LVL ML ON ML.CPGM_ID = CB.CPGM_ID AND ML.CUST_ID = CB.CUST_ID 
            	  AND ML.MBRSHP_LVL_END_DT IS NULL       
                JOIN PRHDB.N_PGM_LVL_TYP NPLT ON NPLT.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD AND NPLT.LANG_CD='EN_US'
                 JOIN PRHDB.PGM_RUL PR ON PR.PGM_LVL_TYP_CD = ML.PGM_LVL_TYP_CD 
                 JOIN PRHDB.CUST_TRANS C ON C.CUST_ID=AMTSPND.CUST_ID 
                                 AND C.SLS_DT >= CB.TM_PRD_BGN_DT 
                                 AND C.SLS_DT<=AMTSPND.TM_PRD_END_DT  
                 LEFT JOIN PRHDB.BU_MER_HIER BU ON BU.STR_NBR = C.STR_NBR       
                JOIN PRHDB.RGSTR_EXTNL_ID D ON C.EXTNL_ID_VAL = D.EXTNL_ID_VAL          
                                 AND C.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD             
                                 AND C.CUST_ID = D.CUST_ID  
                 LEFT JOIN PRHDB.CUST_TRANS_DTL E ON E.SLS_DT=C.SLS_DT 
                                 AND E.STR_NBR=C.STR_NBR 
                                 AND E.RGSTR_NBR=C.RGSTR_NBR 
                                 AND E.POS_TRANS_ID=C.POS_TRANS_ID  
                 LEFT JOIN PRHDB.CRWRD_EXTNL_ID F ON F.EXTNL_ID_VAL = D.EXTNL_ID_VAL 
                                 AND F.EXTNL_ID_TYP_CD = D.EXTNL_ID_TYP_CD 
             WHERE CB.CUST_ID=1803246 AND CB.MBRSHP_AMT_TYP_CD=115 AND CB.TM_PRD_YR=2015 ;

						 
	 * =============================================		 	 
	 */
	
	/**
	 * getSsysParmValuesData
	 * @param custId
	 * @param mbrshpAmtTypCd
	 * @param isCurrentYear
	 * @return
	 * @throws QueryException
	 */
	public static List <ReadCustomerTransactionsDTO> getQuarterlyCashbackStatementData( int custId , int mbrshpAmtTypCd, final boolean isCurrentYear ) throws QueryException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Entering the method getQuarterlyCashbackStatementData");
		}
		String year = "";
		if (isCurrentYear){
			year = String.valueOf( RAPUtil.getCurrentYear());		
		} else {
			year = String.valueOf( RAPUtil.getCurrentYear() - 1);
		}
			AtomicInteger index = new AtomicInteger(1);
			List<ReadCustomerTransactionsDTO> cashbackStatementData = DAO.useJNDI(Constants.PR4_JNDI_NM)
													.setSQL(getQuarterlyCashbackStatementData)
													.input(index.getAndIncrement(), custId)
													.input(index.getAndIncrement(), mbrshpAmtTypCd)
													.input(index.get(),  year)
													.debug(LOGGER)
													.displayAs("getQuarterlyMembershipAmountSummary")
													.list(ReadCustomerTransactionsDTO.class);
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Leaving the method getQuarterlyCashbackStatementData");
		}
		return cashbackStatementData;
	}

	
	/**
	 * getSsysParmValuesData
	 * @param subSysCd
	 * @param periodDates
	 * @return
	 * @throws QueryException
	 */
	public static List<ReadSsysParmValuesDTO> getSsysParmValuesData( String subSysCd , String periodDates ) 
				throws QueryException
	{
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Entering the method getSsysParmValuesData");
		}

			AtomicInteger index = new AtomicInteger(1);
			List<ReadSsysParmValuesDTO> ssysParmValuesDTO = DAO.useJNDI(Constants.PR4_JNDI_NM)
													.setSQL(getSsysParmValuesData)
													.input(index.getAndIncrement(), subSysCd)
													.input(index.getAndIncrement(), periodDates)
													.debug(LOGGER)
													.displayAs("getSsysParmValuesData")
													.list(ReadSsysParmValuesDTO.class);
		if (LOGGER.isDebugEnabled())
		{
			LOGGER.debug("Leaving the method getSsysParmValuesData");
		}
		return ssysParmValuesDTO;
	}
}
