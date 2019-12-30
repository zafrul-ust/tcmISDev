package radian.tcmis.client.share.helpers;

import java.util.*;
import radian.tcmis.client.share.httpCgi.*;
import radian.tcmis.both1.helpers.*;

public class NextNameTextField extends TypeAheadTextField {
  protected final String ACTION = new String("GET_NEXT_NAME");
  protected String actionType = new String("PERSONNEL");
  protected String fac = new String(" ");
  protected String who = new String(" ");
  protected Vector whoV = new Vector();
  protected String preferredActionType = "";

  public NextNameTextField() {
    super();
  }
  public NextNameTextField(String s) {
    super();
    actionType = new String(s);
  }

  public void setFac(String s) {
    this.fac = s;
  }
  public void setActionType(String s) {
    actionType = new String(s);
  }
  public void setPreferredActionType(String s) {
    preferredActionType = new String(s);
  }
  public void setWhoV(Vector v) {
    whoV = v;
   }
  public Vector getWhoV() {
    return whoV;
  }
  public String getPersonnelId(){
    return whoV.elementAt(2).toString();
  }
  public void backSpaceTyped() {
    Vector v = new Vector();
    v.addElement("");
    v.addElement("");
    v.addElement("0");
    setWhoV(v);
  }

  protected String getNextString() {
    if(BothHelpObjs.isBlankString(this.getText())){
      Vector v = new Vector();
      v.addElement("");
      v.addElement("");
      v.addElement("0");
      setWhoV(v);
      //setText("");
      //setCaretPosition(0);
      //this.setSelectionEnd(0);
      return this.getText();
    }

    if(parent == null)return this.getText();
    TcmisHttpConnection cgi = new TcmisHttpConnection(TcmisHttpConnection.PERSONNEL,parent);
    if (cgi == null) return this.getText();
    try {
      Hashtable query = new Hashtable();
      query.put("ACTION",ACTION);
      query.put("ACTIONTYPE",actionType);
      query.put("FAC",fac);
      query.put("WHO",this.getText().trim());

      Hashtable result = cgi.getResultHash(query);
      if (result == null){
        return this.getText();
      }

      setWhoV((Vector) result.get("NEXT_NAMES"));
      if (whoV.size()  == 3){
        if ((new Integer(whoV.elementAt(2).toString())).intValue() != 0){
          return (whoV.elementAt(0).toString() + ", " + whoV.elementAt(1).toString());
        }
      }
    } catch (Exception e1) { e1.printStackTrace();}
    return this.getText();
  }
}



