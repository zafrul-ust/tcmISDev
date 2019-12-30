package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.AreaBean;
import com.tcmis.client.common.beans.BuildingBean;
import com.tcmis.client.common.beans.FacAreaBldgRmOvBean;
import com.tcmis.client.common.beans.FloorBean;
import com.tcmis.client.common.beans.RoomBean;
import com.tcmis.client.common.factory.AreaBeanFactory;
import com.tcmis.client.common.factory.BuildingBeanFactory;
import com.tcmis.client.common.factory.FacAreaBldgRmOvBeanFactory;
import com.tcmis.client.common.factory.FloorBeanFactory;
import com.tcmis.client.common.factory.RoomBeanFactory;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ClientBuildingRoomProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());
	private BuildingBeanFactory buildingFactory;
	private RoomBeanFactory roomFactory;
	private FloorBeanFactory floorFactory;
	private AreaBeanFactory areaFactory;
	private FacAreaBldgRmOvBeanFactory facAreaBldgRmOvBeanFactory;

	public ClientBuildingRoomProcess(String client) {
		super(client);
	}


	public Collection<FacAreaBldgRmOvBean> getAreasBldgsRoomsForFacility(String companyId, String facilityId) throws BaseException, Exception {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		SortCriteria sort = new SortCriteria("facilityId");
		return getFacAreaBldgRmOvBeanFactory().selectObject(criteria, sort);
	}


	public String getNextId() throws BaseException, Exception {
		return dbManager.getOracleSequence("area_building_room_seq") + "";
	}

	public int insertArea(AreaBean bean) throws BaseException, Exception {
		bean.setAreaId(getNextId());
		return getAreaFactory().insert(bean);
	}

	public int updateArea(AreaBean bean) throws BaseException, Exception {
		return getAreaFactory().update(bean);
	}

	public int insertBuilding(BuildingBean bean) throws BaseException, Exception {
		bean.setBuildingId(getNextId());
		return getBuildingFactory().insert(bean);
	}

	public int updateBuilding(BuildingBean bean) throws BaseException, Exception {
		return getBuildingFactory().update(bean);
	}

	public int insertFloor(FloorBean bean) throws BaseException, Exception {
		bean.setFloorId(new BigDecimal(getNextId()));
		return getFloorFactory().insert(bean);
	}

	public int updateFloor(FloorBean bean) throws BaseException, Exception {
		return getFloorFactory().update(bean);
	}

	public int insertRoom(RoomBean bean) throws BaseException, Exception {
		bean.setRoomId(getNextId());
		return getRoomFactory().insert(bean);
	}

	public int updateRoom(RoomBean bean) throws BaseException, Exception {
		return getRoomFactory().updateMinimum(bean);
	}

	public BuildingBeanFactory getBuildingFactory() {
		if (buildingFactory == null) {
			buildingFactory = new BuildingBeanFactory(dbManager);
		}
		return buildingFactory;
	}

	public RoomBeanFactory getRoomFactory() {
		if (roomFactory == null) {
			roomFactory = new RoomBeanFactory(dbManager);
		}
		return roomFactory;
	}

	public FloorBeanFactory getFloorFactory() {
		if (floorFactory == null) {
			floorFactory = new FloorBeanFactory(dbManager);
		}
		return floorFactory;
	}

	public AreaBeanFactory getAreaFactory() {
		if (facAreaBldgRmOvBeanFactory == null) {
			areaFactory = new AreaBeanFactory(dbManager);
		}
		return areaFactory;
	}

	public FacAreaBldgRmOvBeanFactory getFacAreaBldgRmOvBeanFactory() {
		if (facAreaBldgRmOvBeanFactory == null) {
			facAreaBldgRmOvBeanFactory = new FacAreaBldgRmOvBeanFactory(dbManager);
		}
		return facAreaBldgRmOvBeanFactory;
	}

}
