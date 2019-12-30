/*
 *  Copyright 1999-2000
 *
 *  URS Corporation, Radian
 *  All rights reserved
 */
package radian.tcmis.server7.share.servlets;

import java.sql.*;
import java.util.*;
import java.lang.reflect.Array;
import radian.tcmis.server7.share.helpers.*;
import radian.tcmis.both1.helpers.*;

import radian.tcmis.server7.share.dbObjs.*;

/**
 * Description of the Class
 *
 * @created   January 16, 2001
 */
public class Admin extends TcmisServlet {

	//Client Version
	String action = null;
	String userid = null;
	String function = null;
	String groupName = null;
	String groupDesc = null;
	Vector locs = null;
	Vector groups = null;
	Vector facids = null;
	Vector facnames = null;
	Vector facxapp = null;
	Vector appids = null;
	Vector appnames = null;
	Vector wareName = null;
	Vector wareID = null;
	Vector roles = null;
	Vector personnelIds = null;

	String facID = null;
	String personnelID = null;
	String role = null;
	String approvalType = null;
	String companyName = null;
	String facName = null;
	String facType = null;
	String mailLoc = null;
	String shipLoc = null;
	String branchPlant = null;
	String prefWare = null;
	String billTo = null;
	String jde = null;
	String locID = null;
	String address1 = null;
	String address2 = null;
	String address3 = null;
	String city = null;
	String state = null;
	String country = null;
	String zip = null;
	String clientLocCode = null;
	String client = null;
	Vector stateV = null;
	String workAreaID = null;
	String workAreaDesc = null;
	String workAreaLoc = null;
	String workAreaFac = null;
	String groupType = null;
	String memberId = null;
	boolean isRadian = false;
	Vector sendData = null;
	Vector outData = null;
	Vector groupIds = null;
	Vector memStatus = null;

	// trong 3/31
	Vector roleTypeDesc = null;
  //trong 1-29-01
  Vector statusV = null;
  Vector myUpdateStatus = null;

	/**
	 * Constructor for the Admin object
	 *
	 * @param b  Description of Parameter
	 * @param d  Description of Parameter
	 */
	public Admin(ServerResourceBundle b, TcmISDBModel d) {
		super();
		bundle = b;
		db = d;
	}

	/**
	 * Sets the RadianGroup attribute of the Admin object
	 */
	protected void setRadianGroup() {
		try {
			Personnel p = new Personnel(db);
			p.setPersonnelId(Integer.parseInt(userid));
			p.load();
			Vector v = p.getUserGroups();
			isRadian = false;
			for(int i = 0; i < v.size(); i++) {
				UserGroup ug = (UserGroup) v.elementAt(i);
				if(ug.getGroupId().equals("Radian")) {
					isRadian = true;
					break;
				}
			}
		} catch(Exception e) {
			isRadian = false;
		}
	}

