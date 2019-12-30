package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.VvShelfLifeBasisBean;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvSizeUnitBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.CatalogVendorProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.CatalogItemDetailQcInputBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcHeaderViewBean;
import com.tcmis.internal.catalog.beans.CatalogItemQcViewBean;
import com.tcmis.internal.catalog.beans.QualityCheckOriginalHeaderViewBean;
import com.tcmis.internal.catalog.beans.QualityCheckOriginalViewBean;

import radian.tcmis.common.util.StringHandler;

public class CatalogItemDetailQcProcess extends CatalogVendorProcess {

	public CatalogItemDetailQcProcess(BigDecimal personnelId, String client) {
		super(personnelId, client);
	}

	public Collection<QualityCheckOriginalViewBean> getQcOriginalInfo(CatalogItemDetailQcInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select distinct i.REQUEST_ID,i.MANUFACTURER,i.MATERIAL_ID,i.PART_SIZE,i.SIZE_UNIT,i.PKG_STYLE,");
		query.append("i.CASE_QTY,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY,i.LABEL_COLOR");
		//if Wesco master data team then display user's original data, otherwise, display cleaned up data
		if ("TCM_OPS".equals(this.getClient()))
			query.append(",i.MATERIAL_DESC,i.MFG_CATALOG_ID,i.DIMENSION,i.GRADE,i.MFG_TRADE_NAME");
		else
			query.append(",i.postscreen_material_desc MATERIAL_DESC,i.postscreen_mfg_catalog_id MFG_CATALOG_ID,i.postscreen_dimension DIMENSION,i.postscreen_grade GRADE,i.postscreen_mfg_trade_name MFG_TRADE_NAME");
		query.append(" from customer.catalog_add_item_orig i");
		if (input.hasqId()) {
			query.append(", catalog_queue q")
					.append(" where i.request_id = q.catalog_add_request_id")
					.append(" and i.line_item = q.line_item")
					.append(" and q.q_id = ").append(input.getqId());
		}
		else {
			query.append(" where i.request_id = ").append(input.getRequestId())
					.append(" and i.line_item = ").append(input.getLineItem());
		}

		return factory.setBean(new QualityCheckOriginalViewBean()).selectQuery(query.toString());
	}

