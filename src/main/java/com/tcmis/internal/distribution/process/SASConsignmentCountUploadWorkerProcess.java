package com.tcmis.internal.distribution.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.internal.hub.process.*;
import com.tcmis.ws.scanner.beans.ScanDataList;


public class SASConsignmentCountUploadWorkerProcess extends GenericProcess implements Runnable {
	ScanDataList beans;
	BigDecimal personnelId;
	BigDecimal nextupLoadSeq;
	String client = null;
	String url = null;
	public SASConsignmentCountUploadWorkerProcess(String client,BigDecimal nextupLoadSeq, BigDecimal personnelId){
		super(client);
		this.personnelId = personnelId;
		this.nextupLoadSeq = nextupLoadSeq;
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.currentThread().sleep(10000);
			Collection coll = factory.doProcedure("PKG_SAS_USAGE_PROCESSING.P_LOAD_SAS_STAGE_TABLE", 
					buildProcedureInput(nextupLoadSeq,personnelId),new Vector());
			
		}catch(Exception ex){};
	}

}
