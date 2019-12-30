package com.tcmis.client.het.process;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

import com.tcmis.client.het.beans.HetStatus;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;

public class StatusProcess extends GenericProcess {

	public StatusProcess(String client) {
		this(client,defaultLocale);
	}

	public StatusProcess(String client, Locale locale) {
		super(client, locale, new HetStatus());
	}

	public void initiateSynch() throws BaseException {
		factory.doProcedure("pkg_het_api.p_reset_replication", Collections.EMPTY_LIST);
	}

	@SuppressWarnings("unchecked")
	public Collection<HetStatus> getStatusResults() throws BaseException {
		return factory.selectQuery("select * from HET_STATUS where CAPTURE_TIME >= sysdate - 2 order by CAPTURE_TIME desc");
	}
}
