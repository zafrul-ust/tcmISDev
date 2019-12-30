/*
 * EBPShoppingProcess.java
 *
 * Created on July 8, 2004, 2:56 PM
 */

package com.tcmis.client.fec.process;

// import com.tcmis.client.fec.beans.ShoppingCartBean;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import com.tcmis.client.fec.beans.PunchoutOrderMessageBean;
import com.tcmis.client.fec.factory.EbpPendFinanceApprovalViewBeanFactory;
import com.tcmis.client.fec.factory.PunchoutOrderMessageBeanFactory;
import com.tcmis.common.admin.beans.CompanyApplicationLogonBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.CompanyApplicationLogonBeanFactory;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbConnectionException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;

/**
 *
 * @author  mike.najera
 */
public class EBPShoppingProcess extends BaseProcess {

	/** Creates a new instance of VvItemTypeProcess */
	public EBPShoppingProcess(String client) {
		super(client);
	}

	/*
	 * Look up the Haas Personnel user from the SAP EBP user
	 * ID (stored in Company_Application_Logon_Id ), valiedate
	 * and return
	 */
	public PersonnelBean getHaasUserFromSAPId(String sapUserId) {
		CompanyApplicationLogonBean appLogon=null;
		PersonnelBean person=null;

		try {
			DbManager dbManager = new DbManager(this.getClient());

			CompanyApplicationLogonBeanFactory companyAppLogonFactory = new CompanyApplicationLogonBeanFactory(dbManager);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("companyApplicationLogonId",SearchCriterion.EQUALS,sapUserId);
			searchCriteria.addCriterion("application",SearchCriterion.EQUALS,"EBP");
			Collection coAppLogonBeans = companyAppLogonFactory.select(searchCriteria);
			if (coAppLogonBeans.size()>0) {
				Iterator iter = coAppLogonBeans.iterator();
				appLogon = (CompanyApplicationLogonBean) iter.next();
			} else {
				throw new BaseException("No user found");
			}

			PersonnelBeanFactory personnelFactory = new PersonnelBeanFactory(dbManager);

			SearchCriteria searchCriteria2 = new SearchCriteria();
			//searchCriteria2.addCriterion("companyApplicationLogonId",SearchCriterion.EQUALS,sapUserId);
			searchCriteria2.addCriterion("personnelId",SearchCriterion.EQUALS,appLogon.getPersonnelId()+"");
			Collection personBeans = personnelFactory.select(searchCriteria2);
			if (personBeans.size()>0) {
				Iterator iter = personBeans.iterator();
				person = (PersonnelBean) iter.next();
			} else {
				throw new BaseException("No user found");
			}
			dbManager=null;
			personnelFactory=null;
		} catch (BaseException be) {
			log.error("BaseException searching for SAP user id: " + sapUserId + " :: " + be);
		}
		return person;
	}