	/**
	 * Gets the Result attribute of the Admin object
	 *
	 * @param httpI  Description of Parameter
	 */
	protected void getResult(HttpInput httpI) {
		action = httpI.getString("ACTION");
		userid = httpI.getString("USERID");
		function = httpI.getString("FUNCTION");
		groupName = httpI.getString("GROUP_NAME");
		groupDesc = httpI.getString("GROUP_DESC");
		groupType = httpI.getString("GROUP_TYPE");
		facType = httpI.getString("FAC_TYPE");

		locID = httpI.getString("LOCATION_ID");
		address1 = httpI.getString("ADDRESS_1");
		address2 = httpI.getString("ADDRESS_2");
		address3 = httpI.getString("ADDRESS_3");
		city = httpI.getString("CITY");
		state = httpI.getString("STATE");
		country = httpI.getString("COUNTRY");
		zip = httpI.getString("ZIP");
		clientLocCode = httpI.getString("CLIENT_LOC_CODE");
		client = httpI.getString("CLIENT");
		facID = httpI.getString("FACILITY_ID");
		facids = httpI.getVector("FAC_IDS");
		// this is a fix before we fix the client
		for(int i = 0; i < facids.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) facids.elementAt(i)).length() < 1) {
				facids.removeElementAt(i);
			}
		}

		facName = httpI.getString("FACILITY_NAME");
		companyName = httpI.getString("COMPANY_NAME");
		mailLoc = httpI.getString("MAIL_LOC");
		shipLoc = httpI.getString("SHIP_LOC");
		branchPlant = httpI.getString("BRANCH_PLANT");
		prefWare = httpI.getString("PREF_WAREHOUSE");
		billTo = httpI.getString("BILL_TO");
		jde = httpI.getString("JDE");
		personnelID = httpI.getString("PERSONNEL_ID");
		role = httpI.getString("ROLE");
		roles = httpI.getVector("ROLES");
		// this is a fix before we fix the client
		for(int i = 0; i < roles.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) roles.elementAt(i)).length() < 1) {
				roles.removeElementAt(i);
			}
		}

		personnelIds = httpI.getVector("PERSONNEL_IDS");
		// this is a fix before we fix the client
		for(int i = 0; i < personnelIds.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) personnelIds.elementAt(i)).length() < 1) {
				personnelIds.removeElementAt(i);
			}
		}
		approvalType = httpI.getString("APPROVAL_TYPE");
		memberId = httpI.getString("MEMBER_ID");

		workAreaID = httpI.getString("WORK_AREA_ID");
		workAreaDesc = httpI.getString("WORK_AREA_DESC");
		workAreaLoc = httpI.getString("WORK_AREA_LOC");
		workAreaFac = httpI.getString("WORK_AREA_FAC");
		groupIds = httpI.getVector("GROUP_IDS");
    // this is a fix before we fix the client 1-29-01
		for(int i = 0; i < groupIds.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) groupIds.elementAt(i)).length() < 1) {
				groupIds.removeElementAt(i);
			}
		}
		memStatus = httpI.getVector("MEMBERSHIP_STATUS");
    // this is a fix before we fix the client 1-29-01
		for(int i = 0; i < memStatus.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) memStatus.elementAt(i)).length() < 1) {
				memStatus.removeElementAt(i);
			}
		}
		locs = httpI.getVector("LOCATIONS");
    // this is a fix before we fix the client 1-29-01
		for(int i = 0; i < locs.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) locs.elementAt(i)).length() < 1) {
				locs.removeElementAt(i);
			}
		}
		appids = httpI.getVector("WORK_AREA_IDS");
    // this is a fix before we fix the client 1-29-01
		for(int i = 0; i < appids.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) appids.elementAt(i)).length() < 1) {
				appids.removeElementAt(i);
			}
		}
		appnames = httpI.getVector("WORK_AREA_DESC");
    // this is a fix before we fix the client 1-29-01
		for(int i = 0; i < appnames.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) appnames.elementAt(i)).length() < 1) {
				appnames.removeElementAt(i);
			}
		}

    myUpdateStatus = httpI.getVector("WORKAREA_STATUS");
    for(int i = 0; i < myUpdateStatus.size(); i++) {
			if(BothHelpObjs.makeBlankFromNull((String) myUpdateStatus.elementAt(i)).length() < 1) {
				myUpdateStatus.removeElementAt(i);
			}
		}
    //System.out.println("\n\n======== appids: "+appids+" "+facids+" "+appnames+" "+locs+" "+myUpdateStatus);

    setRadianGroup();

		if(action.equals("LOAD_SCREEN")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going getScreenData", getBundle());
			}
			getScreenData();
		} else if(action.equals("LOCATION")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			goLocation();
		} else if(action.equals("FACILITY")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			goFacility();
		} else if(action.equals("WORK_AREA")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			goWorkArea();
		} else if(action.equals("LOAD_FAC_APP")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getFacWorkArea();
		} else if(action.equals("LOAD_LOC")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getLocs();
		} else if(action.equals("LOAD_USER_GROUP")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getGroups();
		} else if(action.equals("LOAD_APPROVAL_ROLES")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getApprovalRoles();
		} else if(action.equals("ADD_ROLE")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addApprovalRole();

			//trong 3/27
		} else if(action.equals("UPDATE_ROLE")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			updateApprovalRole();

		} else if(action.equals("DELETE_ROLE")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteApprovalRole();
		} else if(action.equals("ADD_APPROVER")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addApprover();
		} else if(action.equals("DELETE_APPROVER")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteApprover();
		} else if(action.equals("LOAD_ADD_APPROVER")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			loadAddApprover();
		} else if(action.equals("LOAD_USE_APPROVAL_GROUP_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getUseApprovalGroupInfo();
		} else if(action.equals("LOAD_ALL_LOCATIONS")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getAllLocsInfo();
		} else if(action.equals("ADD_LOCATION")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addLocationId();
		} else if(action.equals("DELETE_LOCATION")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteLocation();
		} else if(action.equals("UPDATE_LOCATION")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			updateLocation();
		} else if(action.equals("CHANGE_GROUP_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			changeGroupInfo();
		} else if(action.equals("DELETE_GROUP")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteGroup();
		} else if(action.equals("ADD_DEL_GROUP_MEMBERS")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addDeleteGroupMembers();
		} else if(action.equals("LOAD_ADMIN_GROUP_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getAdminGroupInfo();
		} else if(action.equals("LOAD_MEMBER_GROUP_SCREEN")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			loadMemberGroupScreen();
		} else if(action.equals("UPDATE_GROUP_MEMBERSHIP")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			updateGroupMembership();
		} else if(action.equals("LOAD_REGULAR_FAC_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			loadRegularFacInfo();
		} else if(action.equals("SAVE_FAC_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			saveFacInfo();
		} else if(action.equals("ADD_FACILITY")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addFacility();
		} else if(action.equals("DELETE_FACILITY")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteFacility();
		} else if(action.equals("LOAD_WORK_AREA_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			loadWorkAreaInfo();
		} else if(action.equals("SAVE_WORK_AREA_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			saveWorkAreaInfo();
		} else if(action.equals("DELETE_WORK_AREA")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			deleteWorkArea();
		} else if(action.equals("ADD_WORK_AREA")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			addWorkArea();
		} else if(action.equals("SAVE_DROP_FAC_INFO")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			saveFacInfo();
		} else if(action.equals("GET_USE_APPROVAL_GROUP_MEMBERSHIP")) {
			if(getBundle().getString("DEBUG").equals("true")) {
				HelpObjs.monitor(1, "Admin:getResult = Going group", getBundle());
			}
			getUseApprovalGroupMembership();
		}
	}

	/**
	 * Gets the ScreenData attribute of the Admin object
	 */
	protected void getScreenData() {
		getLocs();
		getGroups();
		getFacWorkArea();
		if(getBundle().getString("DEBUG").equals("true")) {
			HelpObjs.monitor(1, "Admin:Done Loading Data:", getBundle());
		}
	}

	/**
	 * Gets the ServInt attribute of the Admin object
	 *
	 * @return   The ServInt value
	 */
	protected int getServInt() {
		return TcmisOutputStreamServer.ADMIN;
	}


	/**
	 * Description of the Method
	 */
	protected void resetAllVars() {
		action = null;
		userid = null;
		function = null;
		locs = null;
		groups = null;
		facids = null;
		facnames = null;
		facxapp = null;
		appids = null;
		appnames = null;
		function = null;
		groupName = null;
		groupDesc = null;
		locID = null;
		address1 = null;
		address2 = null;
		address3 = null;
		city = null;
		state = null;
		country = null;
		zip = null;
		clientLocCode = null;
		client = null;
		sendData = null;
		outData = null;
		stateV = null;
		wareName = null;
		wareID = null;
		roles = null;
		personnelIds = null;
		groupType = null;
		memberId = null;
		//trong
		roleTypeDesc = null;

		facID = null;
		personnelID = null;
		role = null;
		approvalType = null;
		companyName = null;
		facName = null;
		facType = null;
		mailLoc = null;
		shipLoc = null;
		branchPlant = null;
		prefWare = null;
		billTo = null;
		jde = null;
		workAreaID = null;
		workAreaDesc = null;
		workAreaLoc = null;
		workAreaFac = null;
		isRadian = false;
		groupIds = null;
		memStatus = null;
	}

	/**
	 * Description of the Method
	 *
	 * @param out  Description of Parameter
	 */
	protected void print(TcmisOutputStreamServer out) {
		try {
			out.printStart();
			printFacsIds();
			out.printEnd();

			out.printStart();
			printFacsNames();
			out.printEnd();

			out.printStart();
			printAppsIds();
			out.printEnd();

			out.printStart();
			printAppsNames();
			out.printEnd();

			out.printStart();
			printFacXApp();
			out.printEnd();

			out.printStart();
			printLocations();
			out.printEnd();

			out.printStart();
			printGroups();
			out.printEnd();

			out.printStart();
			printSendData();
			out.printEnd();

			out.printStart();
			printStateV();
			out.printEnd();

			out.printStart();
			printWareID();
			out.printEnd();

			out.printStart();
			printWareName();
			out.printEnd();

			out.printStart();
			printOutData();
			out.printEnd();

			//trong
			out.printStart();
			printRoleTypeDesc();
			out.printEnd();

      //trong
			out.printStart();
			printStatus();
			out.printEnd();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	protected void printSendData() throws Exception {
		if(sendData == null) {
			return;
		}
		out.println(sendData);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	protected void printOutData() throws Exception {
		if(outData == null) {
			return;
		}
		out.println(outData);
	}

	//trong 3/31
	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	protected void printRoleTypeDesc() throws Exception {
		if(roleTypeDesc == null) {
			return;
		}
		out.println(roleTypeDesc);
	}

	/**
	 * Gets the Locs attribute of the Admin object
	 */
	void getLocs() {
		locs = new Vector();
		try {
			Location l = new Location(db);
			Object[][] oa;
			if(isRadian) {
				oa = l.getAllLocations("", "LOCATION_ID");
			} else {
				oa = l.getAllLocsForClient(getBundle().getString("DB_CLIENT"));
			}
			for(int q = 0; q < Array.getLength(oa); q++) {
				locs.addElement(oa[q][0]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the AllLocsInfo attribute of the Admin object
	 */
	void getAllLocsInfo() {
		locs = new Vector();
		try {
			Location l = new Location(db);
			String s = "";
			if(!isRadian) {
				s = getBundle().getString("DB_CLIENT");
			}
			locs = l.getAllLocationInfo(s);
			stateV = l.getAllStates();
			Vector v = VVs.getCountriesShortList(db);
			sendData = (Vector) v.elementAt(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the ApprovalRoles attribute of the Admin object
	 */
	void getApprovalRoles() {
		try {
			NewChemApproval nca = new NewChemApproval(db);
			Vector[] v = nca.getApprovalRoleInfo();
			facxapp = v[0];
			appnames = v[1];
			sendData = nca.getAllApprovers();
		} catch(Exception e) {
		}
	}

	/**
	 * Gets the Groups attribute of the Admin object
	 */
	void getGroups() {
		try {
			UserGroup ug = new UserGroup(db);
			groups = ug.getAllGroupsForFacility(facID);
		} catch(Exception e) {
		}
	}

	/**
	 * Gets the WarehouseInfo attribute of the Admin object
	 */
	void getWarehouseInfo() {
		try {
			Facility f = new Facility(db);

			Enumeration E;
			Hashtable h = f.getAllWarehouses();
			wareName = new Vector();
			wareID = new Vector();
			int i = 0;
			for(E = h.keys(); E.hasMoreElements(); ) {
				String idTmp = new String((String) E.nextElement());
				String nameTmp = new String((String) h.get(idTmp));
				wareID.addElement(idTmp);
				wareName.addElement(nameTmp);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the FacWorkArea attribute of the Admin object
	 */
	void getFacWorkArea() {
		try {
			Facility f = new Facility(db);
			String where = "";
			Vector myV = new Vector();

			String r = null;
			String w = null;
			if(!isRadian) {
				r = "lower(company_id) like lower('" + getBundle().getString("DB_CLIENT") + "%')";
			}
			if(function.equals("STANDARD")) {
				w = "facility_type is null and branch_plant is null";
			} else if(function.equals("STANDARD_AND_DROP")) {
				w = "branch_plant is null";
			} else if(function.equals("DROP_FACS_ONLY")) {
				w = "lower(facility_type) = 'drop'";
			} else if(function.equalsIgnoreCase("HUBS_ONLY")) {
				w = "branch_plant is not null";
			} else if(function.equalsIgnoreCase("STANDARD_WITH_HUBS")) {
				w = "facility_type is null or branch_plant is not null";
			}
			if(r != null && w != null) {
				where = "where " + r + " and " + w;
			} else if(r != null) {
				where = "where " + r;
			} else if(w != null) {
				where = "where " + w;
			}
			myV = f.getEveryFac(where);
			facids = new Vector();
			facnames = new Vector();
			for(int q = 0; q < myV.size(); q++) {
				Facility f1 = (Facility) myV.elementAt(q);
				f1.load();
				facids.addElement(f1.getFacilityId());
				facnames.addElement(f1.getFacilityName());
			}

			//application
			facxapp = new Vector();
			appids = new Vector();
			appnames = new Vector();
			Object[][] oa;
			for(int q = 0; q < facids.size(); q++) {
				oa = f.getAppsForFac((String) facids.elementAt(q));
				for(int b = 0; b < oa.length; b++) {
					facxapp.addElement(facids.elementAt(q));
					appids.addElement(oa[b][0]);
					appnames.addElement(oa[b][1]);
				}
			}
			if(appids.isEmpty()) {
				appids.addElement("");
			}
			if(appnames.isEmpty()) {
				appids.addElement("");
			}
			if(facxapp.isEmpty()) {
				facxapp.addElement("");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the UseApprovalGroupInfo attribute of the Admin object
	 */
	void getUseApprovalGroupInfo() {
		try {
			Vector V = new Vector();
			V = UserGroup.getUseApprovalAdminScreenInfo(db, userid);
			sendData = (Vector) V.elementAt(0);
			outData = (Vector) V.elementAt(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the UseApprovalGroupMembership attribute of the Admin object
	 */
	void getUseApprovalGroupMembership() {
		try {
			sendData = new Vector();
			Vector V = new Vector();
			UserGroup ug = new UserGroup(db);
			V = ug.getUseApprovalMembership(userid);
			for(int i = 0; i < V.size(); i++) {
				UserGroup u = (UserGroup) V.elementAt(i);
				sendData.addElement(u.getGroupFacId());
				sendData.addElement(u.getGroupId());
				sendData.addElement(u.getGroupDesc());
			}
			//sendData = (Vector)V.elementAt(0);
			//outData = (Vector)V.elementAt(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the AdminGroupInfo attribute of the Admin object
	 */
	void getAdminGroupInfo() {
		try {
			Vector V = new Vector();
			V = UserGroup.getAdminGroupAdminScreenInfo(db);
			sendData = (Vector) V.elementAt(0);
			outData = (Vector) V.elementAt(1);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a feature to the LocationId attribute of the Admin object
	 */
	void addLocationId() {
		sendData = new Vector();
		try {
			Location l = new Location(db);
			if(l.locationExist(locID)) {
				sendData.addElement("dup");
				return;
			}
			l.addLocation(locID);
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void updateLocation() {
		sendData = new Vector();
		try {
			Location l = new Location(db);
			l.setLocationId(locID);
			l.setAddressLine1(address1);
			l.setAddressLine2(address2);
			l.setAddressLine3(address3);
			l.setCity(city);
			l.setStateAbbrev(state);
			l.setCountryAbbrev(country);
			l.setZip(zip);
			l.setClientLocationCode(clientLocCode);
			l.setLocationDesc(client + " / " + locID);
			l.updateLocation();
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}

	/**
	 * Description of the Method
	 */
	void deleteLocation() {
		sendData = new Vector();
		try {
			Location l = new Location(db);
			l.setLocationId(locID);
			l.deleteLocation();
		} catch(Exception e) {
			if(e instanceof SQLException && e.getMessage().indexOf("child record found") > 0) {
				sendData.addElement("child");
				return;
			}
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}

	/**
	 * Description of the Method
	 */
	void deleteGroup() {
		sendData = new Vector();
		try {
			UserGroup ug = new UserGroup(db);
			ug.setGroupId(groupName);
			ug.setGroupFacId(facID);
			ug.deleteGroupAndMembers();
			sendData.addElement("true");
		} catch(Exception e) {
			if(e instanceof SQLException && e.getMessage().indexOf("child record found") > 0) {
				sendData.addElement("child");
				return;
			}
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
	}

	/**
	 * Description of the Method
	 */
	void loadMemberGroupScreen() {
		sendData = new Vector();
		try {
			UserGroup ug = new UserGroup(db);
			Vector grp = ug.getApprovalGroupsCanEdit(userid);
			Vector mem = ug.getAllGroupsIsMember(memberId);
			for(int i = 0; i < grp.size(); i++) {
				UserGroup g = (UserGroup) grp.elementAt(i);
				if(g.getGroupId().equalsIgnoreCase("all")) {
					continue;
				}
				sendData.addElement(g.getGroupFacId());
				sendData.addElement(g.getGroupId());
				sendData.addElement(g.getGroupDesc());
				boolean match = false;
				for(int j = 0; j < mem.size(); j++) {
					UserGroup m = (UserGroup) mem.elementAt(j);
					if(m.equals(g)) {
						match = true;
						break;
					}
				}
				sendData.addElement(match ? "y" : "n");
			}
      //System.out.println("\n\n in Admin loadMemberGroupScreen: "+sendData);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a feature to the DeleteGroupMembers attribute of the Admin object
	 */
	void addDeleteGroupMembers() {
		sendData = new Vector();
		try {
			UserGroup ug = new UserGroup(db);
			ug.setGroupId(groupName);
			ug.setGroupFacId(facID);
			for(int i = 0; i < personnelIds.size(); i++) {
				if(function.equalsIgnoreCase("add")) {
					ug.addGroupMember(personnelIds.elementAt(i).toString());
				} else {
					ug.deleteGroupMember(personnelIds.elementAt(i).toString());
				}
			}
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
	}

	/**
	 * Description of the Method
	 */
	void updateGroupMembership() {
		sendData = new Vector();
		try {
			UserGroup ug = new UserGroup(db);
      //System.out.println("\n\n --------- updategroupmember: "+personnelIds+" "+groupIds+" "+facids+" "+memStatus);
			for(int i = 0; i < personnelIds.size(); i++) {
				ug.setGroupId(groupIds.elementAt(i).toString());
				ug.setGroupFacId(facids.elementAt(i).toString());
				if(memStatus.elementAt(i).toString().equalsIgnoreCase("y")) {
					ug.addGroupMember(personnelIds.elementAt(i).toString());
				} else {
					ug.deleteGroupMember(personnelIds.elementAt(i).toString());
				}
			}
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void loadAddApprover() {
		//trong 3/31
		roleTypeDesc = new Vector();

		sendData = new Vector();
		facnames = new Vector();
		try {
			Facility f = new Facility(db);
			f.setFacilityId(facID);
			f.load();
			facnames.addElement(f.getFacilityName());

			NewChemApproval nca = new NewChemApproval(db);
			sendData = nca.getApprovalRoleForFacility(facID);

			//trong 3/31
			roleTypeDesc = nca.getRoleTypeDesc(f.getFacilityId());
			sendData = nca.getRoleV();
			//System.out.println("----------------------- " + roleTypeDesc);
			//System.out.println("----------------------- " + sendData);
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
	}

	/**
	 * Description of the Method
	 */
	void loadRegularFacInfo() {
		try {
			Facility f = new Facility(db);

			Vector v = f.getEveryFac("");
			sendData = new Vector();
			wareID = new Vector();
			wareName = new Vector();
			locs = new Vector();
			for(int i = 0; i < v.size(); i++) {
				Facility f1 = (Facility) v.elementAt(i);
				f1.load();
				if(facType.equalsIgnoreCase("drop")) {
					if(f1.getFacilityType() == null || !f1.getFacilityType().equalsIgnoreCase(facType)) {
						if(!BothHelpObjs.isBlankString(f1.getBranchPlant())) {
							wareID.addElement(f1.getFacilityId());
							wareName.addElement(f1.getFacilityName());
						}
						continue;
					}
				}
				sendData.addElement(f1.getFacilityId());
				sendData.addElement(f1.getFacilityName());
				sendData.addElement(f1.getJdeBillTo());
				sendData.addElement(f1.getMailLocation());
				sendData.addElement(f1.getShippingLocation());
				sendData.addElement(f1.getPreferredWare());
				sendData.addElement(f1.getJdeID());
				sendData.addElement(f1.getCompanyId());
				if(BothHelpObjs.isBlankString(f1.getBranchPlant())) {
					sendData.addElement("");
				} else {
					sendData.addElement(f1.getBranchPlant());
					wareID.addElement(f1.getFacilityId());
					wareName.addElement(f1.getFacilityName());
				}
			}
			Location l = new Location(db);
			Object[][] oa = l.getAllLocations("", "");
			for(int i = 0; i < oa.length; i++) {
				locs.addElement(oa[i][0]);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description of the Method
	 */
	void deleteFacility() {
		sendData = new Vector();
		try {
			Facility f = new Facility(db);
			f.setFacilityId(facID);
			if(f.deleteFacility()) {
				sendData.addElement("true");
			} else {
				sendData.addElement("false");
			}
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Adds a feature to the Facility attribute of the Admin object
	 */
	void addFacility() {
		sendData = new Vector();
		try {
			// add the facility
			Facility f = new Facility(db);
			if(BothHelpObjs.isBlankString(facType)) {
				f.addFacility(facID, facName);
			} else {
				f.addFacility(facID, facName, facType);
			}

			// add some basic groups
			UserGroup ug = new UserGroup(db);
			// approval group All
			ug.setGroupFacId(facID);
			ug.setGroupId("All");
			ug.setGroupType("Approval");
			ug.setGroupDesc("All users");
			ug.addGroup();

			// Administrator group
			ug.setGroupFacId(facID);
			ug.setGroupId("Administrator");
			ug.setGroupType("Admin");
			ug.setGroupDesc("Administrator");
			ug.addGroup();

			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void saveFacInfo() {
		sendData = new Vector();
		try {
			Facility f = new Facility(db);
			f.setFacilityId(facID);
			f.load();

			f.setFacilityName(facName);
			f.setCompanyId(companyName);
			f.setMailLocation(mailLoc);
			f.setShippingLocation(shipLoc);
			f.setBranchPlant(branchPlant);
			f.setPreferredWare(prefWare);

			// get the jde id
			Facility f2 = new Facility(db);
			f2.setFacilityId(billTo);
			f2.load();
			f.setJdeBillTo(f2.getJdeID());
			if(jde.length() < 1) {
				f.setJdeID(null);
			} else {
				f.setJdeID(jde);
			}

			if(BothHelpObjs.isBlankString(facType)) {
				f.setFacilityType("");
			} else {
				f.setFacilityType(facType);
			}
			f.updateFacility();

			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void loadWorkAreaInfo() {
          statusV = new Vector(2);
          statusV.addElement("Active");
          statusV.addElement("Inactive");
          try {
            sendData = new Vector();
            ApplicationView av = new ApplicationView(db);
            Vector v = new Vector();
            Personnel p = new Personnel(db);
            p.setPersonnelId(Integer.parseInt(userid));
            if(p.isSuperUser()) {
              v = av.getAllApps("");
            } else {
              v = av.getAppsForFacs(p.getAdminFacilities());
            }
            for(int i = 0; i < v.size(); i++) {
              ApplicationView a = (ApplicationView) v.elementAt(i);
              if(a.getApplication().equalsIgnoreCase("all")) {
                continue;
              }
              sendData.addElement(a.getFacilityId());
              sendData.addElement(a.getApplication());
              sendData.addElement(a.getApplicationDesc());
              sendData.addElement(a.getLocationId());
              sendData.addElement(a.getWorkAreaStatus());     //trong 1-29-01
            }
            locs = new Vector();
            outData = new Vector();
            Location l = new Location(db);
            Vector v1 = l.getAllLocations();
            for(int i = 0; i < v1.size(); i++) {
              Location ln = (Location) v1.elementAt(i);
              locs.addElement(ln.getLocationId());
            }
          } catch(Exception e) {
            e.printStackTrace();
            sendData = new Vector();
          }
	} //end of method

  /**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printStatus() throws Exception {
		if(statusV == null) {
			return;
		}
		out.println(statusV);
	}

	/**
	 * Description of the Method
	 */
	void saveWorkAreaInfo() {
		sendData = new Vector();
		try {
      //System.out.println("\n\n======== appids: "+appids+" "+facids+" "+appnames+" "+locs+" "+myUpdateStatus);
			for(int i = 0; i < appids.size(); i++) {
				ApplicationView av = new ApplicationView(db);
				av.setApplication(appids.elementAt(i).toString());
				av.setFacilityId(facids.elementAt(i).toString());
				av.setApplicationDesc(appnames.elementAt(i).toString());
				av.setLocationId(locs.elementAt(i).toString());
        av.setWorkAreaStatusUpdate(myUpdateStatus.elementAt(i).toString());      //trong 1-29-01
				av.updateApplicationNew();
			}
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Adds a feature to the WorkArea attribute of the Admin object
	 */
	void addWorkArea() {
		sendData = new Vector();
		try {
			ApplicationView av = new ApplicationView(db);
			av.addApplication(facID, workAreaID, workAreaDesc, locID, "spaceHolder");
			av.setApplication(workAreaID);
			av.setApplicationDesc(workAreaDesc);
			av.setFacilityId(facID);
			av.setLocationId(locID);
			if("andover".equalsIgnoreCase(facID) ) {
				av.setDeliveryPoint(workAreaID);
                DeliveryPoint dp = new DeliveryPoint(db);
                dp.addDeliveryPoint(facID, locID, workAreaID, workAreaDesc);
			}
			av.updateApplication();
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void deleteWorkArea() {
		sendData = new Vector();
		try {
			ApplicationView av = new ApplicationView(db);
			av.setApplication(workAreaID);
			av.setFacilityId(facID);
			av.deleteApplication();
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Description of the Method
	 */
	void goLocation() {
		sendData = new Vector();
		try {
			Location l = new Location(db);
			if(function.equalsIgnoreCase("info")) {
				if(l.locationExist(locID)) {
					l.setLocationId(locID);
					l.load();
					sendData.addElement("UPDATE");
					sendData.addElement(l.getLocationId());
					sendData.addElement(l.getAddressLine1());
					sendData.addElement(l.getAddressLine2());
					sendData.addElement(l.getAddressLine3());
					sendData.addElement(l.getCity());
					sendData.addElement(l.getStateAbbrev());
					sendData.addElement(l.getCountryAbbrev());
					sendData.addElement(l.getZip());
					sendData.addElement(l.getClientLocationCode());

					// client is the part of desc before the slash (/)
					String temp = l.getLocationDesc();
					if(temp.indexOf("/") > 0) {
						temp = temp.substring(0, temp.indexOf("/") - 1);
						temp.trim();
					} else {
						temp = "";
					}
					sendData.addElement(temp);
				} else {
					sendData.addElement("ADD");
				}
				stateV = l.getAllStates();
			} else if(function.equalsIgnoreCase("add") || function.equalsIgnoreCase("update")) {
				if(function.equalsIgnoreCase("add")) {
					l.addLocation(locID);
				}
				l.setLocationId(locID);
				l.setAddressLine1(address1);
				l.setAddressLine2(address2);
				l.setAddressLine3(address3);
				l.setCity(city);
				l.setStateAbbrev(state);
				l.setCountryAbbrev(country);
				l.setZip(zip);
				l.setClientLocationCode(clientLocCode);
				l.setLocationDesc(client + " / " + locID);
				l.updateLocation();
				sendData.addElement("yes");
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(function.equalsIgnoreCase("info")) {
				sendData = new Vector();
			} else {
				sendData.addElement("no");
			}
		}
	}

	/**
	 * Description of the Method
	 */
	void goFacility() {
		sendData = new Vector();
		try {
			Facility f = new Facility(db);
			if(function.equalsIgnoreCase("info")) {
				if(f.facilityExist(facID)) {
					f.setFacilityId(facID);
					f.load();
					sendData.addElement("UPDATE");
					//load all the fields
					sendData.addElement(f.getFacilityId());
					sendData.addElement(f.getFacilityName());
					sendData.addElement(f.getMailLocation());
					sendData.addElement(f.getShippingLocation());
					sendData.addElement(f.getPreferredWare());
					sendData.addElement(f.getFacIDFromJde(f.getJdeBillTo()));
					sendData.addElement(f.getBranchPlant());
					sendData.addElement(f.getCompanyId());
					sendData.addElement(f.getJdeID());
				} else {
					sendData.addElement("ADD");
				}
				getLocs();
				getFacWorkArea();
				getWarehouseInfo();
			} else if(function.equalsIgnoreCase("add") || function.equalsIgnoreCase("update")) {
				if(function.equalsIgnoreCase("add")) {
					f.addFacility(facID, facName);
				}
				//set all the variables and update
				f.setFacilityId(facID);
				f.load();
				f.setFacilityName(facName);
				f.setCompanyId(companyName);
				f.setMailLocation(mailLoc);
				f.setShippingLocation(shipLoc);
				f.setBranchPlant(branchPlant);
				f.setPreferredWare(prefWare);

				// get the jde id
				Facility f2 = new Facility(db);
				f2.setFacilityId(billTo);
				f2.load();
				f.setJdeBillTo(f2.getJdeID());

				if(jde.length() < 1) {
					f.setJdeID(null);
				} else {
					f.setJdeID(jde);
				}

				//facility type is not used yet...
				f.setFacilityType("");
				f.updateFacility();
				sendData.addElement("yes");
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(function.equalsIgnoreCase("info")) {
				sendData = new Vector();
			} else {
				sendData.addElement("no");
			}
		}
	}

	/**
	 * Description of the Method
	 */
	void goWorkArea() {
		sendData = new Vector();
		try {
			ApplicationView av = new ApplicationView(db);
			if(function.equalsIgnoreCase("info")) {
				if(av.applicationExist(workAreaFac, workAreaID)) {
					av.setApplication(workAreaID);
					av.setFacilityId(workAreaFac);
					av.load();
					sendData.addElement("UPDATE");
					sendData.addElement(av.getApplicationDesc());

					//get the facility NAME
					Facility f2 = new Facility(db);
					f2.setFacilityId(av.getFacilityId());
					f2.load();
					sendData.addElement(f2.getFacilityName());

					sendData.addElement(av.getLocationId());
				} else {
					sendData.addElement("ADD");
				}
				getLocs();
				getFacWorkArea();
			} else if(function.equalsIgnoreCase("add") || function.equalsIgnoreCase("update")) {
				if(function.equalsIgnoreCase("add")) {
					av.addApplication(workAreaFac, workAreaID, workAreaLoc);
				}
				av.setApplication(workAreaID);
				av.setFacilityId(workAreaFac);
				av.load();
				av.setLocationId(workAreaLoc);
				av.setApplicationDesc(workAreaDesc);
				av.updateApplication();
				sendData.addElement("yes");
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(function.equalsIgnoreCase("info")) {
				sendData = new Vector();
			} else {
				sendData.addElement("no");
			}
		}
	}

	/**
	 * Description of the Method
	 */
	void changeGroupInfo() {
		sendData = new Vector();
		try {
			UserGroup ug = new UserGroup(db);
			ug.setGroupId(groupName);
			ug.setGroupDesc(groupDesc);
			ug.setGroupFacId(facID);
			ug.setGroupType(groupType);
			if(ug.groupExist(groupName, facID)) {
				ug.updateDesc();
			} else {
				ug.addGroup();
			}
			sendData.addElement("true");
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
		}
	}

	/**
	 * Adds a feature to the Approver attribute of the Admin object
	 */
	void addApprover() {
		sendData = new Vector();
		try {
			NewChemApproval nca = new NewChemApproval(db);
			for(int i = 0; i < roles.size(); i++) {
				nca.addChemicalApprover(facID, roles.elementAt(i).toString(), Integer.parseInt(personnelID));
			}
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}

	/**
	 * Description of the Method
	 */
	void deleteApprover() {
		sendData = new Vector();
		try {
			NewChemApproval nca = new NewChemApproval(db);
			for(int i = 0; i < roles.size(); i++) {
				//System.out.println("IDS:" + personnelIds.elementAt(i).toString());
				nca.deleteChemicalApprover(facids.elementAt(i).toString(), roles.elementAt(i).toString(), Integer.parseInt(personnelIds.elementAt(i).toString()));
			}
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}

	/**
	 * Adds a feature to the ApprovalRole attribute of the Admin object
	 */
	void addApprovalRole() {
		sendData = new Vector();
		try {
			NewChemApproval nca = new NewChemApproval(db);
			nca.addChemicalApprovalRole(facID, role, approvalType);
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}

	//trong 3/27
	/**
	 * Description of the Method
	 */
	void updateApprovalRole() {
		sendData = new Vector();
		try {
			NewChemApproval nca = new NewChemApproval(db);
			nca.updateChemicalApprovalRole(facID, role, approvalType);
		} catch(Exception e) {
			e.printStackTrace();
			sendData.addElement("false");
			return;
		}
		sendData.addElement("true");
	}


	/**
	 * Description of the Method
	 */
	void deleteApprovalRole() {
		sendData = new Vector();
		try {
			NewChemApproval nca = new NewChemApproval(db);
			for(int i = 0; i < roles.size(); i++) {
				try {
					Boolean B = nca.deleteChemicalApprovalRole(facID, roles.elementAt(i).toString());
					sendData.addElement(B.toString());
				} catch(Exception e) {
					sendData.addElement("false");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printLocations() throws Exception {
		if(locs == null) {
			return;
		}
		out.println(locs);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printGroups() throws Exception {
		if(groups == null) {
			return;
		}
		out.println(groups);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printFacsIds() throws Exception {
		if(facids == null) {
			return;
		}
		out.println(facids);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printWareID() throws Exception {
		if(wareID == null) {
			return;
		}
		out.println(wareID);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printWareName() throws Exception {
		if(wareName == null) {
			return;
		}
		out.println(wareName);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printFacsNames() throws Exception {
		if(facnames == null) {
			return;
		}
		out.println(facnames);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printFacXApp() throws Exception {
		if(facxapp == null) {
			return;
		}
		out.println(facxapp);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printAppsIds() throws Exception {
		if(appids == null) {
			return;
		}
		out.println(appids);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printAppsNames() throws Exception {
		if(appnames == null) {
			return;
		}
		out.println(appnames);
	}

	/**
	 * Description of the Method
	 *
	 * @exception Exception  Description of Exception
	 */
	void printStateV() throws Exception {
		if(stateV == null) {
			return;
		}
		out.println(stateV);
	}

}

