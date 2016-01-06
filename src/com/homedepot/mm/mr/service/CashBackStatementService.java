package com.homedepot.mm.mr.service;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.homedepot.mm.mr.bl.CashBackStatementManager;
import com.homedepot.mm.mr.dao.MembershipAmountDAO;
import com.homedepot.mm.mr.dao.MembershipLevelDAO;
import com.homedepot.mm.mr.dao.MembershipTransactionsDAO;
import com.homedepot.mm.mr.utility.Constants;
import com.homedepot.ta.aa.log4j.OrangeLogHelper;



@Path("/cashback")
public class CashBackStatementService {

	private static final Logger LOGGER = Logger.getLogger(OrangeLogHelper.getLoggerNameUsingTomcatClassLoaderAndTheStack());

	@GET
	@Path("/getQuarterlyStatement")
	@Produces("application/pdf")
	public Response GetQuarterlyStatement(@QueryParam("custId") int custId,
			@QueryParam("cpgmId") int programId,
			@QueryParam("mbrshpAmtTypCd") short mbrshpAmtTypCd,
			@DefaultValue("false") @QueryParam("simulateLoad") boolean isSimulateLoadTest, 
			@DefaultValue("1") @HeaderParam("version") int version ) {
		Status status = Response.Status.OK;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Begin: CashBackStatementService - getQuarterlyStatement");
		}
		// Capture the time that the message is received
		long requestReceiveTime = System.currentTimeMillis();
		byte[] pdfData = null;
		try {
			switch (version) {
				case 1: {
					if (Constants.ZERO_INT != custId && Constants.ZERO_INT != programId && Constants.ZERO_INT != mbrshpAmtTypCd) {
						if (LOGGER.isDebugEnabled()) {
							LOGGER.debug("CashBackStatementService custId: " + custId);
							LOGGER.debug("CashBackStatementService programId: " + programId);
							LOGGER.debug("CashBackStatementService mbrshpAmtTypCd: \n"+ mbrshpAmtTypCd);
						}
						CashBackStatementManager.getInstance().setMembershipAmount(new MembershipAmountDAO());
						CashBackStatementManager.getInstance().setMembershipLevel(new MembershipLevelDAO());
						CashBackStatementManager.getInstance().setMembershipTransactions(new MembershipTransactionsDAO());
						pdfData = CashBackStatementManager.getInstance().generateCashBackStatement(custId, programId, mbrshpAmtTypCd, isSimulateLoadTest);
					} else {
						status = Response.Status.BAD_REQUEST;
					}
				}
			}
		} catch (Exception ex) {
			status = Response.Status.INTERNAL_SERVER_ERROR;
			LOGGER.error("Fatal Error", ex);
		}
		long requestCompleteTime = System.currentTimeMillis();
		long processingTime = requestCompleteTime - requestReceiveTime;
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("CashBackStatementService requestCompleteTime: " + requestCompleteTime);
			LOGGER.debug("CashBackStatementService processingTime: " + processingTime);
			LOGGER.debug("End: CashBackStatementService - getQuarterlyStatement");

		}
		return Response.status(status).entity(pdfData).build();
	}

}
