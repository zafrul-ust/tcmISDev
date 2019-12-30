package radian.tcmis.internal.admin.beans;

import java.util.Collection;
import java.util.Iterator;
import radian.tcmis.common.framework.BaseDataBean;
/******************************************************************************
 * Place holder for a collection of <code>UserGroupMemberIgBean</code> <br>
 * @version: 1.0, Sep 21, 2004 <br>
 *****************************************************************************/

public class PermissionBean
    extends BaseDataBean {

  private Collection userGroupMemberIgBeanCollection;
  private Collection userGroupMemberBeanCollection;

  //constructor
  public PermissionBean() {
    super();
  }

  //setters
  public void setUserGroupMemberIgBeanCollection(Collection coll) {
    this.userGroupMemberIgBeanCollection = coll;
  }

  public void setUserGroupMemberBeanCollection(Collection coll) {
	this.userGroupMemberBeanCollection = coll;
  }


  //getters
  public Collection getUserGroupMemberIgBeanCollection() {
    return this.userGroupMemberIgBeanCollection;
  }

  public Collection getUserGroupMemberBeanCollection() {
	 return this.userGroupMemberBeanCollection;
   }

  public boolean hasPermission(String userGroupId,
                               String facilityId,
                               String inventoryGroup) {
    boolean flag = false;
    if(userGroupMemberIgBeanCollection != null) {
      Iterator iterator = userGroupMemberIgBeanCollection.iterator();
      while(iterator.hasNext() && !flag) {
        UserGroupMemberIgBean bean = (UserGroupMemberIgBean)iterator.next();
        if(userGroupId != null &&
           userGroupId.trim().length() > 0 &&
           bean.getUserGroupId().equals(userGroupId)) {
          if((facilityId == null ||
              facilityId.trim().length() < 1) &&
             (inventoryGroup == null ||
              inventoryGroup.trim().length() < 1)){
            flag = true;
          }
          else if(facilityId != null &&
                  bean.getFacilityId().equals(facilityId) &&
                  (inventoryGroup == null || inventoryGroup.trim().length() < 1)) {
            flag = true;
          }
          else if(inventoryGroup != null &&
                  bean.getInventoryGroup().equals(inventoryGroup) &&
                  (facilityId == null || facilityId.trim().length() < 1)) {
            flag = true;
          }
          else if(facilityId != null &&
                  bean.getFacilityId().equals(facilityId) &&
                  inventoryGroup != null &&
                  bean.getInventoryGroup().equals(inventoryGroup)) {
            flag = true;
          }
        }
      }
    }
    return flag;
  }

  public boolean hasPermission(String userGroupId,
							  String facilityId) {
   boolean flag = false;
   if(userGroupMemberBeanCollection != null) {
	 Iterator iterator = userGroupMemberBeanCollection.iterator();
	 while(iterator.hasNext() && !flag) {
	   UserGroupMemberBean bean = (UserGroupMemberBean)iterator.next();
	   if(userGroupId != null &&
		  userGroupId.trim().length() > 0 &&
		  bean.getUserGroupId().equals(userGroupId)) {
		 if((facilityId == null ||
			 facilityId.trim().length() < 1) ){
		   flag = true;
		 }
		 else if(facilityId != null &&
				 bean.getFacilityId().equals(facilityId) ) {
		   flag = true;
		 }
		 /*else if(inventoryGroup != null &&
				 bean.getInventoryGroup().equals(inventoryGroup) &&
				 (facilityId == null || facilityId.trim().length() < 1)) {
		   flag = true;
		 }*/
		 else if(facilityId != null &&
				 bean.getFacilityId().equals(facilityId)) {
		   flag = true;
		 }
	   }
	 }
   }
   return flag;
 }

}