
package radian.tcmis.server7.share.dbObjs;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

import java.awt.Color;

import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.helpers.HelpObjs;

public class UserGroup  {

   protected TcmISDBModel db;
   protected String groupID = null;
   protected String groupDesc = null;
   protected String facId = null;
   protected String groupType = null;
	 protected String companyId = null;

   public String toString(){
     Hashtable h = new Hashtable();
     h.put("groupID:",groupID==null?"null":groupID);
     h.put("groupDesc:",groupDesc==null?"null":groupDesc);
     h.put("facId:",facId==null?"null":facId);
     h.put("groupType:",groupType==null?"null":groupType);
     h.put("dbModel:",db==null?"null":"not null");
     return h.toString()+"\n";
   }
   public UserGroup() throws Exception {
   }

   public UserGroup(TcmISDBModel db) throws Exception {
     this.db = db;
   }

   public void setDB(TcmISDBModel db){
     this.db = db;
   }

   public void setGroupId(String s) {
     this.groupID = s;
   }

   public String getGroupId() {
     return groupID;
   }

   public void setGroupDesc(String s){
     groupDesc = s;
   }

   public String getGroupDesc(){
      return groupDesc;
   }

   public void setGroupFacId(String s){
     facId = s;
   }

   public String getGroupFacId(){
      return facId;
   }
   public void setGroupType(String s){
     groupType = s;
   }

   public String getGroupType(){
      return groupType;
   }

	 public String getCompanyId(){
		return companyId;
	 }
	 public void setCompanyId(String companyId1){
		companyId = companyId1;
	 }

   public boolean equals(UserGroup ug){
     return this.getGroupFacId().equals(ug.getGroupFacId()) &&
            this.getGroupId().equals(ug.getGroupId());
   }

   public boolean groupExist(String grp, String fac) throws Exception{
     String s = HelpObjs.singleQuoteToDouble(grp);
     try{
       if ("Approval".equalsIgnoreCase(this.getGroupType())) {
         return (DbHelpers.countQuery(db, "approval_user_group", "where user_group_id =  '" + grp + "' and facility_id = '" + fac + "'")) > 0;
       }else {
         return (DbHelpers.countQuery(db, "user_group", "where user_group_id =  '" + grp + "' and facility_id = '" + fac + "'")) > 0;
       }
     }catch(Exception e) {throw e;
       }
   }

   public Hashtable getTableStyle() {
    Hashtable result = new Hashtable();
    Hashtable h = new Hashtable();
    //border color and inset
    h.put("COLOR",new Color(255,255,255));
    h.put("INSET_TOP",new Integer(0));
    h.put("INSET_LEFT",new Integer(1));
    h.put("INSET_BOTTOM",new Integer(1));
    h.put("INSET_RIGHT",new Integer(1));
    h.put("INSET_ALIGN",new Integer(2));    //1- right, 2- left, 3- center
    //font and foreground and background color for columns heading
    h.put("FONT_NAME","Dialog");
    h.put("FONT_STYLE",new Integer(0));
    h.put("FONT_SIZE",new Integer(10));
    h.put("FOREGROUND",new Color(255,255,255));
    h.put("BACKGROUND",new Color(0,0,66));
    h.put("COL_WIDTH",new Integer(200));
    String[] leftTableHeader = {"All Groups"};
    h.put("LEFT_TABLE_HEADER",leftTableHeader);
    String[] rightTableHeader = {"Member of these groups"};
    h.put("RIGHT_TABLE_HEADER",rightTableHeader);
    result.put("TABLE_STYLE",h);

    return result;
   }


