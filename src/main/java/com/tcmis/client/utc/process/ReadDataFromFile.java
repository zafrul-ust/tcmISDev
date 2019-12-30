package com.tcmis.client.utc.process;

import java.util.Collection;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.Hashtable;

import com.tcmis.client.utc.beans.PwaMaresBean;

public class ReadDataFromFile{
  public ReadDataFromFile() {

  } //end of contructor

  public Collection readFile(String fileName) throws Exception {
    Collection pwaMaresBeanColl = new Vector();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      if (bufferedReader != null) {
        String line = null;
        int startingIndex = 0;
        while((line = bufferedReader.readLine())!=null) {
          PwaMaresBean pwaMaresBean = new PwaMaresBean();
          pwaMaresBean.setDispositionCode(line.substring(startingIndex,(startingIndex=startingIndex+3)));
          pwaMaresBean.setRemark(line.substring(startingIndex,(startingIndex=startingIndex+80)));
          pwaMaresBean.setDispositionClock(line.substring(startingIndex,(startingIndex=startingIndex+6)));
          pwaMaresBean.setShipmentId(line.substring(startingIndex,(startingIndex=startingIndex+16)));
          pwaMaresBean.setMclReceivingNo(line.substring(startingIndex,(startingIndex=startingIndex+6)));
          pwaMaresBean.setDispositionDate(line.substring(startingIndex,(startingIndex=startingIndex+10)));
          pwaMaresBean.setExpirationDate(line.substring(startingIndex));
          pwaMaresBean.setFileName(fileName);
          pwaMaresBean.setTcmLoadDate(new Date());
          pwaMaresBeanColl.add(pwaMaresBean);
          startingIndex = 0;
        }
        bufferedReader.close();
      }
    }catch (Exception e) {
      throw e;
    }
    return pwaMaresBeanColl;
  } //end of method

} //end of method

