package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.common.beans.*;

/******************************************************************************
 * Process for PersonnelFacAreaBldgRmVwProcess
 * @version 1.0
 *****************************************************************************/
public class PersonnelFacAreaBldgRmVwProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public PersonnelFacAreaBldgRmVwProcess(String client,String locale)  {
		super(client,locale);
	}
	
	public Collection getFacilityAreaBldgRm(BigDecimal personnelId, String companyId)throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PersonnelFacAreaBldgRmVwBean());
        SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterion("personnelId", SearchCriterion.EQUALS,personnelId.toString());
        if (!StringHandler.isBlankString(companyId))
            criteria.addCriterion("companyId",SearchCriterion.EQUALS,companyId);
        SortCriteria sort = new SortCriteria();
		sort.addCriterion("companyId");
        sort.addCriterion("facilityName");
        sort.addCriterion("areaName");
        sort.addCriterion("buildingName");
        sort.addCriterion("floorDescription");
        sort.addCriterion("roomName");
        return createRelationalObject(factory.select(criteria,sort,"personnel_fac_area_bldg_rm_vw"));
	}

    private Collection createRelationalObject(Collection dataColl) {
        Vector resultColl = new Vector();
        Iterator iter = dataColl.iterator();
        String lastCompanyFacKey = "";
        String lastCompanyFacAreaKey = "";
        String lastCompanyFacAreaBldgKey = "";
        String lastCompanyFacAreaBldgFloorKey = "";
        int count = 0;
        while(iter.hasNext()) {
            PersonnelFacAreaBldgRmVwBean bean = (PersonnelFacAreaBldgRmVwBean)iter.next();
            String companyFacKey = bean.getCompanyId()+bean.getFacilityId();
            String companyFacAreaKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId();
            String companyFacAreaBldgKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId()+bean.getBuildingId();
            String companyFacAreaBldgFloorKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId()+bean.getBuildingId()+bean.getFloorId();

            PersonnelFacAreaBldgRmOvBean cBean;
            Vector facilityColl = new Vector();
            if (count == 0) {
                cBean = new PersonnelFacAreaBldgRmOvBean();
                cBean.setCompanyId(bean.getCompanyId());
                cBean.setPersonnelId(bean.getPersonnelId());
            }else {
                cBean = (PersonnelFacAreaBldgRmOvBean)resultColl.lastElement();
                facilityColl = (Vector)cBean.getFacilityList();
            }

            if (lastCompanyFacKey.equals(companyFacKey)) {
                FacNameAreaBldgRmObjBean fBean = (FacNameAreaBldgRmObjBean)facilityColl.lastElement();
                Vector areaColl = (Vector)fBean.getAreaList();
                if (lastCompanyFacAreaKey.equals(companyFacAreaKey)) {
                    AreaBldgFloorRmObjBean aBean = (AreaBldgFloorRmObjBean)areaColl.lastElement();
                    Vector bldgColl = (Vector)aBean.getBuildingList();
                    if (lastCompanyFacAreaBldgKey.equals(companyFacAreaBldgKey)) {
                        BldgFloorObjBean bBean = (BldgFloorObjBean)bldgColl.lastElement();
                        Vector floorColl = (Vector)bBean.getFloorList();
                        if (lastCompanyFacAreaBldgFloorKey.equals(companyFacAreaBldgFloorKey)) {
                            //room
                            if (bean.getRoomId() != null) {
                                FloorRoomObjBean flBean = (FloorRoomObjBean)floorColl.lastElement();
                                Vector roomColl = (Vector)flBean.getRoomList();
                                RoomObjBean rBean = new RoomObjBean();
                                rBean.setRoomId(bean.getRoomId());
                                rBean.setRoomName(bean.getRoomName());
                                rBean.setRoomDescription(bean.getRoomDescrition());
                                if ("Y".equals(bean.getInterior())) {
                                    rBean.setInterior(true);
                                }else {
                                    rBean.setInterior(false);
                                }
                                roomColl.add(rBean);
                            }
                        }else {
                            FloorRoomObjBean flBean = new FloorRoomObjBean();
                            flBean.setFloorId(bean.getFloorId());
                            flBean.setDescription(bean.getFloorDescription());
                            //room
                            if (bean.getRoomId() != null) {
                                Collection roomColl = new Vector();
                                RoomObjBean rBean = new RoomObjBean();
                                rBean.setRoomId(bean.getRoomId());
                                rBean.setRoomName(bean.getRoomName());
                                rBean.setRoomDescription(bean.getRoomDescrition());
                                if ("Y".equals(bean.getInterior())) {
                                    rBean.setInterior(true);
                                }else {
                                    rBean.setInterior(false);
                                }
                                roomColl.add(rBean);
                                flBean.setRoomList(roomColl);
                            }
                            //floor
                            if (bean.getFloorId() != null)
                                floorColl.add(flBean);
                        }
                    }else {
                        Collection floorColl = new Vector();

                        BldgFloorObjBean bBean = new BldgFloorObjBean();
                        bBean.setBuildingId(bean.getBuildingId());
                        bBean.setBuildingName(bean.getBuildingName());
                        bBean.setBuildingDescription(bean.getBuildingDescription());

                        FloorRoomObjBean flBean = new FloorRoomObjBean();
                        flBean.setFloorId(bean.getFloorId());
                        flBean.setDescription(bean.getFloorDescription());
                        //room
                        if (bean.getRoomId() != null) {
                            Collection roomColl = new Vector();
                            RoomObjBean rBean = new RoomObjBean();
                            rBean.setRoomId(bean.getRoomId());
                            rBean.setRoomName(bean.getRoomName());
                            rBean.setRoomDescription(bean.getRoomDescrition());
                            if ("Y".equals(bean.getInterior())) {
                                rBean.setInterior(true);
                            }else {
                                rBean.setInterior(false);
                            }
                            roomColl.add(rBean);
                            flBean.setRoomList(roomColl);
                        }
                        //floor
                        if (bean.getFloorId() != null) {
                            floorColl.add(flBean);
                            bBean.setFloorList(floorColl);
                        }
                        //building
                        if (bean.getBuildingId() != null)
                           bldgColl.add(bBean);
                    }
                }else {
                    Collection bldgColl = new Vector();
                    Collection floorColl = new Vector();

                    AreaBldgFloorRmObjBean aBean = new AreaBldgFloorRmObjBean();
                    aBean.setAreaId(bean.getAreaId());
                    aBean.setAreaName(bean.getAreaName());
                    aBean.setAreaDescription(bean.getAreaDescription());

                    BldgFloorObjBean bBean = new BldgFloorObjBean();
                    bBean.setBuildingId(bean.getBuildingId());
                    bBean.setBuildingName(bean.getBuildingName());
                    bBean.setBuildingDescription(bean.getBuildingDescription());

                    FloorRoomObjBean flBean = new FloorRoomObjBean();
                    flBean.setFloorId(bean.getFloorId());
                    flBean.setDescription(bean.getFloorDescription());
                    //room
                    if (bean.getRoomId() != null) {
                        Collection roomColl = new Vector();
                        RoomObjBean rBean = new RoomObjBean();
                        rBean.setRoomId(bean.getRoomId());
                        rBean.setRoomName(bean.getRoomName());
                        rBean.setRoomDescription(bean.getRoomDescrition());
                        if ("Y".equals(bean.getInterior())) {
                            rBean.setInterior(true);
                        }else {
                            rBean.setInterior(false);
                        }
                        roomColl.add(rBean);
                        flBean.setRoomList(roomColl);
                    }
                    //floor
                    if (bean.getFloorId() != null) {
                        floorColl.add(flBean);
                        bBean.setFloorList(floorColl);
                    }
                    //building
                    if (bean.getBuildingId() != null) {
                        bldgColl.add(bBean);
                        aBean.setBuildingList(bldgColl);
                    }
                    //area
                    if (bean.getAreaId() != null)
                        areaColl.add(aBean);
                }
            }else {
                Collection areaColl = new Vector();
                Collection bldgColl = new Vector();
                Collection floorColl = new Vector();

                FacNameAreaBldgRmObjBean fBean = new FacNameAreaBldgRmObjBean();
                fBean.setFacilityId(bean.getFacilityId());
                fBean.setFacilityName(bean.getFacilityName());

                AreaBldgFloorRmObjBean aBean = new AreaBldgFloorRmObjBean();
                aBean.setAreaId(bean.getAreaId());
                aBean.setAreaName(bean.getAreaName());
                aBean.setAreaDescription(bean.getAreaDescription());

                BldgFloorObjBean bBean = new BldgFloorObjBean();
                bBean.setBuildingId(bean.getBuildingId());
                bBean.setBuildingName(bean.getBuildingName());
                bBean.setBuildingDescription(bean.getBuildingDescription());

                FloorRoomObjBean flBean = new FloorRoomObjBean();
                flBean.setFloorId(bean.getFloorId());
                flBean.setDescription(bean.getFloorDescription());
                //room
                if (bean.getRoomId() != null) {
                    Collection roomColl = new Vector();
                    RoomObjBean rBean = new RoomObjBean();
                    rBean.setRoomId(bean.getRoomId());
                    rBean.setRoomName(bean.getRoomName());
                    rBean.setRoomDescription(bean.getRoomDescrition());
                    if ("Y".equals(bean.getInterior())) {
                        rBean.setInterior(true);
                    }else {
                        rBean.setInterior(false);
                    }
                    roomColl.add(rBean);
                    flBean.setRoomList(roomColl);
                }
                //floor
                if (bean.getFloorId() != null) {
                    floorColl.add(flBean);
                    bBean.setFloorList(floorColl);
                }
                //building
                if (bean.getBuildingId() != null) {
                    bldgColl.add(bBean);
                    aBean.setBuildingList(bldgColl);
                }
                //area
                if (bean.getAreaId() != null) {
                    areaColl.add(aBean);
                    fBean.setAreaList(areaColl);
                }
                facilityColl.add(fBean);
                cBean.setFacilityList(facilityColl);
                resultColl.add(cBean);
                //log.debug("first record:"+companyFacAreaBldgFloorKey+bean.getRoomId()+"*");
            }
            lastCompanyFacKey = companyFacKey;
            lastCompanyFacAreaKey = companyFacAreaKey;
            lastCompanyFacAreaBldgKey = companyFacAreaBldgKey;
            lastCompanyFacAreaBldgFloorKey = companyFacAreaBldgFloorKey;
            count++;
        } //end of loop

        return resultColl;
    } //end of method

    //this method returns object view by company
    public Collection createRelationalObjects(Collection dataColl) {
        Vector resultColl = new Vector();
        Iterator iter = dataColl.iterator();
        String lastCompanyKey = "";
        String lastCompanyFacKey = "";
        String lastCompanyFacAreaKey = "";
        String lastCompanyFacAreaBldgKey = "";
        String lastCompanyFacAreaBldgFloorKey = "";
        int count = 0;
        while(iter.hasNext()) {
            PersonnelFacAreaBldgRmVwBean bean = (PersonnelFacAreaBldgRmVwBean)iter.next();
            String companyKey = bean.getCompanyId();
            String companyFacKey = bean.getCompanyId()+bean.getFacilityId();
            String companyFacAreaKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId();
            String companyFacAreaBldgKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId()+bean.getBuildingId();
            String companyFacAreaBldgFloorKey = bean.getCompanyId()+bean.getFacilityId()+bean.getAreaId()+bean.getBuildingId()+bean.getFloorId();

            PersonnelFacAreaBldgRmOvBean cBean;
            Vector facilityColl = new Vector();
            if (!lastCompanyKey.equals(companyKey)) {
                cBean = new PersonnelFacAreaBldgRmOvBean();
                cBean.setCompanyId(bean.getCompanyId());
                cBean.setCompanyName(bean.getCompanyName());
                cBean.setPersonnelId(bean.getPersonnelId());
                
                resultColl.add(cBean);
            }else {
                cBean = (PersonnelFacAreaBldgRmOvBean)resultColl.lastElement();
                facilityColl = (Vector)cBean.getFacilityList();
            }
 
            if (lastCompanyFacKey.equals(companyFacKey)) {
                FacNameAreaBldgRmObjBean fBean = (FacNameAreaBldgRmObjBean)facilityColl.lastElement();
                Vector areaColl = (Vector)fBean.getAreaList();
                if (lastCompanyFacAreaKey.equals(companyFacAreaKey)) {
                    AreaBldgFloorRmObjBean aBean = (AreaBldgFloorRmObjBean)areaColl.lastElement();
                    Vector bldgColl = (Vector)aBean.getBuildingList();
                    if (lastCompanyFacAreaBldgKey.equals(companyFacAreaBldgKey)) {
                        BldgFloorObjBean bBean = (BldgFloorObjBean)bldgColl.lastElement();
                        Vector floorColl = (Vector)bBean.getFloorList();
                        if (lastCompanyFacAreaBldgFloorKey.equals(companyFacAreaBldgFloorKey)) {
                            //room
                            if (bean.getRoomId() != null) {
                                FloorRoomObjBean flBean = (FloorRoomObjBean)floorColl.lastElement();
                                Vector roomColl = (Vector)flBean.getRoomList();
                                RoomObjBean rBean = new RoomObjBean();
                                rBean.setRoomId(bean.getRoomId());
                                rBean.setRoomName(bean.getRoomName());
                                rBean.setRoomDescription(bean.getRoomDescrition());
                                if ("Y".equals(bean.getInterior())) {
                                    rBean.setInterior(true);
                                }else {
                                    rBean.setInterior(false);
                                }
                                roomColl.add(rBean);
                            }
                        }else {
                            FloorRoomObjBean flBean = new FloorRoomObjBean();
                            flBean.setFloorId(bean.getFloorId());
                            flBean.setDescription(bean.getFloorDescription());
                            //room
                            if (bean.getRoomId() != null) {
                                Collection roomColl = new Vector();
                                RoomObjBean rBean = new RoomObjBean();
                                rBean.setRoomId(bean.getRoomId());
                                rBean.setRoomName(bean.getRoomName());
                                rBean.setRoomDescription(bean.getRoomDescrition());
                                if ("Y".equals(bean.getInterior())) {
                                    rBean.setInterior(true);
                                }else {
                                    rBean.setInterior(false);
                                }
                                roomColl.add(rBean);
                                flBean.setRoomList(roomColl);
                            }
                            //floor
                            if (bean.getFloorId() != null)
                                floorColl.add(flBean);
                        }
                    }else {
                        Collection floorColl = new Vector();

                        BldgFloorObjBean bBean = new BldgFloorObjBean();
                        bBean.setBuildingId(bean.getBuildingId());
                        bBean.setBuildingName(bean.getBuildingName());
                        bBean.setBuildingDescription(bean.getBuildingDescription());

                        FloorRoomObjBean flBean = new FloorRoomObjBean();
                        flBean.setFloorId(bean.getFloorId());
                        flBean.setDescription(bean.getFloorDescription());
                        //room
                        if (bean.getRoomId() != null) {
                            Collection roomColl = new Vector();
                            RoomObjBean rBean = new RoomObjBean();
                            rBean.setRoomId(bean.getRoomId());
                            rBean.setRoomName(bean.getRoomName());
                            rBean.setRoomDescription(bean.getRoomDescrition());
                            if ("Y".equals(bean.getInterior())) {
                                rBean.setInterior(true);
                            }else {
                                rBean.setInterior(false);
                            }
                            roomColl.add(rBean);
                            flBean.setRoomList(roomColl);
                        }
                        //floor
                        if (bean.getFloorId() != null) {
                            floorColl.add(flBean);
                            bBean.setFloorList(floorColl);
                        }
                        //building
                        if (bean.getBuildingId() != null)
                           bldgColl.add(bBean);
                    }
                }else {
                    Collection bldgColl = new Vector();
                    Collection floorColl = new Vector();

                    AreaBldgFloorRmObjBean aBean = new AreaBldgFloorRmObjBean();
                    aBean.setAreaId(bean.getAreaId());
                    aBean.setAreaName(bean.getAreaName());
                    aBean.setAreaDescription(bean.getAreaDescription());

                    BldgFloorObjBean bBean = new BldgFloorObjBean();
                    bBean.setBuildingId(bean.getBuildingId());
                    bBean.setBuildingName(bean.getBuildingName());
                    bBean.setBuildingDescription(bean.getBuildingDescription());

                    FloorRoomObjBean flBean = new FloorRoomObjBean();
                    flBean.setFloorId(bean.getFloorId());
                    flBean.setDescription(bean.getFloorDescription());
                    //room
                    if (bean.getRoomId() != null) {
                        Collection roomColl = new Vector();
                        RoomObjBean rBean = new RoomObjBean();
                        rBean.setRoomId(bean.getRoomId());
                        rBean.setRoomName(bean.getRoomName());
                        rBean.setRoomDescription(bean.getRoomDescrition());
                        if ("Y".equals(bean.getInterior())) {
                            rBean.setInterior(true);
                        }else {
                            rBean.setInterior(false);
                        }
                        roomColl.add(rBean);
                        flBean.setRoomList(roomColl);
                    }
                    //floor
                    if (bean.getFloorId() != null) {
                        floorColl.add(flBean);
                        bBean.setFloorList(floorColl);
                    }
                    //building
                    if (bean.getBuildingId() != null) {
                        bldgColl.add(bBean);
                        aBean.setBuildingList(bldgColl);
                    }
                    //area
                    if (bean.getAreaId() != null)
                        areaColl.add(aBean);
                }
            }else if(bean.getFacilityId() != null) {
                Collection areaColl = new Vector();
                Collection bldgColl = new Vector();
                Collection floorColl = new Vector();

                FacNameAreaBldgRmObjBean fBean = new FacNameAreaBldgRmObjBean();
                fBean.setFacilityId(bean.getFacilityId());
                fBean.setFacilityName(bean.getFacilityName());
                fBean.setPutAwayRequired(bean.isPutAwayRequired());

                AreaBldgFloorRmObjBean aBean = new AreaBldgFloorRmObjBean();
                aBean.setAreaId(bean.getAreaId());
                aBean.setAreaName(bean.getAreaName());
                aBean.setAreaDescription(bean.getAreaDescription());

                BldgFloorObjBean bBean = new BldgFloorObjBean();
                bBean.setBuildingId(bean.getBuildingId());
                bBean.setBuildingName(bean.getBuildingName());
                bBean.setBuildingDescription(bean.getBuildingDescription());

                FloorRoomObjBean flBean = new FloorRoomObjBean();
                flBean.setFloorId(bean.getFloorId());
                flBean.setDescription(bean.getFloorDescription());
                //room
                if (bean.getRoomId() != null) {
                    Collection roomColl = new Vector();
                    RoomObjBean rBean = new RoomObjBean();
                    rBean.setRoomId(bean.getRoomId());
                    rBean.setRoomName(bean.getRoomName());
                    rBean.setRoomDescription(bean.getRoomDescrition());
                    if ("Y".equals(bean.getInterior())) {
                        rBean.setInterior(true);
                    }else {
                        rBean.setInterior(false);
                    }
                    roomColl.add(rBean);
                    flBean.setRoomList(roomColl);
                }
                //floor
                if (bean.getFloorId() != null) {
                    floorColl.add(flBean);
                    bBean.setFloorList(floorColl);
                }
                //building
                if (bean.getBuildingId() != null) {
                    bldgColl.add(bBean);
                    aBean.setBuildingList(bldgColl);
                }
                //area
                if (bean.getAreaId() != null) {
                    areaColl.add(aBean);
                    fBean.setAreaList(areaColl);
                }
                facilityColl.add(fBean);
                cBean.setFacilityList(facilityColl);
                
                //log.debug("first record:"+companyFacAreaBldgFloorKey+bean.getRoomId()+"*");
            }
            

            lastCompanyKey = companyKey;
            lastCompanyFacKey = companyFacKey;
            lastCompanyFacAreaKey = companyFacAreaKey;
            lastCompanyFacAreaBldgKey = companyFacAreaBldgKey;
            lastCompanyFacAreaBldgFloorKey = companyFacAreaBldgFloorKey;
            count++;
        } //end of loop

        return resultColl;
    } //end of method
} //end of class
