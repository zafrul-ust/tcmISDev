package radian.tcmis.client.share.helpers;

import java.util.*;
import radian.tcmis.client.share.gui.*;

public class UserGroupMembership {
  protected CmisApp cmis;
  protected Vector id;
  protected Vector desc;
  protected Vector fac;

  public UserGroupMembership(CmisApp cmis){
    this.cmis = cmis;
    id = new Vector();
    desc = new Vector();
    fac = new Vector();
  }
  public void setGroups(Vector i,Vector d, Vector f){
    id = i;
    desc = d;
    fac = f;
  }
  public boolean isGroupMember(String group, String facId){
    for(int i=0;i<id.size();i++){
      if(id.elementAt(i).toString().equalsIgnoreCase(group) &&
         fac.elementAt(i).toString().equalsIgnoreCase(facId))return true;
    }
    return false;
  }
  public boolean isAdministrator(String facId){
    for(int i=0;i<id.size();i++){
      if(id.elementAt(i).toString().equalsIgnoreCase("administrator") &&
         fac.elementAt(i).toString().equalsIgnoreCase(facId))return true;
    }
    return false;
  }

  public Vector getAdminFacilities(){
    return getFacsForGroup("administrator");
  }

  public Vector getFacsForGroup(String groupID){
    Vector v = new Vector();
    for(int i=0;i<id.size();i++){
      if(id.elementAt(i).toString().equalsIgnoreCase(groupID)){
        v.addElement(fac.elementAt(i).toString());
      }
    }
    return v;
  }

  public boolean isRadian(){
    return inAllGroup("radian");
  }
  public boolean isSuperUser(){
    return inAllGroup("superuser");
  }
  public boolean canDesignReports(){
    return inAllGroup("designreports");
  }
  public boolean isAdminAtSomeFacility(){
    return inAllGroup("administrator");
  }
  private boolean inAllGroup(String groupId){
    for(int i=0;i<id.size();i++){
      if(id.elementAt(i).toString().equalsIgnoreCase(groupId))return true;
    }
    return false;
  }
}






