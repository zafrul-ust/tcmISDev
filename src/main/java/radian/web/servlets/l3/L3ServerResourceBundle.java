package radian.web.servlets.l3;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3ServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","L3"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/l3"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","L3"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/l3/Home"},
          {"HOME_IMAGE","/images/l3.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/l3/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/l3/Register"},
          {"HOME_SERVLET","/tcmIS/l3/Home"},
          {"JNLP_MAINCLASS","RadianCCL3.jar"},
          {"JNLP_DIRECTORY","/jnlp/l3"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/l3"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.l3.gui.L3CmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/l3/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/l3/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/l3/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/l3/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/l3/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/l3/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/l3/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/l3/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/l3/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/l3/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/l3/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/l3/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/l3/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/l3/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/l3/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/l3/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/l3/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/l3/cancel?"},
		  {"WASTE_ORDER","/tcmIS/l3/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/l3/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/l3/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/l3/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/l3/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/l3/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/l3/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public L3ServerResourceBundle(){
            super();
            addHash(cHash);
     }

}