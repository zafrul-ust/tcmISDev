// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   HelpObjs.java

package radian.tcmis.server7.share.helpers;

import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;
import radian.tcmis.both1.helpers.BothHelpObjs;
import radian.tcmis.server7.share.db.DBResultSet;
import radian.tcmis.server7.share.dbObjs.*;
import com.tcmis.common.admin.process.MailProcess;

// Referenced classes of package radian.tcmis.server7.share.helpers:
//            ServerResourceBundle

public abstract class HelpObjs
{

    public HelpObjs()
    {
    }

    public static void monitor(int mode, String msg, ServerResourceBundle srb)
    {
        if(mode == 0)
        {
            //System.out.println(msg);
            return;
        }
        Date D = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String filename = new ServerResourceBundle().getString("BASE_DIR_TCM")+"/"+formatter.format(D)+".log";
        if(srb != null)
            filename = new ServerResourceBundle().getString("BASE_DIR_TCM")+srb.getString("LOG_DIR")+"/"+formatter.format(D)+".log";
        int inBytes = 0;
        byte inBuf[] = new byte[1];
        try
        {
            try
            {
                FileInputStream inStream = new FileInputStream(filename);
                inBytes = inStream.available();
                inBuf = new byte[inBytes];
                int bytesRead = inStream.read(inBuf, 0, inBytes);
                inStream.close();
            }
            catch(Exception exception) { }
            FileOutputStream outStream = new FileOutputStream(filename);
            if(inBytes > 0)
                outStream.write(inBuf, 0, inBytes);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(new Date());
            String hour = cal.get(11) >= 10 ? (new Integer(cal.get(11))).toString() : "0".concat(String.valueOf(String.valueOf((new Integer(cal.get(11))).toString())));
            String min = cal.get(12) >= 10 ? (new Integer(cal.get(12))).toString() : "0".concat(String.valueOf(String.valueOf((new Integer(cal.get(12))).toString())));
            String sec = cal.get(13) >= 10 ? (new Integer(cal.get(13))).toString() : "0".concat(String.valueOf(String.valueOf((new Integer(cal.get(13))).toString())));
            String now = hour+":"+min+":"+sec+" - ";
            outStream.write(now.getBytes());
            for(int i = 0; i < msg.length(); i++)
                outStream.write(msg.charAt(i));

            outStream.write(10);
            outStream.close();
        }
        catch(Exception exception1) { }
    }

    public static void sendMail(String subject, String message, String receiver[])
    {
        String sender = "tcmis@tcmis.com";
        sendMail(subject, message, sender, receiver);
    }

    public static void sendMail(UserGroup ug, String subject, String message)
        throws Exception
    {
        Vector v = ug.getMembers();
        String s[] = new String[v.size()];
        for(int i = 0; i < v.size(); i++)
        {
            Personnel p = (Personnel)v.elementAt(i);
            String email = p.getEmail();
            if(BothHelpObjs.isBlankString(email))
                email = "";
            s[i] = email;
        }

        sendMail(subject, message, s);
    }

    public static void sendMail(Personnel p, String subject, String message)
    {
        sendMail(subject, message, new String[] {
            p.getEmail()
        });
    }

