package com.tcmis.client.api.factory;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.order.beans.RequestLineItemBean;
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
		
		String personnelId = selectSingleValue(query.toString());
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
	
}
