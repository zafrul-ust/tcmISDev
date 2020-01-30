package com.tcmis.client.api.factory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;

import com.tcmis.client.api.beans.EcommerceShipmentNotificationBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.common.admin.beans.FeatureReleaseBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SqlHandler;

public class CreateMrDataMapper extends GenericSqlFactory implements ICreateMrDataMapper {

	public CreateMrDataMapper() {
		super();
	}

	public CreateMrDataMapper(DbManager dbManager, Object bean) {
		super(dbManager, bean);
	}

	public CreateMrDataMapper(DbManager dbManager) {
		super(dbManager);
	}

	@Override
	public BigDecimal getPersonnelIdFromAddressId(String addressId) throws BaseException {
		StringBuilder query = new StringBuilder("select personnel_id from company_application_logon")
				.append(" where company_application_logon_id = ")
				.append(SqlHandler.delimitString(addressId));
		
		return new BigDecimal(selectSingleValue(query.toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<CatalogInputBean> getPrRulesByCompanyFacility(String companyId, String facilityId)
			throws BaseException {
		StringBuilder query = new StringBuilder("select account_sys_id, charge_type")
				.append(" from pr_rules where company_id = ")
				.append(SqlHandler.delimitString(companyId))
				.append(" and facility_id = ")
				.append(SqlHandler.delimitString(facilityId));
		
		this.setBean(new CatalogInputBean());
		return selectQuery(query.toString()).stream().findFirst();
	}

	@Override
	public RequestLineItemBean getRequestLineItemByPrNumber(BigDecimal prNumber) throws BaseException {
		StringBuilder query = new StringBuilder("select payload_id, pr_number, po_number, required_datetime")
				.append(" from request_line_item where pr_number = ").append(prNumber);
		this.setBean(new RequestLineItemBean());
		try {
			RequestLineItemBean bean = (RequestLineItemBean)selectQuery(query.toString()).stream().findFirst().get();
			return bean;
		} catch(NoSuchElementException e) {
			throw new BaseException(e);
		}
	}

	@Override
	public void setEndUserContactInfo(BigDecimal prNumber, String endUser, String contactInfo) throws BaseException {
		StringBuilder query = new StringBuilder("update purchase_request")
				.append(" set end_user = ").append(SqlHandler.delimitString(endUser))
				.append(", contact_info = ").append(SqlHandler.delimitString(contactInfo))
				.append(" where pr_number = ").append(prNumber);
		
		deleteInsertUpdate(query.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<RequestLineItemBean> getPrLineFromPoLine(String poNumber, String releaseNumber, 
			String companyId, String facilityId, String catalogCompanyId, String catalogId) throws BaseException {
		StringBuilder query = new StringBuilder("select pr_number, line_item from request_line_item")
				.append(" where po_number = ").append(SqlHandler.delimitString(poNumber))
				.append(" and release_number = ").append(SqlHandler.delimitString(releaseNumber))
				.append(" and company_id = ").append(SqlHandler.delimitString(companyId))
				.append(" and facility_id = ").append(SqlHandler.delimitString(facilityId))
				.append(" and catalog_company_id = ").append(SqlHandler.delimitString(catalogCompanyId))
				.append(" and catalog_id = ").append(SqlHandler.delimitString(catalogId));
		
		this.setBean(new RequestLineItemBean());
		return selectQuery(query.toString()).stream().findFirst();
	}

	@Override
	public void updateMrLine(RequestLineItemBean rli) throws BaseException {
		StringBuilder stmt = new StringBuilder("update request_line_item set quantity = ")
				.append(rli.getQuantity())
				.append(" where quantity != ").append(rli.getQuantity())
				.append(" and pr_number = ").append(rli.getPrNumber())
				.append(" and line item = ").append(SqlHandler.delimitString(rli.getLineItem()));
		
		stmt = new StringBuilder("update request_line_item set quantity = ")
				.append(rli.getQuantity())
				.append(" where quantity != ").append(rli.getQuantity())
				.append(" and pr_number = ").append(rli.getPrNumber())
				.append(" and line_item = ").append(SqlHandler.delimitString(rli.getLineItem()));
		
		deleteInsertUpdate(stmt.toString());
	}
	
	@Override
	public void cancelMrLine(RequestLineItemBean rli) throws BaseException {
		StringBuilder query = new StringBuilder("update request_line_item ")
				.append("set cancel_request = sysdate,cancel_status = 'rqcancel'")
				.append(",request_line_status = 'Pending Cancellation',cancel_comment = ''")
				.append(",cancel_requester = ").append(rli.getCancelRequester())
	            .append(" where pr_number = ").append(rli.getPrNumber())
	            .append(" and line_item = ").append(SqlHandler.delimitString(rli.getLineItem()));
		
		deleteInsertUpdate(query.toString());
	}
	
	@Override
	public EcommerceShipmentNotificationBean getRequestByPrNumber(BigDecimal prNumber) throws BaseException {
		StringBuilder query = new StringBuilder("select pr.pr_number, rli.po_number, pr.contact_info, pr.end_user, rli.application_desc")
				.append(" from purchase_request pr, request_line_item rli")
				.append(" where pr.pr_number = ").append(prNumber)
				.append(" and rli.pr_number = pr.pr_number");
		
		this.setBean(new EcommerceShipmentNotificationBean());
		return (EcommerceShipmentNotificationBean)selectQuery(query.toString()).stream().findFirst().get();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Collection<EcommerceShipmentNotificationBean> getRequestLinesByPrNumber(BigDecimal prNumber) throws BaseException {
		StringBuilder query = new StringBuilder("select line_item, part_description, quantity, fac_part_no")
				.append(" from line_item_ii_view where pr_number = ").append(prNumber);
		
		this.setBean(new EcommerceShipmentNotificationBean());
		return selectQuery(query.toString());
	}
	
	@Override
	public boolean isSendOrderConfirmationEmail(BigDecimal prNumber) {
		StringBuilder query = new StringBuilder("select scope from feature_release fr, purchase_request pr")
				.append(" where fr.feature = 'SendPRConfirmation'")
				.append(" and pr.pr_number = ").append(prNumber)
				.append(" and pr.company_id = fr.company_id")
				.append(" and scope in ('ALL', pr.facility_id)")
				.append(" and active = 'Y'");
		
		this.setBean(new FeatureReleaseBean());
		boolean empty = false;
		try {
			empty = CollectionUtils.isEmpty(selectQuery(query.toString()));
		} catch(BaseException e) {
			empty = true;
		}
		return ! empty;
	}
	
}
