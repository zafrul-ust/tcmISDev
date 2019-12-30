package com.tcmis.client.utc.process;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.sql.Timestamp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.PrintWriter;


import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RegexMatcher;
import org.apache.commons.digester.RegexRules;
import org.apache.commons.digester.RuleSet;
import org.apache.commons.digester.SimpleRegexMatcher;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.client.utc.beans.PwaSpecRevisionBean;

/******************************************************************************
 * Process to load MARES data from UTC.
 * @version 1.0
 *****************************************************************************/
public class PwaSpecRevisionDataLoadProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public PwaSpecRevisionDataLoadProcess(String client) {
    super(client);
  }

  public void load() throws Exception {
    ResourceLibrary specLibrary = new ResourceLibrary("PWAFeedData");
    String specHtmFinal = specLibrary.getString("specrevision.filename");
    String localFilePath = specLibrary.getString("specrevision.local.ftp.dir");
    String remoteFilePath = specLibrary.getString("specrevision.remote.ftp.dir");
    SimpleDateFormat prefixFmt = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
    String now = prefixFmt.format(new java.util.Date());
    SimpleDateFormat prefixFmt2 = new SimpleDateFormat("dMMMyyyyEEEHH:mm:ss");
    String finalNow = prefixFmt2.format(new java.util.Date());
    String fromFileName = "";
    String toFileName = "";
    String baseName = "";

    try {
      //get all files in diretory
      File f2 = new File(localFilePath);
      String[] list = f2.list();
      //only deleting final file if there something in the directory
      if (list.length > 0) {
        //first delete final result file
        File ff = new File(remoteFilePath + specHtmFinal);
        if (ff.exists()) {
          ff.delete();
        }
        PrintWriter pw = new PrintWriter(new FileOutputStream(remoteFilePath + specHtmFinal));
        for (int k = 0; k < list.length; k++) {
          baseName = list[k];
          //only process *.htm files
          if (baseName.indexOf(".htm") < 0) {
            continue;
          }
          fromFileName = localFilePath + baseName;
          toFileName = remoteFilePath + now + "-" + baseName;
          //check to see if file exist
          File f = new File(fromFileName);
          if (!f.exists()) {
            log.error("-------SPEC revision no file found: " + fromFileName);
            return;
          }
          //move file to it place
          FileHandler.move(fromFileName, toFileName);
          //load data from file
          Vector v = new Vector(readDataFromPwaMsdsRevision(toFileName));
          for (int i = 0; i < v.size(); i++) {
            PwaSpecRevisionBean bean = (PwaSpecRevisionBean) v.elementAt(i);
            pw.print("START MESSAGE" + bean.getPartNo().trim() + "START MESSAGE,");
            pw.print("START MESSAGE" + bean.getRevision().trim() + "START MESSAGE,");
            pw.print("START MESSAGE" + bean.getRevisionDate().trim() + "START MESSAGE,");
            pw.print("START MESSAGE" + bean.getComment().trim() + "START MESSAGE,");
            pw.print(finalNow);
            pw.println("");
          }
        } //end of list of all *.htm
        pw.close();
      }
    } catch (Exception e) {
      log.error("Error loading PWA SPEC revision data", e);
      throw e;
    }
  } //end of method

  public Collection readDataFromPwaMsdsRevision(String fileName) throws Exception {
    Collection c = new Vector();
    Vector errorV = new Vector();
    boolean skipEntireFile = true;
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      if (bufferedReader != null) {
        String line = null;
        int count = 0;
        boolean mergeRow = false;
        String mergeCellLine = "";
        //determine whether file includes Rev column
        String fileType = "";
        int startingIndex = -1;
        int endingIndex = -1;
        int subStringIndex = -1;
        while((line = bufferedReader.readLine())!=null) {
          if (fileType.length() == 0) {
            if (line.indexOf("Specification") > 0 && line.indexOf("Rev.") > 0 && line.indexOf("Date") > 0 && line.indexOf("Title") > 0) {
              fileType = "fileWithRev";
            }else if (line.indexOf("PROCEDURE") > 0 && line.indexOf("EFFECTIVE") > 0 && line.indexOf("SUBJECT") > 0) {
              fileType = "fileWithoutRev";
            }
            continue;
          }

          //file without REV. column
          if ("fileWithoutRev".equalsIgnoreCase(fileType)) {
            //whether to try to process row
            if (line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp; </th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp; </th><td>");
              startingIndex += "<tr><th>&nbsp;&nbsp; </th><td>".length();
            }else if (line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp;*</th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp;*</th><td>");
              startingIndex += "<tr><th>&nbsp;&nbsp;*</th><td>".length();
            }else if (line.toLowerCase().indexOf("<tr><td>&nbsp;&nbsp;*</th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><td>&nbsp;&nbsp;*</th><td>");
              startingIndex += "<tr><td>&nbsp;&nbsp;*</th><td>".length();
            }else if (line.toLowerCase().indexOf("<tr><td>&nbsp;&nbsp; </th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><td>&nbsp;&nbsp; </th><td>");
              startingIndex += "<tr><td>&nbsp;&nbsp; </th><td>".length();
            }else {
              continue;
            }

            count++;
            PwaSpecRevisionBean pwaMsdsRevisionBean = new PwaSpecRevisionBean();
            //part number
            endingIndex = line.toLowerCase().indexOf("</td><td class=\"c\">");
            if (endingIndex > -1) {
              subStringIndex = endingIndex + "</td><td class=\"c\">".length();
            }
            if (endingIndex < 0) {
              errorV.addElement("No Rev PART line:"+(new Integer(count)).toString()+"-"+line+"\n");
              continue;
            }
            String tmp = line.substring(startingIndex,endingIndex);
            if (tmp.lastIndexOf("-") > 0) {
              pwaMsdsRevisionBean.setPartNo(tmp.substring(0,tmp.lastIndexOf("-")));
              pwaMsdsRevisionBean.setRevision(tmp.substring(tmp.lastIndexOf("-")+1));
            }else {
              pwaMsdsRevisionBean.setPartNo(tmp);
              pwaMsdsRevisionBean.setRevision("");
            }
            line = line.substring(subStringIndex);
            startingIndex = 0;
            //revision date
            endingIndex = line.toLowerCase().indexOf("</td><td><b>");
            if (endingIndex < 0) {
              endingIndex = line.toLowerCase().indexOf("</td><td>");
              if (endingIndex > -1) {
                subStringIndex = endingIndex + "</td><td>".length();
              }
            }else {
              subStringIndex = endingIndex + "</td><td><b>".length();
            }
            if (startingIndex < 0 || endingIndex < 0) {
              errorV.addElement("No Rev REVISION DATE line:"+(new Integer(count)).toString()+"-"+line+"\n");
              continue;
            }
            pwaMsdsRevisionBean.setRevisionDate(line.substring(startingIndex,endingIndex));
            line = line.substring(subStringIndex);

            //comment
            endingIndex = line.indexOf("</b></td></tr>");
            if (endingIndex < 0) {
              endingIndex = line.toLowerCase().indexOf("</td></tr>");
            }
            if (endingIndex < 0) {
              endingIndex = line.toLowerCase().indexOf("td></tr>");
            }

            if (startingIndex < 0 || endingIndex < 0) {
              errorV.addElement("No Rev COMMENT line: "+(new Integer(count)).toString()+"-"+line+"\n");
              continue;
            }
            tmp = removeSpecialCharactersFromString(line.substring(startingIndex,endingIndex));
            pwaMsdsRevisionBean.setCommnet(tmp);
            pwaMsdsRevisionBean.setFileName(fileName);
            c.add(pwaMsdsRevisionBean);
            skipEntireFile = false;
          //else file with REV. column
          }else if ("fileWithRev".equalsIgnoreCase(fileType)) {
            //whether to try to process row
            if (line.toLowerCase().indexOf("<tr><th>&nbsp; </th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp; </th><td>");
              startingIndex += "<tr><th>&nbsp; </th><td>".length();
            } else if (line.toLowerCase().indexOf("<tr><th>&nbsp;*</th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp;*</th><td>");
              startingIndex += "<tr><th>&nbsp;*</th><td>".length();
            }else if (line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp; </th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp; </th><td>");
              startingIndex += "<tr><th>&nbsp;&nbsp; </th><td>".length();
            }else if (line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp;*</th><td>") > -1) {
              startingIndex = line.toLowerCase().indexOf("<tr><th>&nbsp;&nbsp;*</th><td>");
              startingIndex += "<tr><th>&nbsp;&nbsp;*</th><td>".length();
            }else if (mergeRow) {
              //check to see if we reach the end
              if (line.endsWith("</td></tr>")) {
                line = mergeCellLine + line;
                mergeCellLine = "";
                mergeRow = false;
              }else {
                mergeCellLine += line;
                mergeRow = true;
                continue;
              }
            }else {
              continue;
            }
            count++;
            //part number
            endingIndex = line.toLowerCase().indexOf("</td><td class=\"c\">");
            if (endingIndex > -1) {
              subStringIndex = endingIndex + "</td><td class=\"c\">".length();
            }
            if (endingIndex < 0) {
              if (count > -1) {
                mergeCellLine += line;
                mergeRow = true;
              }else {
                errorV.addElement("PART line: " + (new Integer(count)).toString()+"-"+line+"\n");
              }
              continue;
            }
            //check to see if they merge spec number
            String tmpSpecNumber = line.substring(startingIndex, endingIndex);
            //if merge cell found, then expand
            if (tmpSpecNumber.indexOf("<br>") > -1) {
              line = line.substring(subStringIndex);
              startingIndex = 0;
              String[] specNum = tmpSpecNumber.split("<br>");
              String revision = "";
              String revDate = "";
              String comment = "";
              for (int k = 0; k < specNum.length; k++) {
                String tmpSpec = StringHandler.replace(specNum[k].trim(),"&nbsp;","");
                if (k == 0) {
                  //part number / spec number
                  //revision
                  endingIndex = line.toLowerCase().indexOf("</td><td>");
                  if (endingIndex > -1) {
                    subStringIndex = endingIndex + "</td><td>".length();
                  }
                  if (startingIndex < 0 || endingIndex < 0) {
                    errorV.addElement("REVISION line: " + (new Integer(count)).toString()+"-"+line+"\n");
                    continue;
                  }
                  revision = line.substring(startingIndex, endingIndex);
                  line = line.substring(subStringIndex);
                  //revision date
                  endingIndex = line.toLowerCase().indexOf("</td><td><b>");
                  if (endingIndex < 0) {
                    endingIndex = line.toLowerCase().indexOf("</td><td>");
                    if (endingIndex > -1) {
                      subStringIndex = endingIndex + "</td><td>".length();
                    }
                  } else {
                    subStringIndex = endingIndex + "</td><td><b>".length();
                  }
                  if (startingIndex < 0 || endingIndex < 0) {
                    errorV.addElement("REVISION DATE line: " + (new Integer(count)).toString()+"-"+line+"\n");
                    continue;
                  }
                  revDate = line.substring(startingIndex, endingIndex);
                  line = line.substring(subStringIndex);
                  //comment
                  endingIndex = line.indexOf("</b></td></tr>");
                  if (endingIndex < 0) {
                    endingIndex = line.toLowerCase().indexOf("</td></tr>");
                  }
                  if (endingIndex < 0) {
                    endingIndex = line.toLowerCase().indexOf("td></tr>");
                  }
                  if (startingIndex < 0 || endingIndex < 0) {
                    errorV.addElement("COMMENT line: " + (new Integer(count)).toString()+"-"+line+"\n");
                    continue;
                  }
                  comment = removeSpecialCharactersFromString(line.substring(startingIndex, endingIndex));
                }
                //put everything together
                PwaSpecRevisionBean pwaMsdsRevisionBean = new PwaSpecRevisionBean();
                pwaMsdsRevisionBean.setPartNo(tmpSpec);
                pwaMsdsRevisionBean.setRevision(revision.trim());
                pwaMsdsRevisionBean.setRevisionDate(revDate.trim());
                pwaMsdsRevisionBean.setCommnet(comment.trim());
                pwaMsdsRevisionBean.setFileName(fileName);
                c.add(pwaMsdsRevisionBean);
                skipEntireFile = false;
              } //end of expand merge row
              mergeCellLine = "";
            } else {
              PwaSpecRevisionBean pwaMsdsRevisionBean = new PwaSpecRevisionBean();
              pwaMsdsRevisionBean.setPartNo(line.substring(startingIndex, endingIndex));
              line = line.substring(subStringIndex);
              startingIndex = 0;

              //revision
              endingIndex = line.toLowerCase().indexOf("</td><td>");
              if (endingIndex > -1) {
                subStringIndex = endingIndex + "</td><td>".length();
              }
              if (startingIndex < 0 || endingIndex < 0) {
                errorV.addElement("REVISION line: " + (new Integer(count)).toString());
                continue;
              }
              pwaMsdsRevisionBean.setRevision(line.substring(startingIndex, endingIndex));
              line = line.substring(subStringIndex);

              //revision date
              endingIndex = line.toLowerCase().indexOf("</td><td><b>");
              if (endingIndex < 0) {
                endingIndex = line.toLowerCase().indexOf("</td><td>");
                if (endingIndex > -1) {
                  subStringIndex = endingIndex + "</td><td>".length();
                }
              } else {
                subStringIndex = endingIndex + "</td><td><b>".length();
              }
              if (startingIndex < 0 || endingIndex < 0) {
                errorV.addElement("REVISION DATE line: " + (new Integer(count)).toString());
                continue;
              }
              pwaMsdsRevisionBean.setRevisionDate(line.substring(startingIndex, endingIndex));
              line = line.substring(subStringIndex);

              //comment
              endingIndex = line.indexOf("</b></td></tr>");
              if (endingIndex < 0) {
                endingIndex = line.toLowerCase().indexOf("</td></tr>");
              }
              if (endingIndex < 0) {
                endingIndex = line.toLowerCase().indexOf("td></tr>");
              }
              if (startingIndex < 0 || endingIndex < 0) {
                errorV.addElement("COMMENT line: " + (new Integer(count)).toString());
                continue;
              }
              String tmp = removeSpecialCharactersFromString(line.substring(startingIndex, endingIndex));
              pwaMsdsRevisionBean.setCommnet(tmp);
              pwaMsdsRevisionBean.setFileName(fileName);
              c.add(pwaMsdsRevisionBean);
              skipEntireFile = false;
            }
            mergeRow = false;
          } //end of file with REV
        } //end of each row
        bufferedReader.close();
        //notify someone if can't determine file type
        if (fileType.length() == 0) {
          errorV.addElement("CAN'T TELL IF FILE IS WITH/WITHOUT REV COLUMN");
        }
      }
      //notify someone if a the whole file is skipped
      if (skipEntireFile) {
        errorV.addElement("THE WHOLE FILE WAS SKIPPED");
      }

      //unable to read some lines or error
      String msg = "File name: "+fileName+"\n";
      for (int i = 0; i < errorV.size(); i++) {
        msg += errorV.elementAt(i).toString()+"\n";
      }
      if (errorV.size() > 0) {
        sendEmailToAdmin(msg);
      }

    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return c;
  } //end of method

  String removeSpecialCharactersFromString(String tmp) {
    if (tmp.toLowerCase().indexOf("<sub>") > -1) {
      tmp = tmp.replaceAll("<sub>","").replaceAll("<SUB>","").replaceAll("</sub>","").replaceAll("</SUB>","");
    }
    if (tmp.indexOf("&Ograve;") > -1 || tmp.indexOf("&ograve;") > -1) {
      tmp = tmp.replaceAll("&Ograve;","O").replaceAll("&ograve;","o");
    }
    return tmp;
  } //end of method

  void sendEmailToAdmin(String emailMessage) throws Exception {
    try {
      DbManager dbManager = new DbManager(getClient());
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      Collection c = factory.selectUsersEmailByGroup("All","PwaFeedSpec");
      String[] to = new String[c.size()];
      Iterator dataIter = c.iterator();
      int i = 0;
      boolean hasEmail = false;
      while (dataIter.hasNext()) {
        PersonnelBean personnelBean = (PersonnelBean) dataIter.next();
        if (personnelBean.getEmail() != null && personnelBean.getEmail().length() > 0) {
          to[i++] = personnelBean.getEmail();
          hasEmail = true;
        }
      }
      //if no one in group send email developers, otherwise send email to hub
      if (!hasEmail) {
        String[] toDev = {"deverror@tcmis.com"};
        MailProcess.sendEmail(toDev, new String[0], "PWA Feed Spec - no member for PWA Feed Spec", emailMessage, false);
      }else {
        MailProcess.sendEmail(to, new String[0], "PWA Feed Spec", emailMessage, true);
      }
    }catch (Exception ex) {
      log.error("Error sending error mail for PWA Feed Spec (processing).", ex);
      throw ex;
    }
  } //end of method

} //end of class
