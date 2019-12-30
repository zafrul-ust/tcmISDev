//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description

package radian.tcmis.both1.helpers;

import java.util.*;
import java.text.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import crysec.DES;
import crysec.SymmetricKey;
import java.util.Date;
import java.util.Calendar;
import com.tcmis.common.util.NetHandler;

public abstract class BothHelpObjs {

  public static final int NEW_REQUEST = 0;
  public static final int TO_APPROVE = 1;
  public static final int TO_RELEASE = 2;
  public static final int REJECTED = 3;
  public static final int VIEW = 4;
  public static final int DONE = 5;

  public static final String TOKEN_DEL = "/";
  public static final String NAME_VALUE_DEL = "~";
  public static final String VALUE_VALUE_DEL = "|";
  public static final String NAME_NAME_DEL = "`";
  public static final String BYTES_DEL = "^";
  public static final String SUPER_USER = "SuperUser";
  public static final String ADMIN_USER = "Administrator";
  public static final String CONN_USER = "Connection";
  public static final String NEW_LINE_CHAR = "`~^";

  public static final int TABLE_COL_TYPE_STRING = 1;
  public static final int TABLE_COL_TYPE_NUMBER = 2;
  public static final int TABLE_COL_TYPE_DATE_STRING = 3;
  public static final int TABLE_COL_TYPE_BOOLEAN = 4;
  public static final int TABLE_COL_TYPE_JLABEL_STRING = 5;
  public static final int TABLE_COL_TYPE_JLABEL_NUMBER = 6;
  public static final int TABLE_COL_TYPE_JLABEL_DATE = 7;
  public static final int TABLE_COL_TYPE_NUMERIC_STRING = 8;
  public static final int TABLE_COL_TYPE_NUMERIC_STRING_2 = 9;

