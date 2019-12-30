package com.tcmis.internal.hub.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FlowDownBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.internal.catalog.beans.SpecBean;
import com.tcmis.internal.hub.beans.ReceiptDescriptionViewBean;
import com.tcmis.internal.supply.beans.PoHeaderViewBean;
import com.tcmis.internal.supply.beans.PoLineDetailViewBean;



	/******************************************************************************
	 * Process for receiving qc
	 * @version 1.0
	 *****************************************************************************/
	public class SpecFlowDownProcess extends BaseProcess {
		Log log = LogFactory.getLog(this.getClass());

		public SpecFlowDownProcess(String client, Locale locale) {
			super(client, locale);
		}

		public SpecFlowDownProcess(String client) {
			super(client);
		}
		
		public Collection buildSendFlowdowns(PoHeaderViewBean poHeaderViewBean, PoLineDetailViewBean poLineDetailViewBean) throws BaseException 
		{
			ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
			receiptDescriptionViewBean.setHub(poHeaderViewBean.getHubName());
			receiptDescriptionViewBean.setShipToCompanyId(poHeaderViewBean.getShipToCompanyId());
			receiptDescriptionViewBean.setItemId(poLineDetailViewBean.getItemId());
			receiptDescriptionViewBean.setRadianPo(poHeaderViewBean.getRadianPo());
			receiptDescriptionViewBean.setPoLine(poLineDetailViewBean.getPoLine());
			receiptDescriptionViewBean.setAmendment(poLineDetailViewBean.getAmendment());
			return buildSendFlowdowns(receiptDescriptionViewBean);
		}
				
		
		@SuppressWarnings("unchecked")
		public Collection buildSendFlowdowns(ReceiptDescriptionViewBean inputBean) throws BaseException 
		{
			Collection inArgs = null;
			Collection outArgs = null;
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procdFactory = new GenericProcedureFactory(dbManager);
			GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager,new FlowDownBean());
			Collection result = null;
			
			try {
				inArgs = new Vector(6);
				inArgs.add(inputBean.getHub());
				inArgs.add(inputBean.getShipToCompanyId());
				inArgs.add(inputBean.getItemId());
				inArgs.add(inputBean.getRadianPo());
				inArgs.add(inputBean.getPoLine());
				inArgs.add(inputBean.getAmendment());
				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Vector query = (Vector) procdFactory.doProcedure("pkg_po.Po_flowdown", inArgs, outArgs);

				result = sqlFactory.selectQuery(query.firstElement().toString() + ",flow_down_type");

			}
			catch (Exception e) {
			}
			
			return result;
		}
		
		public Collection buildSendSpecs(PoHeaderViewBean poHeaderViewBean, PoLineDetailViewBean poLineDetailViewBean) throws BaseException 
		{
			ReceiptDescriptionViewBean receiptDescriptionViewBean = new ReceiptDescriptionViewBean();
			receiptDescriptionViewBean.setHub(poHeaderViewBean.getHubName());
			receiptDescriptionViewBean.setShipToCompanyId(poHeaderViewBean.getShipToCompanyId());
			receiptDescriptionViewBean.setItemId(poLineDetailViewBean.getItemId());
			receiptDescriptionViewBean.setRadianPo(poHeaderViewBean.getRadianPo());
			receiptDescriptionViewBean.setPoLine(poLineDetailViewBean.getPoLine());
			receiptDescriptionViewBean.setAmendment(poLineDetailViewBean.getAmendment());
			receiptDescriptionViewBean.setInventoryGroup(poHeaderViewBean.getInventoryGroup());
			return buildSendSpecs(receiptDescriptionViewBean);
		}
		
		@SuppressWarnings("unchecked")
		public Collection buildSendSpecs(ReceiptDescriptionViewBean inputBean) throws BaseException 
		{
			Collection inArgs = null;
			Collection outArgs = null;
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericProcedureFactory procdFactory = new GenericProcedureFactory(dbManager);
			GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager,new SpecBean());
			Collection result = null;
			
			try {
				inArgs = new Vector(7);
				inArgs.add(inputBean.getHub());
				inArgs.add(inputBean.getShipToCompanyId());
				inArgs.add(inputBean.getItemId());
				inArgs.add(inputBean.getRadianPo());
				inArgs.add(inputBean.getPoLine());
				inArgs.add(inputBean.getAmendment());
				inArgs.add(inputBean.getInventoryGroup());
				outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));

				Vector query = (Vector) procdFactory.doProcedure("pkg_po.Po_spec", inArgs, outArgs);

				result = sqlFactory.selectQuery(query.firstElement().toString());

			}
			catch (Exception e) {
			}
			
			return result;
		}
}
