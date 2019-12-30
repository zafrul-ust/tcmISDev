package com.tcmis.internal.hub.action;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.process.ReceivingQcCheckListProcess;

public class LogisticsQVRThread extends Thread{
		protected Log log	= LogFactory.getLog(this.getClass());
		ReceivingQcCheckListProcess receivingQcCheckListProcess = null;
		ReceivingInputBean receivingInputBean = null;
		ReceiptDescriptionViewBean viewBean = null;
		PersonnelBean personnelBean = null;

		public LogisticsQVRThread(ReceivingQcCheckListProcess receivingQcCheckListProcess,ReceivingInputBean receivingInputBean,PersonnelBean personnelBean) {
			this.receivingQcCheckListProcess = receivingQcCheckListProcess;
			this.receivingInputBean = receivingInputBean;
			this.personnelBean = personnelBean;
	    }

	    public void run() {
			try {
				log.debug("In printChecklist thread");		
				Vector <ReceiptDescriptionViewBean> results = (Vector <ReceiptDescriptionViewBean>)receivingQcCheckListProcess.getHeaderInfo(receivingInputBean.getSearch());
				ReceiptDescriptionViewBean viewBean = results.firstElement();
				receivingQcCheckListProcess.printChecklist(receivingInputBean, viewBean, personnelBean);
			}
			catch (Exception ex3) {
				log.debug("Error Calling printChecklist for Receipt ID:" + viewBean.getReceiptId() + "");
			}
	    }
	}
