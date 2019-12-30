package radian.web.servlets.ford;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FordServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Ford"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/ford"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Ford"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/ford/Home"},
          {"HOME_IMAGE","/images/ford.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/ford/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/ford/Register"},
          {"HOME_SERVLET","/tcmIS/ford/Home"},
          {"JNLP_MAINCLASS","RadianCCFord.jar"},
          {"JNLP_DIRECTORY","/jnlp/ford"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ford"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ford.gui.FordCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ford/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/ford/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/ford/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/ford/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/ford/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/ford/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/ford/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/ford/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/ford/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/ford/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/ford/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/ford/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/ford/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ford/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/ford/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/ford/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/ford/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/ford/cancel?"},
		  {"WASTE_ORDER","/tcmIS/ford/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/ford/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/ford/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/ford/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/ford/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ford/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ford/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public FordServerResourceBundle(){
            super();
            addHash(cHash);
     }

}