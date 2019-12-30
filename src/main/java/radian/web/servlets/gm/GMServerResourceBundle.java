package radian.web.servlets.gm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","GM"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/gm"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","GM"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/gm/Home"},
          {"HOME_IMAGE","/images/gm.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/gm/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/gm/Register"},
          {"HOME_SERVLET","/tcmIS/gm/Home"},
          {"JNLP_MAINCLASS","RadianCCGM.jar"},
          {"JNLP_DIRECTORY","/jnlp/gm"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/gm"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.gm.gui.GMCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gm/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/gm/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/gm/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/gm/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/gm/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/gm/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/gm/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/gm/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/gm/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/gm/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/gm/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/gm/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/gm/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/gm/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/gm/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/gm/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/gm/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/gm/cancel?"},
		  {"WASTE_ORDER","/tcmIS/gm/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/gm/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/gm/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/gm/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/gm/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/gm/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/gm/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public GMServerResourceBundle(){
            super();
            addHash(cHash);
     }

}