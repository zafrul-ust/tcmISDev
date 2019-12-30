package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.ExpertReviewItemBean;

public class ExpertReviewItemProcess extends GenericProcess {

	public ExpertReviewItemProcess(String client) {
		super(client);
	}
	
	public ExpertReviewItemProcess(String client, String locale) {
		super(client, locale);
	}
	
	public String getItemDesc(BigDecimal item) throws BaseException {
		String query = "select item_desc from item where item_id = "+SqlHandler.validBigDecimal(item);
		return factory.selectSingleValue(query);
	}
	
	public Collection<ExpertReviewItemBean> listsByItem(BigDecimal item) throws BaseException {
		String query = "select "
				+ "er.description "
				+ "from global.expert_review er, global.expert_review_item eri "
				+ "where eri.item_id(+) = " + SqlHandler.validBigDecimal(item) 
				+ " and eri.expert_review_id(+) = er.expert_review_id"
				+ " and eri.status = 'A'";
		
		return factory.setBean(new ExpertReviewItemBean()).selectQuery(query);
	}
	
	public Collection<ExpertReviewItemBean> search(BigDecimal item) throws BaseException {
		String query = "select "
				+ "er.expert_review_id, "
				+ "er.description, "
				+ "nvl(eri.status, 'I') status, "
				+ "eri.created_by, "
				+ "eri.date_created, "
				+ "eri.last_updated_by, "
				+ "eri.last_updated_date, "
				+ "fx_personnel_id_to_name(eri.created_by) created_by_name, "
				+ "fx_personnel_id_to_name(eri.last_updated_by) last_updated_by_name, "
				+ "ere.ops_entity_id, "
				+ "oe.operating_entity_name "
				+ "from global.expert_review er, global.expert_review_item eri, "
				+ "global.expert_review_entity ere, operating_entity oe where "
				+ "eri.item_id(+) = " + SqlHandler.validBigDecimal(item) 
				+ " and eri.expert_review_id(+) = er.expert_review_id"
				+ " and ere.expert_review_id = er.expert_review_id"
				+ " and oe.ops_entity_id = ere.ops_entity_id"
				+ " order by er.expert_review_id";
		
		return factory.setBean(new ExpertReviewItemBean()).selectQuery(query);
	}
	
	public void update(BigDecimal item, PersonnelBean user, Collection<ExpertReviewItemBean> expertReview) throws BaseException {
		int updateCount = 0;
		int modifiedCount = 0;
		BaseException exception = new BaseException("An error occurred trying to update Expert Review Items.");
		BigDecimal listId = new BigDecimal(-1);
		for (ExpertReviewItemBean expertReviewItem : expertReview) {
			if (expertReviewItem.isModified()) {
				// because of ops entities, we get multiple records for some lists
				// we want to skip consecutive lists
				if (expertReviewItem.getExpertReviewId().compareTo(listId) == 0) {
					continue;
				}
				listId = expertReviewItem.getExpertReviewId();
				modifiedCount++;
				String status = (expertReviewItem.isOk()?"'A'":"'I'");
				String updateStmt = "update global.expert_review_item set status=" + status + ", " +
						"last_updated_by=" + user.getPersonnelId() + ", " +
						"last_updated_date=sysdate" +
						" where item_id = "+SqlHandler.validBigDecimal(item) + 
						" and expert_review_id = "+SqlHandler.validBigDecimal(expertReviewItem.getExpertReviewId());
				
				try {
					int updatedRows = factory.deleteInsertUpdate(updateStmt);
					if (updatedRows == 0) {
						String insertStmt = "insert into global.expert_review_item (item_id, expert_review_id, created_by, last_updated_by, date_created, last_updated_date, status)" +
								" VALUES(" + SqlHandler.validBigDecimal(item) + ", " +
								SqlHandler.validBigDecimal(expertReviewItem.getExpertReviewId()) + ", " +
								user.getPersonnelId() + ", " +
								user.getPersonnelId() + ", " +
								"sysdate, sysdate, " +
								status +
								")";
						
						updatedRows = factory.deleteInsertUpdate(insertStmt);
					}
					updateCount += updatedRows;
				}
				catch (BaseException e) {
					exception.addException(e);
				}
			}
		}
		
		if (updateCount != modifiedCount) {
			throw exception;
		}
	}
}