  public static String sendPost(String url, String data, String mimetype) throws Exception {
    //System.setProperty("java.protocol.handler.pkgs","com.sun.net.ssl.internal.www.protocol");
    java.net.URL target = new URL(url);
    java.net.HttpURLConnection uc = (java.net.HttpURLConnection) target.openConnection();
    uc.setDoOutput(true);
    uc.setUseCaches(false);
    uc.setRequestMethod("POST");
    uc.setRequestProperty("Content-Type", mimetype);
    // serialize and write the data
    if (data != null) {
      OutputStream os = uc.getOutputStream();
      PrintWriter writer = new PrintWriter(os);
      writer.write(data);
      writer.close();
    }
    InputStream in = uc.getInputStream();
    BufferedReader br = new BufferedReader(new java.io.InputStreamReader(in));
    String line;
    StringBuffer sb = new StringBuffer();
    while ( (line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();
    //logger.debug(sb.toString());
    // get confirmation
    int rspcode = uc.getResponseCode();
    //System.out.println("------------ response code from POST: " + rspcode + " data: " + data);
    uc.disconnect();
    return sb.toString();
  }

  //COMMENT THIS METHOD OUT IF COMPILE IN JRE1.3 and uncomment for JRE1.4
  public static String sendSslPost(String url, String data, String mimetype) throws Exception {
    NetHandler netHandler = new NetHandler();
    String result = ""+netHandler.sendHttpsPost(url,null,null,data,null);
    return result;
    /*
    java.net.URL target = new URL(url);
    com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl uc = (com.sun.net.ssl.internal.www.protocol.https.HttpsURLConnectionOldImpl) target.openConnection();
    uc.setDoOutput(true);
    uc.setUseCaches(false);
    uc.setRequestMethod("POST");
    uc.setRequestProperty("Content-Type", mimetype);
    // serialize and write the data
    if (data != null) {
      OutputStream os = uc.getOutputStream();
      PrintWriter writer = new PrintWriter(os);
      writer.write(data);
      writer.close();
    }
    InputStream in = uc.getInputStream();
    BufferedReader br = new BufferedReader(new java.io.InputStreamReader(in));
    String line;
    StringBuffer sb = new StringBuffer();
    while ( (line = br.readLine()) != null) {
      sb.append(line);
    }
    br.close();
    //logger.debug(sb.toString());
    // get confirmation
    int rspcode = uc.getResponseCode();
    //System.out.println("------------ response code from ssl POST: " + rspcode + " data: " + data);
    uc.disconnect();
    return sb.toString();
    */
  }


  //this method write to a file and return a URL string
  public static String sendPostWriteToFile(String url, String data, String mimetype, String fileName) throws Exception {
    URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
      public URLStreamHandler createURLStreamHandler(final String protocol) {
        if ("https".equals(protocol)) {
          //JRE1.4+
          //return new com.sun.net.ssl.internal.www.protocol.https.Handler();
          //JRE1.3
          return null;
        }
        ;
        return null;
      }
    });

    java.net.URL target = new URL(url);
    java.net.HttpURLConnection uc = (java.net.HttpURLConnection) target.openConnection();
    uc.setDoOutput(true);
    uc.setUseCaches(false);
    uc.setRequestMethod("POST");
    uc.setRequestProperty("Content-Type", mimetype);

    // serialize and write the data
    if (data != null) {
      OutputStream os = uc.getOutputStream();
      PrintWriter writer = new PrintWriter(os);
      writer.write(data);
      writer.close();
    }

    String wDir = new String(System.getProperty("user.dir"));
    String file = wDir + System.getProperty("file.separator") + fileName;
    File test = new File(file);
    test.delete();
    BufferedWriter bw = new BufferedWriter(new FileWriter(file));

    InputStream in = uc.getInputStream();
    BufferedReader br = new BufferedReader(new java.io.InputStreamReader(in));
    String line;
    StringBuffer sb = new StringBuffer();
    while ( (line = br.readLine()) != null) {
      //sb.append(line);
      if (bw != null) {
        bw.write(line);
      }
    }
    bw.close();
    br.close();
    //logger.debug(sb.toString());

    // get confirmation
    int rspcode = uc.getResponseCode();
    //System.out.println("------------ response code from POST: "+rspcode+" data: "+data);
    uc.disconnect();

    URL tmp = new URL("file:///" + file);

    /*12-12-01
         long eTime = new java.util.Date().getTime();
         long min = (eTime-sTime);
         GregorianCalendar cal = new GregorianCalendar();
         cal.setTime(new Date(min));
         System.out.println("Writing to file Duration: "+cal.get(Calendar.MINUTE)+" minutes and "+cal.get(Calendar.SECOND)+" seconds");
     */
    return (tmp.toExternalForm());
  }

  /*1-04-02 move this method to ClientHelpObjs since I need to get back to server to log the response code from Ariba
     //this method write to a file and return a URL string
     public static String punchOutSendPostWriteToFile(String url, String data, String mimetype, String fileName) throws Exception {
    URL tmp = null;
    try {
      URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
        public URLStreamHandler createURLStreamHandler(final String protocol) {
          if( "https".equals(protocol) ) {
            return new com.sun.net.ssl.internal.www.protocol.https.Handler();
          };
          return null;
        }
      });
      java.net.URL target = new URL(url);
      java.net.HttpURLConnection uc = (java.net.HttpURLConnection)target.openConnection();
      uc.setDoOutput(true);
      uc.setUseCaches(false);
      uc.setRequestMethod("POST");
      uc.setRequestProperty("Content-Type", mimetype);
      // serialize and write the data
      if( data != null ) {
        OutputStream os = uc.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        writer.write(data);
        writer.close();
      }
      String wDir = new String(System.getProperty("user.dir"));
      String file = wDir +  System.getProperty("file.separator") + fileName;
      File test = new File(file);
      test.delete();
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      InputStream in = uc.getInputStream();
      BufferedReader br = new BufferedReader(new java.io.InputStreamReader(in));
      String line;
      StringBuffer sb = new StringBuffer();
      while( (line = br.readLine()) != null ) {
        //sb.append(line);
        if (bw != null){
          bw.write(line);
        }
      }
      bw.close();
      br.close();
      //logger.debug(sb.toString());
      // get confirmation
      int rspcode = uc.getResponseCode();
      //System.out.println("------------ response code from POST: "+rspcode+" data: "+data);
      uc.disconnect();
      //1-04-01 log response code from Ariba
      //sendAribaResponseCodeToServer(rspcode,payLoadID);
      tmp = new URL("file:///"+file);
      //URL tmp = new URL("file:///"+file);
    }catch (Exception e) {
      throw e;
    }
    return (tmp.toExternalForm());
     }  */

