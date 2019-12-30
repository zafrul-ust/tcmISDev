package radian.tcmis.both1.helpers;

import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class CreateTxtFileFromData{
  public CreateTxtFileFromData() {

  } //end of contructor

  public void createFile(Vector data, String[] headerColumn, String fileName) throws Exception {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
      if (bufferedWriter != null) {
        int i = 0;
        //first write column headers
        for (i = 0; i < headerColumn.length; i++) {
          String columnName = headerColumn[i];
          //add blank space to separate columns, but don't add it to the last column
          if (i < headerColumn.length -1) {
            columnName += " ";
          }
          bufferedWriter.write(columnName);
        }
        if (headerColumn.length > 0) {
          bufferedWriter.newLine();
        }
        //next write data
        for (i = 0; i < data.size(); i++) {
          Vector innerV = (Vector) data.elementAt(i);
          for (int j = 0; j < innerV.size(); j++) {
            bufferedWriter.write((String)innerV.elementAt(j));
          }
          bufferedWriter.newLine();
        }
        bufferedWriter.close();
      }
    }catch (Exception e) {
      throw e;
    }
  } //end of method

  //without column header
  public void createFile(Vector data, String fileName) throws Exception {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
      if (bufferedWriter != null) {
        for (int i = 0; i < data.size(); i++) {
          Vector innerV = (Vector) data.elementAt(i);
          for (int j = 0; j < innerV.size(); j++) {
            bufferedWriter.write((String)innerV.elementAt(j));
          }
          bufferedWriter.newLine();
        }
        bufferedWriter.close();
      }
    }catch (Exception e) {
      throw e;
    }
  } //end of method

} //end of method
