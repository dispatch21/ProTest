package com.homedepot.mm.mr.dao.interfaces;

import java.sql.Date;

import com.homedepot.mm.mr.dto.MembershipLevelDTO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public interface IMembershipLevelDAO {
	public MembershipLevelDTO getMembershipLevel(final int customerId, final int programId, final Date date) 
			throws QueryException;
}