  //this method write to a file and return a URL string
  public static String CRAPostWriteToFile(String url, Hashtable data, String mimetype, String fileName) throws Exception {
    String wDir = new String(System.getProperty("user.dir"));
    String file = wDir + System.getProperty("file.separator") + fileName;
    File test = new File(file);
    test.delete();
    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
    if (bw != null) {
      //System.out.println("--------- here in CRAwrite to file");
      bw.write("<HTML>");
      bw.newLine();
      bw.write("<head>");
      bw.newLine();
      bw.write("</head>");
      bw.newLine();
      bw.write("<body onLoad=\"window.status='Generating page with passed parameters. Please wait.';document.request.submit()\" leftmargin=\"0\" topmargin=\"0\" marginheight=\"0\" marginwidth=\"0\">");
      bw.newLine();
      bw.write("<form name=\"request\" method=post action=\"" + url + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"facilid\" value=\"" + (String) data.get("FACILITY_ID") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"building\" value=\"" + (String) data.get("BUILDING") + "\">");
      bw.newLine();
      String msds = (String) data.get("CUSTOMER_MSDS");
      String csmNumber = (String) data.get("PART_NUMBER");
      if (!BothHelpObjs.isBlankString(msds)) {
        bw.write("<input type=\"hidden\" name=\"csmnumber\" value=\"" + csmNumber + "\">");
        bw.newLine();
        bw.write("<input type=\"hidden\" name=\"msdsnumber\" value=\"" + msds + "\">");
        bw.newLine();
      } else {
        bw.write("<input type=\"hidden\" name=\"msdsnumber\" value=\"" + "" + "\">");
        bw.newLine();
        bw.write("<input type=\"hidden\" name=\"csmnumber\" value=\"" + "" + "\">");
        bw.newLine();
      }
      bw.write("<input type=\"hidden\" name=\"containertype\" value=\"" + (String) data.get("PKG_TYPE") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"containersize\" value=\"" + (String) data.get("PART_SIZE") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"containerunit\" value=\"" + (String) data.get("SIZE_UNIT") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"kitflag\" value=\"" + (String) data.get("KIT_FLAG") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"location\" value=\"" + (String) data.get("LOCATION") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"productname\" value=\"" + (String) data.get("CHEMICAL_NAME") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"proposeduse\" value=\"" + (String) data.get("PROCESS_DESC") + "\">");
      bw.newLine();
      bw.write("<input type=\"hidden\" name=\"radianpurchaseid\" value=\"" + (String) data.get("REQUEST_ID") + "\">");
      bw.newLine();
      bw.write("</form>");
      bw.newLine();
      bw.write("</body>");
      bw.newLine();
      bw.write("</HTML>");
    }
    bw.close();

    URL tmp = new URL("file:///" + file);

    return (tmp.toExternalForm());
  }

  /*
    String wDir = new String(System.getProperty("user.dir"));
    String file = wDir +  System.getProperty("file.separator") + "update.txt";
    try {
      File test = new File(file);
      test.delete();
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      if (bw != null){
        for (int j=0;j<classes.size();j++){
          String line = classes.elementAt(j).toString()+" "+clAction.elementAt(j).toString();
          bw.write(line,0,line.length());
          bw.newLine();
        }
      }
      bw.close();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
   */

  public static Hashtable getTableStyle() {
    Hashtable h = new Hashtable();
    //border color and inset
    h.put("COLOR", new Color(255, 255, 255));
    h.put("INSET_TOP", new Integer(0));
    h.put("INSET_LEFT", new Integer(1));
    h.put("INSET_BOTTOM", new Integer(1));
    h.put("INSET_RIGHT", new Integer(1));
    h.put("INSET_ALIGN", new Integer(2));
    //font and foreground and background color for columns heading
    h.put("FONT_NAME", "Dialog");
    h.put("FONT_STYLE", new Integer(0));
    h.put("FONT_SIZE", new Integer(10));
    h.put("FOREGROUND", new Color(255, 255, 255));
    h.put("BACKGROUND", new Color(0, 0, 66));
    h.put("PART_COLOR", new Color(224, 226, 250));
    h.put("BULK_GAS_COLOR", new Color(150, 250, 150));
    return h;
  }

