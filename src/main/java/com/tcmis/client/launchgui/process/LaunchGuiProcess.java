package com.tcmis.client.launchgui.process;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.launchgui.beans.TcmisPrereleaseVersionBean;
import com.tcmis.client.launchgui.factory.TcmisPrereleaseVersionBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process for TDSF Report
 * @version 1.0
 *****************************************************************************/
public class LaunchGuiProcess
    extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());

  public LaunchGuiProcess(String client) {
    super(client);
  }

  public String getHostUrl() {
    ResourceLibrary resource = new ResourceLibrary("launchgui");
    return resource.getString("hosturl");
  }

  public Collection getCompanyDropDown(int personnelId) throws BaseException {
    Collection companyDropDownCollection = null;
    DbManager dbManager = new DbManager(this.getClient());
    try {
      PersonnelBeanFactory factory = new PersonnelBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("personnelId", SearchCriterion.EQUALS, new Integer(personnelId).toString());
      criteria.addCriterion("customer", SearchCriterion.EQUALS, "Y");
       criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
       criteria.addCriterion("hasTcmisAccess", SearchCriterion.EQUALS, "Y");
      SortCriteria sortCriteria = new SortCriteria();
      sortCriteria.addCriterion("companyName");
      sortCriteria.setSortAscending(true);
      companyDropDownCollection = factory.selectMyCompanyDropDown(criteria,sortCriteria);
    } catch (Exception e) {
      log.error("Base Exception in getCompanyDropDown: " + e);
    } finally {
      dbManager = null;
    }
    return companyDropDownCollection;
  } //end of method

  public String launchGui(PersonnelBean personnelBean, String ecomerce, String payLoadId) throws BaseException {
    String launchFile = "";
    DbManager dbManager = new DbManager(this.getClient());
    try {
      Collection versionColl;
      //first check to see if user is in tcmis_prerelease_version
      TcmisPrereleaseVersionBeanFactory factory = new TcmisPrereleaseVersionBeanFactory(dbManager);
      SearchCriteria criteria = new SearchCriteria();
      criteria.addCriterion("username", SearchCriterion.EQUALS,personnelBean.getLogonId());
      versionColl = factory.select(criteria);
      //if user is not in tcmis_prerelease_version then get version,host and path by company
      if (versionColl.size() == 0) {
        criteria = new SearchCriteria();
        criteria.addCriterion("username", SearchCriterion.EQUALS,"All");
        versionColl = factory.select(criteria);
      }
      //if still no data, then return error message
      if (versionColl.size() > 0) {
        Iterator myIterator = versionColl.iterator();
        TcmisPrereleaseVersionBean tcmisPrereleaseVersionBean = new TcmisPrereleaseVersionBean();
        while (myIterator.hasNext()) {
          tcmisPrereleaseVersionBean = (TcmisPrereleaseVersionBean) myIterator.next();
        }
        launchFile = createLaunchFile(personnelBean,tcmisPrereleaseVersionBean,ecomerce,payLoadId);
      }
    } catch (Exception e) {
      log.error("Base Exception in getManifestDropdown: " + e);
    } finally {
      dbManager = null;
    }

    return launchFile;
  } //end of method

  private String createLaunchFile(PersonnelBean personnelBean,TcmisPrereleaseVersionBean bean,String ecomerce, String payLoadId) {
    String launchFile = "";
    StringBuffer Msg = new StringBuffer();
    try {
      //building jnlp files
      Msg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      Msg.append("<jnlp spec=\"1.0+\" version=\"2.0\" codebase=\"" + bean.getHost()+bean.getPath() + "/\">\n");
      Msg.append("  <information>\n");
      Msg.append("    <title>Updating tcmIS</title>\n");
      Msg.append("    <vendor>Haas TCM</vendor>\n");
      
      ResourceLibrary resource = new ResourceLibrary("launchgui");
      String hostUrl = resource.getString("hosturl");
      
      Msg.append("    <homepage href=\"").append(hostUrl).append("\" />\n");
      Msg.append("    <description kind=\"\">tcmIS is a total chemical Management tool</description>\n");
      Msg.append("    <description kind=\"short\">Use this to manage your chemical needs</description>\n");
      Msg.append("    <description kind=\"one-line\">tcmIS</description>\n");
      Msg.append("    <description kind=\"tooltip\">tcmIS Application</description>\n");
      Msg.append("    <icon kind=\"default\" href=\"images/tcmissquare.gif\" />\n");
      Msg.append("    <icon kind=\"selected\" href=\"images/tcmissquare.gif\" />\n");
      Msg.append("    <icon kind=\"disabled\" href=\"images/tcmissquare.gif\" />\n");
      Msg.append("    <icon kind=\"rollover\" href=\"images/tcmissquare.gif\" width=\"75\" height=\"25\" />\n");
      Msg.append("  </information>\n");
      Msg.append("<security>\n");
      Msg.append("      <all-permissions/>\n");
      Msg.append("</security>\n");
      Msg.append("  <resources>\n");
      Msg.append("    <j2se version=\"" + bean.getVersion() + "\" max-heap-size=\"128m\"/>\n");
      Msg.append("    <jar href=\"" + bean.getJarFilename() + "\" main=\"true\" download=\"eager\" />\n");
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
      Msg.append("    <jar href=\"dx.jar\" main=\"false\" download=\"eager\"/>\n");
      Msg.append("    <jar href=\"jsse.jar\" main=\"false\" download=\"eager\"/>\n");
      Msg.append("    <jar href=\"jnet.jar\" main=\"false\" download=\"eager\"/>\n");
      Msg.append("    <jar href=\"jcert.jar\" main=\"false\" download=\"eager\"/>\n");
      if ("CAL".equalsIgnoreCase(this.getClient())) {
        Msg.append("    <jar href=\"tcmis.jar\" main=\"false\" download=\"eager\"/>\n");
      }
      Msg.append("  </resources>\n");
      Msg.append("  <application-desc main-class=\"" + bean.getClassPath() + "\">\n");
      Msg.append(" <argument>" + personnelBean.getLogonId() + "</argument>\n");
      if (this.getClient().equalsIgnoreCase("SEAGATE"))
      {
        Msg.append(" <argument>" + personnelBean.getPassword() + "</argument>\n");
      }
      else
      {
       Msg.append(" <argument>" + personnelBean.getClearTextPassword() + "</argument>\n");
      }
      Msg.append(" <argument>" + ecomerce + "</argument>\n");
      Msg.append(" <argument>" + payLoadId + "</argument>\n");
      if ("TEST".equalsIgnoreCase(bean.getSourceDatabaseProfile())) {
        Msg.append(" <argument>1</argument>\n");    //test
      }else {
        Msg.append(" <argument>2</argument>\n");    //production
      }
      Msg.append("</application-desc>\n");
      Msg.append("</jnlp>\n");
      //execute jnlp file
      try {
        Random rand = new Random();
        String fileName = personnelBean.getLogonId()+rand.nextInt()+rand.nextInt();
        String serverPath = resource.getString("server.path");
        String urlPath = resource.getString("urlpath");

        PrintStream ps = new PrintStream(new FileOutputStream(serverPath + fileName + ".jnlp"), true);
        byte outbuf[];
        outbuf = Msg.toString().getBytes();
        ps.write(outbuf);
        ps.close();
        launchFile = hostUrl+urlPath+fileName+".jnlp";
      } catch (IOException writee) {
        writee.printStackTrace();
      }
    }catch (Exception e) {
      e.printStackTrace();
      launchFile = "";
    }
    return launchFile;
  } //end of method
} //end of class