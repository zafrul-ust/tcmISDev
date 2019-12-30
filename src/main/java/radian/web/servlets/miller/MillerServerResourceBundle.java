package radian.web.servlets.miller;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MillerServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsMiller"},
          {"DEBUG","false"},
          {"DB_CLIENT","Miller"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},
          {"RELATIVE_HTML","/tcmIS/msds/miller"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Miller"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},
          {"HOME_PAGE","/tcmIS/miller/Home"},
          {"HOME_IMAGE","/images/miller.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},
          {"VIEW_MSDS","/tcmIS/miller/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/miller/Register"},
          {"HOME_SERVLET","/tcmIS/miller/Home"},
          {"JNLP_MAINCLASS","RadianCCMiller.jar"},
          {"JNLP_DIRECTORY","/jnlp/miller"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/miller"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.miller.gui.MillerCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/miller/batchreport?"},
          {"ADDUSER_SERVLET","/tcmIS/miller/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/miller/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/miller/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/miller/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/miller/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/miller/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/miller/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/miller/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/miller/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/miller/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/miller/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},
          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/miller/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/miller/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/miller/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/miller/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/miller/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/miller/cancel?"},
		  {"WASTE_ORDER","/tcmIS/miller/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/miller/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/miller/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/miller/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/miller/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/miller/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/miller/ordertrackingmain.do"},
          {"WEB_REGISTRATION","Yes"}

     };

     public MillerServerResourceBundle(){
            super();
            addHash(cHash);
     }

}