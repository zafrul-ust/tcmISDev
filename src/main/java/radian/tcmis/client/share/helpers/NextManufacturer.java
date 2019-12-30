package radian.tcmis.client.share.helpers;

import java.util.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.client.share.gui.*;


public class NextManufacturer extends TypeAheadTextField {
  protected TcmisHttpConnection cgi = null;
  protected final String ACTION = new String("NEXT_MANUFACTURER");
  protected String actionType = new String("SEARCH_INFO");
  protected String manDesc = new String(" ");
  protected String manId = new String(" ");
  protected CmisApp parent = null;


  public NextManufacturer(TcmisHttpConnection cgi) {
    super();
    this.cgi = cgi;
  }

  public NextManufacturer() {
    super();
  }
  public NextManufacturer(String s) {
    super();
    actionType = new String(s);
  }

  public void setActionType(String s) {
    actionType = new String(s);
  }

  public void setManId(String s) {
    manId = s;
   }
  public String getManId() {
    return manId;
  }
  public void setManDesc(String s) {
    manDesc = s;
   }
  public String getManDesc() {
    return manDesc;
  }
  public void setCmisApp(CmisApp parent) {
    this.parent = parent;
  }

  protected String getNextString() {
    if(parent == null) return this.getText();
    cgi = new TcmisHttpConnection(TcmisHttpConnection.SEARCH_INFO,parent);
    try {
      Hashtable query = new Hashtable();
      query.put("ACTION",ACTION);
      query.put("ACTIONTYPE",actionType);
      query.put("QUERY",this.getText().trim());

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        return this.getText();
      }

      Vector v = (Vector)result.get("DATA");
      if(v == null || v.size() < 2) {
        return this.getText();
      }
      setManId(v.elementAt(0).toString());
      setManDesc(v.elementAt(1).toString());

      return getManDesc();
    } catch (Exception e1) { e1.printStackTrace();}
    return this.getText();
  }
}
