package radian.tcmis.server7.share.dbObjs;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;

// Referenced classes of package radian.tcmis.server7.share.dbObjs:
//            Personnel, RequestLineItem, PurchaseRequest, TcmISDBModel

public class FinanceApproverList
{

    protected TcmISDBModel db;
    protected String facility_id;
    protected Integer personnel_id;
    protected float cost_limit;
    protected String preferred;
    protected Integer approver;
    protected float approval_limit = -100;   //place hold coz -1 already reserved for unlimited
    protected String userFullName = "";
    protected Integer adminID;

    public FinanceApproverList()
        throws RemoteException
    {
    }

    public FinanceApproverList(TcmISDBModel db)
        throws RemoteException
    {
        this.db = db;
    }

    public void setDB(TcmISDBModel db)
    {
        this.db = db;
    }

    public void setFacilityId(String id)
    {
        facility_id = id;
    }

    public String getFacilityId()
    {
        return facility_id;
    }

    public void setPersonnelId(int id)
    {
        personnel_id = new Integer(id);
    }

    public Integer getPersonnelId()
    {
        return personnel_id;
    }

    public void setApprover(int id)
    {
        approver = new Integer(id);
    }

    public Integer getApprover()
    {
        return approver;
    }

    public void setCostLimit(float lim)
    {
        cost_limit = lim;
    }

    public float getCostLimit()
    {
        return cost_limit;
    }

    public void setApprovalLimit(float lim)
    {
        approval_limit = lim;
    }

    public float getApprovalLimit()
    {
        return approval_limit;
    }

    public void setPreferred(String st)
    {
        preferred = st;
    }

    public String getPreferred()
    {
        return preferred;
    }

    public void setUserFullName(String st)
    {
        userFullName = st;
    }

    public String getUserFullName()
    {
        return userFullName;
    }

    public Integer getAdminID() {
      return adminID;
    }

    public void setAdminID(Integer i) {
      adminID = i;
    }

