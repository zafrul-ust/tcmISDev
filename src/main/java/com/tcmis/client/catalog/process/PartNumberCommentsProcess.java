package com.tcmis.client.catalog.process;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatPartCommentBean;
import com.tcmis.client.catalog.factory.CatPartCommentBeanFactory;
import com.tcmis.client.catalog.factory.CatPartCommentsViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class PartNumberCommentsProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public PartNumberCommentsProcess(String client) {
	super(client);
 }

 public Collection getsearchResult(String catPartNo,
	String catalogId) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	CatPartCommentsViewBeanFactory factory = new CatPartCommentsViewBeanFactory(
	 dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (catPartNo != null && catPartNo.length() > 0) {
	 criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, catPartNo);
	}

	if (catalogId != null && catalogId.length() > 0) {
	 criteria.addCriterion("catalogId", SearchCriterion.EQUALS, catalogId);
	}

	return factory.select(criteria);
 }

 public void deleteComment(String commentID) throws BaseException {
	DbManager dbManager = new DbManager(getClient());

	CatPartCommentBeanFactory factory = new CatPartCommentBeanFactory(dbManager);

	SearchCriteria criteria = new SearchCriteria();

	if (commentID != null && commentID.length() > 0) {
	 criteria.addCriterion("commentId", SearchCriterion.EQUALS, commentID);
	}

	factory.delete(criteria);
 }

 public void updateComment(CatPartCommentBean catPartCommentBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());

	CatPartCommentBeanFactory factory = new CatPartCommentBeanFactory(dbManager);

	factory.update(catPartCommentBean);
 }

 public void insertComment(CatPartCommentBean catPartCommentBean) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());

	CatPartCommentBeanFactory factory = new CatPartCommentBeanFactory(dbManager);

	factory.insert(catPartCommentBean);
 }

}