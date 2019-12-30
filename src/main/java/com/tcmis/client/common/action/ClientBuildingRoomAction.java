package com.tcmis.client.common.action;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.client.common.beans.AreaBean;
import com.tcmis.client.common.beans.BuildingBean;
import com.tcmis.client.common.beans.FloorBean;
import com.tcmis.client.common.beans.RoomBean;
import com.tcmis.client.common.process.ClientBuildingRoomProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;

/******************************************************************************
 * Controller for Manufacturer Search page
 * @version 1.0
 ******************************************************************************/

public class ClientBuildingRoomAction extends TcmISBaseAction {

	private void doSearch(DynaBean dynaForm, PersonnelBean user) throws Exception {
		String facilityId = (String) dynaForm.get("facilityId");
		ClientBuildingRoomProcess process = new ClientBuildingRoomProcess(getDbUser());
		request.setAttribute("areas", process.getAreasBldgsRoomsForFacility(user.getCompanyId(), facilityId));
		saveTcmISToken(request);
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {
		ActionForward next = mapping.findForward("success");
		if (!isLoggedIn(request)) {
			request.setAttribute("requestedPage", "managebuildingrooms");
			request.setAttribute("requestedURLWithParameters", getRequestedURLWithParameters(request));
			next = mapping.findForward("login");
		}
		else {

			PersonnelBean user = (PersonnelBean) getSessionObject(request, "personnelBean");
			String userAction = request.getParameter("uAction");

			if (form != null && userAction != null) {
				DynaBean dynaForm = (DynaBean) form;

				if (userAction.equals("search")) {
					doSearch(dynaForm, user);
				}
				else if (userAction.equals("updateAreas")) {
					Collection<AreaBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "areas", new AreaBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientBuildingRoomProcess process = new ClientBuildingRoomProcess(getDbUser());
						for (AreaBean area : updatedRows) {
							if (area.hasName()) {
								if (area.isNewArea()) {
									area.setCompanyId(user.getCompanyId());
									process.insertArea(area);
								}
								else {
									process.updateArea(area);
								}
							}
						}
					}
					doSearch(dynaForm, user);
				}
				else if (userAction.equals("updateBuildings")) {
					Collection<BuildingBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "buildings", new BuildingBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientBuildingRoomProcess process = new ClientBuildingRoomProcess(getDbUser());
						for (BuildingBean building : updatedRows) {
							if (building.hasName()) {
								if (building.isNewBuilding()) {
									building.setCompanyId(user.getCompanyId());
									process.insertBuilding(building);
								}
								else {
									process.updateBuilding(building);
								}
							}
						}
					}
					doSearch(dynaForm, user);
				}
				else if (userAction.equals("updateFloors")) {
					Collection<FloorBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "floors", new FloorBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientBuildingRoomProcess process = new ClientBuildingRoomProcess(getDbUser());
						for (FloorBean floor : updatedRows) {
							if (floor.hasDescription()) {
								if (floor.isNewFloor()) {
									floor.setCompanyId(user.getCompanyId());
									process.insertFloor(floor);
								}
								else {
									process.updateFloor(floor);
								}
							}
						}
					}
					doSearch(dynaForm, user);
				}				else if (userAction.equals("updateRooms")) {
					Collection<RoomBean> updatedRows = BeanHandler.getGridBeans(dynaForm, "rooms", new RoomBean(), getLocale(request), "updated");
					if (updatedRows != null && !updatedRows.isEmpty()) {
						ClientBuildingRoomProcess process = new ClientBuildingRoomProcess(getDbUser());
						for (RoomBean room : updatedRows) {
							if (room.hasName()) {
								if (room.isNewRoom()) {
									room.setCompanyId(user.getCompanyId());
									process.insertRoom(room);
								}
								else {
									process.updateRoom(room);
								}
							}
						}
					}
					doSearch(dynaForm, user);
				}
			}
		}
		return next;
	}
}
