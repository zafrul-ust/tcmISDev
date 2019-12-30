
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description

package radian.tcmis.client.share.gui.admin;

import java.text.*;
import javax.swing.tree.*;
import java.util.*;

class AdminApprover extends DefaultMutableTreeNode{
  String name;
  Integer id;
  Integer superId;
  Float costLimit;
  Float approvalLimit;
  boolean isFac = false;
  public AdminApprover(Hashtable h){
    name = h.get("NAME").toString();
    id = (Integer)h.get("ID");
    superId = (Integer)h.get("SUPER_ID");
    costLimit = (Float)h.get("COST_LIMIT");
    approvalLimit = (Float)h.get("APPROVAL_LIMIT");
  }
  public AdminApprover(String fac){
    name = new String(fac);
    id = new Integer(0);
    superId = new Integer(0);
    costLimit = new Float(0.0);
    approvalLimit = new Float(0.0);
    isFac = true;
  }
  public String debugString(){
    return "name:"+name+"ID:"+id+"super:"+superId+"costLimit:"+costLimit;
  }
  public boolean isFac(){
    return isFac;
  }
  public int getId(){
    return id.intValue();
  }
  public int getSuperId(){
    return superId.intValue();
  }
  public String getName(){
    return name;
  }
  public float getCostLimit(){
    return costLimit.floatValue();
  }
  public float getApprovalLimit() {
    return approvalLimit.floatValue();
  }

  public String toString(){
    if(isFac){
      return name;
    }
    //cost limit
    String amount = "";
    if(costLimit .floatValue() < 0){
      amount = "Unlimited";
    }else{
      //amount = NumberFormat.getCurrencyInstance().format(costLimit.doubleValue());
      amount = NumberFormat.getNumberInstance().format(costLimit.doubleValue());
    }
    //approval limit
    String approvalAmount = "";
    if(approvalLimit .floatValue() < 0){
      approvalAmount = "Unlimited";
    }else{
      //approvalAmount = NumberFormat.getCurrencyInstance().format(approvalLimit.doubleValue());
      approvalAmount = NumberFormat.getNumberInstance().format(approvalLimit.doubleValue());
    }
    return name+" "+amount+" / "+approvalAmount;
  }
  public void setParent(AdminApprover parent){
    superId = new Integer(parent.getSuperId());
    super.setParent(parent);
  }
  public void recursiveAdd(Vector v){
    for(int i=v.size();i>0;i--){
      if(i>v.size())continue;
      AdminApprover aa = (AdminApprover) v.elementAt(i-1);
      if(aa.getSuperId() == id.intValue()){
        add(aa);
        v.removeElementAt(i-1);
        aa.recursiveAdd(v);
      }
    }
  }
  public String getFormatedCostLimit(){
    String amount = "";
    if(costLimit.floatValue() < 0){
      amount = "Unlimited";
    }else{
      //amount = NumberFormat.getCurrencyInstance().format(costLimit.doubleValue());
      amount = NumberFormat.getNumberInstance().format(costLimit.doubleValue());
    }
    return amount;
  }
  public String getFormatedapprovalLimit(){
    String amount = "";
    if(approvalLimit.floatValue() < 0){
      amount = "Unlimited";
    }else{
      //amount = NumberFormat.getCurrencyInstance().format(approvalLimit.doubleValue());
      amount = NumberFormat.getNumberInstance().format(approvalLimit.doubleValue());
    }
    return amount;
  } //end of method
} //end of class

