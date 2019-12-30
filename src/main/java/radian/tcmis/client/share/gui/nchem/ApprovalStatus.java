

//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:
package radian.tcmis.client.share.gui.nchem;

import java.util.*;

public class ApprovalStatus {
  Vector roles = new Vector();
  Vector stat = new Vector();
  Vector approvers = new Vector();
  Vector dates = new Vector();
  Vector reasons =  new Vector();
  Vector appNums = new Vector();
  Vector type = new Vector();
  Vector superUser = new Vector();

  public ApprovalStatus(){

  }

  public ApprovalStatus(Vector roles,Vector stat,Vector approvers,Vector dates,Vector reasons,Vector appNums,Vector type,Vector superUser){
       this( roles, stat, approvers, dates, reasons, appNums, type);
       this.superUser = superUser;
  }

  public ApprovalStatus(Vector roles,Vector stat,Vector approvers,Vector dates,Vector reasons,Vector appNums,Vector type){
       this.roles = roles ;
       this.stat = stat;
       this.approvers =approvers;
       this.dates = dates;
       this.reasons = reasons;
       this.appNums = appNums;
       this.type = type;
  }

  public boolean needsApproval(int userid){
       for (int i=0;i<roles.size();i++){
          if (appNums.elementAt(i).toString().equals((new Integer(userid)).toString())){
             if ( stat.elementAt(i) == null ||
                 (!stat.elementAt(i).toString().equalsIgnoreCase("approved") &&
                  !stat.elementAt(i).toString().equalsIgnoreCase("banned")   &&
                  !stat.elementAt(i).toString().equalsIgnoreCase("reject"))){
                    return true;
             }
          }
       }

       return false;
  }

  public Hashtable getRolesToApprove(int userid){
       Hashtable res = new Hashtable();
       for (int i=0;i<roles.size();i++){
          if (appNums.elementAt(i).toString().equals((new Integer(userid)).toString())){
               Vector elems = new Vector();
               //8-01-02 only add approval role where status is Pending
               String tmpStatus = (String)stat.elementAt(i);
               if (!"Pending".equalsIgnoreCase(tmpStatus)) continue;

               elems.addElement(tmpStatus);
               elems.addElement(approvers.elementAt(i).toString());
               elems.addElement(dates.elementAt(i).toString());
               elems.addElement(reasons.elementAt(i).toString());
               elems.addElement(appNums.elementAt(i).toString());
               elems.addElement(type.elementAt(i).toString());

               if (!res.containsKey(roles.elementAt(i).toString())) res.put(roles.elementAt(i).toString(),elems);
          }
       }
       return res;
  }

  public int getMaxRoleType(int userid){
       int t = 0;
       for (int i=0;i<roles.size();i++){
          if (appNums.elementAt(i).toString().equals((new Integer(userid)).toString())){
            if   ( stat.elementAt(i) != null &&
                 ( stat.elementAt(i).toString().equalsIgnoreCase("approved") ||
                   stat.elementAt(i).toString().equalsIgnoreCase("banned")   ||
                   stat.elementAt(i).toString().equalsIgnoreCase("reject"))){
                    continue;
             }
             int m = Integer.parseInt(type.elementAt(i).toString());
             if (t<m) t=m;
          }
       }
       // System.out.println("Returned:"+t);
       return t;
  }

  public boolean isSuperUser(int userid){
       int t = 0;
       for (int i=0;i<roles.size();i++){
          if (appNums.elementAt(i).toString().equals((new Integer(userid)).toString())){
            if   ( stat.elementAt(i) != null &&
                 ( stat.elementAt(i).toString().equalsIgnoreCase("approved") ||
                   stat.elementAt(i).toString().equalsIgnoreCase("banned")   ||
                   stat.elementAt(i).toString().equalsIgnoreCase("reject"))){
                    continue;
             }
             String flag = superUser.elementAt(i).toString();
             // if user is super for any role, he/she is super for the request.
             if (flag.trim().equalsIgnoreCase("y")) return true;
          }
       }
       // System.out.println("Returned:"+t);
       return false;
  }

  //trong 3/29
  public Integer getApproverType(int userid) {
    Vector temp = new Vector();
    Integer num;
    int result = 0;
    for (int i = 0; i < appNums.size(); i++) {
      if (appNums.elementAt(i).toString().equals((new Integer(userid)).toString())){
        temp.addElement(new Integer(i));
      }
    }

    if (temp.size() > 0) {
      for (int j = 0; j < temp.size(); j++) {
        num = (Integer)temp.elementAt(j);
        String n = type.elementAt(num.intValue()).toString();
        num = new Integer(n);
        if (num.intValue() > result) {
          result = num.intValue();
        }
      }
    }
    return (new Integer(result));
  }


}
