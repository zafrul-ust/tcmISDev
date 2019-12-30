package radian.tcmis.both1.helpers;

import java.util.Hashtable;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class ReadDataFromFile{
  public ReadDataFromFile() {

  } //end of contructor

  public Vector readFile(String fileName, String delim, String[] headerColumn) throws Exception {
    Vector rowDataV = new Vector();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      if (bufferedReader != null) {
        String line = null;
        while((line = bufferedReader.readLine())!=null){
          StringTokenizer st = new StringTokenizer(line,delim);
          int count = 0;
          Hashtable columnDataH = new Hashtable(headerColumn.length);
          while(st.hasMoreElements()) {
            columnDataH.put(headerColumn[count++],st.nextElement().toString().trim());
          }
          rowDataV.addElement(columnDataH);
        }
        bufferedReader.close();
      }
    }catch (Exception e) {
      throw e;
    }
    return rowDataV;
  } //end of method
} //end of method