	public QualityCheckOriginalHeaderViewBean getQcOriginalHeader(CatalogItemDetailQcInputBean input) throws BaseException {
		StringBuilder detailquery = new StringBuilder("");
		detailquery.append("select car.MESSAGE_TO_APPROVERS,car.PART_DESCRIPTION,car.request_id,car.CAT_PART_NO,");
		detailquery.append("cai.VENDOR_CONTACT_NAME,cai.VENDOR_CONTACT_EMAIL,cai.VENDOR_CONTACT_PHONE,cai.VENDOR_CONTACT_FAX,cai.SUGGESTED_VENDOR, ");
		detailquery.append("car.company_id, car.catalog_id, to_char(car.SUBMIT_DATE,'dd Month yyyy HH:Mi AM') submitdate ");
		detailquery.append("from customer.catalog_add_request_new car,customer.catalog_add_item cai where ");
		detailquery.append(" car.request_id = cai.request_id and cai.line_item = ");
		detailquery.append(input.getLineItem()).append(" and cai.part_id = 1 and ");
		detailquery.append("car.request_id = ").append(input.getRequestId());

		QualityCheckOriginalHeaderViewBean bean = null;
		try {
			if ("TCM_OPS".equals(this.getClient())) {
				Collection<QualityCheckOriginalHeaderViewBean> beanColl = factory.setBean(new QualityCheckOriginalHeaderViewBean()).selectQuery(detailquery.toString());
				Iterator<QualityCheckOriginalHeaderViewBean> it = beanColl.iterator();
				while (it.hasNext()) {
					bean = it.next();
					break;
				}
			}
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return bean;
	}

	public Collection<CatalogItemQcHeaderViewBean> getHeaderInfo(CatalogItemDetailQcInputBean input) throws BaseException {
		Connection conn = dbManager.getConnection();
		List<CatalogItemQcHeaderViewBean> headers = new ArrayList<CatalogItemQcHeaderViewBean>();
		try {
			headers.add(getRequestInfo(input,conn));
			if (input.hasItemId()) {
				headers.add(reloadItemInfo(input,conn));
			}
			else {
				headers.add(getItemInfo(input,conn));
			}
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return headers;
	}

	public Collection<CatalogItemQcHeaderViewBean> reloadHeaderInfo(CatalogItemDetailQcInputBean input) throws BaseException {
		Connection conn = dbManager.getConnection();
		List<CatalogItemQcHeaderViewBean> headers = new ArrayList<CatalogItemQcHeaderViewBean>();
		try {
			headers.add(getRequestInfo(input,conn));
			headers.add(reloadItemInfo(input,conn));
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return headers;
	}

	private CatalogItemQcHeaderViewBean getRequestInfo(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		CatalogItemQcHeaderViewBean bean = null;

		if ("TCM_OPS".equals(getClient()) && input.hasLineItem() && input.hasRequestId()) {
			StringBuilder query = new StringBuilder();
			query.append("select carn.request_id, caiq.line_item, p.phone, carn.company_id, ");
			query.append("to_char(carn.submit_date,'dd Month yyyy HH:Mi AM') submitdate, ");
			query.append("p.last_name || ', ' || p.first_name full_name, ");
			query.append("p.email, carn.catalog_id, carn.engineering_evaluation, carn.cat_part_no, ");
			query.append("(select cars.request_status_desc from customer.vv_catalog_add_request_status cars where carn.starting_view=cars.request_status and carn.company_id=cars.company_id) STARTING_VIEW, ");
			query.append("carn.part_description ");
			query.append("from customer.catalog_add_request_new carn, customer.catalog_add_item_qc caiq, global.personnel p ");
			query.append("where carn.requestor = p.personnel_id and carn.request_id = caiq.request_id ");
			query.append("and caiq.line_item = ").append(input.getLineItem());
			query.append(" and caiq.part_id = 1 and carn.request_id = ").append(input.getRequestId());

			try {
				Vector<CatalogItemQcHeaderViewBean> beanColl = (Vector)factory.setBean(new CatalogItemQcHeaderViewBean()).selectQuery(query.toString(),conn);
				bean = beanColl.get(0);
			}
			catch(Exception e) {
				throw new BaseException(e);
			}
		}
		else {
			bean = new CatalogItemQcHeaderViewBean();
		}

		return bean;
	}

	private CatalogItemQcHeaderViewBean getItemInfo(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select i.item_id, i.category_id, i.item_type, i.sample_only, i.inseparable_kit ");
		query.append("from customer.catalog_add_item_qc caiq, item i where ");
		query.append("caiq.request_id = ").append(input.getRequestId()).append(" and ");
		query.append("caiq.line_item = ").append(input.getLineItem()).append(" and caiq.part_id = 1 and ");
		query.append("caiq.item_id = i.item_id(+)");

		CatalogItemQcHeaderViewBean bean = null;
		try {
			Vector<CatalogItemQcHeaderViewBean> beanColl = (Vector)factory.setBean(new CatalogItemQcHeaderViewBean()).selectQuery(query.toString(),conn);
			bean = beanColl.get(0);
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return bean;
	}

	private CatalogItemQcHeaderViewBean reloadItemInfo(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select i.item_id, i.category_id, i.item_type, i.sample_only, i.inseparable_kit ");
		query.append("from item i where i.item_id = ").append(input.getItemId());

		CatalogItemQcHeaderViewBean bean = null;
		try {
			Vector<CatalogItemQcHeaderViewBean> beanColl = (Vector)factory.setBean(new CatalogItemQcHeaderViewBean()).selectQuery(query.toString(), conn);
			bean = beanColl.get(0);
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return bean;
	}

	public Collection<CatalogItemQcViewBean> search(CatalogItemDetailQcInputBean input) throws BaseException {

		String caiqTable = "customer.catalog_add_item_qc";
		String carnTable = "customer.catalog_add_request_new";

		StringBuilder query = new StringBuilder("select x.*,p.RECERT,p.SIZE_VARIES,mfg.* from ");
		query.append("(select i.MIN_TEMP, i.MAX_TEMP, i.TEMP_UNITS, car.MANAGE_KITS_AS_SINGLE_UNIT,i.item_id,i.company_id,i.REQUEST_ID,i.MATERIAL_DESC,i.MANUFACTURER, ");
		query.append("i.MATERIAL_ID,i.PART_ID,i.PART_SIZE, ");
		query.append("i.SIZE_UNIT,i.PKG_STYLE,i.MFG_CATALOG_ID MFG_PART_NO,i.DIMENSION, ");
		query.append("i.GRADE,i.MFG_TRADE_NAME,i.NETWT,i.NETWT_UNIT,i.COMPONENTS_PER_ITEM,i.SAMPLE_ONLY, ");
		query.append("i.MFG_SHELF_LIFE_BASIS SHELF_LIFE_BASIS,i.MFG_SHELF_LIFE SHELF_LIFE_DAYS,i.ITEM_VERIFIED,i.MFG_STORAGE_TEMP,i.COMMENTS,i.MATERIAL_TYPE ");
		query.append("from ").append(caiqTable).append(" i, ").append(carnTable).append(" car ");
		query.append("where i.request_id = car.request_id and i.line_item = ");
		query.append(input.getLineItem()).append(") x, ");
		query.append("global.part p,global.material m,global.manufacturer mfg ");
		query.append("where ");
		query.append("x.item_id = p.item_id(+) and ");
		query.append("x.material_id = p.material_id(+) and ");
		query.append("x.part_id = p.part_id(+) and ");
		query.append("x.material_id = m.material_id(+) and ");
		query.append("x.company_id = '").append(input.getCompanyId()).append("' and ");
		query.append("x.request_id = ").append(input.getRequestId()).append(" and m.MFG_ID = mfg.MFG_ID(+) order by x.PART_ID asc");

		Collection<CatalogItemQcViewBean> beanColl = null;
		try {
			beanColl = factory.setBean(new CatalogItemQcViewBean()).selectQuery(query.toString());
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return beanColl;
	}

	public Collection<CatalogItemQcViewBean> reloadItemComponents(CatalogItemDetailQcInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select mfg.mfg_id, mfg.mfg_desc manufacturer, mfg.phone, mfg.contact, mfg.mfg_url, mfg.email, mfg.notes, ");
		query.append("p.material_id, m.material_desc, p.shelf_life_days, p.shelf_life_basis, m.trade_name mfg_trade_name, ");
		query.append("p.net_wt netwt, p.net_wt_unit netwt_unit, p.dimension, p.pkg_style, p.components_per_item, p.part_size, p.size_unit, ");
		query.append("caiq.comments, p.item_verified, p.min_temp, p.max_temp, p.temp_units, p.grade, p.mfg_part_no, p.mfg_part_no_extension ");
		query.append("from manufacturer mfg, material m, part p, catalog_add_item_qc caiq where ");
		query.append("p.item_id = ").append(input.getItemId()).append(" and ");
		query.append("m.material_id = p.material_id and ");
		query.append("mfg.mfg_id = m.mfg_id ");
		query.append("and caiq.request_id = ").append(input.getRequestId());
		query.append(" and caiq.material_id = p.material_id order by p.part_id asc");

		Collection<CatalogItemQcViewBean> beanColl = null;
		try {
			beanColl = factory.setBean(new CatalogItemQcViewBean()).selectQuery(query.toString());
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		return beanColl;
	}

	private boolean itemAlreadyVerified(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from part ");
		query.append("where ITEM_ID = ").append(input.getItemId()).append(" ");
		query.append("and ITEM_VERIFIED = 'Y' ");

		int count = 0;
		try {
			count = Integer.parseInt(factory.selectSingleValue(query.toString(),conn));
		}
		catch (NumberFormatException e) {
			throw new BaseException("Error converting count to integer", e);
		}
		return count > 0;
	}

	public void save(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components,PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			if ( ! input.hasqId()) {
				updateRequestHeader(input, conn);
			}
			if ( ! itemAlreadyVerified(input,conn)) {
				if (input.getItemId() != null) {
					upsertItemData(input, conn);
					upsertPartDetails(input, components, user, conn);
				}
				updateCatAddItemQc(input, components, conn);
			}
			else {
				updateCatAddItemQcItem(input, components, conn);
			}

			if (input.isApprove()) {
				if (input.getItemId() == null) {
					throw new BaseException("Approval failed: no Item ID");
				}
				else if ( ! (input.isItemVerifiedStatus() || input.hasqId())) {
					throw new BaseException("Approval failed: Item not Verified");
				}
				else {
					advanceCatAddRequest(input,user,conn);
				}
			}
		}
		catch(BaseException e) {
			throw e;
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		finally {
			if (conn != null) {
				dbManager.returnConnection(conn);
			}
		}
	}

	public void advanceCatAddRequest(CatalogItemDetailQcInputBean input, PersonnelBean user, Connection conn) throws BaseException {
		try {
			updateItemApproval(input, user, conn);
			copyToCatAddItem(input, conn);
			updateItemVerified(input,user,conn);
			//advance request if all lines are approved
			StringBuilder approvedQuery = new StringBuilder("select count(*) from catalog_add_item_qc");
			approvedQuery.append(" where ITEM_QC_STATUS='Pending'");
			approvedQuery.append(" and REQUEST_ID=").append(input.getRequestId());
			if ("0".equals(factory.selectSingleValue(approvedQuery.toString(), conn))) {
				CatalogAddRequestProcess approvalProcess = new CatalogAddRequestProcess(this.getClient());
				StringBuilder query = new StringBuilder("select * from catalog_add_request_new where request_id = ").append(input.getRequestId());
				factory.setBeanObject(new CatAddHeaderViewBean());
				Vector<CatAddHeaderViewBean> dataC = (Vector) factory.selectQuery(query.toString(), conn);
				CatAddHeaderViewBean bean = dataC.get(0);
				approvalProcess.setFactoryConnection(factory, conn);
				approvalProcess.approvalRequestFromSpecificPage(bean, "Item QC", user);

				StringBuilder passItemQcStmt = new StringBuilder();
				passItemQcStmt.append("update customer.catalog_add_request_new set QC_STATUS='Passed Item QC',QC_DATE=sysdate ");
				passItemQcStmt.append("where qc_status in ('Pending Item QC','Pending Item','Pending QC','Pending SDS Indexing') and REQUEST_ID = ");
				passItemQcStmt.append(input.getRequestId());
				factory.deleteInsertUpdate(passItemQcStmt.toString(), conn);
			}
		}catch(Exception e) {
			throw new BaseException(e);
		}
	}

	public void reverse(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			DateFormat df = new SimpleDateFormat("ddMMMyyyy");
			String comments = "'Reversed - " + SqlHandler.validQuery(input.getReverseComments())
					+ " (' || fx_personnel_id_to_name(" + user.getPersonnelIdBigDecimal()
					+ ") || ' - " + df.format(new Date()) + ") - Reversed; ' || comments";

			int i = 0;
			for (CatalogItemQcViewBean comp : components) {
				comp.setPartId(new BigDecimal(++i));
			}
			
			Map<String, List<BigDecimal>> reversalMap = components.stream()
					.filter(comp -> comp.isReversed())
					.collect(Collectors.groupingBy(CatalogItemQcViewBean::getComponentReversed,
							Collectors.mapping(comp -> comp.getPartId(), Collectors.toList())));
			String reversedParts = reversalMap.values().stream().flatMap(l -> l.stream())
					.map(BigDecimal::toString).collect(Collectors.joining(","));
			//Update Catalog Add item QC status
			String updatecataddqc="update customer.catalog_add_item_qc set "
					+ "material_qc_status=null"
					+ ",item_qc_status = 'Reversed'"
					+ ",comments = " + comments
					+ ",material_approved_on=null"
					+ ",material_approved_by=null"
					+ ",material_id = null"
					+ " where "
					+ "REQUEST_ID = " + input.getRequestId()
					+ " and line_item = " + input.getLineItem()
					+ " and part_id in ("+reversedParts+")";
			factory.deleteInsertUpdate(updatecataddqc, conn);
			if ( ! reversalMap.values().stream().flatMap(l -> l.stream()).anyMatch(v -> v.intValue() == 1)) {
				updatecataddqc="update customer.catalog_add_item_qc set "
						+ "comments = " + comments
						+ " where "
						+ "REQUEST_ID = " + input.getRequestId()
						+ " and line_item = " + input.getLineItem()
						+ " and part_id = 1";
				factory.deleteInsertUpdate(updatecataddqc, conn);
			}
			//Update Catalog Add item status
			updatecataddqc="update customer.catalog_add_item set "
					+ "material_qc_status=null"
					+ ",item_qc_status = null"
					+ ",material_id = null"
					+ " where "
					+ "REQUEST_ID = " + input.getRequestId()
					+ " and line_item = " + input.getLineItem()
					+ " and part_id in ("+reversedParts+")";
			factory.deleteInsertUpdate(updatecataddqc, conn);
			//catalog add request
			updatecataddqc="update customer.catalog_add_request_new carn set "
					+ "md_verified = null"
					+ ",qc_status = 'Pending Assignment'"
					+ ",approval_group_seq = ("
					+ "select approval_group_seq from vv_chemical_approval_role car"
					+ " where car.approval_role = 'Pending Assignment'"
					+ " and car.company_id = carn.company_id"
					+ " and car.facility_id = carn.eng_eval_facility_id"
					+ " and car.catalog_id = carn.catalog_id"
					+ " and car.catalog_company_id = carn.catalog_company_id)"
					+ ",qc_date = null"
					+ " where REQUEST_ID = " + input.getRequestId();
			factory.deleteInsertUpdate(updatecataddqc, conn);
			//delete catalog_add_approval
			updatecataddqc = "delete from customer.catalog_add_approval where approval_role in ('Material QC', 'MSDS Indexing', 'Pending Assignment') and request_id = "+input.getRequestId();
			factory.deleteInsertUpdate(updatecataddqc, conn);

			String updatecail = "update catalog_add_item_locale set "
					+ "sds_indexing_status = 'Pending'"
					+ ",sds_sourcing_status = 'Pending'"
					+ " where "
					+ "catalog_add_item_id in ("
					+ "select catalog_add_item_id from catalog_add_item_qc"
					+ " where request_id = " + input.getRequestId()
					+ " and line_item = " + input.getLineItem()
					+ " and part_id in (" + reversedParts + "))";
			factory.deleteInsertUpdate(updatecail);

			if (input.hasqId()) {
				String closeWqi = "update catalog_queue set status = 'Reversed' where q_id = " + input.getqId();
				factory.deleteInsertUpdate(closeWqi, conn);
			}
			
			String reverseWqiQuery = "update catalog_queue set cat_add_reversed = 'Y', status='Approved'"
					+ " WHERE (insert_date, task, locale_code, catalog_add_item_id) in "
					+ "(SELECT MAX(cq.insert_date), cq.task, cq.locale_code, caiq.catalog_add_item_id"
		            + " FROM catalog_add_item_qc caiq"
	                + ", catalog_queue cq"
	                + " WHERE caiq.part_id IN ("+reversedParts+")"
	                + " AND caiq.catalog_add_item_id = cq.catalog_add_item_id"
	                + " AND caiq.request_id = " + input.getRequestId()
	                + " AND cq.catalog_add_item_id = caiq.catalog_add_item_id"
	                + " AND cq.catalog_add_request_id = caiq.request_id"
	                + " AND cq.task in ('SDS Sourcing', 'SDS Indexing')"
	                + " GROUP BY cq.task, caiq.catalog_add_item_id, cq.locale_code)";
			
			factory.deleteInsertUpdate(reverseWqiQuery, conn);
			
			if ( ! reversalMap.getOrDefault(CatalogItemQcViewBean.VENDOR_ERROR, Collections.emptyList()).isEmpty()) {
				String vendorErrors = reversalMap.get(CatalogItemQcViewBean.VENDOR_ERROR).stream()
						.map(part -> part.toString()).collect(Collectors.joining(","));
				
				reverseWqiQuery = "update catalog_queue set billable = 'N'"
						+ " WHERE (insert_date, task, locale_code, catalog_add_item_id) in "
						+ "(SELECT MAX(cq.insert_date), cq.task, cq.locale_code, caiq.catalog_add_item_id"
			            + " FROM catalog_add_item_qc caiq"
		                + ", catalog_queue cq"
		                + " WHERE caiq.part_id IN ("+vendorErrors+")"
		                + " AND caiq.catalog_add_item_id = cq.catalog_add_item_id"
		                + " AND caiq.request_id = " + input.getRequestId()
		                + " AND cq.catalog_add_item_id = caiq.catalog_add_item_id"
		                + " AND cq.catalog_add_request_id = caiq.request_id"
		                + " AND cq.task in ('SDS Sourcing', 'SDS Indexing')"
		                + " GROUP BY cq.task, caiq.catalog_add_item_id, cq.locale_code)";
				
				factory.deleteInsertUpdate(reverseWqiQuery, conn);
			}
		}
		catch(Exception e) {
			throw new BaseException(e);
		}
		finally {
			if (conn != null) {
				dbManager.returnConnection(conn);
			}
		}
	}

	private void updateRequestHeader(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		StringBuilder stmt=new StringBuilder("update customer.catalog_add_request_new set CAT_PART_NO=");
		stmt.append(SqlHandler.delimitString(input.getCatPartNo())).append(", ");
		stmt.append("PART_DESCRIPTION=").append(SqlHandler.delimitString(input.getPartDescription()));
		stmt.append(" where REQUEST_ID = ").append(input.getRequestId());
		factory.deleteInsertUpdate(stmt.toString(),conn);
	}

	private void upsertItemData(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		boolean update = false;
		int test_number=Integer.parseInt(factory.selectSingleValue("select count(*) from global.item where item_id = " + input.getItemId(),conn));
		if ( test_number > 0 ) {
			update=true;
		}

		StringBuilder query = new StringBuilder();
		if (update) {
			query.append("update item set ");
			query.append("CATEGORY_ID=").append(input.getCategoryId()).append(", ");
			query.append("SAMPLE_ONLY=").append(SqlHandler.delimitString(input.getSampleOnly())).append(", ");
			query.append("ITEM_TYPE=").append(SqlHandler.delimitString(input.getItemType())).append(", ");
			query.append("STOCKING_TYPE='S', ");
			query.append("INSEPARABLE_KIT=").append(input.getInseparableKit()?"'Y'":"'N'");
			query.append(" where ITEM_ID = ").append(input.getItemId());
		}
		else {
			query.append("insert into item (ITEM_ID,CATEGORY_ID,SAMPLE_ONLY,ITEM_TYPE,STOCKING_TYPE,INSEPARABLE_KIT) values (");
			query.append(input.getItemId()).append(", ");
			query.append(input.getCategoryId()).append(", ");
			query.append(SqlHandler.delimitString(input.getSampleOnly())).append(", ");
			query.append(SqlHandler.delimitString(input.getItemType())).append(", ");
			query.append("'S', ");
			query.append(input.getInseparableKit()?"'Y'":"'N'").append(")");
		}

		factory.deleteInsertUpdate(query.toString(),conn);
	}

	private void upsertPartDetails(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components, PersonnelBean user, Connection conn) throws BaseException {
		boolean update = false;
		int test_number=Integer.parseInt(factory.selectSingleValue("select count(*) from global.part where item_id = " + input.getItemId(),conn));
		if ( test_number > 0 ) {
			update=true;
		}

		if (update) {
			int partId = 0;
			for (CatalogItemQcViewBean comp : components) {
				StringBuilder stmt = new StringBuilder();
				stmt.append("update global.part set ");
				stmt.append("MATERIAL_ID=").append(comp.getMaterialId()).append(", ");
				stmt.append("GRADE=").append(SqlHandler.delimitString(comp.getGrade())).append(", ");
				stmt.append("PKG_STYLE=").append(SqlHandler.delimitString(comp.getPkgStyle())).append(", ");
				stmt.append("PART_SIZE=").append(comp.getPartSize()).append(", ");
				stmt.append("SIZE_UNIT=").append(SqlHandler.delimitString(comp.getSizeUnit())).append(", ");
				stmt.append("SHELF_LIFE_DAYS=").append(comp.getShelfLifeDays()).append(", ");
				stmt.append("SHELF_LIFE_BASIS=").append(SqlHandler.delimitString(comp.getShelfLifeBasis())).append(", ");
				stmt.append("MFG_PART_NO=").append(SqlHandler.delimitString(comp.getMfgPartNo())).append(", ");
				stmt.append("NET_WT_UNIT=").append(SqlHandler.delimitString(comp.getNetwtUnit())).append(", ");
				stmt.append("NET_WT=").append(comp.getNetwt()).append(", ");
				stmt.append("DIMENSION=").append(SqlHandler.delimitString(comp.getDimension())).append(", ");
				stmt.append("RECERT=").append(SqlHandler.delimitString(comp.getRecert())).append(", ");
				stmt.append("COMPONENTS_PER_ITEM=").append(comp.getComponentsPerItem()).append(", ");
				stmt.append("SIZE_VARIES=").append(SqlHandler.delimitString(comp.getSizeVaries())).append(", ");
				stmt.append("STOCKING_TYPE='S', ");
				if ("Y".equals(comp.getItemVerified())) {
					stmt.append("ITEM_VERIFIED_BY=").append(user.getPersonnelId()).append(", ");
					stmt.append("DATE_ITEM_VERIFIED=sysdate, ");
				}
				stmt.append("ITEM_VERIFIED=").append(SqlHandler.delimitString(comp.getItemVerified())).append(", ");
				stmt.append("MIN_TEMP=").append(comp.getMinTemp()).append(", ");
				stmt.append("MAX_TEMP=").append(comp.getMaxTemp()).append(", ");
				stmt.append("TEMP_UNITS=").append(SqlHandler.delimitString(comp.getTempUnits())).append(" ");
				stmt.append("where ITEM_ID = ").append(input.getItemId()).append(" ");
				stmt.append("and ITEM_VERIFIED <> 'Y' ");
				stmt.append("and PART_ID = ").append(++partId);

				factory.deleteInsertUpdate(stmt.toString(),conn);
			}
		}
		else {
			if (components.size() > 0) {
				StringBuilder stmt = new StringBuilder("insert all ");
				int partId = 0;
				for (CatalogItemQcViewBean comp : components) {
					stmt.append("into global.part (PART_ID,MATERIAL_ID,GRADE,PKG_STYLE,PART_SIZE,SIZE_UNIT,SHELF_LIFE_DAYS,SHELF_LIFE_BASIS,MFG_PART_NO,NET_WT_UNIT,NET_WT,DIMENSION,RECERT,COMPONENTS_PER_ITEM,SIZE_VARIES,ITEM_ID,STOCKING_TYPE,");
					if ("Y".equals(comp.getItemVerified())) {
						stmt.append("ITEM_VERIFIED_BY,DATE_ITEM_VERIFIED,");
					}
					stmt.append("ITEM_VERIFIED,MIN_TEMP, MAX_TEMP, TEMP_UNITS) values (");
					stmt.append(++partId).append(", ");
					stmt.append(comp.getMaterialId()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getGrade())).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getPkgStyle())).append(", ");
					stmt.append(comp.getPartSize()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getSizeUnit())).append(", ");
					stmt.append(comp.getShelfLifeDays()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getShelfLifeBasis())).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getMfgPartNo())).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getNetwtUnit())).append(", ");
					stmt.append(comp.getNetwt()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getDimension())).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getRecert())).append(", ");
					stmt.append(comp.getComponentsPerItem()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getSizeVaries())).append(", ");
					stmt.append(input.getItemId()).append(", ");
					stmt.append("'S', ");
					if ("Y".equals(comp.getItemVerified())) {
						stmt.append(user.getPersonnelId()).append(", ");
						stmt.append("sysdate, ");
					}
					stmt.append(SqlHandler.delimitString(comp.getItemVerified())).append(", ");
					stmt.append(comp.getMinTemp()).append(", ");
					stmt.append(comp.getMaxTemp()).append(", ");
					stmt.append(SqlHandler.delimitString(comp.getTempUnits())).append(")");
				}

				stmt.append("select * from dual");

				factory.deleteInsertUpdate(stmt.toString(),conn);
			}
		}
	}

	private void updateCatAddItemQc(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components, Connection conn) throws BaseException {
		int partId = 0;

		for (CatalogItemQcViewBean comp : components) {
			partId++;
			StringBuilder materialquery=new StringBuilder();
			if (comp.getMaterialId() != null) {
				materialquery.append("update global.material set MATERIAL_DESC=");
				materialquery.append(SqlHandler.delimitString(comp.getMaterialDesc()));
				materialquery.append(" where MATERIAL_ID = ").append(comp.getMaterialId());
				factory.deleteInsertUpdate(materialquery.toString(),conn);
			}

			StringBuilder query  = new StringBuilder("update customer.catalog_add_item_qc set ");
			query.append("MATERIAL_DESC=").append(SqlHandler.delimitString(comp.getMaterialDesc())).append(", ");
			query.append("MANUFACTURER=").append(SqlHandler.delimitString(comp.getManufacturer())).append(", ");
			query.append("MATERIAL_ID=").append(comp.getMaterialId()).append(", ");
			query.append("PART_SIZE=").append(comp.getPartSize()).append(", ");
			query.append("SIZE_UNIT=").append(SqlHandler.delimitString(comp.getSizeUnit())).append(", ");
			query.append("PKG_STYLE=").append(SqlHandler.delimitString(comp.getPkgStyle())).append(", ");
			query.append("MFG_CATALOG_ID=").append(SqlHandler.delimitString(comp.getMfgPartNo())).append(", ");
			query.append("DIMENSION=").append(SqlHandler.delimitString(comp.getDimension())).append(", ");
			query.append("GRADE=").append(SqlHandler.delimitString(comp.getGrade())).append(", ");
			query.append("MFG_TRADE_NAME=").append(SqlHandler.delimitString(comp.getMfgTradeName())).append(", ");
			query.append("NETWT=").append(comp.getNetwt()).append(", ");
			query.append("NETWT_UNIT=").append(SqlHandler.delimitString(comp.getNetwtUnit())).append(", ");
			query.append("COMPONENTS_PER_ITEM=").append(comp.getComponentsPerItem()).append(", ");
			query.append("COMMENTS=").append(SqlHandler.delimitString(comp.getComments())).append(", ");
			query.append("MFG_SHELF_LIFE_BASIS=").append(SqlHandler.delimitString(comp.getShelfLifeBasis())).append(", ");
			query.append("MFG_SHELF_LIFE=").append(comp.getShelfLifeDays()).append(", ");
			query.append("MFG_ID=").append(comp.getMfgId()).append(", ");
			query.append("MIN_TEMP=").append(comp.getMinTemp()).append(", ");
			query.append("MAX_TEMP=").append(comp.getMaxTemp()).append(", ");
			query.append("TEMP_UNITS=").append(SqlHandler.delimitString(comp.getTempUnits())).append(", ");
			query.append("LABEL_COLOR=").append(SqlHandler.delimitString(comp.getLabelColor())).append(", ");
			query.append("ITEM_VERIFIED=").append(SqlHandler.delimitString(comp.getItemVerified()));
			if (input.getItemId() != null) {
				query.append(", ").append("ITEM_ID=").append(input.getItemId()).append(" ");
			}
			query.append("where REQUEST_ID = ").append(input.getRequestId());
			query.append(" and PART_ID = ").append(partId).append(" and LINE_ITEM = ").append(input.getLineItem());

			factory.deleteInsertUpdate(query.toString(),conn);
		}
	}

	private void updateCatAddItemQcItem(CatalogItemDetailQcInputBean input, Collection<CatalogItemQcViewBean> components, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder();
		query.append("select m.material_desc, mfg.mfg_desc manufacturer, p.material_id, p.part_size, p.size_unit, ");
		query.append("p.pkg_style, p.mfg_part_no, p.dimension, p.grade, m.trade_name mfg_trade_name, p.net_wt netwt, ");
		query.append("p.net_wt_unit netwt_unit, p.components_per_item, p.shelf_life_days, ");
		query.append("p.shelf_life_basis, m.mfg_id, p.min_temp, p.max_temp, p.temp_units, ");
		query.append("p.item_verified, p.item_id ");
		query.append("from material m, manufacturer mfg, part p ");
		query.append("where p.item_id = ").append(input.getItemId());
		query.append(" and m.material_id = p.material_id and m.mfg_id = mfg.mfg_id order by p.part_id asc");

		Collection<CatalogItemQcViewBean> parts = factory.setBean(new CatalogItemQcViewBean()).selectQuery(query.toString(),conn);
		Iterator<CatalogItemQcViewBean> it = parts.iterator();
		for (CatalogItemQcViewBean comp : components) {
			if (it.hasNext()) {
				it.next().setComments(comp.getComments());
			}
		}

		updateCatAddItemQc(input, parts, conn);
	}

	private void updateItemVerified(CatalogItemDetailQcInputBean input, PersonnelBean user, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update part p set item_verified = 'Y',date_item_verified = sysdate,item_verified_by = ").append(user.getPersonnelId())
				.append(", temp_verified = 'Y',date_temp_verified = sysdate, temp_verified_by = ").append(user.getPersonnelId())
				.append(" where (p.item_id,p.part_id) in (select cai.item_id,cai.part_id from catalog_add_item cai")
				.append(" where cai.request_id = ").append(input.getRequestId())
				.append(" AND cai.line_item = ").append(input.getLineItem())
				.append(")");
		factory.deleteInsertUpdate(query.toString(),conn);
	}

	private void updateItemApproval(CatalogItemDetailQcInputBean input, PersonnelBean user, Connection conn) throws BaseException {
		StringBuilder qcQuery = new StringBuilder();
		qcQuery.append("update customer.catalog_add_item_qc set ITEM_APPROVED_BY=");
		qcQuery.append(user.getPersonnelIdBigDecimal()).append(", ");
		qcQuery.append("ITEM_APPROVED_ON=sysdate, ITEM_VERIFIED = 'Y', ");
		qcQuery.append("ITEM_QC_STATUS='Approved', status = 'Approved QC' where REQUEST_ID = ").append(input.getRequestId());
		qcQuery.append(" AND LINE_ITEM = ").append(input.getLineItem());
		factory.deleteInsertUpdate(qcQuery.toString(),conn);
	}

	private void copyToCatAddItem(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		//String fromClause = "from customer.catalog_add_item_qc qc where c.part_id = qc.part_id and c.request_id = qc.request_id and c.line_item = qc.line_item";
		StringBuilder catadditemquery  = new StringBuilder("update customer.catalog_add_item c set (");
		catadditemquery.append("MATERIAL_DESC, MANUFACTURER, MATERIAL_ID, PART_SIZE, SIZE_UNIT, ");
		catadditemquery.append("PKG_STYLE, MFG_CATALOG_ID, DIMENSION, GRADE, MFG_TRADE_NAME, ");
		catadditemquery.append("NETWT, NETWT_UNIT, COMPONENTS_PER_ITEM, LABEL_COLOR, ITEM_ID, ITEM_APPROVED_BY, ITEM_APPROVED_ON, ITEM_QC_STATUS) = (");
		catadditemquery.append("select MATERIAL_DESC, MANUFACTURER, MATERIAL_ID, PART_SIZE, SIZE_UNIT, ");
		catadditemquery.append("PKG_STYLE, MFG_CATALOG_ID, DIMENSION, GRADE, MFG_TRADE_NAME, ");
		catadditemquery.append("NETWT, NETWT_UNIT, COMPONENTS_PER_ITEM, LABEL_COLOR, ITEM_ID, ITEM_APPROVED_BY, ITEM_APPROVED_ON, ITEM_QC_STATUS ");
		catadditemquery.append("from customer.catalog_add_item_qc qc where c.part_id = qc.part_id ");
		catadditemquery.append("and c.request_id = qc.request_id and c.line_item = qc.line_item) ");
		catadditemquery.append("where REQUEST_ID = ").append(input.getRequestId());
		catadditemquery.append(" and LINE_ITEM = ").append(input.getLineItem());

		factory.deleteInsertUpdate(catadditemquery.toString(), conn);
	}

	public Collection<VvShelfLifeBasisBean> getShelfLifeBasisCollection() throws BaseException {
		return factory.setBean(new VvShelfLifeBasisBean()).selectQuery("select * from vv_shelf_life_basis where display_on_item_qc = 'Y'");
	}

	public Collection<VvSizeUnitBean> getNetwtUnitCollection() throws BaseException {
		return factory.setBean(new VvSizeUnitBean()).selectQuery("select size_unit from global.size_unit_view where net_wt_required <> 'Y' order by size_unit");
	}

	public String advanceCatalogQueue(CatalogItemDetailQcInputBean input, PersonnelBean user) throws BaseException {
		Connection conn = dbManager.getConnection();
		String result = "";
		try {
			if (input.isAssigned()) {
				String updateStmt = "update catalog_queue set"
						+ " task_complete_date = sysdate";
				//if Master Data team is working on this task then don't need to stop for QC and Approval
				if ("TCM_OPS".equals(this.getClient())) {
					updateStmt += ", status = 'Approved'"
							+ ", approved_date = sysdate"
							+ ", approved_by = " + user.getPersonnelIdBigDecimal();
					result = "approved";
				}else {
					updateStmt += ", status = 'Pending QC'";
					result = "submitted";
				}
				updateStmt += " where q_id = " + input.getqId()
						+ " and status = 'Assigned'";

				factory.deleteInsertUpdate(updateStmt, conn);
			}
			else if (input.isPendingVendorQc()) {
				String updateStmt = "update catalog_queue set"
						+ " supplier_approved_date = sysdate"
						+ ", supplier_approved_by = " + user.getPersonnelIdBigDecimal()
						+ ", status = 'Pending Approval'"
						+ " where q_id = " + input.getqId()
						+ " and status = 'Pending QC'";

				factory.deleteInsertUpdate(updateStmt, conn);
				result = "approved";
				notifyMasterDataTeam(input, conn);
			}
			else if (input.isPendingApproval()) {
				String updateStmt = "update catalog_queue set"
						+ " approved_date = sysdate"
						+ ", approved_by = " + user.getPersonnelIdBigDecimal()
						+ ", status = 'Approved'"
						+ " where q_id = " + input.getqId()
						+ " and status = 'Pending Approval'";

				factory.deleteInsertUpdate(updateStmt, conn);
				result = "approved";
			}
			else if ( ! input.hasqId() || input.isWorkQueueItemClosed()) {
				result = "approved";
			}
		} finally {
			dbManager.returnConnection(conn);
		}
		return result;
	}

	private void notifyMasterDataTeam(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		try {
			String query = "select count(*) from catalog_queue where catalog_add_request_id = "
					+ input.getRequestId()
					+ " and task = 'Item Creation'"
					+ " and status <> 'Pending Approval'";

			String count = factory.selectSingleValue(query, conn);

			if (Integer.parseInt(count) == 0) {
				EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(), getLocale(), "");
				engEvalProcess.setFactoryConnection(factory, conn);
				engEvalProcess.sendApproversEmail(input.getRequestId(), "", Collections.emptyList(), Collections.emptyList(),false);
			}
		} catch(Exception e) {
			throw new BaseException(e);
		}
	}

	public Optional<CatalogQueueBean> getCatalogQueueData(CatalogItemDetailQcInputBean input) throws BaseException {
		Optional<CatalogQueueBean> queue = null;
		Connection conn = dbManager.getConnection();
		try {
			queue = getCatalogQueueData(input,conn);
		} catch(BaseException e) {
			throw new BaseException(e);
		}finally {
			dbManager.returnConnection(conn);
		}
		return queue;
	}

	private Optional<CatalogQueueBean> getCatalogQueueData(CatalogItemDetailQcInputBean input, Connection conn) throws BaseException {
		Collection<CatalogQueueBean> queue = null;
		try {
			factory.setBean(new CatalogQueueBean());
			StringBuilder query = new StringBuilder();
			if (input.hasqId()) {
				query.append("select * from catalog_queue where q_id = ").append(input.getqId());
				queue = factory.selectQuery(query.toString(), conn);
			}
			else {
				queue = Collections.emptyList();
			}

		} catch(BaseException e) {
			queue = Collections.emptyList();
		}
		return queue.stream().findFirst();
	}

	public void fillInputWithQueueData(final CatalogItemDetailQcInputBean input) throws BaseException {
		Connection conn = dbManager.getConnection();
		try {
			getCatalogQueueData(input, conn).ifPresent(q -> {
				input.setqId(q.getqId());
				input.setCatalogQueueRowStatus(q.getStatus());
				input.setRequestId(q.getCatalogAddRequestId());
				input.setLineItem(q.getLineItem());
			});

			String companyQuery = "select company_id from customer.catalog_add_request_new where"
					+ " request_id = " + input.getRequestId();

			String companyId = factory.selectSingleValue(companyQuery, conn);
			input.setCompanyId(companyId);

		} catch(BaseException e) {
			throw new BaseException(e);
		}finally {
			dbManager.returnConnection(conn);
		}
	}

	public void rejectCannotFulfill(CatalogItemDetailQcInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		rejectRequest(inputBean.getRejectionComments(), new BigDecimal(7), inputBean, personnelBean);
	}

	private void rejectRequest(String reason, BigDecimal rejectStatus, CatalogItemDetailQcInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		Connection connection = dbManager.getConnection();
		try {
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),"");
			engEvalProcess.setFactoryConnection(factory,connection);
			//insert approval role into catalog_add_approval
			if (engEvalProcess.insertApprovalData(inputBean.getRequestId(),inputBean.getApprovalRole(),"Rejected",reason,new BigDecimal(personnelBean.getPersonnelId()),"N")) {
				//reject request
				StringBuilder query = new StringBuilder();
				query.append("update catalog_add_request_new set request_status = ").append(rejectStatus)
						.append(",approval_group_seq = 0,request_rejected = 'Y'")
						.append(",eval_status = 'Rejected',last_updated = sysdate,approval_group_seq_start_time = null where request_id = ")
						.append(inputBean.getRequestId());
				factory.deleteInsertUpdate(query.toString(),connection);
				//send user email
				engEvalProcess.sendUserConfirmedEmail(inputBean.getRequestId());
			}
		}catch (Exception e) {
			log.error(e);
		}finally {
			dbManager.returnConnection(connection);
		}
	}  //end of method
}