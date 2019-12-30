package com.tcmis.client.waste.process;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.waste.beans.TsdfWasteReceivingInputBean;
import com.tcmis.client.waste.beans.WasteTsdfSourceViewBean;
import com.tcmis.client.waste.factory.TsdfReceivingViewBeanFactory;
import com.tcmis.client.waste.factory.WasteTsdfSourceViewBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for TDSF Waste Receiving
 * @version 1.0
 *****************************************************************************/
public class TsdfWasteReceivingProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public TsdfWasteReceivingProcess(String client) {
		super(client);
	}

	public Collection getFlatSearchData(int userId)  throws BaseException {

		DbManager dbManager = new DbManager(getClient());
		WasteTsdfSourceViewBeanFactory factory = new WasteTsdfSourceViewBeanFactory(dbManager);
		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS,(new Integer(userId)).toString());
		//do your search criteria here
		return factory.select(criteria);
	}

	public Collection getNormalizedSearchData(int userId)  throws BaseException {
		return this.getNormalizedResultCollection(this.getFlatSearchData(userId));
	}

	private Collection getNormalizedResultCollection(Collection c) throws BaseException {
		Collection normalizedCollection = new Vector(c.size());
		if(c.size() > 0) {
			try {
				Iterator flatIterator = c.iterator();
				String previousTsdfEpaId = "";
				String previousGeneratorCompanyId = "";

				WasteTsdfSourceViewBean tsdfEpaBean = null;
				WasteTsdfSourceViewBean generatorCompanyBean = null;
				WasteTsdfSourceViewBean generatorBean = null;
				while (flatIterator.hasNext()) {
					WasteTsdfSourceViewBean flatBean = (WasteTsdfSourceViewBean) flatIterator.next();
					if (!previousTsdfEpaId.equalsIgnoreCase(flatBean.getTsdfEpaId())) {
						//new bean
						if (previousTsdfEpaId.length() == 0) {
							//first time in loop
							tsdfEpaBean = new WasteTsdfSourceViewBean();
							generatorCompanyBean = new WasteTsdfSourceViewBean();
							generatorBean = new WasteTsdfSourceViewBean();
							this.copyGeneratorData(flatBean, generatorBean);
							this.copyCompanyData(flatBean, generatorCompanyBean);
							this.copyTsdfEpaData(flatBean, tsdfEpaBean);
						}
						else {
							//different tsdf
							generatorCompanyBean.addTsdfFacilityIdForGeneratorBeanCollection( (WasteTsdfSourceViewBean)generatorBean.clone());
							tsdfEpaBean.addGeneratorCompanyIdBeanCollection( (WasteTsdfSourceViewBean)generatorCompanyBean.clone());
							normalizedCollection.add( tsdfEpaBean.clone());
							tsdfEpaBean = new WasteTsdfSourceViewBean();
							generatorCompanyBean = new WasteTsdfSourceViewBean();
							generatorBean = new WasteTsdfSourceViewBean();
							this.copyGeneratorData(flatBean, generatorBean);
							this.copyCompanyData(flatBean, generatorCompanyBean);
							this.copyTsdfEpaData(flatBean, tsdfEpaBean);
						}
					}
					else {
						//same mr line
						if (!previousGeneratorCompanyId.equalsIgnoreCase(flatBean.getGeneratorCompanyId())) {
							//new needed date
							generatorCompanyBean.addTsdfFacilityIdForGeneratorBeanCollection( (WasteTsdfSourceViewBean)generatorBean.clone());
							tsdfEpaBean.addGeneratorCompanyIdBeanCollection( (WasteTsdfSourceViewBean) generatorCompanyBean.clone());
							generatorCompanyBean = new WasteTsdfSourceViewBean();
							generatorBean = new WasteTsdfSourceViewBean();
							this.copyGeneratorData(flatBean, generatorBean);
							this.copyCompanyData(flatBean, generatorCompanyBean);
						}
						else {
							//same needed date
							generatorCompanyBean.addTsdfFacilityIdForGeneratorBeanCollection( (WasteTsdfSourceViewBean)generatorBean.clone());
							generatorBean = new WasteTsdfSourceViewBean();
							this.copyGeneratorData(flatBean, generatorBean);
						}
					}
					previousTsdfEpaId = flatBean.getTsdfEpaId();
					previousGeneratorCompanyId = flatBean.getGeneratorCompanyId();
				}
				generatorCompanyBean.addTsdfFacilityIdForGeneratorBeanCollection( (WasteTsdfSourceViewBean) generatorBean.clone());
				tsdfEpaBean.addGeneratorCompanyIdBeanCollection( (WasteTsdfSourceViewBean) generatorCompanyBean.clone());
				normalizedCollection.add( tsdfEpaBean.clone());
			}
			catch(Exception e) {
				log.error("Error normalizing data", e);
				throw new BaseException(e);
			}
		}
		return normalizedCollection;
	} //end of method

	private void copyTsdfEpaData(WasteTsdfSourceViewBean fromBean, WasteTsdfSourceViewBean toBean) {
		toBean.setTsdfCompanyId(fromBean.getTsdfCompanyId());
		toBean.setTsdfEpaId(fromBean.getTsdfEpaId());
		toBean.setTsdfName(fromBean.getTsdfName());
		toBean.setTsdfFacilityId(fromBean.getTsdfFacilityId());
		toBean.setTsdfLocationDesc(fromBean.getTsdfLocationDesc());
		toBean.setTsdfLocationStatus(fromBean.getTsdfLocationStatus());
	}

	private void copyCompanyData(WasteTsdfSourceViewBean fromBean, WasteTsdfSourceViewBean toBean) {
		toBean.setGeneratorCompanyId(fromBean.getGeneratorCompanyId());
	}

	private void copyGeneratorData(WasteTsdfSourceViewBean fromBean, WasteTsdfSourceViewBean toBean) {
		toBean.setGeneratorFacilityId(fromBean.getGeneratorFacilityId());
		toBean.setGeneratorWasteLocationId(fromBean.getGeneratorWasteLocationId());
		toBean.setTsdfFacilityIdForGenerator(fromBean.getTsdfFacilityIdForGenerator());
		toBean.setGeneratorActive(fromBean.getGeneratorActive());
		toBean.setGeneratorLocationDesc(fromBean.getGeneratorLocationDesc());
		toBean.setGeneratorLocationStatus(fromBean.getGeneratorLocationStatus());
	}

	public void receive(Collection c, int receiverId) throws Exception  {
		try {
			Iterator iterator = c.iterator();
			while (iterator.hasNext()) {
				TsdfWasteReceivingInputBean inputBean = (TsdfWasteReceivingInputBean)iterator.next();
				if (!StringHandler.isBlankString(inputBean.getOk())) {
					DbManager dbManager = new DbManager(this.getClient());
					TsdfReceivingViewBeanFactory factory = new TsdfReceivingViewBeanFactory(dbManager);
					if (log.isDebugEnabled()) {
						log.debug("tsdfWasteReceivingProcess: "+factory.createTsdfWasteRequest(inputBean,receiverId));
					}
				}
			}
		}catch (Exception e) {
			log.error("Exception in tsdf waste receiving receive(): " + e);
			throw e;
		}
	} //end of method

	public Collection getTsdfWaste(TsdfWasteReceivingInputBean inputBean) throws BaseException {
		Collection ilmBeans = null;
		DbManager dbManager = new DbManager(this.getClient());
		try {
			TsdfReceivingViewBeanFactory factory = new TsdfReceivingViewBeanFactory(dbManager);
			SearchCriteria criteria = new SearchCriteria();
			//generator waste location
			if (!StringHandler.isBlankString(inputBean.getTsdfFacilityIdForGenerator())) {
				String[] temp = inputBean.getTsdfFacilityIdForGenerator().split(":::");
				if (temp.length == 2) {
					//facility id
					criteria.addCriterion("generatorFacilityId", SearchCriterion.EQUALS,temp[0]);
					//waste location id
					criteria.addCriterion("generatorWasteLocationId", SearchCriterion.EQUALS,temp[1]);
				}
			}
			//tsdf
			if (!StringHandler.isBlankString(inputBean.getTsdf())) {
				criteria.addCriterion("vendorId", SearchCriterion.EQUALS,inputBean.getTsdf());
			}
			//generator company
			if (!StringHandler.isBlankString(inputBean.getGeneratorCompany())) {
				criteria.addCriterion("generatorCompanyId", SearchCriterion.EQUALS,inputBean.getGeneratorCompany());
			}
			ilmBeans = factory.selectOV(criteria);
		} catch (Exception e) {
			log.error("Base Exception in getTsdfWaste: " + e);
		} finally {
			dbManager = null;
		}

		return ilmBeans;
	} //end of method

}
