package com.homedepot.mm.mr.dao.interfaces;

import java.sql.Date;
import java.util.List;

import com.homedepot.mm.mr.dto.ReadCustomerTransactionsDTO;
import com.homedepot.ta.aa.dao.exceptions.QueryException;

public interface IMembershipTransactionsDAO {
	public List<ReadCustomerTransactionsDTO> getMembershipTransactions(final int customerId, final int programId, final Date startDate, final Date endDate) 
			throws QueryException;
}
