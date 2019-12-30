package radian.web.servlets.ablaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABLaeroServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","ABLaero"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/ablaero"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","ABLaero"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/ablaero/Home"},
          {"HOME_IMAGE","/images/ablaero.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/ablaero/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/ablaero/Register"},
          {"HOME_SERVLET","/tcmIS/ablaero/Home"},
          {"JNLP_MAINCLASS","RadianCCABLaero.jar"},
          {"JNLP_DIRECTORY","/jnlp/ablaero"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/ablaero"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.ablaero.gui.ABLaeroCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/ablaero/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/ablaero/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/ablaero/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/ablaero/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/ablaero/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/ablaero/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/ablaero/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/ablaero/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/ablaero/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/ablaero/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/ablaero/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/ablaero/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/ablaero/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/ablaero/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/ablaero/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/ablaero/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/ablaero/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/ablaero/cancel?"},
		  {"WASTE_ORDER","/tcmIS/ablaero/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/ablaero/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/ablaero/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/ablaero/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/ablaero/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/ablaero/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/ablaero/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public ABLaeroServerResourceBundle(){
            super();
            addHash(cHash);
     }

}