	/*
	 * Create the JNLP file for the given user id and cart id
	 */
	public String createJnlpUrl(String loginId, String cartId) {
		String url=null;
		StringBuilder Msg = new StringBuilder();
		Random rand=new Random();
		int tmpReq=rand.nextInt();
		Integer tmpReqNum=new Integer( tmpReq );
		ResourceLibrary fecResourceLoader = new ResourceLibrary("fec");
		PrintStream ps;
		PrintWriter out = null;
		int tmpReq1=rand.nextInt();
		Integer tmpReqNum1=new Integer( tmpReq1 );

		String passwd = "haastcm";
		String WebStartY = "";
		String Logon_ID1 = loginId;
		String payload_ID1 = cartId;
		String Ariba_Flag1 = "Y";
		String Logged = "N";
		String iprocoracle = "N";


		//System.out.println("WebStart  " +WebStartY+" Logon Id "+Logon_ID1+"  "+payload_ID1);


		//if ("Yes".equalsIgnoreCase(WebStartY))
		if (true)
		{
			/* ** VALIDATION goes here
         try
         {
         String query = "select LOGGED,ORACLE from punchout_session where PAYLOAD_ID = '"+payload_ID1+"'";
         dbrs = db.doQuery(query);
         rs=dbrs.getResultSet();

         while (rs.next())
         {
            Logged = rs.getString("LOGGED")==null?"N":rs.getString("LOGGED").trim();
            iprocoracle = rs.getString("ORACLE")==null?"N":rs.getString("ORACLE").trim();
         }
         }
         catch (Exception e)
         {
         e.printStackTrace();
         BuildIntorPage("ERROR","","",out);
         return;
         }
         finally
         {
             dbrs.close();
         }

         if ("N".equalsIgnoreCase(Logged))
         {
           HttpSession session = request.getSession();
           //System.out.println("Session Id"+session.getId());

           try
           {
           String query = "update punchout_session set LOGGED = 'Y' where PAYLOAD_ID = '"+payload_ID1+"' and SESSION_ID = '"+session.getId()+"'";
           db.doUpdate(query);
           }
           catch (Exception e)
           {
           e.printStackTrace();
           BuildIntorPage("ERROR","","",out);
           return;
           }
           finally
           {
               dbrs.close();
           }

           try
           {
           String query = "select PASSWORD from personnel where lower(LOGON_ID) = lower('"+Logon_ID1+"')";
           dbrs = db.doQuery(query);
           rs=dbrs.getResultSet();

           while (rs.next())
           {
              passwd = (rs.getString("PASSWORD")==null?"":rs.getString("PASSWORD"));
           }
           }
           catch (Exception e)
           {
           e.printStackTrace();
           BuildIntorPage("ERROR","","",out);
           return;
           }
           finally
           {
               dbrs.close();
           }

           String query = "select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('"+Logon_ID1+"')";
           String prere14query="select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('" + Logon_ID1 + "') and VERSION = '1.4' ";
           String allre14query="select count(*) from tcmis_prerelease_version where lower(USERNAME) = lower('All') and VERSION = '1.4' ";

           int prereleasecount1=0;
           int prerelease14count1=0;
           int allrelease14count1=0;

           try
           {
             prereleasecount1 = DbHelpers.countQuery(db,query);
             prerelease14count1=DbHelpers.countQuery( db,prere14query );
             allrelease14count1=DbHelpers.countQuery( db,allre14query );
             //System.out.println("Query count is " + prereleasecount1 );
           }
           catch (Exception e)
           {
             e.printStackTrace();
             BuildIntorPage("ERROR","","",out);
             return;
           }
			 */

			//response.setContentType("application/x-java-jnlp-file");
			String jnlpcodebase = fecResourceLoader.getString("jnlp.codebase");
			String jnlp14dir = fecResourceLoader.getString("prerelease14dir");

			//PrintWriter out1 = new PrintWriter (response.getOutputStream());
			//System.out.println("**** Starting tcmIS");

			Msg.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" );
			Msg.append( "<jnlp spec=\"1.0).append(\" version=\"2.0\" codebase=\"" ).append( jnlpcodebase ).append( fecResourceLoader.getString( "jnlp.directory" ) ).append( "/\">\n" );
			Msg.append( "  <information>\n" );
			Msg.append( "    <title>Updating tcmIS</title>\n" );
			Msg.append( "    <vendor>Haas TCM</vendor>\n" );
			Msg.append( "    <homepage href=\"http://www.tcmis.com\" />\n" );
			Msg.append( "    <description kind=\"\">tcmIs is a total chemical Management tool</description>\n" );
			Msg.append( "    <description kind=\"short\">Use this to manage your chemical needs</description>\n" );
			Msg.append( "    <description kind=\"one-line\">tcmIS</description>\n" );
			Msg.append( "    <description kind=\"tooltip\">tcmIS Application</description>\n" );
			Msg.append( "    <icon kind=\"default\" href=\"images/tcmissquare.gif\" />\n" );
			Msg.append( "    <icon kind=\"selected\" href=\"images/tcmissquare.gif\" />\n" );
			Msg.append( "    <icon kind=\"disabled\" href=\"images/tcmissquare.gif\" />\n" );
			Msg.append( "    <icon kind=\"rollover\" href=\"images/tcmissquare.gif\" width=\"75\" height=\"25\" />\n" );
			Msg.append( "  </information>\n" );

			Msg.append("<security>\n");
			Msg.append("	<all-permissions/>\n");
			Msg.append("</security>\n");
			Msg.append("  <resources>\n");

			Msg.append("    <j2se version=\"1.4\" max-heap-size=\"128m\"/>\n" );
			Msg.append("    <jar href=\"").append( fecResourceLoader.getString("jnlp.mainclass")).append("\" main=\"true\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianBoth1.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSAdmin.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSDelivery.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSGui.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSHelpers.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSHttpCgi.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSNChem.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSSpanCellTable.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"RadianCSWaste.jar\" main=\"false\" download=\"eager\" />\n");

			Msg.append("    <jar href=\"ResourceLoader.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"jbcl3.0.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"SSLava.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"acme.jar\" main=\"false\" download=\"eager\" />\n");
			Msg.append("    <jar href=\"dx.jar\" download=\"eager\"/>\n");
			Msg.append("    <jar href=\"jsse.jar\" download=\"eager\"/>\n");
			Msg.append("    <jar href=\"jnet.jar\" download=\"eager\"/>\n");
			Msg.append("    <jar href=\"jcert.jar\" download=\"eager\"/>\n");
			Msg.append("  </resources>\n");

			Msg.append("  <application-desc main-class=\"").append(fecResourceLoader.getString("tcmis.startclass")).append("\">\n");
			Msg.append(" <argument>").append(Logon_ID1).append("</argument>\n");
			Msg.append(" <argument>").append(passwd).append("</argument>\n");
			Msg.append(" <argument>").append(Ariba_Flag1).append("</argument>\n");
			Msg.append(" <argument>").append(payload_ID1).append("</argument>\n");
			Msg.append(" <argument>2</argument>\n");   // 1 for test, 2 for production
			Msg.append("</application-desc>\n");
			Msg.append("</jnlp>\n");

			try
			{
				String filepathtojnlpfile=fecResourceLoader.getString("jnlp.filepath");
				ps=new PrintStream( new FileOutputStream( filepathtojnlpfile + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp" ),true );

				byte outbuf[];
				outbuf=Msg.toString().getBytes();
				ps.write( outbuf );
				ps.close();
			}
			catch ( IOException writee )
			{
				writee.printStackTrace();
			}

			String hosturl = fecResourceLoader.getString("hosturl");
			String jnlpUrlDir = fecResourceLoader.getString("jnlp.urldir");
			String filename = "" + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp";
			url = hosturl + jnlpUrlDir + filename;

			// BuildIntorPageredirect( "" + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp", payload_ID1,Logon_ID1,iprocoracle,out );
			//System.out.println( jnlpcodebase + "/jnlp/loginfile/" + Logon_ID1 + tmpReqNum.toString() + tmpReqNum1.toString() + ".jnlp" );

			//Msg.toString());
			//out1.close();
			//}
			//else
			//{
			//response.setContentType("text/html");
			//System.out.println("Already Logged in Once");
			// BuildIntorPage("LOGGED",Logon_ID1,payload_ID1,out); //** what to do here, what to do ...
			//}
		}
		else
		{
			//response.setContentType("text/html");
			//BuildIntorPage("NOTHING",Logon_ID1,payload_ID1,out);  //** what to do here, what to do ...
		}
		return url;

	}

	/*
	 * Check to see if the order is completed (punchout="Y" for the
	 * given payload ID.) Return the valid PR num if it is completed,
	 * or else null if not.
	 */
	public BigDecimal orderCompleted(String cartId) {
		BigDecimal prNum = null;
		try {
			DbManager dbManager = new DbManager(this.getClient());
			PunchoutOrderMessageBeanFactory pomFactory = new PunchoutOrderMessageBeanFactory(dbManager);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("payloadId",SearchCriterion.EQUALS,cartId);
			searchCriteria.addCriterion("punchout",SearchCriterion.EQUALS,"Y");
			searchCriteria.addCriterion("postedToOracle",SearchCriterion.IS, "null" );
			Collection lineBeans = pomFactory.select(searchCriteria);
			if (lineBeans != null && ! lineBeans.isEmpty()) {
				PunchoutOrderMessageBean punchoutBean = null;
				Iterator iter = lineBeans.iterator();
				if (iter.hasNext()) {
					punchoutBean = (PunchoutOrderMessageBean) iter.next();
				}
				prNum = punchoutBean.getPrNumber();
			}
			dbManager=null;
			pomFactory=null;
			lineBeans=null;
		} catch (BaseException be) {
			log.error("Base Exception in orderCompleted(): " + be);
		}

		return prNum;
	}

	/*
	 * Reset the punchout and payload id records.
	 */
	public void resetPunchout(String cartId) {

		try {
			DbManager dbManager = new DbManager(this.getClient());
			PunchoutOrderMessageBeanFactory pomFactory = new PunchoutOrderMessageBeanFactory(dbManager);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("payloadId",SearchCriterion.EQUALS,cartId);
			searchCriteria.addCriterion("punchout",SearchCriterion.EQUALS,"Y");
			int result = pomFactory.delete(searchCriteria);
			dbManager=null;
			pomFactory=null;
		} catch (BaseException be) {
			log.error("Base Exception in resetPunchout(): " + be);
		}
	}

	//  Set the flag to "Y" on the record(s) we are sending
	public void updatePunchout(String cartId, BigDecimal prNum) {

		try {
			DbManager dbManager = new DbManager(this.getClient());
			PunchoutOrderMessageBeanFactory pomFactory = new PunchoutOrderMessageBeanFactory(dbManager);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("payloadId",SearchCriterion.EQUALS,cartId);
			searchCriteria.addCriterion("punchout",SearchCriterion.EQUALS,"Y");
			searchCriteria.addCriterion("postedToOracle",SearchCriterion.EQUALS, "N" );
			searchCriteria.addCriterion("prNumber",SearchCriterion.EQUALS, prNum.toString());
			Collection lineBeans = pomFactory.select(searchCriteria);
			if (lineBeans != null && ! lineBeans.isEmpty()) {
				PunchoutOrderMessageBean punchoutBean = null;
				Iterator iter = lineBeans.iterator();
				if (iter.hasNext()) {
					punchoutBean = (PunchoutOrderMessageBean) iter.next();
				}
				if (punchoutBean!=null) {
					punchoutBean.setPostedToOracle("Y");
					pomFactory.update(punchoutBean);
				}
			}
			dbManager=null;
			pomFactory=null;
		} catch (BaseException be) {
			log.error("Base Exception in resetPunchout(): " + be);
		}
	}

	/*
	 * Return a Collection of EbpPendFinanceApprovalViewBean(s) for the
	 * give payload id and PR number.
	 */
	public Collection getShopppingCart(String cartId, BigDecimal prNum) {

		Collection cart = null;
		try {
			DbManager dbManager = new DbManager(this.getClient());
			EbpPendFinanceApprovalViewBeanFactory ebpFactory = new EbpPendFinanceApprovalViewBeanFactory(dbManager);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.addCriterion("payloadId",SearchCriterion.EQUALS,cartId);
			searchCriteria.addCriterion("newItemCustField2",SearchCriterion.EQUALS,prNum.toString());
			cart = ebpFactory.select(searchCriteria);
			dbManager=null;
			ebpFactory=null;
		} catch (BaseException be) {
			log.error("BaseException in getShoppingCart(): " + be);
		}

		return cart;
	}

	/*
	 * execute the query
	 */
	private ResultSet doQuery(String query) throws BaseException {
		ResultSet rs = null;
		try {
			DbManager dbManager = new DbManager(this.getClient());
			Connection conn = dbManager.getConnection();
			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(query);
		} catch (DbConnectionException dbce) {
			log.error("Db Connection Exception executing internal order process query: " + dbce);
		} catch (SQLException sqle) {
			log.error("SQL Exception executing internal order process query: " + sqle);
		}

		return rs;
	}

}