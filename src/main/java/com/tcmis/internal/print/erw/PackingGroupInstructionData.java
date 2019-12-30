package com.tcmis.internal.print.erw;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 1998
 * Company:      Your Company
 * @author Your Name
 * @version
 */

import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.print.beans.PackingGroupInstructionViewBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class PackingGroupInstructionData
{
	private String packingGroupId;
	private String instructions;

  public PackingGroupInstructionData(PackingGroupInstructionViewBean bean) {
    packingGroupId=StringHandler.emptyIfNull(bean.getPackingGroupId().toString());
	  instructions=StringHandler.emptyIfNull(bean.getInstructions());
  } //end of method


	//getters
	public String getPackingGroupId() {
		return packingGroupId;
	}

	public String getInstructions() {
		return instructions;
	}


public static Vector getFieldVector() {
  Vector v = new Vector();
  v.addElement("packingGroupId= getPackingGroupId");
	v.addElement("instructions= getInstructions");
  return v;
 }


public static Vector getVector(Collection in) {
	Vector out = new Vector();
	Iterator iterator = in.iterator();
	while (iterator.hasNext()) {
		out.addElement(new PackingGroupInstructionData((PackingGroupInstructionViewBean) iterator.next()));
	}
	return out;
}  
}