   public void load()  throws Exception {
      String query = "";
      if ("Approval".equalsIgnoreCase(this.getGroupType())) {
        query = "select * from approval_user_group where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
      }else {
        query = "select * from user_group where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
      }
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int myCount = 0;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           setGroupDesc(rs.getString("user_group_desc"));
         }


      } catch (Exception e) { e.printStackTrace(); throw e;}
      finally { dbrs.close(); }
   }

   public void addGroup() throws Exception {
      if(groupExist(groupID,facId)) return;
      String query = "";
      if ("Approval".equalsIgnoreCase(this.getGroupType())) {
        query = "insert into approval_user_group ";
        query += "(user_group_id, user_group_desc, facility_id)";
        query += " values ('" + HelpObjs.singleQuoteToDouble(groupID) + "','" + HelpObjs.singleQuoteToDouble(groupDesc) + "','" + facId + "')";
      }else {
        query = "insert into user_group ";
        query += "(user_group_id, user_group_desc, facility_id,user_group_type)";
        query += " values ('" + HelpObjs.singleQuoteToDouble(groupID) + "','" + HelpObjs.singleQuoteToDouble(groupDesc) + "','" + facId + "','" + groupType + "')";
      }

      try {
        db.doUpdate(query);
      } catch (Exception e) { e.printStackTrace();throw e;
       }
   }

   public void addGroupMember(String userID) throws Exception {
      // check to see if already in group...
      String query = "";
      if ("Approval".equalsIgnoreCase(this.getGroupType())) {
        String s = "select count(*) from approval_user_group_member where user_group_id = '"+ HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '"+facId+"' and personnel_id ="+userID;
        if (DbHelpers.countQuery(db, s) > 0) {
          return;
        }
        query = "insert into approval_user_group_member ";
        query += "(user_group_id, facility_id,personnel_id)";
        query += " values ('"+ HelpObjs.singleQuoteToDouble(groupID) +"','"+facId+ "',"+userID+")";
      }else {
        String s = "select count(*) from user_group_member where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "' and personnel_id =" + userID;
        if (DbHelpers.countQuery(db, s) > 0) {
          return;
        }
        query = "insert into user_group_member ";
        query += "(user_group_id, facility_id,personnel_id)";
        query += " values ('" + HelpObjs.singleQuoteToDouble(groupID) + "','" + facId + "'," + userID + ")";
      }

      try {
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   public void deleteGroup() throws Exception {
      try {
        String query = "";
        if ("Approval".equalsIgnoreCase(this.getGroupType())) {
          query = "delete approval_user_group ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
        }else {
          query = "delete user_group ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
        }
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   public void deleteGroupMember(String userID) throws Exception {
      try {
        String query = "";
        if ("Approval".equalsIgnoreCase(this.getGroupType())) {
          query = "delete approval_user_group_member ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "' and personnel_id =" + userID;
        }else {
          query = "delete user_group_member ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "' and personnel_id =" + userID;
        }
        db.doUpdate(query);

      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   public void deleteAllGroupMembers() throws Exception {
      try {
        String query = "";
        if ("Approval".equalsIgnoreCase(this.getGroupType())) {
          query = "delete approval_user_group_member ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
        }else {
          query = "delete user_group_member ";
          query += "where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
        }
        db.doUpdate(query);
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
   }

   public void deleteGroupAndMembers() throws Exception {
     try{
       deleteAllGroupMembers();
       deleteGroup();
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }

   public void updateDesc() throws Exception {
     String query = "";
     if ("Approval".equalsIgnoreCase(this.getGroupType())) {
       query = "update approval_user_group set user_group_desc = '" + HelpObjs.singleQuoteToDouble(groupDesc) + "' where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
     }else {
       query = "update user_group set user_group_desc = '" + HelpObjs.singleQuoteToDouble(groupDesc) + "' where user_group_id = '" + HelpObjs.singleQuoteToDouble(groupID) + "' and facility_id = '" + facId + "'";
     }
      try {
          db.doUpdate(query);
      } catch (Exception e) { e.printStackTrace();throw e;
       }
   }

   public Vector getAllGroupsForFacility(String f) throws Exception  {
      Vector out = new Vector();
      String query = "";
      if ("Approval".equalsIgnoreCase(this.getGroupType())) {
        query = "select user_group_id from approval_user_group where facility_id = '"+f+"' order by user_group_id";
      }else {
        query = "select user_group_id from user_group where facility_id = '"+f+"' order by user_group_id";
      }

      DBResultSet dbrs = null;
      ResultSet rs = null;
      int myCount = 0;
      try {
         dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
         while (rs.next()){
           out.addElement(rs.getString("user_group_id"));
         }


      } catch (Exception e) { e.printStackTrace();  throw e;}
      finally {
         dbrs.close();
      }
      return out;
   }

   public Vector getAllGroups(String type) throws Exception  {
      Vector out = new Vector();
      String query = "";
      if ("Approval".equalsIgnoreCase(type)) {
       query = "select * from approval_user_group order by user_group_desc";
      }else {
       query = "select * from user_group where user_group_type = '"+type+"'order by user_group_desc";
      }
      DBResultSet dbrs = null;
      ResultSet rs = null;
      int myCount = 0;
      try {
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();
         while (rs.next()){
           UserGroup ug = new UserGroup(db);
           ug.setGroupId(rs.getString("user_group_id"));
           ug.setGroupDesc(rs.getString("user_group_desc"));
           ug.setGroupFacId(rs.getString("facility_id"));
           if ("Approval".equalsIgnoreCase(type)) {
             ug.setGroupType("Approval");
           }else {
             ug.setGroupType(rs.getString("user_group_type"));
           }
           out.addElement(ug);
         }
      } catch (Exception e) { e.printStackTrace();  throw e;}
      finally { dbrs.close(); }
      return out;
   }

   public Vector getMembers() throws Exception {
     Vector v = new Vector();
     String query = "";
     if ("Approval".equalsIgnoreCase(this.getGroupType())) {
       query = "select personnel_id from approval_user_group_member ";
     }else {
       query = "select personnel_id from user_group_member ";
     }

     query = query + "where user_group_id = '"+this.getGroupId()+"'";
     query = query + " and facility_id = '"+this.getGroupFacId()+"'";
     if (this.getCompanyId() != null && this.getCompanyId().trim().length() > 0)
     {
       query = query + " and company_id = '"+this.getCompanyId()+"'";
     }
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()){
         Personnel p = new Personnel(db);
         p.setPersonnelId(Integer.parseInt(rs.getString("personnel_id")));
         p.load();
         v.addElement(p);
       }


      } catch (Exception e) { e.printStackTrace();throw e;}
      finally { dbrs.close(); }
      return v;
   }

   public Vector getApprovalGroupsCanEdit(String userId)throws Exception{
     Vector out = new Vector();
     DBResultSet dbrs = null;
     ResultSet rs = null;
     try{
       Personnel p = new Personnel(db);
       p.setPersonnelId(Integer.parseInt(userId));
       if(p.isSuperUser()){
         return getAllGroups("Approval");
       }
       String query = "select * from approval_user_group where facility_id in ";
       query = query + "(select facility_id from user_group_member where personnel_id = "+userId+" and lower(user_group_id) = 'administrator')";

       int myCount = 0;

       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         UserGroup ug = new UserGroup(db);
         ug.setGroupId(rs.getString("user_group_id"));
         ug.setGroupDesc(rs.getString("user_group_desc"));
         ug.setGroupFacId(rs.getString("facility_id"));
         out.addElement(ug);
       }
     }catch (Exception e) {e.printStackTrace();throw e;
     }finally{
       if(dbrs != null) dbrs.close();
     }
     return out;
   }

   public Vector getAllGroupsIsMember(String userID)throws Exception{
     Vector v = new Vector();
     String query = "select facility_id,user_group_id from user_group_member where personnel_id = '"+userID+"'"+
                    " union "+
                    "select facility_id,user_group_id from approval_user_group_member where personnel_id = '"+userID+"'";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);   rs=dbrs.getResultSet();
       while (rs.next()){
         UserGroup ug = new UserGroup(db);
         ug.setGroupId(rs.getString("user_group_id"));
         ug.setGroupFacId(rs.getString("facility_id"));
         v.addElement(ug);
       }
      } catch (Exception e) { e.printStackTrace(); throw e;
       } finally{
             dbrs.close();
           }
     return v;
   }

   public Vector getUseApprovalMembership(String userId) throws Exception  {
     Vector v = new Vector();
     String query = "select * from approval_user_group_member ";
     query = query + "where personnel_id = '"+userId+"'" ;
     query = query + "order by facility_id, user_group_id";
     DBResultSet dbrs = null;
      ResultSet rs = null;
     int myCount = 0;
     try {
       dbrs = db.doQuery(query);
       rs=dbrs.getResultSet();
       while (rs.next()){
         UserGroup ug = new UserGroup(db);
         ug.setGroupFacId(rs.getString("facility_id"));
         ug.setGroupId(rs.getString("user_group_id"));
         ug.setGroupType("Approval");
         ug.load();
         if(!BothHelpObjs.isBlankString(ug.getGroupType()) &&
            ug.getGroupType().equalsIgnoreCase("approval")){
           v.addElement(ug);
         }
       }

      } catch (Exception e) { e.printStackTrace();throw e;}
      finally { dbrs.close(); }
      return v;
   }

   public static Vector getUseApprovalAdminScreenInfo(TcmISDBModel db,String userID) throws Exception{
     Vector bigOne = new Vector();
     Vector groupInfo = new Vector();
     Vector memberInfo = new Vector();
     try{
       UserGroup ug = new UserGroup(db);
       Vector groups = ug.getApprovalGroupsCanEdit(userID);
       for(int i=0;i<groups.size();i++){
         UserGroup myGroup = (UserGroup)groups.elementAt(i);
         if(myGroup.getGroupId().equalsIgnoreCase("all"))continue;
         groupInfo.addElement(myGroup.getGroupFacId());
         groupInfo.addElement(myGroup.getGroupId());
         groupInfo.addElement(myGroup.getGroupDesc());
         Vector members = myGroup.getMembers();
         for(int j=0;j<members.size();j++){
           Personnel p = (Personnel)members.elementAt(j);
           memberInfo.addElement(p.getFullName());
           memberInfo.addElement(myGroup.getGroupFacId());
           memberInfo.addElement(myGroup.getGroupId());
           memberInfo.addElement(p.getPersonnelId().toString());
         }
      }
     }catch(Exception e){e.printStackTrace();throw e;
       }
     bigOne.addElement(groupInfo);
     bigOne.addElement(memberInfo);
     return bigOne;
   }
   public static Vector getAdminGroupAdminScreenInfo(TcmISDBModel db) throws Exception{
     Vector bigOne = new Vector();
     Vector groupInfo = new Vector();
     Vector memberInfo = new Vector();
     int numG = 0;
     int numM = 0;
     try{
       UserGroup ug = new UserGroup(db);
       Vector groups = ug.getAllGroups("Admin");
       for(int i=0;i<groups.size();i++){
         numG++;
         UserGroup myGroup = (UserGroup)groups.elementAt(i);
         groupInfo.addElement(myGroup.getGroupFacId());
         groupInfo.addElement(myGroup.getGroupId());
         groupInfo.addElement(myGroup.getGroupDesc());
         Vector members = myGroup.getMembers();
         for(int j=0;j<members.size();j++){
         numM++;
           Personnel p = (Personnel)members.elementAt(j);
           memberInfo.addElement(p.getFullName());
           memberInfo.addElement(myGroup.getGroupFacId());
           memberInfo.addElement(myGroup.getGroupId());
           memberInfo.addElement(p.getPersonnelId().toString());
         }
       }
     }catch(Exception e){e.printStackTrace();throw e;
       }
     bigOne.addElement(groupInfo);
     bigOne.addElement(memberInfo);
     return bigOne;
   }

   public void removeUserFromExtraGroups(String userid) throws Exception{
     Personnel p = new Personnel(db);
     p.setPersonnelId(Integer.parseInt(userid));
     Vector v = p.getFacs();
     Vector fi = new Vector();
     for(int i=0;i<v.size();i++){
       Facility f = (Facility)v.elementAt(i);
       fi.addElement(f.getFacilityId());
     }
     Vector mem = this.getAllGroupsIsMember(userid);
     for(int i=0;i<mem.size();i++){
       UserGroup ug = (UserGroup)mem.elementAt(i);
       if(!ug.getGroupFacId().equalsIgnoreCase("all") &&  !fi.contains(ug.getGroupFacId()) && !ug.getGroupFacId().endsWith("Hub")){
        if (!ug.getGroupId().equalsIgnoreCase("CSR") && !ug.getGroupId().equalsIgnoreCase("HubManager")) {
         ug.deleteGroupMember(userid);
        }
       }
     }
   }
   public boolean isMember(int pid)throws Exception{
     try{
       String query = "";
       if ("Approval".equalsIgnoreCase(this.getGroupType())) {
         query = "select count(*) from approval_user_group_member "+
               "where personnel_id = "+pid+" and facility_id = '"+facId+"' and "+
               "user_group_id = '"+groupID+"'";
       }else {
         query = "select count(*) from user_group_member "+
                        "where personnel_id = "+pid+" and facility_id = '"+facId+"' and "+
                        "user_group_id = '"+groupID+"'";
       }
       return DbHelpers.countQuery(db,query) > 0;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }

   public boolean isWasteManager(int pid)throws Exception{
     try{
       String q = "select count(*) from user_group_member ";
       q = q + "where personnel_id = "+pid+" and facility_id = '"+facId+"' and ";
       q = q + "user_group_id = '"+groupID+"'";
       return DbHelpers.countQuery(db,q) > 0;
     }catch(Exception e){e.printStackTrace();throw e;
       }
   }

}
