package com.tcmis.client.swa.process;

import java.util.Collection;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.math.BigDecimal;

import com.tcmis.common.db.DbManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
/* header columns in file
 PO_NUMBER, PART_NUMBER, MFR_PART_NUMBER, FSC_CODE, DOC_TYPE, STATUS, PO_DATE,
 STATION, DEPARTMENT, QUANTITY_ORDERED, QUANTITY_RECEIVED, UNIT_PURCHASED, CONVERSION_FACTOR, TOTAL_AMOUNT, SHIP_VIA,
 NEED_DATE, UPDATE_DATE, SWA_BUYER, AIRCRAFT, PACKAGE_CODE, REMARKS_LINE_1, REMARKS_LINE_2, REMARKS_LINE_3,
 REMARKS_LINE_4, BUYERS_NOTES_1, BUYERS_NOTES_2, BUYERS_NOTES_3, BUYERS_NOTES_4, BUYERS_NOTES_5, BUYERS_NOTES_6, BUYERS_NOTES_7,
 BUYERS_NOTES_8, BUYERS_NOTES_9, BUYERS_NOTES_10, BUYERS_NOTES_11, BUYERS_NOTES_12, BUYERS_NOTES_13, BUYERS_NOTES_14, BUYERS_NOTES_15
*/

public class ReadDataFromFile{
  Log log = LogFactory.getLog(this.getClass());

  private static int PO_COL = 0;
  private static int PART_COL = 1;
  private static int MFG_COL = 2;
  private static int FSC_COL = 3;
  private static int DOC_COL = 4;
  private static int STATUS_COL = 5;
  private static int PO_DATE_COL = 6;
  private static int STATION_COL = 7;
  private static int DEPARTMENT_COL = 8;
  private static int QTY_ORDERED_COL = 9;
  private static int QTY_RECEIVED_COL = 10;
  private static int UNIT_COL = 11;
  private static int CONVERSION_COL = 12;
  private static int TOTAL_COL = 13;
  private static int SHIP_TO_COL = 14;
  private static int NEED_DATE_COL = 15;
  private static int UPDATE_DATE_COL = 16;
  private static int BUYER_COL = 17;
  private static int AIRCRAFT_COL = 18;
  private static int PACKAGE_COL = 19;
  private static int REMARK_1_COL = 20;
  private static int REMARK_2_COL = 21;
  private static int REMARK_3_COL = 22;
  private static int REMARK_4_COL = 23;
  private static int NOTES_1_COL = 24;
  private static int NOTES_2_COL = 25;
  private static int NOTES_3_COL = 26;
  private static int NOTES_4_COL = 27;
  private static int NOTES_5_COL = 28;
  private static int NOTES_6_COL = 29;
  private static int NOTES_7_COL = 30;
  private static int NOTES_8_COL = 31;
  private static int NOTES_9_COL = 32;
  private static int NOTES_10_COL = 33;
  private static int NOTES_11_COL = 34;
  private static int NOTES_12_COL = 35;
  private static int NOTES_13_COL = 36;
  private static int NOTES_14_COL = 37;
  private static int NOTES_15_COL = 38;

  public ReadDataFromFile() {

  } //end of contructor

