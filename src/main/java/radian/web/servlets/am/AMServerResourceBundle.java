package radian.web.servlets.am;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logs"},
          {"DEBUG","false"},
          {"DB_CLIENT","AM"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/am"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","AM"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/am/Home"},
          {"HOME_IMAGE","/images/am.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/am/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/am/Register"},
          {"HOME_SERVLET","/tcmIS/am/Home"},
          {"JNLP_MAINCLASS","RadianCCAM.jar"},
          {"JNLP_DIRECTORY","/jnlp/am"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/am"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.am.gui.AMCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/am/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/am/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/am/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/am/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/am/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/am/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/am/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/am/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/am/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/am/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/am/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/am/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/am/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/am/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/am/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/am/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/am/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/am/cancel?"},
		  {"WASTE_ORDER","/tcmIS/am/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/am/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/am/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/am/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/am/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/am/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/am/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public AMServerResourceBundle(){
            super();
            addHash(cHash);
     }

}