package radian.web.barcode;

/**
 * Title:        Your Product Name
 * Description:  Your description
 * Copyright:    Copyright (c) 2004
 * Company:      Haas TCM
 * @author Nawaz Shaik
 * @version
 *
 * 06-30-04 Changing Printer Path to be based on personnel ID
 * 07-29-04 I am doing this to reduce the number of template fiels I need to maintain for each hub. Also don't want to many if statements in  the code.
 * 07-30-04 Changing the templates to be based on inventory group instead of hub
 * 09-13-04 Printing Main Receipt Label and the kit labels consecutively
 * 11-17-04 Printg the Label Qty * Case Qty of Labels
 * 12-02-04 If it is a inseperable case, not printing total component labels
 * 02-28-05 Giving the option to not print Kit/Case Qty Labels. Also giving the option to pick a prnter if there are multiple printers at a hub
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ZplHandler;

import radian.tcmis.common.util.StringHandler;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.web.BarCodeHelpObj;

public class ZPLBarCodeGenerator {
	TcmISDBModel db = null;

	public ZPLBarCodeGenerator(TcmISDBModel d) {
		db = d;
	}

	private static Logger zpllog = Logger.getLogger(ZPLBarCodeGenerator.class);
	private static Pattern barCode2dReplace = Pattern.compile("\\||,", Pattern.MULTILINE);
	private static String qrStrIndicator = "_qrstr";

  public void buildCabZpl(Vector AllTheData, String NameofFile, String gencase, String PaperSizeO, String hubNum, Vector CabData, String perosnnelID, String printerPath, String printRes, Vector invgrpsprint) throws Exception {
    if ("generatecabbinlabels".equalsIgnoreCase(gencase)) {
      StringBuffer buffer = buildcabinetbinlabels(hubNum, CabData, AllTheData, true, printerPath, printRes, invgrpsprint);
      writefiletodisk(buffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/cabinetlab" + NameofFile + ".txt", perosnnelID, printerPath, buffer.toString()), "cabinetlab" + NameofFile + "", true);
    } else if ("generatebinlistlabels".equalsIgnoreCase(gencase)) {
      StringBuffer buffer = buildcabinetbinlabels(hubNum, CabData, AllTheData, false, printerPath, printRes, invgrpsprint);
      writefiletodisk(buffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/cabinetlab" + NameofFile + ".txt", perosnnelID, printerPath, buffer.toString()), "cabinetlab" + NameofFile + "", true);
    } else {
      StringBuffer buffer = buildcabinetlabels(hubNum, AllTheData, printerPath, printRes, invgrpsprint);
      writefiletodisk(buffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/cabinetlab" + NameofFile + ".txt", perosnnelID, printerPath, buffer.toString()), "cabinetlab" + NameofFile + "", true);
    }
  }

  @SuppressWarnings("rawtypes")
public void buildReceiptZpl(Vector AllTheData, String NameofFile, String PaperSizeO, String hubNum, Hashtable CompData, String perosnnelID,
                              String printerPath, String printRes, Vector invgrpsprint, boolean printKitLabels) throws Exception {
    String writefilepath = radian.web.tcmisResourceLoader.getsavelabelpath();
    String file = "";
    file = writefilepath + "Barcode" + NameofFile + ".txt";
    PrintWriter pw = new PrintWriter(new FileOutputStream(file));

    Hashtable lablformatlist = new Hashtable();
		Hashtable lablformatLocalelist = new Hashtable();
    if ("038".equalsIgnoreCase(PaperSizeO)) {
      lablformatlist = getlabelformatlist(invgrpsprint, "tape");
    } else if ("35".equalsIgnoreCase(PaperSizeO)) {
      lablformatlist = getlabelformatlist(invgrpsprint, "pmc");
    } else if ("exitLabels".equalsIgnoreCase(PaperSizeO)) {
      lablformatlist = getlabelformatlist(invgrpsprint, "exitLabels");
    } else {
      lablformatlist = getlabelformatlist(invgrpsprint, "container");
			lablformatLocalelist = getlabelformatlist(invgrpsprint, "containerLocale");
    }

    Hashtable lablfieldlist = new Hashtable();
    lablfieldlist = getlbldeflist(lablformatlist, printRes);

		Hashtable lablfieldLocalelist = new Hashtable();
		lablfieldLocalelist = getlbldeflist(lablformatLocalelist, printRes);

    Hashtable kitlablformatlist = new Hashtable();
    if ("exitLabels".equalsIgnoreCase(PaperSizeO)) {
     kitlablformatlist = getlabelformatlist(invgrpsprint, "exitKit");
    }
    else
    {
     kitlablformatlist = getlabelformatlist(invgrpsprint, "kit");
    }

        Hashtable kitlablfieldlist = new Hashtable();
		kitlablfieldlist = getlbldeflist(kitlablformatlist, printRes);

		Hashtable kitlablformatLocalelist = new Hashtable();
		kitlablformatLocalelist = getlabelformatlist(invgrpsprint, "kitLocale");

		Hashtable kitlablfieldLocalelist = new Hashtable();
		kitlablfieldLocalelist = getlbldeflist(kitlablformatLocalelist, printRes);

    boolean printtemplate = true;

    StringBuilder zpl = new StringBuilder();
    if (printtemplate) {
      String templates = "";
      if ("038".equalsIgnoreCase(PaperSizeO)) {
        templates = gettemplates(invgrpsprint, "tape", printRes);
      } else if ("35".equalsIgnoreCase(PaperSizeO)) {
        templates = gettemplates(invgrpsprint, "pmc", printRes);
      } else if ("exitLabels".equalsIgnoreCase(PaperSizeO)) {
        templates = gettemplates(invgrpsprint, "exitLabels", printRes);
      }else {
        templates = gettemplates(invgrpsprint, "container", printRes);
      }
      zpl.append(templates);
    }

    try {
      for (int i = 0; i < AllTheData.size(); i++) {
        Hashtable h = (Hashtable) AllTheData.elementAt(i);
        String qtyreceived = (String) h.get("QUANTITY_RECEIVED").toString();
        int labelqty = 0;
        try {
          labelqty = Integer.parseInt(qtyreceived);
        } catch (Exception eee) {
          labelqty = 1;
        }

        Hashtable recptData = (Hashtable) h.get("DATA");
        String kitlabel = (recptData.get("DETAIL_16") == null ? "" : recptData.get("DETAIL_16").toString());
        String keyReceipt = (recptData.get("DETAIL_2") == null ? "" : recptData.get("DETAIL_2").toString());
        String invengrp = (recptData.get("INVENTORY_GROUP") == null ? "" : recptData.get("INVENTORY_GROUP").toString());
        String nofofcominkit = (recptData.get("NUMBER_OF_KITS") == null ? "" : recptData.get("NUMBER_OF_KITS").toString());
        int numfkits = Integer.parseInt(nofofcominkit);
        String inseperablekit = (recptData.get("INSEPARABLE_KIT") == null ? "" : recptData.get("INSEPARABLE_KIT").toString());
        String numberofcomp = (recptData.get("TOTAL_COMPONENTS") == null ? "" : recptData.get("TOTAL_COMPONENTS").toString());
        String receiptId = (recptData.get("RECEIPT_ID") == null ? "" : recptData.get("RECEIPT_ID").toString());
        int noofcomp = 0; 
        try {
          noofcomp = Integer.parseInt(numberofcomp);
        } catch (Exception eee) {
          noofcomp = 1;
        }
        if ("Y".equalsIgnoreCase(inseperablekit)) {
          noofcomp = 1;
        }

        StringBuffer recLabel = new StringBuffer();
        StringBuffer comprecLabel = new StringBuffer();
        boolean usepqvalue = true;
        if ("N".equalsIgnoreCase(kitlabel) || (numfkits > 1 && !"Y".equalsIgnoreCase(inseperablekit))) {
          usepqvalue = false;
        }

        String lastIdPrinted = (recptData.get("LAST_ID_PRINTED") == null ? "" : recptData.get("LAST_ID_PRINTED").toString());
        String rePrintContainerId ="";
        try {
           rePrintContainerId = recptData.get("RE_PRINT_CONTAINER_ID") == null ? "" : recptData.get("RE_PRINT_CONTAINER_ID").toString();
        }
        catch (Exception eee1) {}

        int lastIdPrintedValue = 0;
        if (rePrintContainerId.length() > 0)
        {
            try {
            lastIdPrintedValue = Integer.parseInt(rePrintContainerId);
            } catch (Exception eee) {
            lastIdPrintedValue = 0;
            }
        }
        else
        {
            if (lastIdPrinted.length() == 0) {
                lastIdPrinted = "0";
            }

            try {
            lastIdPrintedValue = Integer.parseInt(lastIdPrinted);
            } catch (Exception eee) {
            lastIdPrintedValue = 0;
            }
        }

        if ("35".equalsIgnoreCase(PaperSizeO)) {
          recLabel.append(pmclabel(recptData, "" + labelqty + "", usepqvalue,lablformatlist, lablfieldlist));
        } else {
             if ( ("NRM".equals(invengrp) || "LM Troy".equals(invengrp) || "LM Troy OS".equals(invengrp) || "Phoenix Boeing Mesa".equals(invengrp) || "BOE STL FSL067".equals(invengrp) || "BOE STL FSL101".equals(invengrp) || "SFSL061".equals(invengrp) ||
                   "STL BOE NONPROD".equals(invengrp) || "STL BOE SADVANCED".equals(invengrp) ||
                    invengrp.equals("STL BOE") || invengrp.equals("Dallas LM Co-Producer") || invengrp.equals("FTW LM Co-Producer") ||
                    invengrp.equals("LM Marietta") || invengrp.equals("LM Marietta OS") || invengrp.equals("Brugherio") ||
                    invengrp.equals("Torino") || invengrp.equals("Luserna") || invengrp.equals("LA - LM Palmdale") ||
                    invengrp.equals("Dallas LM Palmdale") || invengrp.equals("Tucson Ray") || invengrp.equals("TucRaySubSpec") || 
                    invengrp.equals("Cedar Rapids CA") || invengrp.equals("Cedar Rapids Mexicali") || invengrp.equals("Cedar Rapids RCI") ||
                    invengrp.equals("Cedar Rapids Tustin") || invengrp.equals("Cedar Rapids Wilsonville") || invengrp.equals("Dallas DEMO") ||
                    invengrp.equals("Dallas RCI CA") || invengrp.equals("Dallas RCI Cedar Rapids") || invengrp.equals("Dallas RCI Mexicali") ||
                    invengrp.equals("Dallas RCI Tustin") || invengrp.equals("Dallas RCI Wilsonville") || invengrp.equals("Melbourne RCI")) && labelqty > 0)
          {
            // Tucson Raytheon 2D barcodes require a unique label for each item rather than a quantity count
                usepqvalue = false;
                for (int nooflabels = 0; nooflabels < labelqty; nooflabels++) {
                     if (rePrintContainerId.length() == 0)
                        lastIdPrintedValue++;
                    if (printKitLabels) {
                    recLabel.append(stditemlabel(recptData, "" + (labelqty * noofcomp) + "", hubNum, lablformatlist, lablfieldlist,lablformatLocalelist,lablfieldLocalelist, usepqvalue,""+lastIdPrintedValue+""));
                  } else {
                    recLabel.append(stditemlabel(recptData, "" + (labelqty) + "", hubNum, lablformatlist, lablfieldlist,lablformatLocalelist,lablfieldLocalelist, usepqvalue,""+lastIdPrintedValue+""));
                  }
                    if (numfkits > 1 && !"Y".equalsIgnoreCase(inseperablekit) && printKitLabels) {
                    for (int nooflabels1 = 0; nooflabels1 < (noofcomp - 1); nooflabels1++) {
                        if (!("NRM".equals(invengrp) || invengrp.equals("LM Marietta") || invengrp.equals("LM Marietta OS") || invengrp.equals("LA - LM Palmdale") || invengrp.equals("Dallas LM Palmdale") || "LM Troy".equals(invengrp) || "LM Troy OS".equals(invengrp) || "Phoenix Boeing Mesa".equals(invengrp) || "BOE STL FSL067".equals(invengrp) || "BOE STL FSL101".equals(invengrp) || "SFSL061".equals(invengrp) ||
                              "STL BOE NONPROD".equals(invengrp) || "STL BOE SADVANCED".equals(invengrp) || invengrp.equals("STL BOE") || 
                              invengrp.equals("Cedar Rapids CA") || invengrp.equals("Cedar Rapids Mexicali") || invengrp.equals("Cedar Rapids RCI") ||
                              invengrp.equals("Cedar Rapids Tustin") || invengrp.equals("Cedar Rapids Wilsonville") || invengrp.equals("Dallas DEMO") ||
                              invengrp.equals("Dallas RCI CA") || invengrp.equals("Dallas RCI Cedar Rapids") || invengrp.equals("Dallas RCI Mexicali") ||
                              invengrp.equals("Dallas RCI Tustin") || invengrp.equals("Dallas RCI Wilsonville") || invengrp.equals("Melbourne RCI")))
                        {
                             if (rePrintContainerId.length() == 0)
                                 lastIdPrintedValue++;
                        }
                        recLabel.append(stdkitlabel(recptData, "" + (noofcomp - 1) + "", hubNum, kitlablformatlist, kitlablfieldlist, kitlablformatLocalelist, kitlablfieldLocalelist,invengrp, false,""+lastIdPrintedValue+""));
                        }
                    }
                }

                 if (rePrintContainerId.length() == 0)
                 {
                     String printupquery="update receipt set LAST_ID_PRINTED="+lastIdPrintedValue+" where receipt_id = "+receiptId+"";
                     db.doUpdate( printupquery );
                 }                
          }
          else
          {
            if (printKitLabels) {
            recLabel.append(stditemlabel(recptData, "" + (labelqty * noofcomp) + "", hubNum, lablformatlist, lablfieldlist,lablformatLocalelist,lablfieldLocalelist, usepqvalue,""+lastIdPrintedValue+""));
            } else {
            recLabel.append(stditemlabel(recptData, "" + (labelqty) + "", hubNum, lablformatlist, lablfieldlist,lablformatLocalelist,lablfieldLocalelist, usepqvalue,""+lastIdPrintedValue+""));
            }
          }
          if ("N".equalsIgnoreCase(kitlabel)) {
            Vector compV = new Vector();
            compV = (Vector) CompData.get(keyReceipt);

            if (compV == null) {
              compV = new Vector();
            }

            if (printKitLabels) {
              comprecLabel.append(componentlabel(compV, hubNum, kitlablformatlist, kitlablfieldlist, invengrp, usepqvalue));
            }
          } else if (numfkits > 1 && !"Y".equalsIgnoreCase(inseperablekit) && printKitLabels) {
              if (("NRM".equals(invengrp) || "LM Troy".equals(invengrp) || "LM Troy OS".equals(invengrp) || "Phoenix Boeing Mesa".equals(invengrp) || "BOE STL FSL067".equals(invengrp) || "BOE STL FSL101".equals(invengrp) || "SFSL061".equals(invengrp) ||
                   "STL BOE NONPROD".equals(invengrp) || "STL BOE SADVANCED".equals(invengrp) || invengrp.equals("STL BOE") ||
                   invengrp.equals("Dallas LM Co-Producer") || invengrp.equals("FTW LM Co-Producer") ||
                   invengrp.equals("LM Marietta") || invengrp.equals("LM Marietta OS") || invengrp.equals("Brugherio") ||
                   invengrp.equals("Torino") || invengrp.equals("Luserna") || invengrp.equals("LA - LM Palmdale") ||
                   invengrp.equals("Dallas LM Palmdale") || invengrp.equals("Tucson Ray") || invengrp.equals("TucRaySubSpec")|| 
                   invengrp.equals("Cedar Rapids CA") || invengrp.equals("Cedar Rapids Mexicali") || invengrp.equals("Cedar Rapids RCI") ||
                   invengrp.equals("Cedar Rapids Tustin") || invengrp.equals("Cedar Rapids Wilsonville") || invengrp.equals("Dallas DEMO") ||
                   invengrp.equals("Dallas RCI CA") || invengrp.equals("Dallas RCI Cedar Rapids") || invengrp.equals("Dallas RCI Mexicali") ||
                   invengrp.equals("Dallas RCI Tustin") || invengrp.equals("Dallas RCI Wilsonville") || invengrp.equals("Melbourne RCI")) && labelqty > 0)
              {                
              }
              else
              {
                comprecLabel.append(stdkitlabel(recptData, "" + (noofcomp - 1) + "", hubNum, kitlablformatlist, kitlablfieldlist, kitlablformatLocalelist, kitlablfieldLocalelist,invengrp, true,""));
              }
          }
        }

        if (usepqvalue || ("NRM".equals(invengrp) || "LM Troy".equals(invengrp) || "LM Troy OS".equals(invengrp) || "Phoenix Boeing Mesa".equals(invengrp) || "BOE STL FSL067".equals(invengrp) || "BOE STL FSL101".equals(invengrp) || "SFSL061".equals(invengrp) ||
                           "STL BOE NONPROD".equals(invengrp) || "STL BOE SADVANCED".equals(invengrp) ||
                           invengrp.equals("STL BOE") || invengrp.equals("Dallas LM Co-Producer") ||
                           invengrp.equals("FTW LM Co-Producer") || invengrp.equals("LM Marietta") ||
                           invengrp.equals("LM Marietta OS") || invengrp.equals("Brugherio") || invengrp.equals("Torino") ||
                           invengrp.equals("Dallas LM Palmdale") || invengrp.equals("Luserna") || invengrp.equals("LA - LM Palmdale") || invengrp.equals("Tucson Ray") ||
                           invengrp.equals("TucRaySubSpec")|| 
                           invengrp.equals("Cedar Rapids CA") || invengrp.equals("Cedar Rapids Mexicali") || invengrp.equals("Cedar Rapids RCI") ||
                           invengrp.equals("Cedar Rapids Tustin") || invengrp.equals("Cedar Rapids Wilsonville") || invengrp.equals("Dallas DEMO") ||
                           invengrp.equals("Dallas RCI CA") || invengrp.equals("Dallas RCI Cedar Rapids") || invengrp.equals("Dallas RCI Mexicali") ||
                           invengrp.equals("Dallas RCI Tustin") || invengrp.equals("Dallas RCI Wilsonville") || invengrp.equals("Melbourne RCI"))) {
          zpl.append(recLabel);
          zpl.append(comprecLabel);
        } else {
          if (!("NRM".equals(invengrp) || "LM Troy".equals(invengrp) || "LM Troy OS".equals(invengrp) || "Phoenix Boeing Mesa".equals(invengrp) || "BOE STL FSL067".equals(invengrp) || "BOE STL FSL101".equals(invengrp) || "SFSL061".equals(invengrp) ||
                "STL BOE NONPROD".equals(invengrp) || "STL BOE SADVANCED".equals(invengrp) || invengrp.equals("STL BOE") ||
                invengrp.equals("Dallas LM Co-Producer") || invengrp.equals("FTW LM Co-Producer") ||
                invengrp.equals("LM Marietta") || invengrp.equals("LM Marietta OS") || invengrp.equals("Brugherio") ||
                invengrp.equals("Torino") || invengrp.equals("Luserna") || invengrp.equals("LA - LM Palmdale") ||
                invengrp.equals("Dallas LM Palmdale") || invengrp.equals("Tucson Ray") || invengrp.equals("TucRaySubSpec") || 
                invengrp.equals("Cedar Rapids CA") || invengrp.equals("Cedar Rapids Mexicali") || invengrp.equals("Cedar Rapids RCI") ||
                invengrp.equals("Cedar Rapids Tustin") || invengrp.equals("Cedar Rapids Wilsonville") || invengrp.equals("Dallas DEMO") ||
                invengrp.equals("Dallas RCI CA") || invengrp.equals("Dallas RCI Cedar Rapids") || invengrp.equals("Dallas RCI Mexicali") ||
                invengrp.equals("Dallas RCI Tustin") || invengrp.equals("Dallas RCI Wilsonville") || invengrp.equals("Melbourne RCI")))
          {
            for (int nooflabels = 0; nooflabels < labelqty; nooflabels++) {
              zpl.append(recLabel);
              zpl.append(comprecLabel);
            }
          }
        }

        if ("31".equalsIgnoreCase(PaperSizeO) || "exitLabels".equalsIgnoreCase(PaperSizeO)) {
          String lineFeed = "" + (char) (13) + (char) (10);
          zpl.append("^XA").append(lineFeed);
          zpl.append("^PH").append(lineFeed);
          zpl.append("^XZ").append(lineFeed);
        }
      }
      pw.print(zpl.toString());
    } catch (Exception e11) {
      e11.printStackTrace();
    }
    finally {
    	pw.close();
    }
    StringBuffer blankbuffer = new StringBuffer();
    writefiletodisk(blankbuffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/Barcode" + NameofFile + ".txt", perosnnelID, printerPath, zpl.toString()), "Barcode" + NameofFile + "", false);
  }

  public void buildboxZpl(Vector AllTheData, String NameofFile, String PaperSizeO, String hubNum, String perosnnelID, String printerPath, String printRes, Vector invgrpsprint) throws Exception {
	StringBuffer buffer = buildboxlabels(AllTheData, hubNum, printerPath, printRes, invgrpsprint);
    writefiletodisk(buffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/boxlabel" + NameofFile + ".txt", perosnnelID, printerPath, buffer.toString()), "boxlabel" + NameofFile + "", true);
  }

  public void buildbinZpl(Vector AllTheData, String NameofFile, String PaperSizeO, String hubNum, String perosnnelID, String printerPath, String printRes) throws Exception {
	StringBuffer buffer = buildbinlabels(AllTheData, hubNum, printerPath, printRes);
    writefiletodisk(buffer, buildprintjnlpfile(hubNum, PaperSizeO, "labels/hubbin" + NameofFile + ".txt", perosnnelID, printerPath, buffer.toString()), "hubbin" + NameofFile + "", true);
  }

  private StringBuffer binlabel(String binname) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    reclabel.append("^XA" + linefeedd);
    reclabel.append("^XFHUB_BIN^FS" + linefeedd);

    reclabel.append("^FN1^FD*" + binname + "^FS" + linefeedd);
    reclabel.append("^FN2^FD^FS" + linefeedd);
    reclabel.append("^PQ1" + linefeedd);
    reclabel.append("^XZ" + linefeedd);

    return reclabel;
  }

  private StringBuffer buildbinlabels(Vector bindata, String hubNum, String printerpath, String printRes) {
    StringBuffer recLabel = new StringBuffer();
    BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();
    Vector invgrpsprint = new Vector();
    invgrpsprint.add("Dallas");

    recLabel.append(gettemplates(invgrpsprint, "hubbin", printRes));

    try {
      Hashtable summary = new Hashtable();
      summary = (Hashtable) bindata.elementAt(0);
      int total = ( (Integer) (summary.get("TOTAL_NUMBER"))).intValue();
      int i = 0;

      for (int loop = 0; loop < total; loop++) {
        i++;
        Hashtable boxh = (Hashtable) bindata.elementAt(i);

        String usrcheck = (String) boxh.get("USERCHK").toString();
        String binname = (String) boxh.get("BIN").toString();

        if ("yes".equalsIgnoreCase(usrcheck)) {
          recLabel.append(binlabel(binname));
        }
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }

    return recLabel;
  }

  private StringBuffer buildboxlabels(Vector boxdata, String hubNum, String printerpath, String printRes, Vector invgrpsprint) {
    StringBuffer recLabel = new StringBuffer();
    BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();
    recLabel.append(gettemplates(invgrpsprint, "box", printRes));

    try {
      Hashtable lablformatlist = new Hashtable();
      lablformatlist = getlabelformatlist(invgrpsprint, "box");

      Hashtable lablfieldlist = new Hashtable();
      lablfieldlist = getlbldeflist(lablformatlist, printRes);

      for (int i = 0; i < boxdata.size(); i++) {
        Hashtable boxh = (Hashtable) boxdata.elementAt(i);
        recLabel.append(boxlabel(boxh, "1", lablformatlist, lablfieldlist));
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }

    return recLabel;
  }

  public Hashtable getlbldeflist(Hashtable lblformatlist, String printRes) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable finalResult = new Hashtable();
    Vector doneforlblfmt = new Vector();

    for (Enumeration e = lblformatlist.keys(); e.hasMoreElements(); ) {
      String key = (String) e.nextElement();
      String lblformat = lblformatlist.get(key).toString();

      if (!doneforlblfmt.contains(lblformat)) {
        doneforlblfmt.add(lblformat);
        Hashtable ResultV = new Hashtable();

        String query = "select distinct x.LABEL_FIELD_ID, x.LABEL_FIELD_CONTENT from label_format_field_definition x where ";
        query += "x.LABEL_FORMAT = '" + lblformat + "' and x.PRINTER_RESOLUTION_DPI = " + printRes + "";

        try {
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while (rs.next()) {

            String lblfieldid = (rs.getString("LABEL_FIELD_ID") == null ? "" : rs.getString("LABEL_FIELD_ID"));
            String lblfieldcont = (rs.getString("LABEL_FIELD_CONTENT") == null ? "" : rs.getString("LABEL_FIELD_CONTENT"));
            ResultV.put(lblfieldid, lblfieldcont);
          }
        } catch (Exception exx) {
          exx.printStackTrace();
        } finally {
          if (dbrs != null) {
            dbrs.close();
          }
        }

        finalResult.put(lblformat, ResultV);
      }
    }
    doneforlblfmt = null;
    return finalResult;
  }

  public Hashtable getlabelformatlist(Vector invgrpstoprint, String labeltype) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Hashtable ResultV = new Hashtable();

    String invgrpsin = "";
    for (int k = 0; k < invgrpstoprint.size(); k++) {
      String invgrp = (String) (invgrpstoprint.elementAt(k));
      invgrpsin += "'" + invgrp + "',";
    }
    invgrpsin = invgrpsin.substring(0, invgrpsin.length() - 1);

    String query = "select distinct INVENTORY_GROUP,LABEL_FORMAT from inventory_group_label_format where INVENTORY_GROUP in (" + invgrpsin + ") and label_type = '" + labeltype + "' ";
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String lblformat = (rs.getString("LABEL_FORMAT") == null ? "" : rs.getString("LABEL_FORMAT"));
        String invgrp = (rs.getString("INVENTORY_GROUP") == null ? "" : rs.getString("INVENTORY_GROUP"));
        ResultV.put(invgrp, lblformat);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return ResultV;
  }
  
  public String gettemplates(Vector invgrpstoprint, String labeltype, String printRes) {
	  DBResultSet dbrs = null;
	  ResultSet rs = null;
	  StringBuilder template = new StringBuilder();
	  
	  String invgrpsin = "";
	  for (int k = 0; k < invgrpstoprint.size(); k++) {
		  String invgrp = (String) (invgrpstoprint.elementAt(k));
		  invgrpsin += "'" + invgrp + "',";
	  }
	  invgrpsin = invgrpsin.substring(0, invgrpsin.length() - 1);
	  
	  StringBuilder query = new StringBuilder();
	  query.append("select y.label_template_filename, z.label_template from label_format_template_file y, label_format_template z ");
	  query.append("where exists(select label_format from inventory_group_label_format x where x.INVENTORY_GROUP in (").append(invgrpsin).append(") ");
	  query.append("and x.label_type = '").append(labeltype).append("' and x.LABEL_FORMAT = y.LABEL_FORMAT) ");
	  query.append("and y.printer_resolution_dpi = '").append(printRes).append("' ");
	  query.append("and y.label_template_filename = z.label_template_filename");
		 
	  try {
		  String linefeed = "";
		  linefeed += (char) (13);
		  linefeed += (char) (10);
		    
	      dbrs = db.doQuery(query.toString());
	      rs = dbrs.getResultSet();
	      while (rs.next()) {
	    	  Clob clob = rs.getClob("LABEL_TEMPLATE");
	    	  Reader stream = clob.getCharacterStream();
	    	  BufferedReader br = new BufferedReader(stream);
	    	  String line = "";
	    	  while ((line = br.readLine()) != null) {
	    		  template.append(line).append(linefeed);
	    	  }
	    	  br.close();
	      }
	  } catch (Exception e) {
	      e.printStackTrace();
	  } finally {
	      if (dbrs != null) {
	    	  dbrs.close();
	      }
	  }

	    return template.toString();
  }

  public Vector gettemplatefilelist(Vector invgrpstoprint, String labeltype, String printRes) {
    DBResultSet dbrs = null;
    ResultSet rs = null;
    Vector ResultV = new Vector();

    String invgrpsin = "";
    for (int k = 0; k < invgrpstoprint.size(); k++) {
      String invgrp = (String) (invgrpstoprint.elementAt(k));
      invgrpsin += "'" + invgrp + "',";
    }
    invgrpsin = invgrpsin.substring(0, invgrpsin.length() - 1);

    String query = "select distinct y.LABEL_TEMPLATE_FILENAME from inventory_group_label_format x,label_format_template_file y  where x.LABEL_FORMAT = y.LABEL_FORMAT and";
    query += " x.INVENTORY_GROUP in (" + invgrpsin + ") and x.label_type = '" + labeltype + "' and y.PRINTER_RESOLUTION_DPI = '" + printRes + "' ";
    
    try {
      dbrs = db.doQuery(query);
      rs = dbrs.getResultSet();
      while (rs.next()) {
        String filename = (rs.getString("LABEL_TEMPLATE_FILENAME") == null ? "" : rs.getString("LABEL_TEMPLATE_FILENAME"));
        ResultV.add(filename);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (dbrs != null) {
        dbrs.close();
      }
    }

    return ResultV;
  }

  private StringBuffer boxlabel(Hashtable recData, String qty, Hashtable lablformatlist, Hashtable lablfieldlist) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    String invengrp = (recData.get("INVENTORY_GROUP") == null ? "" : recData.get("INVENTORY_GROUP").toString());
    String labelformat = (String) lablformatlist.get(invengrp);

    reclabel.append("^XA" + linefeedd);
    reclabel.append("^XF" + labelformat + "^FS" + linefeedd);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();

      if (recData.get("" + keyvalue + "") != null)
      {
        reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      }
    }
    reclabel.append("^PQ" + qty + "" + linefeedd);
    reclabel.append("^XZ" + linefeedd);

    return reclabel;
  }

    public StringBuffer pmclabel(Hashtable recData, String qty, boolean pqvalues) {
     StringBuffer reclabel = new StringBuffer();
     String linefeedd = "";
     linefeedd += (char) (13);
     linefeedd += (char) (10);

     reclabel.append("^XA" + linefeedd);
     reclabel.append("^XFPMC^FS" + linefeedd);
     String pmcpartnumber = recData.get("DETAIL_9").toString();
     String partDescription = recData.get("PART_DESCRIPTION").toString();

			 if (pmcpartnumber.length() > 10)
			 {
       reclabel.append("^FN1^FD" + pmcpartnumber.substring(0, 9) + "^FS" + linefeedd);
       reclabel.append("^FN2^FD" + pmcpartnumber.substring(9, pmcpartnumber.length()) + "^FS" + linefeedd);
			 }
			 else
			 {
				reclabel.append("^FN1^FD" + pmcpartnumber.substring(0, pmcpartnumber.length()) + "^FS" + linefeedd);

		   }
     		reclabel.append("^FN7^FD" + partDescription + "^FS" + linefeedd);

     if (pqvalues) {
       reclabel.append("^PQ" + qty + "" + linefeedd);
     } else {
       reclabel.append("^PQ1" + linefeedd);
     }
     reclabel.append("^XZ" + linefeedd);

     return reclabel;
   }

   public StringBuffer pmclabel(Hashtable recData, String qty, boolean pqvalues, Hashtable lablformatlist, Hashtable lablfieldlist) {
     StringBuffer reclabel = new StringBuffer();
     String linefeedd = "";
     linefeedd += (char) (13);
     linefeedd += (char) (10);

     String pmcpartnumber = recData.get("DETAIL_9").toString();

     String invengrp = (recData.get("INVENTORY_GROUP") == null ? "" : recData.get("INVENTORY_GROUP").toString());
     String labelformat = (String) lablformatlist.get(invengrp);
     if ("pmccontainer".equalsIgnoreCase(labelformat))
     {
      reclabel.append("^XA" + linefeedd);
      reclabel.append("^XF" + labelformat + "^FS" + linefeedd);
      Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");
      for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();
      if ("FN1".equalsIgnoreCase(key)) {
        reclabel.append("^" + key + "^FD>;" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      }
      else if ("FN10".equalsIgnoreCase(key)) {
       if (pmcpartnumber.length() > 10)
	   {
         reclabel.append("^FN10^FD" + pmcpartnumber.substring(0, 9) + "^FS" + linefeedd);
         reclabel.append("^FN11^FD" + pmcpartnumber.substring(9, pmcpartnumber.length()) + "^FS" + linefeedd);
	   }
	   else
	   {
		reclabel.append("^FN10^FD" + pmcpartnumber.substring(0, pmcpartnumber.length()) + "^FS" + linefeedd);
	   }
      }
      else {
        if (recData.get("" + keyvalue + "") != null)
        {
        reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
        }
      }
      }
     }
     else
     {
      reclabel.append("^XA" + linefeedd);
      reclabel.append("^XFPMC^FS" + linefeedd);

			 if (pmcpartnumber.length() > 10)
			 {
       reclabel.append("^FN1^FD" + pmcpartnumber.substring(0, 9) + "^FS" + linefeedd);
       reclabel.append("^FN2^FD" + pmcpartnumber.substring(9, pmcpartnumber.length()) + "^FS" + linefeedd);
			 }
			 else
			 {
				reclabel.append("^FN1^FD" + pmcpartnumber.substring(0, pmcpartnumber.length()) + "^FS" + linefeedd);
		   }
     }
     if (pqvalues) {
       reclabel.append("^PQ" + qty + "" + linefeedd);
     } else {
       reclabel.append("^PQ1" + linefeedd);
     }
     reclabel.append("^XZ" + linefeedd);

     return reclabel;
   }

  public StringBuffer stditemlabel(Hashtable recData, String qty, String hubndame, Hashtable lablformatlist, Hashtable lablfieldlist,
	 Hashtable lablformatLocalelist, Hashtable lablfieldLocalelist, boolean pqvalues,String lastIdPrintedValue) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    String invengrp = (recData.get("INVENTORY_GROUP") == null ? "" : recData.get("INVENTORY_GROUP").toString());
    String labelformat = (String) lablformatlist.get(invengrp);
		boolean twoDBarcode = false;
		if (lablformatLocalelist != null && lablformatLocalelist.size() > 0)
		{
			 twoDBarcode = true;
		}
		if (invengrp.equalsIgnoreCase("Decatur Boeing"))
		{
		   twoDBarcode = true;
	  }	

    reclabel.append("^XA" + linefeedd);
    reclabel.append("^XF" + labelformat + "^FS" + linefeedd);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");
    
    // Get the part number fields defined for this label format
    Collection labelPartsList = new Vector();
    try{
    	labelPartsList = getLabelPartsList(lblfeilds);
    }
    catch(Exception ex){
    	Log.error("Unable to retrieve label parts list");
    }

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();
      if ("FN1".equalsIgnoreCase(key) && !twoDBarcode) {
        reclabel.append("^" + key + "^FD>;" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String labelStorageTemp = recData.get("DETAIL_8").toString();
        reclabel.append("^" + key + "^FD" + getTucson2dFN2(expireDateLocale,labelStorageTemp) + "^FS" + linefeedd);
      }
      else if ("FN3".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String mfgLot = recData.get("MFG_LOT").toString();
        if (mfgLot.length() > 24)
        {
             mfgLot = mfgLot.substring(0,23);
        }
        reclabel.append("^" + key + "^FD" + mfgLot + "^FS" + linefeedd);
      }
      else if ("FN4".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String recerts = recData.get("DETAIL_5").toString();

        reclabel.append("^" + key + "^FD" + getTucson2dFN4(recerts) + "^FS" + linefeedd);
      }
      else if ("FN9".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        int length = 34;
        reclabel.append("^" + key + "^FD" + getTucson2dFN1(catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6,length) + "^FS" + linefeedd);
      }
      else if ("FN10".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String expireDate = recData.get("BARCODE_EXPIRE_DATE").toString();
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        String mfgLot = recData.get("MFG_LOT").toString();

        reclabel.append("^" + key + "^FH^FD" + getTucson2dFN10(expireDate,mfgLot,catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6) + "^FS" + linefeedd);
      }
      else if ("FN11".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        reclabel.append("^" + key + "^FD" + getTucson2dFN11(receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN12".equalsIgnoreCase(key) && "tucsonRay2d".equalsIgnoreCase(labelformat) )
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String mfgLot = recData.get("MFG_LOT").toString();
        String labelStorageTemp = recData.get("DETAIL_8").toString();
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        reclabel.append("^" + key + "^FH^FD" + getTucson2dFN12(mfgLot,expireDateLocale,receiptId,lastIdPrintedValue,labelStorageTemp,catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6) + "^FS" + linefeedd);
      }
      else if ("FN13".equalsIgnoreCase(key) && "avcoItem".equalsIgnoreCase(labelformat) )
      {
    	  reclabel.append("^" + key + "^FD" + getAvecorpFN13(recData.get("LOT_STATUS").toString(),recData.get("DETAIL_0").toString()) + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && "avcoItem".equalsIgnoreCase(labelformat) )
      {
    	  reclabel.append("^" + key + "^FD" + getAvecorpFN2(recData.get("LOT_STATUS").toString()) + "^FS" + linefeedd);
      }
      else if ("FN4".equalsIgnoreCase(key) && "avcoItem".equalsIgnoreCase(labelformat) )
      {
    	 reclabel.append("^" + key + "^FD" + getAvecorpFN4(recData.get("OWNER_COMPANY_ID").toString(),recData.get("RECEIPT_ID").toString(),recData.get("PO_NUMBER").toString() + "^FS" + linefeedd));
      }

      else if ("FN13".equalsIgnoreCase(key) && ("lmpalitem".equalsIgnoreCase(labelformat) || "lmcoitem".equalsIgnoreCase(labelformat) || "lmnfpacontainer".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String mfgLot = recData.get("MFG_LOT").toString();
        reclabel.append("^" + key + "^FH^FD" + getLockheed2dFN13(mfgLot,expireDateLocale,receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN13".equalsIgnoreCase(key) && ("boehmis".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String mfgLot = recData.get("MFG_LOT").toString();
        String itemId = recData.get("ITEM_ID").toString();
        reclabel.append("^" + key + "^FH^FD" + getBoeingStl2dFN13(mfgLot,expireDateLocale,receiptId,lastIdPrintedValue,itemId) + "^FS" + linefeedd);
      }
      else if ("FN10".equalsIgnoreCase(key) && ("troyitem".equalsIgnoreCase(labelformat) || "boehmis".equalsIgnoreCase(labelformat) || "lmcoitem".equalsIgnoreCase(labelformat) || "lmpalitem".equalsIgnoreCase(labelformat) || "lmnfpacontainer".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        reclabel.append("^" + key + "^FH^FD" + getLockheed2dFN10(receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN12".equalsIgnoreCase(key) && ( "italyLocalenew".equalsIgnoreCase(labelformat) || "italylabelnew".equalsIgnoreCase(labelformat)) )
      {        
        reclabel.append("^" + key + "^FD" + lastIdPrintedValue + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && ("drs2dcontainer".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String boeing2dExpireDate = recData.get("BOEING_EXPIRE_DATE").toString();
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String mfgLot = recData.get("MFG_LOT").toString();
        String partDescription = recData.get("PART_DESCRIPTION").toString();

        reclabel.append("^" + key + "^FD" + getDrs2dFN1(receiptId,catPartNo1,mfgLot,boeing2dExpireDate,partDescription,expireDateLocale) + "^FS" + linefeedd);
      }
      else if ("FN9".equalsIgnoreCase(key) && ("seagatelabel".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();

        reclabel.append("^" + key + "^FD" + getSeagateFN9(receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN20".equalsIgnoreCase(key) && ("seagatelabel".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDate = recData.get("EXPIRE_DATE").toString();

        reclabel.append("^" + key + "^FD" + getSeagate2dFN20(receiptId,lastIdPrintedValue,expireDate) + "^FS" + linefeedd);
      }
      else if ("FN13".equalsIgnoreCase(key) && ("rciitem".equalsIgnoreCase(labelformat)))
      {
      	Hashtable tempMap = new Hashtable(recData);
      	tempMap.put("RCI_UID", getRciUid(recData.get("RECEIPT_ID").toString(), lastIdPrintedValue));
    	reclabel.append("^" + key + "^FD" + getConfigurableLabelBarcodeStr(tempMap, labelformat) + "^FS" + linefeedd);
      }
      else if ("FN14".equalsIgnoreCase(key) && ("rciitem".equalsIgnoreCase(labelformat)))
      {
    	  String expireDate = recData.get("EXPIRE_DATE").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getExpireDateMonth(expireDate, invengrp) + "^FS" + linefeedd);
      }
      else if ("FN15".equalsIgnoreCase(key) && ("rciitem".equalsIgnoreCase(labelformat)))
      {
    	  String receiptId = recData.get("RECEIPT_ID").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getRciUid(receiptId, lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && ("lmgpitem".equalsIgnoreCase(labelformat)))
      {
    	  String catPartNo1 = recData.get("DETAIL_9").toString();
    	  String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
    	  String expireDate = recData.get("EXPIRE_DATE").toString();
    	  String receiptId = recData.get("RECEIPT_ID").toString();
    	  String componentMfgLots = recData.get("COMPONENT_MFG_LOTS").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getLmgp2dFN2(catPartNo1, expireDateLocale, receiptId, expireDate, componentMfgLots) + "^FS" + linefeedd);
      }
      else if("BARCODE_STR".equalsIgnoreCase(keyvalue)){
    	String receiptId = recData.get("DETAIL_2").toString();
  		String expireDate = recData.get("BARCODE_EXPIRE_DATE").toString();
  		String mfgLot = recData.get("MFG_LOT").toString();		
  		String catPartNo1 = recData.get("DETAIL_9").toString();
  		String catPartNo2 = recData.get("DETAIL_10").toString();
  		String catPartNo3 = recData.get("DETAIL_11").toString();
  		String catPartNo4 = recData.get("DETAIL_12").toString();
  		String catPartNo5 = recData.get("DETAIL_13").toString();
  		
		reclabel.append("^" + key + "^FD" + getBarcodeStr(receiptId, expireDate, mfgLot, catPartNo1, catPartNo2, catPartNo3, catPartNo4, catPartNo5, labelPartsList) + "^FS" + linefeedd);
      }
      else {
        if (recData.get("" + keyvalue + "") != null)
        {
        reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
        }
      }
    }

    if (pqvalues) {
      reclabel.append("^PQ" + qty + "" + linefeedd);
    } else {
      reclabel.append("^PQ1" + linefeedd);
    }

    reclabel.append("^XZ" + linefeedd);

    if (lablformatLocalelist != null && lablformatLocalelist.size() > 0)
		{
		String labelLocaleformat = (String) lablformatLocalelist.get(invengrp);
		reclabel.append("^XA" + linefeedd);
		reclabel.append("^XF" + labelLocaleformat + "^FS" + linefeedd);

	 Hashtable lblfeildsLocale = (Hashtable) lablfieldLocalelist.get("" + labelLocaleformat + "");

	 for (Enumeration elbl = lblfeildsLocale.keys(); elbl.hasMoreElements(); ) {
		String key = (String) elbl.nextElement();
		String keyvalue = lblfeildsLocale.get(key).toString();

		if ("FN1".equalsIgnoreCase(key) && !twoDBarcode) {
		 reclabel.append("^" + key + "^FD>;" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
		} else {
         if (recData.get("" + keyvalue + "") != null)
         {
          reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
         }
        }
	 }

	 if (pqvalues) {
		reclabel.append("^PQ" + qty + "" + linefeedd);
	 } else {
		reclabel.append("^PQ1" + linefeedd);
	 }

	 reclabel.append("^XZ" + linefeedd);
	}

    return reclabel;
 }

  public StringBuffer stdkitlabel(Hashtable recData, String qty, String hubndame, Hashtable lablformatlist, Hashtable lablfieldlist,Hashtable lablformatLocalelist, Hashtable lablfieldLocalelist, String invengrp, boolean pqvalues,String lastIdPrintedValue) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    String labelformat = (String) lablformatlist.get(invengrp);
		boolean twoDBarcode = false;
		if (lablformatLocalelist != null && lablformatLocalelist.size() > 0)
		{
			 twoDBarcode = true;
		}

    reclabel.append("^XA" + linefeedd);
    reclabel.append("^XF" + labelformat + "^FS" + linefeedd);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();

      if ("FN2".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String labelStorageTemp = recData.get("DETAIL_8").toString();
        reclabel.append("^" + key + "^FD" + getTucson2dFN2(expireDateLocale,labelStorageTemp) + "^FS" + linefeedd);
      }
      else if ("FN3".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String mfgLot = recData.get("MFG_LOT").toString();
        if (mfgLot.length() > 24)
        {
             mfgLot = mfgLot.substring(0,24);
        }
        reclabel.append("^" + key + "^FD" + mfgLot + "^FS" + linefeedd);
      }
      else if ("FN4".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String recerts = recData.get("DETAIL_5").toString();

        reclabel.append("^" + key + "^FD" + getTucson2dFN4(recerts) + "^FS" + linefeedd);
      }
      else if ("FN9".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        int length = 34;
        reclabel.append("^" + key + "^FD" + getTucson2dFN1(catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6,length) + "^FS" + linefeedd);
      }
      else if ("FN10".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String expireDate = recData.get("BARCODE_EXPIRE_DATE").toString();
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        String mfgLot = recData.get("MFG_LOT").toString();

        reclabel.append("^" + key + "^FH^FD" + getTucson2dFN10(expireDate,mfgLot,catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6) + "^FS" + linefeedd);
      }
      else if ("FN11".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
          zpllog.info("receiptId  "+receiptId+" lastIdPrintedValue"+lastIdPrintedValue+"");
        reclabel.append("^" + key + "^FD" + getTucson2dFN22(receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN12".equalsIgnoreCase(key) && "tuc2dKit".equalsIgnoreCase(labelformat) )
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
        String mfgLot = recData.get("MFG_LOT").toString();
        String labelStorageTemp = recData.get("DETAIL_8").toString();
        String catPartNo1 = recData.get("DETAIL_9").toString();
        String catPartNo2 = recData.get("DETAIL_10").toString();
        String catPartNo3 = recData.get("DETAIL_11").toString();
        String catPartNo4 = recData.get("DETAIL_12").toString();
        String catPartNo6 = recData.get("DETAIL_17").toString();
        reclabel.append("^" + key + "^FH^FD" + getTucson2dFN23(mfgLot,expireDateLocale,receiptId,lastIdPrintedValue,labelStorageTemp,catPartNo1,catPartNo2,catPartNo3,catPartNo4,"",catPartNo6) + "^FS" + linefeedd);
      }
      else if ("FN13".equalsIgnoreCase(key) && "avcokit".equalsIgnoreCase(labelformat) )
      {
    	  reclabel.append("^" + key + "^FD" + getAvecorpFN13(recData.get("LOT_STATUS").toString(),recData.get("DETAIL_0").toString()) + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && "avcokit".equalsIgnoreCase(labelformat) )
      {
    	  reclabel.append("^" + key + "^FD" + getAvecorpFN2(recData.get("LOT_STATUS").toString()) + "^FS" + linefeedd);
      }
      else if ("FN4".equalsIgnoreCase(key) && "avcokit".equalsIgnoreCase(labelformat) )
      {
    	 reclabel.append("^" + key + "^FD" + getAvecorpFN4(recData.get("OWNER_COMPANY_ID").toString(),recData.get("RECEIPT_ID").toString(),recData.get("PO_NUMBER").toString() + "^FS" + linefeedd));
      }
      else if ("FN10".equalsIgnoreCase(key) && ( "troykit".equalsIgnoreCase(labelformat) || "boehmiskit".equalsIgnoreCase(labelformat) || "lmcokit".equalsIgnoreCase(labelformat) || "lmpalkit".equalsIgnoreCase(labelformat) ) )
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        reclabel.append("^" + key + "^FH^FD" + getLockheed2dFN10(receiptId,lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN13".equalsIgnoreCase(key) && ("rcikit".equalsIgnoreCase(labelformat)))
      {
      	Hashtable tempMap = new Hashtable(recData);
      	tempMap.put("RCI_UID", getRciUid(recData.get("RECEIPT_ID").toString(), lastIdPrintedValue));
    	reclabel.append("^" + key + "^FD" + getConfigurableLabelBarcodeStr(tempMap, labelformat) + "^FS" + linefeedd);
      }
      else if ("FN14".equalsIgnoreCase(key) && ("rcikit".equalsIgnoreCase(labelformat)))
      {
    	  String expireDate = recData.get("EXPIRE_DATE").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getExpireDateMonth(expireDate, invengrp) + "^FS" + linefeedd);
      }
      else if ("FN15".equalsIgnoreCase(key) && ("rcikit".equalsIgnoreCase(labelformat)))
      {
    	  String receiptId = recData.get("RECEIPT_ID").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getRciUid(receiptId, lastIdPrintedValue) + "^FS" + linefeedd);
      }
      else if ("FN2".equalsIgnoreCase(key) && ("lmgpkit".equalsIgnoreCase(labelformat)))
      {
    	  String catPartNo1 = recData.get("DETAIL_9").toString();
    	  String expireDateLocale = recData.get("EXPIRE_DATE_LOCALE").toString();
    	  String expireDate = recData.get("EXPIRE_DATE").toString();
    	  String receiptId = recData.get("RECEIPT_ID").toString();
    	  String componentMfgLots = recData.get("COMPONENT_MFG_LOTS").toString();
    	  
    	  reclabel.append("^" + key + "^FD" + getLmgp2dFN2(catPartNo1, expireDateLocale, receiptId, expireDate, componentMfgLots) + "^FS" + linefeedd);
      }
      else if ("FN20".equalsIgnoreCase(key) && ("seagatelabel".equalsIgnoreCase(labelformat)))
      {
        String receiptId = recData.get("RECEIPT_ID").toString();
        String expireDate = recData.get("EXPIRE_DATE").toString();

        reclabel.append("^" + key + "^FD" + getSeagate2dFN20(receiptId,lastIdPrintedValue,expireDate) + "^FS" + linefeedd);
      }
      else if (recData.get("" + keyvalue + "") != null)
      {
        reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      }
    }

    if (pqvalues) {
      reclabel.append("^PQ" + qty + "" + linefeedd);
    } else {
      reclabel.append("^PQ1" + linefeedd);
    }

    reclabel.append("^XZ" + linefeedd);

    if (lablformatLocalelist != null && lablformatLocalelist.size() > 0)
		{
		String labelLocaleformat = (String) lablformatLocalelist.get(invengrp);

		 reclabel.append("^XA" + linefeedd);
		 reclabel.append("^XF" + labelLocaleformat + "^FS" + linefeedd);

		 Hashtable lblfeildsLocale = (Hashtable) lablfieldLocalelist.get("" + labelLocaleformat + "");

		 for (Enumeration elbl = lblfeildsLocale.keys(); elbl.hasMoreElements(); ) {
			 String key = (String) elbl.nextElement();
			 String keyvalue = lblfeildsLocale.get(key).toString();

             if (recData.get("" + keyvalue + "") != null)
             {
			    reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
             }
         }

		 if (pqvalues) {
			 reclabel.append("^PQ" + qty + "" + linefeedd);
		 } else {
			 reclabel.append("^PQ1" + linefeedd);
		 }

		 reclabel.append("^XZ" + linefeedd);
		}

    return reclabel;
  }

  public StringBuffer componentlabel(Vector compData, String hubname, Hashtable lablformatlist, Hashtable lablfieldlist, String invengrp, boolean pqvalues) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    try {
      for (int i = 0; i < compData.size(); i++) {
        Hashtable comph = (Hashtable) compData.elementAt(i);

        String numberofcomp = (comph.get("ITEM_COMPONENT_QUANTITY") == null ? "" : comph.get("ITEM_COMPONENT_QUANTITY").toString());
        int noofcomp = 0;
        try {
          noofcomp = Integer.parseInt(numberofcomp);
        } catch (Exception eee) {
          noofcomp = 1;
        }

        String labelformat = (String) lablformatlist.get(invengrp);

        reclabel.append("^XA" + linefeedd);
        reclabel.append("^XF" + labelformat + "^FS" + linefeedd);

        Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

        for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
          String key = (String) elbl.nextElement();
          String keyvalue = lblfeilds.get(key).toString();
          if (comph.get("" + keyvalue + "") != null)
          {
            reclabel.append("^" + key + "^FD" + comph.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
          }
        }
          reclabel.append("^PQ" + noofcomp + "" + linefeedd);

        reclabel.append("^XZ" + linefeedd);
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }

    return reclabel;
  }

  private StringBuffer buildcabinetlabels(String Hubnum, Vector cabData, String printerPath, String printRes, Vector invgrpsprint) {
    StringBuffer cablabel = new StringBuffer();
    BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();
    cablabel.append(gettemplates(invgrpsprint, "cabinet", printRes));

    try {
      Hashtable lablformatlist = new Hashtable();
      lablformatlist = getlabelformatlist(invgrpsprint, "cabinet");

      Hashtable lablfieldlist = new Hashtable();
      lablfieldlist = getlbldeflist(lablformatlist, printRes);

      for (int i = 0; i < cabData.size(); i++) {
        Hashtable cabh = (Hashtable) cabData.elementAt(i);
        cablabel.append(cabinetlabel(cabh, lablformatlist, lablfieldlist));
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }

    return cablabel;
  }

  public StringBuffer cabinetlabel(Hashtable cabData, Hashtable lablformatlist, Hashtable lablfieldlist) {
    StringBuffer cabnlabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    String invengrp = (cabData.get("INVENTORY_GROUP") == null ? "" : cabData.get("INVENTORY_GROUP").toString());
    String labelformat = (String) lablformatlist.get(invengrp);

    cabnlabel.append("^XA" + linefeedd);
    cabnlabel.append("^XF" + labelformat + "^FS" + linefeedd);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();

      if (cabData.get("" + keyvalue + "") != null)
      {
       cabnlabel.append("^" + key + "^FD" + cabData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      }
    }
    cabnlabel.append("^XZ" + linefeedd);

    return cabnlabel;
  }

  private StringBuffer buildcabinetbinlabels(String Hubnum, Vector cabData, Vector binData, boolean printcabinetlabels, String printerPath, String printRes, Vector invgrpsprint) {
    StringBuffer cabbinlabel = new StringBuffer();
    BarCodeHelpObj BarcodeConverter = new BarCodeHelpObj();
    Vector labeledCabs = new Vector();
    cabbinlabel.append(gettemplates(invgrpsprint, "cabinet", printRes));
    cabbinlabel.append(gettemplates(invgrpsprint, "cabinetbin", printRes));

    try {
      Hashtable cablablformatlist = new Hashtable();
      cablablformatlist = getlabelformatlist(invgrpsprint, "cabinet");

      Hashtable binlablformatlist = new Hashtable();
      binlablformatlist = getlabelformatlist(invgrpsprint, "cabinetbin");

      Hashtable cablablfieldlist = new Hashtable();
      cablablfieldlist = getlbldeflist(cablablformatlist, printRes);

      Hashtable binlablfieldlist = new Hashtable();
      binlablfieldlist = getlbldeflist(binlablformatlist, printRes);

      for (int j = 0; j < binData.size(); j++) {
        Hashtable binsh = (Hashtable) binData.elementAt(j);
        String bincabinetid = binsh.get("DETAIL_12").toString();
        String invengrp = (binsh.get("INVENTORY_GROUP") == null ? "" : binsh.get("INVENTORY_GROUP").toString());

        if (!labeledCabs.contains(bincabinetid) && printcabinetlabels) {
          Hashtable cabh = new Hashtable();
          cabh.put("DETAIL_1", bincabinetid);
          cabh.put("DETAIL_2", binsh.get("DETAIL_11").toString());
          cabh.put("INVENTORY_GROUP", invengrp);
          cabh.put("LOCATION_DETAIL", binsh.get("LOCATION_DETAIL").toString());

          cabbinlabel.append(cabinetlabel(cabh, cablablformatlist, cablablfieldlist));
          labeledCabs.add(bincabinetid);
        }
        cabbinlabel.append(cabbinlabel(binsh, Hubnum, binlablformatlist, binlablfieldlist));
      }
    } catch (Exception e11) {
      e11.printStackTrace();
    }

    return cabbinlabel;
  }

  public StringBuffer cabbinlabel(Hashtable cabbinData, String Hub, Hashtable lablformatlist, Hashtable lablfieldlist) {
    StringBuffer binlabel = new StringBuffer();

    String linefeed = "";
    linefeed += (char) (13);
    linefeed += (char) (10);

    String invengrp = (cabbinData.get("INVENTORY_GROUP") == null ? "" : cabbinData.get("INVENTORY_GROUP").toString());
    String labelformat = (String) lablformatlist.get(invengrp);

    binlabel.append("^XA" + linefeed);
    binlabel.append("^XF" + labelformat + "^FS" + linefeed);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();
      
      binlabel.append(printFieldData(key, keyvalue, cabbinData, linefeed));
    }
    binlabel.append("^XZ" + linefeed);

    if ("2118".equalsIgnoreCase(Hub)) {
      binlabel.append("^XA" + linefeed);
      binlabel.append("^XFDECATURDESCRIPTION^FS" + linefeed);
      binlabel.append("^FN1^FD" + cabbinData.get("DETAIL_3").toString() + "^FS" + linefeed);

      String partDesc = cabbinData.get("DETAIL_4").toString();
      if (partDesc.length() > 32) {
        binlabel.append("^FN2^FD" + partDesc.substring(0, 30) + "^FS" + linefeed);
        binlabel.append("^FN3^FD" + partDesc.substring(30, partDesc.length()) + "^FS" + linefeed);
      } else {
        binlabel.append("^FN2^FD" + partDesc + "^FS" + linefeed);
      }

      binlabel.append("^XZ" + linefeed);
    }

    return binlabel;
  }

  public StringBuffer buildprintjnlpfile(String hudbname, String papersize, String filename, String personnelId, String printerPath, String zpl) {
    StringBuffer itmlbltemp = new StringBuffer();
    String wwwHome = radian.web.tcmisResourceLoader.gethosturl();
    String jnlpurl = wwwHome + filename;

    itmlbltemp.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    itmlbltemp.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\""+wwwHome+"labelPrinterJar/\">\n");
    itmlbltemp.append("  <information>\n");
    itmlbltemp.append("	<title>Updating tcmIS Print Program</title>\n");
    itmlbltemp.append("	<vendor>Haas TCM</vendor>\n");
    itmlbltemp.append("	<homepage href=\""+wwwHome+"\" />\n");
    itmlbltemp.append("	<description kind=\"\">tcmIs is a total chemical Management tool</description>\n");
    itmlbltemp.append("	<description kind=\"short\">Use this to print your labels</description>\n");
    itmlbltemp.append("	<description kind=\"one-line\">tcmIS</description>\n");
    itmlbltemp.append("	<description kind=\"tooltip\">tcmIS Print Application</description>\n");
    itmlbltemp.append("	<icon kind=\"default\" href=\"images/tcmissquare.gif\" />\n");
    itmlbltemp.append("	<icon kind=\"selected\" href=\"images/tcmissquare.gif\" />\n");
    itmlbltemp.append("	<icon kind=\"disabled\" href=\"images/tcmissquare.gif\" />\n");
    itmlbltemp.append("	<icon kind=\"rollover\" href=\"images/tcmissquare.gif\" width=\"75\" height=\"25\" />\n");
    itmlbltemp.append("  </information>\n");
    itmlbltemp.append("<security>\n");
    itmlbltemp.append("	  <all-permissions/>\n");
    itmlbltemp.append("</security>\n");
    itmlbltemp.append("  <resources>\n");
    itmlbltemp.append("	<j2se version=\"1.4+\" max-heap-size=\"128m\"/>\n");
    itmlbltemp.append("	<jar href=\"LabelPrinter.jar\" main=\"true\" download=\"eager\" />\n");
    itmlbltemp.append("	<jar href=\"jbcl3.0.jar\" main=\"false\" download=\"eager\" />\n");
    itmlbltemp.append("  </resources>\n");
    itmlbltemp.append("  <application-desc main-class=\"radian.tcmis.client.share.printers.PrinterApp\">\n");
    itmlbltemp.append(" <argument>" + jnlpurl + "</argument>\n");
    itmlbltemp.append(" <argument>" + printerPath + "</argument>\n");
    itmlbltemp.append(" <argument>" + StringEscapeUtils.escapeXml(zpl) + "</argument>\n");
    itmlbltemp.append("</application-desc>\n");
    itmlbltemp.append("</jnlp>\n");

    return itmlbltemp;
  }

  public void writefiletodisk(StringBuffer txtfile, StringBuffer jnlpfile, String filename, boolean writeemptyfile) {
    String writefilepath = radian.web.tcmisResourceLoader.getsavelabelpath();

    String file = "";
    String jnfile = "";
    PrintStream ps = null;

    file = writefilepath + filename + ".txt";
    jnfile = writefilepath + filename + ".jnlp";

    if (writeemptyfile) {
      try {
        ps = new PrintStream(new FileOutputStream(file), true);

        String contents = "";
        contents += txtfile;
        byte outbuf[];
        outbuf = contents.getBytes();
        ps.write(outbuf);
        ps.close();
      } catch (IOException writee) {
        writee.printStackTrace();
      }
    }

    try {
      ps = new PrintStream(new FileOutputStream(jnfile), true);

      String contents = "";
      contents += jnlpfile;
      byte outbuf[];
      outbuf = contents.getBytes();
      ps.write(outbuf);
      ps.close();
    } catch (IOException writee) {
      writee.printStackTrace();
    }

  }

  public void buildLargeLabelZpl(Vector AllTheData, String NameofFile, String PaperSizeO, String perosnnelID,
                                 String printerPath, String printRes, Vector invgrpsprint) throws Exception {
    String writefilepath = radian.web.tcmisResourceLoader.getsavelabelpath();
    String file = "";
    file = writefilepath + "Barcode" + NameofFile + ".txt";
    PrintWriter pw = new PrintWriter(new FileOutputStream(file));

    Hashtable lablformatlist = new Hashtable();
    lablformatlist = getlabelformatlist(invgrpsprint, "largelabel");

    Hashtable lablfieldlist = new Hashtable();
    lablfieldlist = getlbldeflist(lablformatlist, printRes);

    StringBuilder zpl = new StringBuilder(gettemplates(invgrpsprint, "largelabel", printRes));

    try {
      for (int i = 0; i < AllTheData.size(); i++) {
        Hashtable recptData = (Hashtable) AllTheData.elementAt(i);
        String qtyreceived = (String) recptData.get("NO_OF_LARGE_LABELS").toString();
        int labelqty = 0;
        try {
          labelqty = Integer.parseInt(qtyreceived);
        } catch (Exception eee) {
          labelqty = 1;
        }

        StringBuffer recLabel = new StringBuffer();
        boolean usepqvalue = true;
        recLabel.append(largeLabel(recptData, "" + (labelqty) + "", lablformatlist, lablfieldlist, usepqvalue));
        zpl.append(recLabel);
      }
      pw.print(zpl.toString());
    } catch (Exception e11) {
      e11.printStackTrace();
    }
    finally {
    	pw.close();
    }
    StringBuffer blankbuffer = new StringBuffer();
    writefiletodisk(blankbuffer, buildprintjnlpfile("", PaperSizeO, "labels/Barcode" + NameofFile + ".txt", perosnnelID, printerPath, zpl.toString()),
                    "Barcode" + NameofFile + "", false);
  }

  public StringBuffer largeLabel(Hashtable recData, String qty, Hashtable lablformatlist, Hashtable lablfieldlist, boolean pqvalues) {
    StringBuffer reclabel = new StringBuffer();
    String linefeedd = "";
    linefeedd += (char) (13);
    linefeedd += (char) (10);

    String invengrp = (recData.get("INVENTORY_GROUP") == null ? "" : recData.get("INVENTORY_GROUP").toString());
    String labelformat = (String) lablformatlist.get(invengrp);
    String partHazardImage = (recData.get("PART_HAZARD_IMAGE") == null ? "" : recData.get("PART_HAZARD_IMAGE").toString());

    reclabel.append("^XA" + linefeedd);
    reclabel.append("^XF" + labelformat + "^FS" + linefeedd);

    Hashtable lblfeilds = (Hashtable) lablfieldlist.get("" + labelformat + "");

    for (Enumeration elbl = lblfeilds.keys(); elbl.hasMoreElements(); ) {
      String key = (String) elbl.nextElement();
      String keyvalue = lblfeilds.get(key).toString();

      if ("FN1".equalsIgnoreCase(key)) {
        reclabel.append("^" + key + "^FD>;" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
      } else {
        if (recData.get("" + keyvalue + "") != null)
        {
            reclabel.append("^" + key + "^FD" + recData.get("" + keyvalue + "").toString() + "^FS" + linefeedd);
        }
      }
    }
    if ("corrosiv".equalsIgnoreCase(partHazardImage)) {
      reclabel.append("^FO400,100^XGE:CORROSIV.GRF,1,1^FS" + linefeedd);
    } else if ("flam".equalsIgnoreCase(partHazardImage)) {
      reclabel.append("^FO400,100^XGE:FLAM.GRF,1,1^FS" + linefeedd);
    } else if ("oxidizer".equalsIgnoreCase(partHazardImage)) {
      reclabel.append("^FO400,100^XGE:OXIDIZER.GRF,1,1^FS" + linefeedd);
    } else if ("poison".equalsIgnoreCase(partHazardImage)) {
      reclabel.append("^FO400,100^XGE:POISON.GRF,1,1^FS" + linefeedd);
    }

    if (pqvalues) {
      reclabel.append("^PQ" + qty + "" + linefeedd);
    } else {
      reclabel.append("^PQ1" + linefeedd);
    }

    reclabel.append("^XZ" + linefeedd);

    return reclabel;
  }
  


  public String getAvecorpFN13(String lotStatus,String barcodeReceiptId)
  {
  	if(!lotStatus.equalsIgnoreCase("test") && !lotStatus.equalsIgnoreCase("cert review"))
  		return ">;" + barcodeReceiptId;
  	else
  		return "";
  }
  
  public String getAvecorpFN2(String lotStatus)
  {
  	if(lotStatus.equalsIgnoreCase("test"))
  		return "QA HOLD";
  	else if(lotStatus.equalsIgnoreCase("cert review"))
  		return "MATERIAL ON PRE-RELEASE";
  	else 
  		return "";
  }
  
  public String getAvecorpFN4(String ownerCompanyId, String receiptId, String poNumber)
  {
  	if(ownerCompanyId == null)
  		return "AV" + receiptId;
  	else
  		return "AV" + ((poNumber != null) ? poNumber : "");
  	
  }

  public String getTucson2dFN1 (String catPartNo1,String catPartNo2,String catPartNo3,String catPartNo4,String catPartNo5,String catPartNo6, int length) {
		// CatPartNo's separated by ; truncated at 34 chars
		String field = getTucsonPartNos(catPartNo1,catPartNo2,catPartNo3,catPartNo4,catPartNo5,catPartNo6).replaceAll(" ", "");
		return field.length() > length ?  field.substring(0, length-1) : field;
	}

	public String getTucson2dFN10 (String expireDateStr, String mfgLot,String catPartNo1,String catPartNo2,String catPartNo3,String catPartNo4,String catPartNo5,String catPartNo6) {
		// Expiration date as MM/DD/YYYY then
		// tab then 
		// CatPartNo's separated by ; then
		// tab-tab-tab then 
		// LotNumber
		StringBuilder line = new StringBuilder();
		
		line.append(expireDateStr);
		line.append("\t").append(getTucsonPartNos(catPartNo1,catPartNo2,catPartNo3,catPartNo4,catPartNo5,catPartNo6));
		line.append("\t\t\t").append(mfgLot);
		
		return line.toString().replaceAll("\t", "_09");
	}

	public String getTucson2dFN11 (String receiptId, String startingPrintId) {
		StringBuilder line = new StringBuilder("H");
        int receiptIdValue = 0;
        int startingPrintIdValue = 0;
        try {
          receiptIdValue = Integer.parseInt(receiptId);
            startingPrintIdValue = Integer.parseInt(startingPrintId);
        } catch (Exception eee) {
          receiptIdValue = 0;
          startingPrintIdValue = 0;
        }

        if (receiptIdValue <= 9999999)
        {
            line.append(String.format("%08d", receiptIdValue));
        }
        else
        {
            line.append(String.format("%07d", receiptIdValue));
        }
        line.append(String.format("%04d", startingPrintIdValue));
        return line.toString();
	}

    public String getTucson2dFN22 (String receiptId, String startingPrintId) {
		StringBuilder line = new StringBuilder("I");
        int receiptIdValue = 0;
        int startingPrintIdValue = 0;
        try {
          receiptIdValue = Integer.parseInt(receiptId);
            startingPrintIdValue = Integer.parseInt(startingPrintId);
        } catch (Exception eee) {
          receiptIdValue = 0;
          startingPrintIdValue = 0;
        }
        
        if (receiptIdValue <= 9999999)
        {
            line.append(String.format("%08d", receiptIdValue));
        }
        else
        {
            line.append(String.format("%07d", receiptIdValue));
        }
        line.append(String.format("%04d", startingPrintIdValue));
        return line.toString();
	}

    public String getTucson2dFN12 (String mfgLot, String expireDateLocale, String receiptId,String startingPrintId,String labelStorageTemp,String catPartNo1,String catPartNo2,String catPartNo3,String catPartNo4,String catPartNo5,String catPartNo6) {
		// CatPartNo's separated by ;<space>
		// <tab> then numberOfItems, hardcoded as 1
		// <tab><tab> H+receiptId
		// <tab>lotNumber
		// <tab>Expiration date as DD Mon YYYY
		// <tab>Storage temp as LowerLimit-UpperLimit UnitOfMeasure  (i.e. 50-75 F)
		// <tab>receiptId
		// <tab><tab>
		StringBuilder line = new StringBuilder(getTucsonPartNos(catPartNo1,catPartNo2,catPartNo3,catPartNo4,catPartNo5,catPartNo6));
		line.append("\t").append("1");
		line.append("\t\t").append(getTucson2dFN11(receiptId,startingPrintId));
		line.append("\t").append(mfgLot);
		line.append("\t").append(expireDateLocale);
		line.append("\t").append(getTucsonStorageTemp(labelStorageTemp).replace('@', ' ').trim());
		line.append("\t").append(receiptId);
		line.append("\t\t");
		return line.toString().replaceAll("\t", "_09");
	}

    public String getLM2dFN12 (String mfgLot, String expireDateLocale, String receiptId,String startingPrintId,String catPartNo1) {

            StringBuilder line = new StringBuilder("LCM$");
            if (catPartNo1.length() > 30)
            {
                 catPartNo1 = catPartNo1.substring(0,30);
            }
           line.append("\t").append(catPartNo1);

           int receiptIdValue = 0;
           try {
            receiptIdValue = Integer.parseInt(receiptId);
           } catch (Exception eee) {
              receiptIdValue = 0;
           }
           line.append(String.format("%10d", receiptIdValue));
           line.append("\t").append(expireDateLocale);
           //TODO Actuaally loop thru receipt component records. below is 80% fix
           if (mfgLot.length() > 35)
           {
                 mfgLot = "*"+ mfgLot.substring(1,34);
           }
           line.append("\t").append(mfgLot);
           line.append("\t").append("HGI");
           //TODO Label Id
           line.append("\t").append("9");
            return line.toString().replaceAll("\t", "_09");
    }

    public String getTucson2dFN23 (String mfgLot, String expireDateLocale, String receiptId,String startingPrintId,String labelStorageTemp,String catPartNo1,String catPartNo2,String catPartNo3,String catPartNo4,String catPartNo5,String catPartNo6) {
		// CatPartNo's separated by ;<space>
		// <tab> then numberOfItems, hardcoded as 1
		// <tab><tab> H+receiptId
		// <tab>lotNumber
		// <tab>Expiration date as DD Mon YYYY
		// <tab>Storage temp as LowerLimit-UpperLimit UnitOfMeasure  (i.e. 50-75 F)
		// <tab>receiptId
		// <tab><tab>
		StringBuilder line = new StringBuilder(getTucsonPartNos(catPartNo1,catPartNo2,catPartNo3,catPartNo4,catPartNo5,catPartNo6));
		line.append("\t").append("1");
		line.append("\t\t").append(getTucson2dFN22(receiptId,startingPrintId));
		line.append("\t").append(mfgLot);
		line.append("\t").append(expireDateLocale);
		line.append("\t").append(getTucsonStorageTemp(labelStorageTemp).replace('@', ' ').trim());
		line.append("\t").append(receiptId);
		line.append("\t\t");
		return line.toString().replaceAll("\t", "_09");
	}

    public String getTucson2dFN2(String expireDateLocale,String labelStorageTemp) {
		// CatPartNo's separated by ; truncated at 34 chars
		StringBuilder field = new StringBuilder(StringHandler.emptyIfNull(expireDateLocale));
		field.append(" ").append(getTucsonStorageTemp(labelStorageTemp));
        return field.toString();
	}

	public String getTucson2dFN4(String recerts) {
		// 0/5
		String field = recerts;
		if (recerts != null && recerts.trim().endsWith("/")) {
			field += "0";
		}
		return field;
	}

	public String getTucson2dFN5(String totalRecertsAllowed, String recerts,String expireDateLocale) {
        boolean noMaxRecerts = false;
		if (!isExpireDateIndefiniteOrShelfLifeExempt(expireDateLocale)) {
			if (totalRecertsAllowed == null || totalRecertsAllowed.length() ==0) {
				if (recerts.endsWith("0") || recerts.trim().endsWith("/")) {
					noMaxRecerts = true;
				}
			}
			else if (totalRecertsAllowed.equalsIgnoreCase("0")) {
				noMaxRecerts = true;
			}
		}
		return noMaxRecerts  ? "Red Label - No Recert" : "";
	}

	private String getTucsonStorageTemp (String labelStorageTemp) {
		return StringHandler.isBlankString(labelStorageTemp) ? "Store Per MFG" : labelStorageTemp ;
	}

    public boolean isExpireDateIndefiniteOrShelfLifeExempt(String expireDateLocale) {
		return expireDateLocale != null && ("N/A".equals(expireDateLocale.trim()) || "INDEFINITE".equals(expireDateLocale.trim()) || "SL Exempt".equals(expireDateLocale.trim()));
	}

    private String getTucsonPartNos (String catPartNo1,String catPartNo2,String catPartNo3,String catPartNo4,String catPartNo5,String catPartNo6) {
		StringBuilder line = new StringBuilder();
		if (!StringHandler.isBlankString(catPartNo1)) {
			line.append(catPartNo1);
		}
		if (!StringHandler.isBlankString(catPartNo2)) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append(catPartNo2);
		}
		if (!StringHandler.isBlankString(catPartNo3)) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append(catPartNo3);
		}
		if (!StringHandler.isBlankString(catPartNo4)) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("; ").append(catPartNo4);
		}
		if (!StringHandler.isBlankString(catPartNo5)) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("; ").append(catPartNo5);
		}
		if (!StringHandler.isBlankString(catPartNo6)) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("; ").append(catPartNo6);
		}
		return line.toString();
	}

    public String getLockheed2dFN13 (String mfgLot, String expireDateLocale, String receiptId,String startingPrintId) {
		StringBuilder line = new StringBuilder("");
        int receiptIdValue = 0;
        int startingPrintIdValue = 0;
        try {
          receiptIdValue = Integer.parseInt(receiptId);
            startingPrintIdValue = Integer.parseInt(startingPrintId);
        } catch (Exception eee) {
          receiptIdValue = 0;
          startingPrintIdValue = 0;
        }
        line.append(String.format("%08d", receiptIdValue));
        line.append(",");
        line.append(String.format("%08d", startingPrintIdValue));
        line.append(",");
		if (mfgLot !=null && mfgLot.length() > 30)
        {
            line.append(mfgLot.substring(0,30));
        }
        else
        {
            line.append(mfgLot);
        }
        line.append(",");
        line.append(expireDateLocale);
        return line.toString();
	}

    public String getBoeingStl2dFN13 (String mfgLot, String expireDateLocale, String receiptId,String startingPrintId, String itemId) {
           StringBuilder line = new StringBuilder("");
           int receiptIdValue = 0;
           int startingPrintIdValue = 0;
           try {
             receiptIdValue = Integer.parseInt(receiptId);
               startingPrintIdValue = Integer.parseInt(startingPrintId);
           } catch (Exception eee) {
             receiptIdValue = 0;
             startingPrintIdValue = 0;
           }
           line.append(String.format("%08d", receiptIdValue));
           line.append(",");
           line.append(String.format("%08d", startingPrintIdValue));
           line.append(",");
           if (mfgLot !=null && mfgLot.length() > 30)
           {
               line.append(mfgLot.substring(0,30));
           }
           else
           {
               line.append(mfgLot);
           }
           line.append(",");
           line.append(expireDateLocale);
           line.append(",");
           line.append(itemId);
           return line.toString();
       }


    public String getLockheed2dFN10 (String receiptId,String startingPrintId) {
		StringBuilder line = new StringBuilder("");
        int receiptIdValue = 0;
        int startingPrintIdValue = 0;
        try {
          receiptIdValue = Integer.parseInt(receiptId);
            startingPrintIdValue = Integer.parseInt(startingPrintId);
        } catch (Exception eee) {
          receiptIdValue = 0;
          startingPrintIdValue = 0;
        }
        line.append(String.format("%08d", receiptIdValue));
        line.append("-");
        line.append(startingPrintId);        
        return line.toString();
	}

    public String getDrs2dFN1(String receiptId, String catPartNo1, String mfgLot, String boeing2dExpireDate, String partDescription,String expireDateLocale) {
	   //Receipt ID
       //Part Number – limit to first 30 chars
       //Lot Number – limit to first 36 chars
       //Exp date DD-MMM-YY
       //Item Description – first 20 chars
        StringBuilder line = new StringBuilder(receiptId);
        line.append("|");
        String field = catPartNo1;
		if (field != null && field.length() > 30) {
			line.append(field.substring(0, 30));
		}
		else {
			line.append(field);
		}
		line.append("|");
        field = mfgLot;
		if (field != null && field.length() > 36) {
			line.append(field.substring(0, 36));
		}
		else {
			line.append(field);
		}
		line.append("|");
        if (!isExpireDateIndefiniteOrShelfLifeExempt(expireDateLocale))
        {
            line.append(boeing2dExpireDate);
        }
        else
        {
            line.append(expireDateLocale);
        }
        line.append("|");
        field = partDescription;
		if (field != null && field.length() > 20) {
			line.append(field.substring(0, 20));
		}
		else {
			line.append(field);
		}
        return line.toString();
	}
    
    public String getSeagateFN9 (String receiptId, String startingPrintId) {
		StringBuilder line = new StringBuilder(receiptId);
		line.append("-").append(startingPrintId);
		return line.toString();
	}
    
    public String getSeagate2dFN20 (String receiptId, String startingPrintId, String expireDate) {
    	StringBuilder line = new StringBuilder("");
    	int receiptIdValue = 0;
    	int startingPrintIdValue = 0;
    	try {
    		receiptIdValue = Integer.parseInt(receiptId);
    		startingPrintIdValue = Integer.parseInt(startingPrintId);
    	} catch (Exception eee) {
    		receiptIdValue = 0;
    		startingPrintIdValue = 0;
    	}
    	line.append(String.format("%08d", receiptIdValue));
    	line.append(",");
    	line.append(String.format("%08d", startingPrintIdValue));
    	line.append(",");
		line.append(StringHandler.emptyIfNull(expireDate));
		line.append(",").append("HGI4");
		return line.toString();
	}
    
    public String getRciUid(String receiptId, String startingPrintId){
    	String uid = receiptId + startingPrintId;
		if(uid.length() > 10){
			int startIndex = uid.length() - 10;
			uid = uid.substring(startIndex);
		}

		return uid;
    }
    
    public String getLmgp2dFN2 (String catPartNo, String expireDateLocale, String receiptId, String expireDate, String componentMfgLots) {
    	StringBuilder line = new StringBuilder("   LCM$");
		String field = (!StringHandler.isBlankString(catPartNo) && (catPartNo.indexOf("|") != -1 ||catPartNo.indexOf(",") != -1) ? barCode2dReplace.matcher(catPartNo).replaceAll(" "):catPartNo);
		
		if (field != null && field.length() > 30) {
			line.append("\t").append(field.substring(0, 30));
		}
		else {
			line.append("\t").append(field);
		}

		line.append("\t").append(receiptId);
		
		if(expireDateLocale.equals("NONE REQUIRED") || expireDateLocale.equals("INDEFINITE"))
			line.append("\t").append("01013000");
		else
			line.append("\t").append(expireDate);

		line.append("\t").append(componentMfgLots);
		
		line.append("\t").append("HGI8");
		
		return line.toString();
	}
   
   
    public String getExpireDateMonth(String expireDateStr, String invengrp){
    	String month = "";
    	
    	if(!StringHandler.isBlankString(expireDateStr) && !StringHandler.isBlankString(invengrp)) {
    		try{
	    		Date expireDate = DateHandler.getDateUsingInventoryGroupDefaultDateFormat(invengrp, expireDateStr, db.getClient().toUpperCase());
	    		month = DateHandler.formatDate(expireDate, "MMM").toUpperCase();
    		}
	    	catch(Exception e){
	    		zpllog.debug("Unable to convert date: " + expireDateStr);
	    	}
    	}
    	
    	return month;
    }
    
    public String getBarcodeStr(String receiptId, String expireDate, String mfgLot, String catPartNo1, 
    		String catPartNo2, String catPartNo3, String catPartNo4, String catPartNo5, Collection labelPartsList){
    	// Create the label field content BARCODE_STR
		// This is a generic barcode used for container labels
		StringBuilder barcodeStr = new StringBuilder("");
		
		// Receipt ID
		barcodeStr.append(receiptId).append("|");
		
		// Expiration Date
		barcodeStr.append(expireDate).append("|");

		// Mfg Lot		
		barcodeStr.append(mfgLot).append("|");		
		
		// Customer Part Number
		barcodeStr.append(!StringHandler.isBlankString(catPartNo1) && labelPartsList.contains("DETAIL_9") ? catPartNo1 : "null");
		barcodeStr.append("\t").append(!StringHandler.isBlankString(catPartNo2) && labelPartsList.contains("DETAIL_10") ? catPartNo2 : "null");
		barcodeStr.append("\t").append(!StringHandler.isBlankString(catPartNo3) && labelPartsList.contains("DETAIL_11") ? catPartNo3 : "null");
		barcodeStr.append("\t").append(!StringHandler.isBlankString(catPartNo4) && labelPartsList.contains("DETAIL_12") ? catPartNo4 : "null");
		barcodeStr.append("\t").append(!StringHandler.isBlankString(catPartNo5) && labelPartsList.contains("DETAIL_13") ? catPartNo5 : "null");
		return barcodeStr.toString();
	}

	private Collection getLabelPartsList(Hashtable labelFields) throws BaseException {
		Collection labelPartsList = new Vector();
		
		if (labelFields.size() > 0) {		
			for (Enumeration elbl = labelFields.keys(); elbl.hasMoreElements(); ) {
			      String key = (String) elbl.nextElement();
			      String keyvalue = labelFields.get(key).toString();

				if(keyvalue.equals("DETAIL_9") ||
						keyvalue.equals("DETAIL_10") ||		
						keyvalue.equals("DETAIL_11") ||
						keyvalue.equals("DETAIL_12") ||
						keyvalue.equals("DETAIL_13") ||
						keyvalue.equals("DETAIL_17") ||
						keyvalue.equals("DETAIL_18") 					
				  )
					labelPartsList.add(keyvalue);
			}
		}
		
		return labelPartsList;
	}
	
	private boolean isQrStr(String keyvalue) {
		if (keyvalue.toLowerCase().endsWith(qrStrIndicator)) 
	    	  return true;
		
		return false;
	}
	
	private String getStrippedKeyvalue(String keyvalue) {
		// this method can accommodate additional indicators
		
		if(isQrStr(keyvalue))
			return keyvalue.substring(0, keyvalue.length() - qrStrIndicator.length());
		
		return keyvalue;
	}
	
	private String printFieldData(String key, String keyvalue, Hashtable data, String linefeed) {
		StringBuffer line = new StringBuffer("");
		
		if(data.get("" + getStrippedKeyvalue(keyvalue) + "") != null) {
			line.append("^"+key);
			
			if(isQrStr(keyvalue))
				line.append( "^FH^FDLA "); // escape field data output
			else
				line.append("^FD");
			
			line.append(data.get("" + getStrippedKeyvalue(keyvalue) + "").toString());
			line.append("^FS");
			line.append(linefeed);
		}

		return (line.toString());
	}
	
	private String getConfigurableLabelBarcodeStr(Hashtable data, String labelFormat) {
		return ZplHandler.getConfigurableLabelBarcodeStr(data, labelFormat, db.getClient().toUpperCase());
	}
}