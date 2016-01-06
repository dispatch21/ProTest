package com.homedepot.mm.mr.dao.interfaces;

import com.homedepot.mm.mr.dto.MembershipAmountDTO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public interface IMembershipAmountDAO {
	
	public MembershipAmountDTO getQuarterlyMembershipSummary(final int custId, final int programId, int mbrshpAmtTypCd, final String year) 
			throws QueryException;

}
