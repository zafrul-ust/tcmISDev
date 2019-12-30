package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.hub.beans.ExpertReviewEntityBean;
import com.tcmis.internal.hub.beans.ExpertReviewItemBean;

public class ExpertReviewEntityProcess extends GenericProcess {

	public ExpertReviewEntityProcess(String client) {
		super(client);
	}
	
	public ExpertReviewEntityProcess(String client, String locale) {
		super(client, locale);
	}
	
	public String getExpertReviewDesc(BigDecimal expertReviewId) throws BaseException {
		String query = "select description from global.expert_review where expert_review_id = "+SqlHandler.validBigDecimal(expertReviewId);
		return factory.selectSingleValue(query);
	}
	
	public Collection<ExpertReviewEntityBean> search(BigDecimal expertReviewId) throws BaseException {
		String query = "select "
				//+ "nvl(eri.status, 'I') status, "
				+ "ere.created_by, "
				+ "ere.date_created, "
				//+ "eri.last_updated_by, "
				//+ "eri.last_updated_date, "
				+ "fx_personnel_id_to_name(ere.created_by) created_by_name, "
				//+ "fx_personnel_id_to_name(eri.last_updated_by) last_updated_by_name, "
				+ "oe.ops_entity_id, "
				+ "oe.operating_entity_name, "
				+ "ere.po_review, "
				+ "ere.mr_review "
				+ "from global.expert_review_entity ere, operating_entity oe where "
				+ "ere.expert_review_id(+) = " + SqlHandler.validBigDecimal(expertReviewId)
				+ " and oe.ops_entity_id = ere.ops_entity_id(+)";
		
		return factory.setBean(new ExpertReviewEntityBean()).selectQuery(query);
	}
	
	public void update(BigDecimal expertReviewId, PersonnelBean user, Collection<ExpertReviewEntityBean> expertReview) throws BaseException {
		int updateCount = 0;
		int modifiedCount = 0;
		BaseException exception = new BaseException("An error occurred trying to update Expert Review Items.");
		for (ExpertReviewEntityBean expertReviewItem : expertReview) {
			if (expertReviewItem.isModified()) {
				modifiedCount++;
				if (expertReviewItem.isOk()) {
					String poReview = (expertReviewItem.isPoReview()?"'Y'":"'N'");
					String mrReview = (expertReviewItem.isMrReview()?"'Y'":"'N'");
					String updateStmt = "update global.expert_review_entity set " +
							//"status=" + status + ", " +
							//"last_updated_by=" + user.getPersonnelId() + ", " +
							//"last_updated_date=sysdate" +
							"po_review=" + poReview + ", " +
							"mr_review=" + mrReview +
							" where ops_entity_id = "+SqlHandler.delimitString(expertReviewItem.getOpsEntityId()) + 
							" and expert_review_id = "+SqlHandler.validBigDecimal(expertReviewId);
							//" and status <> " + status;
					
					try {
						int updatedRows = factory.deleteInsertUpdate(updateStmt);
						if (updatedRows == 0) {
							String insertStmt = "insert into global.expert_review_entity (ops_entity_id, expert_review_id, created_by, date_created, po_review, mr_review)" +
									" VALUES(" + SqlHandler.delimitString(expertReviewItem.getOpsEntityId()) + ", " +
									SqlHandler.validBigDecimal(expertReviewId) + ", " +
									user.getPersonnelId() + ", " +
									"sysdate, " +
									poReview + ", " +
									mrReview +
									")";
							
							updatedRows = factory.deleteInsertUpdate(insertStmt);
						}
						updateCount += updatedRows;
					}
					catch (BaseException e) {
						exception.addException(e);
					}
				}
				else {
					String deleteStmt = "delete from global.expert_review_entity where expert_review_id = " + 
							SqlHandler.validBigDecimal(expertReviewId) +
							" and ops_entity_id = " + SqlHandler.delimitString(expertReviewItem.getOpsEntityId());
					int updatedRows = factory.deleteInsertUpdate(deleteStmt);
					updateCount += updatedRows;
				}
			}
		}
		
		if (updateCount != modifiedCount) {
			throw exception;
		}
	}
}