  //2-05-02
  public static Hashtable getColKey() {
    Hashtable h = new Hashtable();
    final String[] colHeadsKey = {
        "Part Color", "Comment", "Part Group", "Catalog", "Group", "Inventory Group", "Currency ID", "Part", "Description", "Type", "Catalog Price", "Unit Price", "Min Buy", "Example Packaging", "Shelf Life @ Storage Temp", "# per Part", "Item", "Item Group", "Component Description", "Packaging",
        "Grade", "Manufacturer", "Mfg Part No.", "Status", "SDS", "Material", "Part Item Row", "Bulk Gas", "Size Unit Option", "Item Type", "Create MR", "Baseline Reset", "Baseline Expiration", "Inventory Group Name", "Order Quantity", "Order Quantity Rule", "Median Lead Time"};
    for (int i = 0; i < colHeadsKey.length; i++) {
      h.put(colHeadsKey[i], new Integer(i));
    }
    return h;
  }

  //trong 3/22
  public static String changeSingleQuotetoTwoSingleQuote(String myString) {
    String result = "";
    for (int i = 0; i < myString.length(); i++) {
      String temp = myString.substring(i, i + 1);
      if (temp.equalsIgnoreCase("'")) {
        result += temp + "'";
      } else {
        result += temp;
      }
    }
    return result;
  }

