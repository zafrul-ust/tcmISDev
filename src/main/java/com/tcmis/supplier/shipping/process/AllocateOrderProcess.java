package com.tcmis.supplier.shipping.process;

import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.supplier.shipping.beans.AllocateOrderInputBean;
import com.tcmis.supplier.shipping.beans.SupplierInventoryBean;

import radian.tcmis.common.util.SqlHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;

public class AllocateOrderProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;
	protected ResourceLibrary commonResourcesLibrary = null;

	public AllocateOrderProcess(String client, String locale) {
		super(client, locale);
		init();
	}

	private void init() {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager);
			commonResourcesLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
		} catch (Exception ex) {
			log.error("Error initializing AllocateOrder", ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<SupplierInventoryBean> getSearchResult(AllocateOrderInputBean inputBean) throws Exception {
		StringBuilder query = new StringBuilder("select * from table(supplier.pkg_transaction_mgmt.fx_inventory_to_pick(");
		query.append(SqlHandler.delimitString(inputBean.getSupplier()));
		query.append(", ").append(SqlHandler.delimitString(inputBean.getShipFromLocationId()));
		query.append(", ").append(SqlHandler.delimitString(inputBean.getCatPartNo()));
		query.append(", ").append(inputBean.getQtyToPick());
		query.append(", ").append(SqlHandler.delimitString(inputBean.getVmi())).append("))");
		
		return factory.setBean(new SupplierInventoryBean()).selectQuery(query.toString());
	}
	
	public String getInventoryLevelId(String supplier, String shipFromLocationId, String catPartNo) throws Exception {
		return factory.getFunctionValue("supplier.pkg_transaction_mgmt.fx_inventory_level_id ", supplier, shipFromLocationId, catPartNo);
	}
	
	public BigDecimal getBatchId() throws BaseException {
		return factory.getSequence("print_batch_seq");
	}
	
	/**
	 * Call confirm procedure on each record of resultBeanColl. As long as a record is confirmed successfully,
	 * the procedure as a whole is considered successful.
	 * @param inputBean
	 * @param resultBeanColl
	 * @param personnelId
	 * @param batchId
	 * @return
	 * @throws BaseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String confirmAllocation(AllocateOrderInputBean inputBean, Collection<SupplierInventoryBean> resultBeanColl, BigDecimal personnelId, BigDecimal batchId)  throws BaseException {
		BigDecimal totalQtySent = new BigDecimal("0");
		String finalMsg = commonResourcesLibrary.getString("generic.error");

		Vector inArgs;
		Vector outArgs;
		Vector inArgs2;
		Connection connection = null;
		try {
			connection = dbManager.getConnection();
			BigDecimal packingGroupId = factory.getSequence("packing_group_seq", connection);
			for (SupplierInventoryBean resultBean : resultBeanColl) {
				inArgs = new Vector();
				Timestamp curTime = new Timestamp(new Date().getTime());
				inArgs.add(inputBean.getRadianPo() == null ? "" : inputBean.getRadianPo());
				inArgs.add(inputBean.getPoLine() == null ? "" : inputBean.getPoLine());
				inArgs.add(inputBean.getItemId() == null ? "" : inputBean.getItemId());
				inArgs.add(StringHandler.emptyIfNull(resultBean.getMfgLot()));
				inArgs.add("Supplier Shipping"); //bin
				inArgs.add(curTime);
				inArgs.add(resultBean.getDateOfManufacture() == null ? "" : new Timestamp(resultBean.getDateOfManufacture().getTime()));
				inArgs.add(inputBean.getVendorShipDate() == null ? "" : new Timestamp(inputBean.getVendorShipDate().getTime()));
				inArgs.add(resultBean.getPickedQty());
				inArgs.add("Available"); // lot status
				inArgs.add(inputBean.getReceiptDate() == null ? curTime : new Timestamp(inputBean.getReceiptDate().getTime()));
				inArgs.add(StringHandler.emptyIfNull(inputBean.getBranchPlant()));
				inArgs.add(personnelId.toString());
				inArgs.add(resultBean.getExpireDate() == null ? "" : new Timestamp(resultBean.getExpireDate().getTime()));
				inArgs.add("");
				inArgs.add(inputBean.getVendorShipDate() == null ? curTime : new Timestamp(inputBean.getVendorShipDate().getTime()));
				inArgs.add(inputBean.getMrNumber() == null ? "" : inputBean.getMrNumber());
				inArgs.add(StringHandler.emptyIfNull(inputBean.getMrLineItem()));
				inArgs.add(batchId);
				inArgs.add("");
		
				outArgs = new Vector();
				outArgs.add( new Integer(Types.NUMERIC) );
				outArgs.add( new Integer(Types.NUMERIC) );
				outArgs.add( new Integer(Types.VARCHAR) );
		
				inArgs2 = new Vector();
				inArgs2.add(inputBean.getPartsPerBox());
				inArgs2.add(inputBean.getSupplierSalesOrderNo());
				inArgs2.add(packingGroupId);
				inArgs2.add(resultBean.getInventoryId());
				inArgs2.add(inputBean.getCustomerPo());
		
				log.info("p_supplier_shipping inArgs " + inArgs + " " + inArgs2);
				Vector v = (Vector) factory.doProcedure(connection, "p_supplier_shipping", inArgs, outArgs, inArgs2);
				
				String errorCode = StringHandler.emptyIfNull((String) v.get(2));
				String infoMsg = "USGOV p_supplier_shipping PO-Line-InventoryId: " + inputBean.getRadianPo().toPlainString() + "-" + inputBean.getPoLine().toPlainString() + "-" + resultBean.getInventoryId().toPlainString() + " Error Code " + errorCode;
				infoMsg += v.get(0) != null ? " " + ((BigDecimal) v.get(0)).toPlainString() : "";
				infoMsg += v.get(1) != null ? " " + ((BigDecimal) v.get(1)).toPlainString() : "";
				log.info(infoMsg);
				
				if (StringHandler.isBlankString(errorCode))
					finalMsg = "OK";
				
				if ("Dover".equalsIgnoreCase(inputBean.getCity())
					|| "Norfolk".equalsIgnoreCase(inputBean.getCity())
					|| "McGuire AFB".equalsIgnoreCase(inputBean.getCity())
					|| "Travis AFB".equalsIgnoreCase(inputBean.getCity())) {
					totalQtySent.add(resultBean.getPickedQty());
				}
			}
		} finally {
			dbManager.returnConnection(connection);
		}
		
		if (totalQtySent.compareTo(new BigDecimal("0")) != 0) {
			BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
			String subject = "PO selected to ship to " + inputBean.getCity();
			String msg = "PO selected to ship to " + inputBean.getCity() + "\n\n PO:" + inputBean.getRadianPo().toPlainString() + "\n\nQuantity:   " + totalQtySent.toPlainString() + "\n\nShip From: " + inputBean.getShipFromLocationName(); 
			bulkMailProcess.sendBulkEmail(new BigDecimal("11054"), subject, msg, false);
			bulkMailProcess.sendBulkEmail(new BigDecimal("15583"), subject, msg, false);
		}
		
		return finalMsg;
	}
}