  public Collection readFile(DbManager dbManager,String fileName, String delimiter, String header, String trailer) throws Exception {
    Collection swaOrderBeanColl = new Vector();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      if (bufferedReader != null) {
        String line = null;
        int startingIndex = 0;
        while((line = bufferedReader.readLine())!=null) {
          //if line is empty
          if (StringHandler.isBlankString(line)) {
            continue;
          }
          //skip header and trailer
          String[] result = line.split(delimiter);
          if (result[0].startsWith(header) || result[result.length-1].startsWith(trailer)) {
            log.error("--- skipping header and trailer line");
            continue;
          }

          //fill bean
          CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();
          customerPoPreStageBean.setDocumentControlNumber(fileName);
          customerPoPreStageBean.setStatus("NEW");
          customerPoPreStageBean.setLineSequence(new BigDecimal("1"));
          customerPoPreStageBean.setChangeOrderSequence(new BigDecimal("0"));
          customerPoPreStageBean.setTransport("FTP");
          customerPoPreStageBean.setTransporterAccount("swa");
          customerPoPreStageBean.setTradingPartner("swa");
          customerPoPreStageBean.setTradingPartnerId("swa");
          BigDecimal loadID =  new BigDecimal(dbManager.getOracleSequence("customer_po_load_seq"));
          customerPoPreStageBean.setInterchangeControlNumber(loadID.toString());
          customerPoPreStageBean.setLoadId(loadID);
          customerPoPreStageBean.setLoadLine(new BigDecimal(1));

          customerPoPreStageBean.setCustomerPoNo(result[this.PO_COL].trim());
          customerPoPreStageBean.setCatPartNo(result[this.PART_COL].trim());
          customerPoPreStageBean.setManufacturerPartNum(result[this.MFG_COL].trim());
          customerPoPreStageBean.setTransactionType(result[this.STATUS_COL].trim());
          String tmpDate = result[this.PO_DATE_COL].trim();
          DateHandler poDate = new DateHandler();
          customerPoPreStageBean.setCustomerPoLineNo(poDate.formatDate(poDate.getDateFromString("MM/dd/yy",tmpDate),"yyyyMMdd"));
          customerPoPreStageBean.setDateIssued(poDate.getDateFromString("MM/dd/yy",tmpDate));
          customerPoPreStageBean.setShiptoPartyId(result[this.STATION_COL].trim()+"."+result[this.DEPARTMENT_COL].trim());
          BigDecimal qtyOrdered = new BigDecimal(result[this.QTY_ORDERED_COL].trim());
          customerPoPreStageBean.setQuantity(qtyOrdered);
          BigDecimal qtyReceived = new BigDecimal(result[this.QTY_RECEIVED_COL].trim());
          customerPoPreStageBean.setQuantityLeft(qtyOrdered.min(qtyReceived));
          customerPoPreStageBean.setUom(result[this.UNIT_COL].trim());
          BigDecimal totalAmount = new BigDecimal(result[this.TOTAL_COL].trim());
          customerPoPreStageBean.setTotalAmountOnPo(totalAmount.divide(new BigDecimal(1000),totalAmount.scale()+2,BigDecimal.ROUND_HALF_UP));
          customerPoPreStageBean.setCarrier(result[this.SHIP_TO_COL].trim());
          customerPoPreStageBean.setRequestedDeliveryDate(poDate.getDateFromString("MM/dd/yy",result[this.NEED_DATE_COL].trim()));
          customerPoPreStageBean.setPoUpdateDate(result[this.UPDATE_DATE_COL].trim());
          //po_notes
          String poNotes = "";
          String tmp = result[this.REMARK_1_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            poNotes += tmp +"; ";
          }
          tmp = result[this.REMARK_2_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            poNotes += tmp +"; ";
          }
          tmp = result[this.REMARK_3_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            poNotes += tmp +"; ";
          }
          tmp = result[this.REMARK_4_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            poNotes += tmp +"; ";
          }
          //remove the last ';'
          if (poNotes.length() > 2) {
            poNotes = poNotes.substring(0,poNotes.length()-2);
          }
          customerPoPreStageBean.setCustomerPoNote(poNotes);
          //line_notes
          String lineNotes = "";
          tmp = result[this.NOTES_1_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_2_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_3_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_4_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_5_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_6_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_7_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_8_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_9_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_10_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_11_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_12_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_13_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          tmp = result[this.NOTES_14_COL].trim();
          if (!StringHandler.isBlankString(tmp)) {
            lineNotes += tmp +"; ";
          }
          //remove the last ';'
          if (lineNotes.length() > 2) {
            lineNotes = lineNotes.substring(0,lineNotes.length()-2);
          }
          customerPoPreStageBean.setCustomerPoLineNote(lineNotes);
          //add to collection
          swaOrderBeanColl.add(customerPoPreStageBean);
        }
        bufferedReader.close();
      }
    }catch (Exception e) {
      log.error("SWA - PO FEED: Error while reading data from file");
      throw e;
    }
    return swaOrderBeanColl;
  } //end of method

} //end of class