  public static boolean isDateFormatRight(String date, String format) {
    try {
      SimpleDateFormat df = new SimpleDateFormat(format);
      df.setLenient(false); // this is important!
      Date dt2 = df.parse(date);
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, -5);
      Date dt3 = cal.getTime();
      //System.out.println("---- date: "+dt2.toString()+"-"+dt2.getTime()+"="+dt3.getTime()+"="+dt3.toString());
      if (dt2.getTime() < dt3.getTime()) {
        return false;
      }
    } catch (ParseException e) {
      //System.out.println(e.getMessage());
      return false;
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  //trong 8-24
  public static boolean isDateFormatRight(String date) {
    String format = "MM/dd/yyyy";
    // String dt = "1990-10/10" throws a ParseException
    // String dt = "1990-10-35" throws a IllegalArgumentException
    try {
      SimpleDateFormat df = new SimpleDateFormat(format);
      df.setLenient(false); // this is important!
      Date dt2 = df.parse(date);
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, -5);
      Date dt3 = cal.getTime();
      //System.out.println("---- date: "+dt2.toString()+"-"+dt2.getTime()+"="+dt3.getTime()+"="+dt3.toString());
      if (dt2.getTime() < dt3.getTime()) {
        return false;
      }
    } catch (ParseException e) {
      //System.out.println(e.getMessage());
      return false;
    } catch (IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  public static String formatDate(String action, String date) {
    SimpleDateFormat charF = new SimpleDateFormat("MMM dd, yyyy");
    charF.setTimeZone(TimeZone.getDefault());
    SimpleDateFormat numF = new SimpleDateFormat("MM/dd/yyyy");
    numF.setTimeZone(TimeZone.getDefault());
    SimpleDateFormat dbF = new SimpleDateFormat("yyyy-MM-dd");
    dbF.setTimeZone(TimeZone.getDefault());
    SimpleDateFormat orderF = new SimpleDateFormat("yyyyDDD");
    orderF.setTimeZone(TimeZone.getDefault());
    ParsePosition pos = new ParsePosition(0);
    if (action.equals("toCharfromNum")) {
      Calendar cal = Calendar.getInstance();
      try {
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      try {
        numF = new SimpleDateFormat("MM/dd/yy");
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      try {
        numF = new SimpleDateFormat("MM.dd.yyyy");
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      try {
        numF = new SimpleDateFormat("MM.dd.yy");
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      try {
        numF = new SimpleDateFormat("MM-dd-yyyy");
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      try {
        numF = new SimpleDateFormat("MM-dd-yy");
        Date D = numF.parse(date, pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {}
      /*
                     try {
        numF = new SimpleDateFormat("MMM dd, yy");
        Date D = numF.parse(date,pos);
        cal.setTime(D);
        cal = check2DigitYear(cal);
        System.out.println("--------- date: "+cal.getInstance());
        return new String(charF.format(cal.getTime()));
                     } catch (Exception e){}
       */
    }
    if (action.equals("toNumfromChar")) {
      try {
        Date D = charF.parse(date, pos);
        Calendar cal = Calendar.getInstance();
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(numF.format(cal.getTime()));
      } catch (Exception e) {
        return "";
      }
    }

    if (action.equals("toCharfromDB")) {
      try {
        Date D = dbF.parse(date, pos);
        Calendar cal = Calendar.getInstance();
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {
        return "";
      }
    }

    if (action.equals("toOrderfromDB")) {
      try {
        Date D = dbF.parse(date, pos);
        Calendar cal = Calendar.getInstance();
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(orderF.format(cal.getTime()));
      } catch (Exception e) {
        return "";
      }
    }

    if (action.equals("toCharfromOrder")) {
      try {
        Date D = orderF.parse(date, pos);
        Calendar cal = Calendar.getInstance();
        cal.setTime(D);
        cal = check2DigitYear(cal);
        return new String(charF.format(cal.getTime()));
      } catch (Exception e) {
        return "";
      }
    }
    return "";
  }

  public static Vector getVectorFrom2DArray(Object[][] oa) {
    Vector v = new Vector();
    if (oa == null || oa.length < 1) {
      return v;
    }
    for (int row = 0; row < oa.length; row++) {
      for (int col = 0; col < oa[row].length; col++) {
        v.addElement(oa[row][col]);
      }
    }
    return v;
  }

  public static Object[][] get2DArrayFromVector(Vector v, int cols) {
    int rows = v.size() / cols;
    Object[][] oa = null;
    //System.out.println("---------- what's going on: "+v.size()+" -- "+v.firstElement()+" * "+v.elementAt(0));
    if (v == null || v.size() < 1) {
      //System.out.println("---------- what's going on in false: ");
      return oa;
    }
    oa = new Object[rows][cols];
    int y = 0;
    int x = 0;
    for (int r = 0; r < v.size(); r++) {
      //System.out.println("####### and what's r: "+r+" @ "+v.elementAt(0));
      oa[x][y++] = v.elementAt(r);
      if (y >= cols) {
        y = 0;
        x++;
        if (x >= rows) {
          break;
        }
      }
    }
    return oa;
  }

  public String[] sortNames(String[] pass) {
    String[] in = new String[pass.length];
    String[] out = new String[pass.length];
    Hashtable names = new Hashtable();
    Vector old = new Vector();
    Vector newV = new Vector();
    int i, j, k, c, place;
    place = 0;
    String first, last;
    for (i = 0; i < pass.length; i++) {
      if (pass[i].indexOf(" ") < 0) {
        in[i] = pass[i];
        names.put(in[i], new String(""));
      } else {
        first = pass[i].substring(0, pass[i].indexOf(" "));
        last = pass[i].substring(pass[i].indexOf(" ") + 1);
        in[i] = last;
        names.put(last, first);
      }
    }
    old.addElement(in[0]);
    boolean hit = false;
    for (i = 1; i < pass.length; i++) {
      for (j = 0; j < i; j++) {
        place = j;
        c = in[i].compareTo( (String) old.elementAt(j));
        if (c <= 0) {
          hit = true;
          j = i + 1;
        }
      }
      if (!hit) {
        place++;
      }
      for (k = 0; k < place; k++) {
        newV.addElement(old.elementAt(k));
      }
      newV.addElement(in[i]);
      for (k = place; k < old.size(); k++) {
        newV.addElement(old.elementAt(k));
      }
      old = newV;
      newV = new Vector();
      hit = false;
    }
    for (i = 0; i < pass.length; i++) {
      out[i] = names.get(old.elementAt(i).toString()).toString();
      if (!out[i].equals("")) {
        out[i] = out[i] + " " + old.elementAt(i).toString();
      }
    }
    return out;
  }

  public static Vector sort(Vector v) {
    if (v == null || v.isEmpty() || v.size() == 1) {
      return v;
    }
    String[] sa = new String[v.size()];
    for (int x = 0; x < v.size(); x++) {
      sa[x] = v.elementAt(x).toString();
    }
    String[] sx = sort(sa);
    v.removeAllElements();
    for (int y = 0; y < sx.length; y++) {
      v.addElement(sx[y]);
    }
    return v;
  }

  public static String[] sort(String[] in) {
    if (in.length < 1) {
      return in;
    }
    String[] out = new String[in.length];
    Vector old = new Vector();
    Vector newV = new Vector();
    int i, j, k, c, place;
    place = 0;
    old.addElement(in[0]);
    boolean hit = false;
    for (i = 1; i < in.length; i++) {
      for (j = 0; j < i; j++) {
        place = j;
        c = in[i].compareTo( (String) old.elementAt(j));
        if (c <= 0) {
          hit = true;
          j = i + 1;
        }
      }
      if (!hit) {
        place++;
      }
      for (k = 0; k < place; k++) {
        newV.addElement(old.elementAt(k));
      }
      newV.addElement(in[i]);
      for (k = place; k < old.size(); k++) {
        newV.addElement(old.elementAt(k));
      }
      old = newV;
      newV = new Vector();
      hit = false;
    }
    for (i = 0; i < in.length; i++) {
      out[i] = old.elementAt(i).toString();
    }
    return out;
  }

  public static Calendar check2DigitYear(Calendar c) {
    int d = c.get(c.YEAR);
    if (d < 100) {
      d = d + 1900;
      Calendar c2 = Calendar.getInstance();
      int yr = c2.get(c2.YEAR);
      if (d < (yr - 50)) {
        d = d + 100;
      }
      c.set(d, c.get(c.MONTH), c.get(c.DATE));
    }
    return c;
  }

  /** getNewChemTrackCols returns a Hashtable that
      contains a key for each column in the Newchemtrack array
      as returned by the servelet. The value for each key is
      an Integer representing the _index_ of the column */
  public static Hashtable getNewChemTrackCols() {
    Hashtable h = new Hashtable();
    int i = 0;

    h.put("REQ_ID", new Integer(i++));
    h.put("REQUESTOR", new Integer(i++));
    h.put("REQ_DATE", new Integer(i++));
    h.put("REQ_STATUS", new Integer(i++));
    h.put("FAC_ID", new Integer(i++));
    h.put("APPLICATION", new Integer(i++)); //12-20-01
    h.put("FAC_PART_NUM", new Integer(i++));
    h.put("ITEM_ID", new Integer(i++)); //1-11-02
    h.put("MAT_DESC", new Integer(i++));
    h.put("MANUFACTURER", new Integer(i++));
    h.put("PACKAGING", new Integer(i++));
    //h.put("PART_ID",new Integer(i++));
    h.put("MFG_CATALOG_ID", new Integer(i++));
    h.put("CUSTOMER_REQ_ID", new Integer(i++)); //1-11-02

    return h;
  }

  public static boolean isBlankString(String s) {
    if (s == null || s.length() < 1) {
      return true;
    }

    while (s.length() > 1) {
      if (!s.startsWith(" ")) {
        return false;
      }
      s = s.substring(1);
    }
    return s.startsWith(" ");
  }

  public static int getArrayIndex(String s, String[] sa) {
    int x = -1;
    if (sa == null || s == null) {
      return x;
    }
    for (int i = 0; i < sa.length; i++) {
      if (sa[i].equalsIgnoreCase(s)) {
        x = i;
        break;
      }
    }
    return x;
  }

  public static void print2DArray(Object[][] data) {
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[i].length; j++) {
        //System.out.print(data[i][j].toString() + "\t");
      }
      //System.out.println("");
    }
  }

// CBK begin
  /** This is a kludge method that was used by the
      tracking float window (server side logic) to format dates.
      Don't write anything new to call this method.
   */
  public static String fixDate(String yuck) {
    if (yuck != null) {
      if (yuck.startsWith("199") || yuck.startsWith("20")) {
        yuck = BothHelpObjs.formatDate("toCharfromDB", yuck);
      } else {
        yuck = BothHelpObjs.formatDate("toCharfromNum", yuck);
      }
    } else {
      yuck = " ";
    }
    return yuck;
  }

// CBK end

  public static String encryptDes(String kk, String msg) {
    try {
      String k = kk;
      if (k == null) {
        k = new String("Rodrigo$$%isthe!author:)Zerlotti2000");

      }
      byte key[] = k.getBytes();

      String pad = new String("        ");
      if (msg.length() % 8 > 0) {
        msg = new String(msg + pad.substring(msg.length() % 8));
      }

      String result = new String("");
      for (int i = 0; i < msg.length(); i += 8) {
        DES des = new DES();
        des.setKey(new SymmetricKey(key));
        byte[] plain = msg.substring(i, i + 8).getBytes();
        //byte[] plain = msg.getBytes();
        //System.out.println("encypting:"+new String(plain));
        byte out[] = des.encrypt(plain);
        //System.out.println("encypted:"+new String(out));
        result += new String(out);
      }
      return new String(result);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  //trong 10-23
  /*
     public static String addSpaceForUrl(String s) {
    StringTokenizer st = new StringTokenizer(s," ");
    int i=0;
    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        if (tok.equalsIgnoreCase("&")) {
          tok = "%26";
        }
        result += tok + "%20";
      }
      //removing the last '%20'
      result = result.substring(0,result.length()-3);
    }else {
      result = s;
    }
    return result;
     }*/
  //trong 12-30
  public static String addSpaceForUrl(String s) {
    StringTokenizer st = new StringTokenizer(s, " ");
    int i = 0;
    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        if (tok.startsWith("#")) {
          String temp = tok.substring(1);
          tok = "%23" + temp;
        }

        if (tok.equalsIgnoreCase("&")) {
          tok = "%26";
        }
        result += tok + "%20";
      }
      //removing the last '%20'
      result = result.substring(0, result.length() - 3);
    } else {
      result = s;
    }
    return result;
  }

  //trong 12-28
  public static String addSlashUrl(String s) {
    StringTokenizer st = new StringTokenizer(s, "\\");
    int i = 0;
    String result = "";
    if (st.countTokens() > 1) {
      while (st.hasMoreTokens()) {
        String tok = st.nextToken();
        if (tok.equalsIgnoreCase("&")) {
          tok = "%26";
        }
        result += tok + "%5C";
      }
      //removing the last '%5C'
      result = result.substring(0, result.length() - 3);
    } else {
      result = s;
    }
    return result;
  }

  public static String decryptDes(String kk, String msg) {
    try {
      String k = kk;
      if (k == null) {
        k = new String("Rodrigo$");

      }
      byte key[] = k.getBytes();

      String result = new String("");

      String pad = new String("        ");
      if (msg.length() % 8 > 0) {
        return null;
      }

      for (int i = 0; i < msg.length(); i += 8) {
        DES des = new DES();
        des.setKey(new SymmetricKey(key));
        byte[] cipher = msg.substring(i, i + 8).getBytes();
        //byte[] cipher = msg.getBytes();
        //System.out.println("decypting:"+new String(cipher));
        byte out[] = des.decrypt(cipher);
        //System.out.println("decypted:"+new String(out));

        result += new String(out);
      }
      /*
             for (int i=result.length();i>0;i--){
       if (result.charAt(i-1)!='~') {
          result = result.substring(0,i);
          break;
       }
             }
       */
      return result.trim();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String makeBlankFromNull(String s) {
    return (s == null ? new String("") : s.trim());
  }

  public static String makeSpaceFromNull(String s) {
    if (s == null) {
      return new String("&nbsp;");
    }
    return (s.trim().length() == 0 ? new String("&nbsp;") : s.trim());
  }

  public static int makeZeroFromNull(String s) {
    try {
      return Integer.parseInt(s);
    } catch (Exception e) {
      return 0;
    }
  }

  public static boolean isNumericString(String s) {
    if (s == null) {
      return false;
    }
    if (BothHelpObjs.isBlankString(s)) {
      return false;
    }
    s.trim();
    try {
      Float F = new Float(s);
      return true;
    } catch (Exception e1) {
      try {
        Integer I = new Integer(s);
        return true;
      } catch (Exception e) {
        return false;
      }
    }
  }

  //trong 8-18 positive number greater than zero
  public static boolean isPositiveNumber(String value) {
    int number = 0;
    try {
      number = Integer.parseInt(value);
      if (number <= 0) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  //power of
  public static int mypow(int y, int x) {
    int value = 1;
    for (int i = 0; i < x; i++) {
      value *= y;
    }
    return value;
  }

  //formatting number
  public static Double formatNumber(double d) {
    DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
    String outp = myFormatter.format(d);
    return new Double(outp);
  }

  public static boolean isZero(String s) {
    if (s == null) {
      return false;
    }
    if (BothHelpObjs.isBlankString(s)) {
      return false;
    }
    s.trim();
    try {
      Float F = new Float(s);
      if (F.floatValue() == 0.0) {
        return true;
      }
      return false;
    } catch (Exception e1) {
      try {
        Integer I = new Integer(s);
        if (I.intValue() == 0) {
          return true;
        }
        return false;
      } catch (Exception e) {
        return false;
      }
    }
  }

  public static boolean isNegative(String s) {
    if (s == null) {
      return false;
    }
    if (BothHelpObjs.isBlankString(s)) {
      return false;
    }
    s.trim();
    try {
      Float F = new Float(s);
      if (F.floatValue() < 0.0) {
        return true;
      }
      return false;
    } catch (Exception e1) {
      try {
        Integer I = new Integer(s);
        if (I.intValue() < 0) {
          return true;
        }
        return false;
      } catch (Exception e) {
        return false;
      }
    }
  }

  public static double makeFloatFromNull(String s) {
    try {
      return (new Double(s)).doubleValue();
    } catch (Exception e) {
      return (new Double("0")).doubleValue();
    }
  }

  public static boolean sameDay(Calendar c1, Calendar c2) {
    return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
            c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
            c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
  }

  public static String getDateStringFromCalendar(Calendar myCal) {
    String dateString = "";
    DateFormatSymbols dfs = new DateFormatSymbols();
    String[] months = dfs.getMonths();
    String monthName = months[myCal.get(Calendar.MONTH)];
    dateString = monthName + " " + (new Integer(myCal.get(Calendar.DAY_OF_MONTH))).toString() + ", " + (new Integer(myCal.get(Calendar.YEAR))).toString();
    return dateString;
  }

  public static boolean isMinMax(String s) {
    return s.indexOf("MM") > -1;
  }

  public static boolean isOOR(String s) {
    return s.equalsIgnoreCase("oor");
  }

  public static boolean isNewMinMax(String s) {
    return s.equalsIgnoreCase("newmm");
  }

  public static String substitute(String s, char oldValue, String newValue) {
    if (s.indexOf(oldValue) < 0) {
      return s;
    }
    String ss = new String(s);
    ss = " " + ss;

    String out = "";
    StringCharacterIterator sci = new StringCharacterIterator(ss);
    while (true) {
      char tempChar = sci.next();
      if (tempChar == StringCharacterIterator.DONE) {
        break;
      }
      if (tempChar == oldValue) {
        out = out + newValue;
      } else {
        out = out + tempChar;
      }
    }
    return out;
  }

  public static Calendar setCalendarToNonWeekend(Calendar c, boolean forward) {
    int i = 0;
    if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
      if (forward) {
        i = 2;
      } else {
        i = -1;
      }
    } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      if (forward) {
        i = 1;
      } else {
        i = -2;
      }
    } else {
      return c;
    }
    c.add(Calendar.DATE, i);
    return c;
  }

  // exec an external process and waits for the return. If the process doesn't return
  // it would hang on the wait status. So, make sure the external process, returns!!
  // put all arguments on the pr array
  public static boolean execAndWait(String[] pr) {
    try {
      Runtime r = Runtime.getRuntime();
      Process p = r.exec(pr);

      if (p.waitFor() != 0) {
        //System.out.println("Error on exec " + pr);
        return false;
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
