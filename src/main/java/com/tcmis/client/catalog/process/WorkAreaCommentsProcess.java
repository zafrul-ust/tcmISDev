package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.FacLocAppPartCommentBean;
import com.tcmis.client.catalog.factory.FacLocAppPartCommentBeanFactory;
import com.tcmis.client.catalog.factory.FacLocAppPartCommentsViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class WorkAreaCommentsProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public WorkAreaCommentsProcess(String client) {
		super(client);
	 }

 public WorkAreaCommentsProcess(String client,Locale locale) {
		super(client,locale);
	 }

 public Collection getsearchResult(String catPartNo, String partGroupNo,
	String catalogId, String facilityId, String workArea) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	FacLocAppPartCommentsViewBeanFactory factory = new
	 FacLocAppPartCommentsViewBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (catPartNo != null && catPartNo.length() > 0) {
	 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, catPartNo);
	}

	if (catalogId != null && catalogId.length() > 0) {
	 criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
	}

	if (partGroupNo != null && partGroupNo.length() > 0) {
	 criteria.addCriterion("partGroupNo", SearchCriterion.EQUALS, partGroupNo);
	}

	if (facilityId != null && facilityId.length() > 0 &&
	 !"All".equalsIgnoreCase(facilityId)) {
	 criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
	}

	if (facilityId != null && facilityId.length() > 0 && !"All".equalsIgnoreCase(facilityId)) {
		if (workArea != null) {
			if ("My Work Areas".equalsIgnoreCase(workArea)) {
				criteria.addCriterion("application", SearchCriterion.EQUALS, "All");
			}else {
				criteria.addCriterion("application", SearchCriterion.EQUALS, workArea);
			}
		}
	}

	return factory.select(criteria);
 }

 public void deleteComment(String commentID) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	FacLocAppPartCommentBeanFactory factory = new FacLocAppPartCommentBeanFactory(
	 dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (commentID != null && commentID.length() > 0) {
	 criteria.addCriterion("commentId", SearchCriterion.EQUALS, commentID);
	}

	factory.delete(criteria);
 }

 public void updateComment(FacLocAppPartCommentBean facLocAppPartCommentBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());

	FacLocAppPartCommentBeanFactory factory = new FacLocAppPartCommentBeanFactory(
	 dbManager);

	factory.update(facLocAppPartCommentBean);
 }

 public void insertComment(FacLocAppPartCommentBean facLocAppPartCommentBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());

	FacLocAppPartCommentBeanFactory factory = new FacLocAppPartCommentBeanFactory(
	 dbManager);

	factory.insert(facLocAppPartCommentBean);
 }

}