package radian.tcmis.client.share.httpCgi;


import java.util.*;
import radian.tcmis.both1.helpers.*;

public class HttpFormatter {

   // Constructors
  public HttpFormatter() {
  }

  public String hashToString(Hashtable hash)  {
    return null;
  }

  public Vector hash2DToString(Hashtable hash)  {
     Vector result = new Vector();//it will have one name on each size
     Hashtable h1D = new Hashtable();
     Enumeration E;
     String value1 = new String("");
     String value2 = new String("");
     String value3= new String("");
     for(E=hash.keys();E.hasMoreElements();){
       Object value1T = E.nextElement();
       h1D = (Hashtable) hash.get(value1T);
       Enumeration E2;
       boolean flagString = true;
       for (E2=h1D.keys();E2.hasMoreElements();){
         Object value2T  = E2.nextElement();
         String value3T  = new String();
         Vector value3TV = new Vector();
         try{
           value3T = (String) h1D.get(value2T);
           flagString = true;
         } catch (Exception e){
           try{
             value3T = ((Integer) h1D.get(value2T)).toString();
             flagString = true;
           } catch (Exception e2){
             value3TV = (Vector) h1D.get(value2T);
             flagString = false;
           }
         }
         if (flagString){
           value1 = value1 + (value1.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + value1T.toString() ;
           value2 = value2 + (value3.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"")+ value2T.toString() ;
           value3 = value3 + (value3.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + value3T;
         } else {
           for (int i=0;i<value3TV.size();i++){
             value1 = value1 + (value1.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + value1T ;
             value2 = value2 + (value2.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + value2T;
             value3 = value3 + (value3.length()>0?BothHelpObjs.VALUE_VALUE_DEL:"") + value3TV.elementAt(i);
           }
         }
       }
     }
     result.addElement(value1);
     result.addElement(value2);
     result.addElement(value3);

     return result;
  }

  public static Object[][] vectorTo2DArray(Vector v, int cols) {
    Object[][] data = null;
    int colJ = 0;
    int rowI = 0;
    if (v.size() > 1){
      int rowNum =  v.size() / cols;
      data = new Object[rowNum][cols];
      for (int i=0;i<v.size();i++){
        data[rowI][colJ] = (Object) v.elementAt(i);
        colJ++;
        if (colJ == cols) {
         rowI++;
         colJ=0;
        }
      }
    }
    return data;
  }


}






















