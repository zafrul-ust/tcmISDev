package com.tcmis.ws.scanner.process;

import java.math.BigDecimal;
import java.util.Vector;

import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.CabinetInventoryCountStageBean;
import com.tcmis.internal.hub.beans.CabinetItemInventoryCountBean;
import com.tcmis.internal.hub.beans.WorkAreaBinCountBean;
import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.ws.scanner.beans.ScanDataList;

public class CabinetCountWorkerProcess implements Runnable {
	ScanDataList	beans;
	BigDecimal		personnelId;
	BigDecimal		uploadSeq;
	String			client	= null;
	String			url		= null;

	public CabinetCountWorkerProcess(ScanDataList beans, BigDecimal personnelId, BigDecimal nextupLoadSeq, String client, String url) {
		this.beans = beans;
		this.personnelId = personnelId;
		this.uploadSeq = nextupLoadSeq;
		this.client = client;
		this.url = url;
	}

	public void run() {
		try {
			Thread.currentThread().sleep(1000);
			CabinetCountProcess countProcess = new CabinetCountProcess(client);
			Vector<WorkAreaBinCountBean> workAreaBinCountBeans = new Vector<WorkAreaBinCountBean>();
			Vector<CabinetInventoryCountStageBean> countByKBeans = new Vector<CabinetInventoryCountStageBean>();
			Vector<CabinetItemInventoryCountBean> countByItemBeans = new Vector<CabinetItemInventoryCountBean>();
			Vector<CabinetInventoryCountStageBean> countByReceiptBeans = new Vector<CabinetInventoryCountStageBean>();
			Vector<String> binIdsScanned = new Vector<String>();
			BigDecimal zero = new BigDecimal("0");
			for (ScanDataBean scannedBean : beans.getScanData()) {
				BigDecimal binId = scannedBean.getBinId();
				if (binId == null) {
					binId = zero;
					scannedBean.setBinId(zero);
				}
				if (!binIdsScanned.contains(binId + "")) {
					binIdsScanned.add(binId + "");
				}

				if (scannedBean.isCountByContainer() || scannedBean.isCountByReceipt() || scannedBean.isCountByAdvancedReceipt()) {
					workAreaBinCountBeans.add(new WorkAreaBinCountBean(scannedBean, uploadSeq));
				}
				else if (scannedBean.isCountByKanban()) {
					countByKBeans.add(new CabinetInventoryCountStageBean(scannedBean, uploadSeq));
				}
				else {
					countByItemBeans.add(new CabinetItemInventoryCountBean(scannedBean, uploadSeq));
				}
			}
			countProcess.process(binIdsScanned, countByReceiptBeans, countByItemBeans, countByKBeans, personnelId, url, uploadSeq, workAreaBinCountBeans);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		;
	}

}