    public void load() throws Exception {
        String query = "select nvl(cost_limit,0) cost_limit, nvl(approver,0) approver, nvl(approval_limit,0) approval_limit"+
                       " from finance_approver_list where facility_id = '"+facility_id+"' and personnel_id = "+personnel_id.toString()+
                       " and personnel_id <> nvl(approver,0)";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while ( rs.next()) {
            setApprover((new Integer(rs.getInt("approver"))).intValue());
            setCostLimit(rs.getFloat("cost_limit"));
            setApprovalLimit(rs.getFloat("approval_limit"));
          }
        } catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
    }

    public void addApprover() throws Exception {
        String query = "insert into finance_approver_list ";
        if (approval_limit == -100) {
          approval_limit = cost_limit;
        }
        if (adminID == null) {
          adminID = new Integer(-1);
        }
        if(approver == null) {
          query += "(last_date_updated,facility_id,personnel_id,cost_limit,approval_limit,last_updated_by) "+
                   "values(sysdate,'"+facility_id+"',"+personnel_id.toString()+","+cost_limit+","+approval_limit+","+adminID.intValue()+")";
        }else {
          query += "(last_date_updated,facility_id,personnel_id,cost_limit,approver,approval_limit,last_updated_by) "+
                   "values(sysdate,'"+facility_id+"',"+personnel_id.toString()+","+cost_limit+","+approver.toString()+","+approval_limit+","+adminID.intValue()+")";
        }
        try {
          db.doUpdate(query);
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }
    }

    public Hashtable retrieve() throws Exception {
        String query = "select p.personnel_id,p.first_name||' '||p.last_name full_name from finance_approver_list f,personnel p where f.facility_id = '"+facility_id+
                       "' and f.personnel_id = p.personnel_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable H = new Hashtable();
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          String name = new String("");
          Integer ID;
          while (rs.next()) {
            ID = new Integer(rs.getString("personnel_id"));
            name = rs.getString("full_name");
            H.put(ID, name);
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return H;
    }

    public Hashtable retrieveRaw() throws Exception {
        String query = "select unique f.personnel_id,p.first_name||' '||p.last_name full_name from finance_approver_list" +
                       " f,personnel p where f.personnel_id = p.personnel_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable H = new Hashtable();
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          String name = new String("");
          Integer ID;
          while (rs.next()) {
            ID = new Integer(rs.getInt("personnel_id"));
            name = rs.getString("full_name");
            H.put(name, ID);
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return H;
    }

    public boolean isValid(String fac, Integer num) throws Exception {
        String query = "select count(*) from finance_approver_list where facility_id = '"+fac+"' and personnel_id ="+num;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int count = 0;
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          if(rs.next()) {
            count = rs.getInt(1);
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally{
          dbrs.close();
        }
        return count > 0;
    }

    public Integer getPreferredForFacility(String facID) throws Exception {
        String query = "select personnel_id from finance_approver_list where preferred = 'Y' and facility_id = '"+facID+"'";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Integer ID = null;
        try{
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          if(rs.next()) {
            ID = new Integer(rs.getInt("personnel_id"));
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return ID;
    }

    public Hashtable getApprovedApprover(String prNumber) throws Exception {
        Hashtable result = new Hashtable();
        String query = "select p.last_name || ', '|| p.first_name full_name,nvl(p.phone,' ') phone,nvl(p.email,' ') email"+
                       ",nvl(a.rejection_reason,' ') rejection_reason from personnel p,purchase_request a"+
                       " where p.personnel_id = a.requested_finance_approver and a.pr_number = "+prNumber;
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while (rs.next()) {
            result.put("NAME", rs.getString("full_name"));
            result.put("PHONE", rs.getString("phone"));
            result.put("EMAIL", rs.getString("email"));
            result.put("COMMENT", rs.getString("rejection_reason"));
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }

        if(!result.containsKey("NAME")) {
            result.put("NAME", "No Approver Required");
        }
        return result;
    }

    public Hashtable getAllApproversForPR(String prNumber) throws Exception {
        Hashtable result = new Hashtable(5);
        Vector dataV = new Vector();
        String colHeads[] = {"Approver", "Phone", "E-mail"};
        int colWidths[] = {120, 100, 180};
        int colTypes[] = {1, 1, 1};
        String query = "select p.last_name || ', '|| p.first_name full_name,p.phone,p.email"+
                       " from personnel p where p.personnel_id = fx_mr_finance_approver("+prNumber+")";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        try {
          dbrs = db.doQuery(query);
          Object oa[];
          for(rs = dbrs.getResultSet(); rs.next(); dataV.addElement(((Object) (oa)))) {
              oa = new Object[colHeads.length];
              oa[0] = BothHelpObjs.makeBlankFromNull(rs.getString("full_name"));
              oa[1] = BothHelpObjs.makeBlankFromNull(rs.getString("phone"));
              oa[2] = BothHelpObjs.makeBlankFromNull(rs.getString("email"));
          }

        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        result.put("TABLE_HEADERS", colHeads);
        result.put("TABLE_WIDTHS", colWidths);
        result.put("TABLE_TYPES", colTypes);
        dataV.trimToSize();
        result.put("TABLE_DATA", dataV);
        return result;
    }

    public Vector getAllApprovers() throws Exception {
        String query = "select p.personnel_id from finance_approver_list f,personnel p where f.facility_id = '"+facility_id+
                       "' and f.personnel_id = p.personnel_id";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector app = new Vector();
        try {
          dbrs = db.doQuery(query);
          Personnel p;
          rs = dbrs.getResultSet();
          while (rs.next()) {
              p = new Personnel(db);
              p.setPersonnelId(rs.getInt("personnel_id"));
              p.load();
              app.addElement(p);
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return app;
    }

    public Hashtable getApproversInfo(Integer approver, Vector approversV) {
      Hashtable result = new Hashtable(7);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      String tmp = "";
      if (approver.intValue() != 0) {
        tmp = approver.toString();
      }else {
        for (int i = 0; i < approversV.size(); i++) {
          tmp += approversV.elementAt(i).toString()+",";
        }
        //remove the last commas ','
        if (tmp.length() > 1 ) {
          tmp = tmp.substring(0,tmp.length()-1);
        }
      }
      //if there are no approvers then don't execute sql for info
      if (tmp.length() > 0) {
        String query = "";
        try {
          query = "select last_name||', '||first_name full_name,nvl(phone,' ') phone,nvl(email,' ') email from personnel where personnel_id in("+tmp+")";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          Vector approverNameV = new Vector(23);
          Vector approverEmailV = new Vector(23);
          while (rs.next()) {
            String fullName = rs.getString("full_name");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            //approver name
            if (phone.length() > 0) {
              fullName += " ("+phone+")";
            }
            approverNameV.addElement(fullName);
            //approver email, if email is not blank then add to list otherwise ignore
            if (email.length() > 0) {
              approverEmailV.addElement(email);
            }
          }
          result.put("APPROVER_NAME",approverNameV);
          result.put("APPROVER_EMAIL",approverEmailV);
          result.put("OKAY",new Boolean(true));
        }catch  (Exception e) {
          e.printStackTrace();
        }finally {
          dbrs.close();
        }
      }else {
        result.put("OKAY",new Boolean(false));
      }
      return result;
    } //end of method

    public Hashtable getAllApprovers(double amount, PurchaseRequest pr ) throws Exception {
      Hashtable result = new Hashtable(3);
      if (pr.getFinancialApprover().intValue() > 0 && pr.getFinancialApprover().intValue() != pr.getRequestor().intValue()) {
        //first set approver
        result.put("APPROVER",pr.getFinancialApprover());
        //get approver that we pass coz his/her approval limit is lower than total price of MR
        Vector otherApproversV = new Vector(7);
        getOtherApprovers(pr.getRequestor(),otherApproversV,pr.getFinancialApprover(),pr.getFacilityId());
        //next get alternate approvers
        Vector alternateApproversV = new Vector(7);
        getAlternateApprovers(pr.getFinancialApprover(),alternateApproversV,pr.getFacilityId());
        //put it all together
        result.put("OTHER_APPROVERS",otherApproversV);
        result.put("ALTERNATE_APPROVERS",alternateApproversV);
      }else {
        if (pr.getFinancialApprover().intValue() == pr.getRequestor().intValue()) {
          result.put("APPROVER", new Integer(0));
          result.put("OTHER_APPROVERS", new Vector(0));
          result.put("ALTERNATE_APPROVERS", new Vector(0));
        }else {
          result.put("APPROVER", new Integer(86030));
          result.put("OTHER_APPROVERS", new Vector(0));
          result.put("ALTERNATE_APPROVERS", new Vector(0));
        }
      }
      return result;
    } //end of method

    //this method return list of approver above the request of MR and below the approver that can approve the MR dollar limit
    public void getOtherApprovers(Integer tmpUserID, Vector result, Integer mrApprover, String facilityID) throws Exception {
      DBResultSet dbrs = null;
      ResultSet rs = null;
      String query = "";
      try {
        query = "select nvl(approver,0) approver from finance_approver_list where facility_id = '"+facilityID+"' and personnel_id = "+tmpUserID.intValue()+
                " and personnel_id <> nvl(approver,0)";
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int approverID = 0;
        while (rs.next()) {
          approverID = rs.getInt("approver");
        }
        if (approverID == mrApprover.intValue()) {
          //DONE - do nothing
        }else if (approverID == 0) {
          //DONE - do nothing
        }else {
          //otherwise, add approver to approver list and then go get his approver
          result.addElement(new Integer(approverID));
          getOtherApprovers(new Integer(approverID),result,mrApprover,facilityID);
        }
      }catch  (Exception e) {
        e.printStackTrace();
        result.addElement(new Integer(86030));
      }finally {
        dbrs.close();
      }
    } //end of method

    public Integer getApprover(String facId, double amount, Integer userId) throws Exception {
        FinanceApproverList fal = new FinanceApproverList(db);
        fal.setFacilityId(facId);
        fal.setPersonnelId(userId.intValue());
        fal.load();
        //compare user cost limit
        if(amount <= (double)fal.getCostLimit() || fal.getCostLimit() < (float)0) {
            if(fal.getPersonnelId() == null) {
                return new Integer(0);
            } else {
                return fal.getPersonnelId();
            }
        }
        if(fal.getApprover() != null && fal.getApprover().intValue() != 0) {
            return getApproverNew(facId,amount,fal.getApprover());
        } else {
            return new Integer(0);
        }
    }

    public Integer getApproverNew(String facId, double amount, Integer userId) throws Exception {
        FinanceApproverList fal = new FinanceApproverList(db);
        fal.setFacilityId(facId);
        fal.setPersonnelId(userId.intValue());
        do {
          fal.load();
          if(amount <= (double)fal.getApprovalLimit() || fal.getApprovalLimit() < (float)0) {
              if(fal.getPersonnelId() == null) {
                  return new Integer(0);
              } else {
                  return fal.getPersonnelId();
              }
          }
          if(fal.getApprover() != null && fal.getApprover().intValue() != 0) {
              int nextOne = fal.getApprover().intValue();
              fal = new FinanceApproverList(db);
              fal.setFacilityId(facId);
              fal.setPersonnelId(nextOne);
          } else {
              return new Integer(0);
          }
        } while(true);
    }


    public boolean canApprove(String facId, double amount, Integer userId) throws Exception {
        try {
          Integer app = new Integer(getApprover(facId, amount, userId).intValue());
          if(app.intValue() == -1 || userId.intValue() == app.intValue()) {
              boolean flag = true;
              return flag;
          } else {
              boolean flag1 = false;
              return flag1;
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }
    }

    public boolean canApprovePR(int reqNum, Integer userId) throws Exception {
      String query = "select nvl(pr.requested_finance_approver,0) approver, nvl(facility_id,' ') facility_id from purchase_request pr"+
                     " where pr_number = "+reqNum;
      boolean result = false;
      Vector approversV = new Vector(23);
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int approver = 0;
        String facID = "";
        while (rs.next()) {
          approver = rs.getInt("approver");
          approversV.addElement(new Integer(approver));
          facID = rs.getString("facility_id");
        }
        if (approver != 0) {
          //get approver alternates
          getAlternateApprovers(new Integer(approver),approversV,facID);
          //get current approver - approver (going up the approval chain)
          getMyApproverList(new Integer(approver),approversV,facID);
        }
        result = approversV.contains(userId);
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        dbrs.close();
      }
      return result;
    }

    public void getAlternateApprovers(Integer approver, Vector approversV, String facID) throws Exception {
      String query = "select alternate_approver from finance_alternate_approver where approver = "+approver.intValue()+" and facility_id = '"+facID+"'";
      boolean result = false;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        while (rs.next()) {
          approversV.addElement(new Integer(rs.getString("alternate_approver")));
        }
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        dbrs.close();
      }
    }

    //this method return all the my approvers and their alternate in my approval chain
    //i.e. trong -> chuck -> mike -> thad
    //i.e. chuck -> mike -> thad
    //i.e. mike -> thad
    public void getMyApproverList(Integer approver, Vector approversV, String facID) throws Exception {
      String query = "select nvl(approver,0) approver from finance_approver_list where personnel_id = "+approver.intValue()+" and facility_id = '"+facID+"' and personnel_id <> nvl(approver,0)";
      boolean result = false;
      DBResultSet dbrs = null;
      ResultSet rs = null;
      try {
        dbrs = db.doQuery(query);
        rs = dbrs.getResultSet();
        int tmp = 0;
        while (rs.next()) {
          tmp = rs.getInt("approver");
        }
        if (tmp != 0) {
          //add approver to list
          approversV.addElement(new Integer(tmp));
          //get approver alternates
          getAlternateApprovers(new Integer(tmp),approversV,facID);
          //get current approver - approver (going up the approval chain)
          getMyApproverList(new Integer(tmp),approversV,facID);
        }
      }catch(Exception e) {
        e.printStackTrace();
      }finally {
        dbrs.close();
      }
    }

    public Vector getApproversForFacility() throws Exception {
        String query = "select f.personnel_id,f.approver,f.cost_limit,f.approval_limit,p.last_name||', '||p.first_name full_name"+
                       " from finance_approver_list f, personnel p"+
                       " where f.personnel_id = p.personnel_id and f.facility_id = '"+facility_id+"' and f.personnel_id <> nvl(f.approver,0)";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Vector v = new Vector();
        try {
          dbrs = db.doQuery(query);
          FinanceApproverList f;
          for(rs = dbrs.getResultSet(); rs.next(); v.addElement(f)) {
              f = new FinanceApproverList(db);
              f.setFacilityId(facility_id);
              f.setPersonnelId(rs.getInt("personnel_id"));
              f.setUserFullName(rs.getString("full_name"));
              f.setApprover(rs.getInt("approver"));
              f.setCostLimit(rs.getFloat("cost_limit"));
              f.setApprovalLimit(rs.getFloat("approval_limit"));
          }
        }catch(Exception e) {
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return v;
    }

    public void updateApprover(String facId, Integer pid, float costLimit, float approvalLimit, String superID, Boolean moveChildren, Integer tmpAdminID) throws Exception {
        String query = "";
        FinanceApproverList fal = new FinanceApproverList(db);
        fal.setAdminID(tmpAdminID);
        fal.setPersonnelId(pid.intValue());
        fal.setFacilityId(facId);
        if(!moveChildren.booleanValue()) {
          moveChildrenToSuper(fal);
        }
        fal.setCostLimit(costLimit);
        fal.setApprovalLimit(approvalLimit);
        if(approvalLimit > (float)-1) {
          fal.setApprover((new Integer(superID)).intValue());
        }
        fal.update();
    }

    public void deleteApprover() throws Exception {
      try {
        moveChildrenToSuper(this);
        String query = "";
        //first update this record to show you updated this record
        if (adminID == null) {
          adminID = new Integer(-1);
        }
        query = "update finance_approver_list set last_date_updated = sysdate,last_updated_by = "+adminID.intValue()+
                " where facility_id = '"+facility_id+"' and personnel_id = "+personnel_id.toString();
        db.doUpdate(query);
        //now delete record
        query = "delete finance_approver_list where facility_id = '"+facility_id+"' and personnel_id = "+personnel_id.toString();
        db.doUpdate(query);
      }catch(Exception e) {
        e.printStackTrace();
      }
    }

    void moveChildrenToSuper(FinanceApproverList fal) throws Exception {
        fal.load();
        if (adminID == null) {
          adminID = new Integer(-1);
        }
        String query = "update finance_approver_list set approver = "+fal.getApprover().toString()+",last_date_updated = sysdate,last_updated_by = "+adminID.intValue()+
                       " where facility_id = '"+fal.getFacilityId()+"' and approver = "+fal.getPersonnelId().toString();
        try {
          db.doUpdate(query);
        }catch(Exception e1) {
          e1.printStackTrace();
          throw e1;
        }
    }

    void update() throws Exception {
        try {
          String appString = "";
          if(approver != null) {
            appString = approver.toString();
          } else {
            appString = null;
          }
          if (approval_limit == -100) {
            approval_limit = cost_limit;
          }
          if (adminID == null) {
            adminID = new Integer(-1);
          }
          String query = "update finance_approver_list set approver = "+appString+", cost_limit = "+cost_limit+", approval_limit = "+approval_limit+
                         ", last_date_updated = sysdate, last_updated_by = "+adminID.intValue()+
                         " where facility_id = '"+facility_id+"' and personnel_id = "+personnel_id.toString();
          db.doUpdate(query);
        }catch(Exception e) {
          e.printStackTrace();
        }
    }
} //end of class
