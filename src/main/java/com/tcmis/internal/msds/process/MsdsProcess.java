package com.tcmis.internal.msds.process;

import java.io.File;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.GeneralException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.msds.beans.MsdsInputBean;
import com.tcmis.internal.msds.factory.CurrentMsdsLoadViewBeanFactory;
import com.tcmis.internal.msds.factory.FacBldgFloorDeptEditOvBeanFactory;
import com.tcmis.internal.msds.factory.MillerMsdsSearchBeanFactory;
import com.tcmis.internal.msds.factory.VvCustomerMsdsStatusBeanFactory;
import com.tcmis.internal.msds.factory.VvMsdsHazardClassificationBeanFactory;

/******************************************************************************
 * Process for processing Msds data
 * @version 1.0
 *****************************************************************************/
public class MsdsProcess
extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());

	public MsdsProcess(String client) {
		super(client);
	}

	public Collection getFacilityDropDownData() throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting facility drowdown");
		}
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);
		return factory.selectObject(new SearchCriteria());
	}

	public Collection getStatusDropDownData() throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting status drowdown");
		}
		DbManager dbManager = new DbManager(getClient());
		VvCustomerMsdsStatusBeanFactory factory = new
		VvCustomerMsdsStatusBeanFactory(dbManager);
		return factory.select(new SearchCriteria("display", SearchCriterion.EQUALS,
		"Y"));
	}

	public Collection getHazardClassificationDropDownData() throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting hazard drowdown");
		}
		DbManager dbManager = new DbManager(getClient());
		VvMsdsHazardClassificationBeanFactory factory = new
		VvMsdsHazardClassificationBeanFactory(dbManager);
		return factory.select(new SearchCriteria());
	}

	public Collection getLocations(MsdsInputBean bean) throws
	BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting locations " + bean.getMsds());
		}
		DbManager dbManager = new DbManager(getClient());
		SearchCriteria criteria = new SearchCriteria();
		if (bean.getMsds() != null && bean.getMsds().trim().length() > 0) {
			criteria.addCriterion("customerMsdsNo", SearchCriterion.EQUALS,
					bean.getMsds());
		}

		CurrentMsdsLoadViewBeanFactory factory = new CurrentMsdsLoadViewBeanFactory(
				dbManager);
		return factory.select(criteria);
	}

	public Collection getSearchResult(MsdsInputBean bean) throws
	BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting search data" + bean.getRevisionDate());
		}
		DbManager dbManager = new DbManager(getClient());
		SearchCriteria criteria = new SearchCriteria();
		if (bean.getMsds() != null && bean.getMsds().trim().length() > 0) {
			criteria.addCriterion("customerMsdsNo", SearchCriterion.EQUALS,
					bean.getMsds());
		}
		if (bean.getStatus() != null && bean.getStatus().trim().length() > 0) {
			criteria.addCriterion("status", SearchCriterion.EQUALS, bean.getStatus());
		}
		if (bean.getTradeName() != null && bean.getTradeName().trim().length() > 0) {
			criteria.addCriterion("tradeName", SearchCriterion.EQUALS,
					bean.getTradeName());
		}
		if (bean.getManufacturerName() != null &&
				bean.getManufacturerName().trim().length() > 0) {
			criteria.addCriterion("manufacturerName", SearchCriterion.EQUALS,
					bean.getManufacturerName());
		}
		CurrentMsdsLoadViewBeanFactory factory = new CurrentMsdsLoadViewBeanFactory(
				dbManager);
		return factory.select(criteria);
	}

	public String getNextMillerMsdsSeq() throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);
		return factory.getNextMillerMsdsSeq().toString();
	}

	public void processMsds(MsdsInputBean bean) throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("processing msds");
		}
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);
		//if (bean.getMsds() == null || bean.getMsds().trim().length() < 1) {
		//  bean.setMsds(factory.getNextMillerMsdsSeq().toString());
		//}
		if (bean.getDepartment() != null &&
				bean.getDepartment().trim().length() < 1) {
			bean.setDepartment("ALL");
		}
		if (bean.getBuilding() != null && bean.getBuilding().trim().length() < 1) {
			bean.setBuilding("ALL");
		}
		if (bean.getFloor() != null && bean.getFloor().trim().length() < 1) {
			bean.setFloor("ALL");
		}

		if (bean.getFile() != null && bean.getFile().length() > 0) {
			//copy file to server
			bean.setOnLine("Y");
			File f = bean.getFile();
			String fileType = f.getName().substring(f.getName().lastIndexOf("."));
			bean.setFileName(bean.getMsds() + fileType);
			try {
				ResourceLibrary library = new ResourceLibrary("msds");
				FileHandler.copy(bean.getFile(),
						new File(library.getString("miller.msdsfile.path") +
								bean.getFileName()));
			}
			catch (Exception e) {
				BaseException be = new BaseException("Can't put msds file on server:" +
						e.getMessage());
				be.setRootCause(e);
				throw be;
			}
		}
		else {
			bean.setFileName(bean.getMsds());
			bean.setOnLine("N");
		}

		if (!this.isProcessInputValid(bean)) {
			BaseException be = new BaseException("Invalid input");
			throw be;
		}
		String message = factory.processMsds(bean);
		if (log.isDebugEnabled()) {
			log.debug("msds_data_load returned:" + message);
		}
		//if procedure doesn't return null then throw error
		if (message != null) {
			throw new GeneralException(message);
		}
	}

	public void updateMsds(MsdsInputBean bean) throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("updating msds");
		}
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);
		if (bean.getMsds() == null || bean.getMsds().trim().length() < 1) {
			BaseException be = new BaseException("Invalid input");
			throw be;
		}
		bean.setFacilityId("NONE");
		bean.setDepartment("NONE");
		bean.setBuilding("NONE");
		bean.setFloor("NONE");

		if (bean.getFile() != null && bean.getFile().length() > 0) {
			//copy file to server
			bean.setOnLine("Y");
			File f = bean.getFile();
			String fileType = f.getName().substring(f.getName().lastIndexOf("."));
			bean.setFileName(bean.getMsds() + fileType);
			try {
				ResourceLibrary library = new ResourceLibrary("msds");
				FileHandler.copy(bean.getFile(),
						new File(library.getString("miller.msdsfile.path") +
								bean.getFileName()));
			}
			catch (Exception e) {
				BaseException be = new BaseException("Can't put msds file on server:" +
						e.getMessage());
				be.setRootCause(e);
				throw be;
			}
		}
		else {
			bean.setFileName(bean.getFileName().substring(bean.getFileName().
					lastIndexOf("/") + 1));
		}

		String message = factory.processMsds(bean);
		if (log.isDebugEnabled()) {
			log.debug("msds_data_load returned:" + message);
		}
		//if procedure doesn't return null then throw error
		if (message != null) {
			throw new GeneralException(message);
		}
	}

	public void addMsdsLocation(MsdsInputBean bean) throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("adding msds location");
		}
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);

		if (!this.isProcessInputValid(bean)) {
			BaseException be = new BaseException("Invalid input");
			throw be;
		}
		bean.setFileName(bean.getFileName().substring(bean.getFileName().
				lastIndexOf("/") + 1));
		log.debug("FILE:" + bean.getFileName());
		String message = factory.processMsds(bean);
		if (log.isDebugEnabled()) {
			log.debug("msds_data_load returned:" + message);
		}
		//if procedure doesn't return null then throw error
		if (message != null) {
			throw new GeneralException(message);
		}
	}

	private boolean isProcessInputValid(MsdsInputBean bean) {
		if(log.isDebugEnabled()) {
			log.debug("msds:" + bean.getMsds());
			log.debug("status:" + bean.getStatus());
			log.debug("fac:" + bean.getFacilityId());
			log.debug("dep:" + bean.getDepartment());
			log.debug("bui:" + bean.getBuilding());
			log.debug("flo:" + bean.getFloor());
			log.debug("file:" + bean.getFileName());
		}
		if (bean == null ||
				bean.getMsds() == null || bean.getMsds().trim().length() < 1 ||
				bean.getStatus() == null || bean.getStatus().trim().length() < 1 ||
				bean.getFacilityId() == null ||
				bean.getFacilityId().trim().length() < 1 ||
				bean.getDepartment() == null ||
				bean.getDepartment().trim().length() < 1 ||
				bean.getBuilding() == null || bean.getBuilding().trim().length() < 1 ||
				bean.getFloor() == null || bean.getFloor().trim().length() < 1 ||
				bean.getFileName() == null || bean.getFileName().trim().length() < 1) {
			return false;
		}
		return true;
	}

	public void deleteMsdsLocation(MsdsInputBean bean) throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug("deleting msds location");
		}
		DbManager dbManager = new DbManager(getClient());
		FacBldgFloorDeptEditOvBeanFactory factory = new
		FacBldgFloorDeptEditOvBeanFactory(dbManager);

		if (!this.isDeleteInputValid(bean)) {
			BaseException be = new BaseException("Invalid input for delete");
			throw be;
		}
		String message = factory.deleteMsdsLocation(bean);
		if (log.isDebugEnabled()) {
			log.debug("msds_data_delete returned:" + message);
		}
		//if procedure doesn't return null then throw error
		if (message != null) {
			throw new GeneralException(message);
		}
	}

	private boolean isDeleteInputValid(MsdsInputBean bean) {
		if(log.isDebugEnabled()) {
			log.debug("msds:" + bean.getMsds());
			log.debug("fac:" + bean.getFacilityIdToDelete());
			log.debug("dep:" + bean.getDepartmentToDelete());
			log.debug("buil:" + bean.getBuildingToDelete());
			log.debug("floor:" + bean.getFloorToDelete());
		}

		if (bean == null ||
				bean.getMsds() == null || bean.getMsds().trim().length() < 1 ||
				bean.getFacilityIdToDelete() == null ||
				bean.getFacilityIdToDelete().trim().length() < 1 ||
				bean.getDepartmentToDelete() == null ||
				bean.getDepartmentToDelete().trim().length() < 1 ||
				bean.getBuildingToDelete() == null ||
				bean.getBuildingToDelete().trim().length() < 1 ||
				bean.getFloorToDelete() == null ||
				bean.getFloorToDelete().trim().length() < 1) {
			return false;
		}
		return true;
	}

	public Collection getMillerSearchResult(MsdsInputBean bean) throws
	BaseException {
		if (log.isDebugEnabled()) {
			log.debug("getting search data");
		}
		DbManager dbManager = new DbManager(getClient());
		if (bean.getFacilityId() != null &&
				bean.getFacilityId().trim().length() < 1) {
			bean.setFacilityId("All");
		}
		if (bean.getDepartment() != null &&
				bean.getDepartment().trim().length() < 1) {
			bean.setDepartment("All");
		}
		if (bean.getBuilding() != null && bean.getBuilding().trim().length() < 1) {
			bean.setBuilding("All");
		}
		if (bean.getFloor() != null && bean.getFloor().trim().length() < 1) {
			bean.setFloor("All");
		}

		MillerMsdsSearchBeanFactory factory = new MillerMsdsSearchBeanFactory(
				dbManager);
		Collection c = factory.select(bean);
		if(log.isDebugEnabled()) {
			log.debug("got " + c.size() + " records");
		}
		return c;
	}

}
