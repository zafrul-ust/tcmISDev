package radian.web.servlets.doe;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DOEServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","DOE"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/doe"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","DOE"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/doe/Home"},
          {"HOME_IMAGE","/images/doe.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/doe/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/doe/Register"},
          {"HOME_SERVLET","/tcmIS/doe/Home"},
          {"JNLP_MAINCLASS","RadianCCDOE.jar"},
          {"JNLP_DIRECTORY","/jnlp/doe"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/doe"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.doe.gui.DOECmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/doe/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/doe/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/doe/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/doe/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/doe/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/doe/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/doe/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/doe/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/doe/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/doe/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/doe/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/doe/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/doe/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/doe/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/doe/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/doe/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/doe/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/doe/cancel?"},
		  {"WASTE_ORDER","/tcmIS/doe/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/doe/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/doe/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/doe/matrixreport?"},

          {"WORKAREA_MANAGEMENT","Yes"},
          {"WORKAREA_MANAGEMENT_SERVLET","/tcmIS/doe/showuseapprovalstatus.do"},

          {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/doe/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/doe/shipinfo"},
      {"WEB_ORDER_TRACKING","Yes"},
      {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/doe/ordertrackingmain.do"},
      {"WEB_REGISTRATION","Yes"},
      {"WEB_CATALOG","Yes"},
      {"WEB_CATALOG_SERVLET","/tcmIS/doe/catalog.do"}
     };

     public DOEServerResourceBundle(){
            super();
            addHash(cHash);
     }

}