    public static boolean sendMail(TcmISDBModel db, String subject, String message, int userID, boolean sendToAdmin)
    {
        //System.out.println("\n\n=============== sending email message: "+subject+"\n"+message+"\n"+userID+"\n");
        if(db.getDBUrl().indexOf("hawkdev") > 0)
        {
            //System.out.println("\n----- NOT send email coz I am in test env");
            return true;
        }
        String sender = "tcmis@tcmis.com";
        String admins[] = {"tngo@haastcm.com", "nshaik@haastcm.com"};
        String s = "";
        Personnel p = null;
        try
        {
            p = new Personnel(db);
            p.setPersonnelId(userID);
            p.load();
            s = p.getEmail();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        String recipients[];
        if(BothHelpObjs.isBlankString(s))
        {
            if(sendToAdmin)
            {
                message = message+" Caution: Personnel id: "+userID+"\nName: "+p.getLastName()+","+p.getFirstName()+" does not have an email address.";
                recipients = admins;
            } else
            {
                return false;
            }
        } else
        {
            recipients = (new String[] {
                s
            });
        }
        javaSendMail(sender, subject, recipients, message);
        return true;
    }

    public static void sendMail(String subject, String message, String sender, String receiver[])
    {
        javaSendMail(sender, subject, receiver, message);
    }

    public static Hashtable getKeyCol(String columnH[])
    {
        Hashtable h = new Hashtable();
        for(int i = 0; i < columnH.length; i++)
            h.put(columnH[i], new Integer(i));

        return h;
    }

    public static void insertUpdateTable(TcmISDBModel db, String query)
        throws Exception
    {
        try
        {
            db.doUpdate(query);
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public static int getNextValFromSeq(TcmISDBModel db, String seq)
        throws Exception
    {
        String query = "select "+seq+".nextval from dual";
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int next = 0;
        try
        {
            dbrs = db.doQuery(query);
            for(rs = dbrs.getResultSet(); rs.next();)
                next = rs.getInt(1);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return next;
    }

    public static String getStringValuesFromVector(Vector dataV) {
      String values = new String("");
      for (int i = 0; i < dataV.size(); i++)
      {
          String temp3 =  BothHelpObjs.changeSingleQuotetoTwoSingleQuote(dataV.elementAt(i).toString());
          //first change commas to its hex \2C
          String tmpS = "";
          StringTokenizer st = new StringTokenizer(temp3,",");
          while (st.hasMoreTokens()) {
            tmpS += st.nextToken().toString() + "/2C";
          }
          //remove the last \2C
          if (tmpS.length() > 3) {
            tmpS = tmpS.substring(0,tmpS.length()-3);
          }
          values += tmpS + ",";
      }
      //removing the last commas ','
      if (values.length() > 0)
      {
          values = values.substring(0,values.length()-1);
      }
      return values;
    } //end of method

    public static Vector getVectorFromString(String value) {
      Vector result = new Vector();
      StringTokenizer st = new StringTokenizer(value,",");      //2C hex for commas
      while (st.hasMoreTokens()) {
          String tmp = st.nextElement().toString();
          Vector field = new Vector();
          convertToCommas(tmp,field);

          String temp = "";
          for (int i = 0; i < field.size(); i++) {
            temp += (String)field.elementAt(i) + ",";
          }
          //removing the last commas
          if (temp.length() > 0) {
            temp = temp.substring(0,temp.length()-1);
          }
          result.addElement(temp);
      }
      return result;
    } //end of method

    static void convertToCommas(String tmp, Vector field) {
      int pos = tmp.indexOf("/2C");
      if (pos < 0) {   //string does not contain commas
        field.addElement(tmp);
      }else {
        field.addElement(tmp.substring(0,pos));
        tmp = tmp.substring(pos+3);
        convertToCommas(tmp,field);
      }
    }   //end of method

    public static boolean deleteDataFromTable(TcmISDBModel db, String tableName, String sessionID) throws Exception
    {
        String query = "delete from "+tableName+" where report_id = "+sessionID;
        try
        {
            db.doUpdate(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    } //end of method


    public static String getValueFromTable(TcmISDBModel db, String query)
        throws Exception
    {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String result = "0";
        try
        {
            dbrs = db.doQuery(query);
            for(rs = dbrs.getResultSet(); rs.next();)
                result = rs.getString(1);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return result;
    }

    public static int countQuery(TcmISDBModel db, String from, String where)
        throws Exception
    {
        String query = "select count(*) from ".concat(String.valueOf(String.valueOf(from)));
        if(where != null)
            query = query+" where "+where;
        return countQuery(db, query);
    }

    public static boolean showCurrency(TcmISDBModel db) {
      boolean result = true;
      /*
      String query = "select count(unique price_group_id) from price_group";
      try {
        result = HelpObjs.countQuery(db,query) > 0;
      } catch (Exception e) {
        e.printStackTrace();
      }*/
      return result;
    } //end of method

    public static Hashtable getCreditCardType(TcmISDBModel db) throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable result = new Hashtable(3);
        try {
          String query = "select credit_card_type,nvl(description,' ') description from vv_credit_card_type order by description";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          Vector ccID = new Vector(7);
          Vector ccDesc = new Vector(7);
          while (rs.next()) {
            ccID.addElement(rs.getString("credit_card_type"));
            ccDesc.addElement(rs.getString("description"));
          }
          result.put("CREDIT_CARD_TYPE_ID",ccID);
          result.put("CREDIT_CARD_TYPE_DESC",ccDesc);
        }
        catch(Exception e){
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return result;
    } //end of method

    public static String getCurrency(TcmISDBModel db, String facID) throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        String result = "";
        try {
          String query = "select pg.currency_id from price_group pg, facility f where pg.price_group_id = f.price_group_id"+
                         " and f.facility_id =  '"+facID+"'";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while (rs.next()) {
            result = rs.getString("currency_id");
          }
        }
        catch(Exception e){
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        return result;
    } //end of method

    public static Hashtable getCurrencyList(TcmISDBModel db, String orderBy, String priceGroupCurrency, String prNumber) throws Exception {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        Hashtable result = new Hashtable(3);
        //
        Vector idV = new Vector();
        Vector descV = new Vector();
        Vector conversionFactorV = new Vector();
        try {
          String query = "select currency_id,currency_description";
          if (BothHelpObjs.isBlankString(priceGroupCurrency)) {
            query += ",fx_conversion_rate(currency_id,currency_id,sysdate, nvl((select ops_entity_id from purchase_request where PR_NUMBER = "+prNumber+"), 'HAASTCMUSA')) conversion_factor";
          }else {
            query += ",fx_conversion_rate(currency_id,'"+priceGroupCurrency+"',sysdate, nvl((select ops_entity_id from purchase_request where PR_NUMBER = "+prNumber+"), 'HAASTCMUSA')) conversion_factor";
          }
          query += " from vv_currency order by "+orderBy;
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while (rs.next()) {
            idV.addElement(rs.getString("currency_id"));
            descV.addElement(rs.getString("currency_description"));
            conversionFactorV.addElement(rs.getString("conversion_factor"));
          }
        }
        catch(Exception e){
          e.printStackTrace();
          throw e;
        }finally {
          dbrs.close();
        }
        result.put("CURRENCY_ID",idV);
        result.put("CURRENCY_DESC",descV);
        result.put("CONVERSION_FACTOR",conversionFactorV);
        return result;
    } //end of method


    public static BigDecimal getConversionFactor(TcmISDBModel db, String fromCurrencyID, String toCurrencyID, String forDate, int prNumber) {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        BigDecimal result = new BigDecimal(1);
        try {
          String query = "select fx_conversion_rate('"+fromCurrencyID+"','"+toCurrencyID+"',to_date('"+forDate+"','mm/dd/yyyy'), nvl((select ops_entity_id from purchase_request where PR_NUMBER = "+prNumber+"), 'HAASTCMUSA')) conversion_factor  from dual";
          dbrs = db.doQuery(query);
          rs = dbrs.getResultSet();
          while (rs.next()) {
            result = rs.getBigDecimal("conversion_factor");
          }
        }
        catch(Exception e){
          e.printStackTrace();
        }finally {
          dbrs.close();
        }
        return result;
    } //end of method


    public static int countQuery(TcmISDBModel db, String query)
        throws Exception
    {
        DBResultSet dbrs = null;
        ResultSet rs = null;
        int x = 0;
        try
        {
            dbrs = db.doQuery(query);
            rs = dbrs.getResultSet();
            if(rs.next())
                x = rs.getInt(1);
        }
        catch(Exception e)
        {
            throw e;
        }
        finally
        {
            dbrs.close();
        }
        return x;
    }

    public static String singleQuoteToDouble(String s)
    {
        String y = null;
        if(s == null)
            return y;
        if(s.indexOf("'") < 0)
        {
            y = new String(s);
            return y;
        }
        StringBuffer sb1 = new StringBuffer(s);
        StringBuffer sb2 = new StringBuffer();
        for(int x = 0; x < sb1.length(); x++) {
            if(sb1.charAt(x) == '\'') {
                sb2.append("''");
            } else {
              try {
                sb2.append(sb1.charAt(x));
              }catch (Exception e) {
                e.printStackTrace();
              }
            }
        }
        y = sb2.toString();
        return y;
    }

    public static String addSlashForJavascript(String s)
    {
        StringTokenizer st = new StringTokenizer(s, "'");
        int i = 0;
        String result = "";
        if(st.countTokens() > 1)
        {
            while(st.hasMoreTokens())
            {
                String tok = st.nextToken();
                result = String.valueOf(result) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("\\'"));
            }
            result = result.substring(0, result.length() - 2);
        } else
        {
            result = s;
        }
        return result;
    }

    public static String findReplace(String source, String toReplace, String replacement)
    {
        if(source !=null && source.indexOf(toReplace) > -1)
        {
            StringBuffer sb = new StringBuffer();
            for(int ix = -1; (ix = source.indexOf(toReplace)) >= 0;)
            {
                //sb.append(source.substring(0, ix)).append(replacement);
                sb.append(source.substring(0,ix));
                sb.append(replacement);
                source = source.substring(ix + toReplace.length());
            }

            if(source.length() > 0)
                sb.append(source);
            return sb.toString();
        } else
        {
            return source;
        }
    }

    public static String addescapesForJavascript(String s)
    {
        int i = 0;
        String result4 = "";
        String resultFinal = "";
        if(s != null && s.length() > 0)
        {
            if(s.endsWith("\""))
                s = String.valueOf(String.valueOf(s.substring(0, s.length() - 1))).concat("&#34;");
					  if(s.endsWith("\\"))
							  s = String.valueOf(String.valueOf(s.substring(0, s.length() - 1))).concat("&#92;");
            if(s.endsWith(")"))
                s = String.valueOf(String.valueOf(s.substring(0, s.length() - 1))).concat("&#41;");
            if(s.endsWith("("))
                s = String.valueOf(String.valueOf(s.substring(0, s.length() - 1))).concat("&#40;");
            if(s.startsWith("\""))
                s = "&#34;".concat(String.valueOf(String.valueOf(s.substring(1, s.length()))));
					  if(s.startsWith("\\"))
                s = "&#92;".concat(String.valueOf(String.valueOf(s.substring(1, s.length()))));
            if(s.startsWith(")"))
                s = "&#41;".concat(String.valueOf(String.valueOf(s.substring(1, s.length()))));
            if(s.startsWith("("))
                s = "&#40;".concat(String.valueOf(String.valueOf(s.substring(1, s.length()))));
            StringTokenizer st = new StringTokenizer(s, "\"");
            String result = "";
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    result = String.valueOf(result) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("&#34;"));
                }
                result = result.substring(0, result.length() - 5);
            } else
            {
                result = s;
            }
                  
			st = new StringTokenizer(result, "\\");
			  i = 0;
				String result1 = "";
				if(st.countTokens() > 1)
			  {
					 while(st.hasMoreTokens())
					 {
							 String tok = st.nextToken();
							 result1 = String.valueOf(result1) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("\\'"));
					 }
					 result1 = result1.substring(0, result1.length() - 2);
			  } else
			  {
					 result1 = result;
			  }
            
            st = new StringTokenizer(result1, "'");
            i = 0;
            String result5 = "";
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    result5 = String.valueOf(result5) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("\\'"));
                }
                result5 = result5.substring(0, result5.length() - 2);
            } else
            {
                result5 = result1;
            }

            st = new StringTokenizer(result5, "\n");
            i = 0;
            String result2 = "";
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    tok = tok.trim();
                    result2 = String.valueOf(result2) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("<BR>"));
                }
                result2 = result2.substring(0, result2.length() - 4);
            } else
            {
                result2 = result5;
            }
            st = new StringTokenizer(result2, "(");
            i = 0;
            String result3 = "";
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    result3 = String.valueOf(result3) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("&#40;"));
                }
                result3 = result3.substring(0, result3.length() - 5);
            } else
            {
                result3 = result2;
            }
            st = new StringTokenizer(result3, ")");
            i = 0;
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    result4 = String.valueOf(result4) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("&#41;"));
                }
                result4 = result4.substring(0, result4.length() - 5);
            } else
            {
                result4 = result3;
            }
            result4 = result4.trim();

            st = new StringTokenizer(result4, "\r");
            i = 0;
            resultFinal = "";
            if(st.countTokens() > 1)
            {
                while(st.hasMoreTokens())
                {
                    String tok = st.nextToken();
                    tok = tok.trim();
                    resultFinal = String.valueOf(resultFinal) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("<BR>"));
                }
                resultFinal = resultFinal.substring(0, resultFinal.length() - 4);
            }
            else
            {
                resultFinal = result4;
            }

            String testString = resultFinal.substring(resultFinal.length() - 1, resultFinal.length());
            if(testString.equalsIgnoreCase("'"))
                resultFinal = String.valueOf(String.valueOf(resultFinal.substring(0, resultFinal.length() - 1))).concat("\\'");
        }
        return resultFinal;
    }

    public static String decodeHexcodesfoUpdate(String s)
    {
        int i = 0;
        StringTokenizer st = new StringTokenizer(s, "\n");
        i = 0;
        String result2 = "";
        if(st.countTokens() > 1)
        {
            while(st.hasMoreTokens())
            {
                String tok = st.nextToken();
                tok = tok.trim();
                result2 = String.valueOf(result2) + String.valueOf(String.valueOf(String.valueOf(tok)).concat("<BR>"));
            }
            result2 = result2.substring(0, result2.length() - 4);
        } else
        {
            result2 = s;
        }
        return result2;
    }

    public static void javaSendMail(String sender, String subject, String rec[], String msg)
    {
      try {
        //System.out.println("------ javasendmailnew start");
        MailProcess mailProcess = new MailProcess();
        String[] cc = new String[0];

	      for (int i = 0; i < rec.length; i++) {
				 String emailAddresess = rec[i];
				 StringTokenizer st = new StringTokenizer(emailAddresess, ",");

				 if(st.countTokens() > 1)
				 {
					String[] recipients =new String[st.countTokens()];
					int emailCount = 0;
					while(st.hasMoreTokens())
					{
					 String tok = st.nextToken();
					 recipients[emailCount] =tok;
					 emailCount++;
					 System.out.println(tok);
					}
					mailProcess.sendEmail(recipients,cc,subject,msg,true);
				 }
				 else
				 {
					String[] recipients =new String[1];
					recipients[0] =emailAddresess;
					mailProcess.sendEmail(recipients,cc,subject,msg,true);
				 }
				}

        /*
        radian.tcmis.mail.MailMessage m = new radian.tcmis.mail.MailMessage();
        m.setFromAddress("tcmis@tcmis.com");
        m.addBCCAddress("email.repository@tcmis.com");
        for (int i = 0; i < rec.length; i++) {
          System.out.println(rec[i]);
          m.addToAddress(rec[i]);
        }
        m.setSubject(subject);
        StringWriter errmsg = new StringWriter();
        errmsg.write(msg);
        m.println(errmsg.toString());
        m.sendMessage();
        */
      }catch (Exception e) {
        e.printStackTrace();
      }
      //System.out.println("------ javasendmailnew done");
    }

    public static void javaSendMail(String subject, String msg, String rec[], String cc[]) {
      try {
        //System.out.println("------ javasendmailnew with cc start");
				MailProcess mailProcess = new MailProcess();
				for (int i = 0; i < rec.length; i++) {
				 String emailAddresess = rec[i];
				 StringTokenizer st = new StringTokenizer(emailAddresess, ",");

		if(st.countTokens() > 1)
		{
		 String[] recipients =new String[st.countTokens()];
		 int emailCount = 0;
		 while(st.hasMoreTokens())
		 {
			String tok = st.nextToken();
			recipients[emailCount] =tok;
			emailCount++;
			System.out.println(tok);
		 }
		 mailProcess.sendEmail(recipients,cc,subject,msg,true);
		}
		else
		{
		 String[] recipients =new String[1];
		 recipients[0] =emailAddresess;
		 mailProcess.sendEmail(recipients,cc,subject,msg,true);
		}
	 }

        /*
        radian.tcmis.mail.MailMessage m = new radian.tcmis.mail.MailMessage();
        m.setFromAddress("tcmis@tcmis.com");
        //keep a copy in repository - don't show this to user
        m.addBCCAddress("email.repository@tcmis.com");
        //main receipenents
        for (int i = 0; i < rec.length; i++) {
          System.out.println("to: "+rec[i]);
          m.addToAddress(rec[i]);
        }
        //any cc
        for (int j = 0; j < cc.length; j++) {
          System.out.println("cc: "+cc[j]);
          m.addCCAddress(cc[j]);
        }
        m.setSubject(subject);
        StringWriter errmsg = new StringWriter();
        errmsg.write(msg);
        m.println(errmsg.toString());
        m.sendMessage();
        */
      }catch (Exception e) {
        e.printStackTrace();
      }
      //System.out.println("------ javasendmailnew with cc done");
      //System.out.println("----"+subject+"\n"+msg);
    }

    protected abstract ServerResourceBundle getBundle();
}