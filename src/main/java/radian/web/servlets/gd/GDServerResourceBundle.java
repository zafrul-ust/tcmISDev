package radian.web.servlets.gd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","GD"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/gd"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","GD"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/gd/Home"},
          {"HOME_IMAGE","/images/gd.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/gd/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/gd/Register"},
          {"HOME_SERVLET","/tcmIS/gd/Home"},
          {"JNLP_MAINCLASS","RadianCCGD.jar"},
          {"JNLP_DIRECTORY","/jnlp/gd"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/gd"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.gd.gui.GDCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/gd/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/gd/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/gd/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/gd/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/gd/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/gd/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/gd/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/gd/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/gd/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/gd/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/gd/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/gd/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/gd/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/gd/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/gd/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/gd/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/gd/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/gd/cancel?"},
		  {"WASTE_ORDER","/tcmIS/gd/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/gd/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/gd/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/gd/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/gd/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/gd/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/gd/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public GDServerResourceBundle(){
            super();
            addHash(cHash);
     }

}