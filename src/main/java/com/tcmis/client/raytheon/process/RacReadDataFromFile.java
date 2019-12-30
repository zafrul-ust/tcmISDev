package com.tcmis.client.raytheon.process;

import java.util.Collection;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Date;
import java.util.regex.Pattern;

import com.tcmis.client.order.beans.CustomerPoPreStageBean;
/* header columns in file
 po_number, po_date, po_doctype, po_line, part_number,
 mfg_part_number, part_description, required_date, quantity, uom, extended_price, plant,
 storage_location, notes, email, phone, delete_flag, change_flag, PAST_END_OF_ROW
*/

public class RacReadDataFromFile{


  public RacReadDataFromFile() {

  } //end of contructor

  public Collection readFile(String fileName, String delimiter) throws Exception {
    Collection racOrderBeanColl = new Vector();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      if (bufferedReader != null) {
        String line = null;
        int startingIndex = 0;
        while((line = bufferedReader.readLine())!=null) {
          String[] result = line.split(delimiter);
          //fill bean
          CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();
          for (int x=0; x<result.length; x++) {
            System.out.println("column: "+x+"-"+result[x]);
          }
          racOrderBeanColl.add(customerPoPreStageBean);
        }
        bufferedReader.close();
      }
    }catch (Exception e) {
      throw e;
    }
    return racOrderBeanColl;
  } //end of method

} //end of class
