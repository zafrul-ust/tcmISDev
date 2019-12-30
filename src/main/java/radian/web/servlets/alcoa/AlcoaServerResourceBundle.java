package radian.web.servlets.alcoa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlcoaServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Alcoa"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/alcoa"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Alcoa"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/alcoa/home.do"},
          {"HOME_IMAGE","/images/alcoa.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/alcoa/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/alcoa/Register"},
          {"HOME_SERVLET","/tcmIS/alcoa/home.do"},
          {"JNLP_MAINCLASS","RadianCCAlcoa.jar"},
          {"JNLP_DIRECTORY","/jnlp/alcoa"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/alcoa"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.alcoa.gui.AlcoaCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/alcoa/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/alcoa/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/alcoa/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/alcoa/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/alcoa/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/alcoa/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/alcoa/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/alcoa/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/alcoa/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/alcoa/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/alcoa/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/alcoa/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/alcoa/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/alcoa/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/alcoa/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/alcoa/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/alcoa/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/alcoa/cancel?"},
		  {"WASTE_ORDER","/tcmIS/alcoa/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/alcoa/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/alcoa/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/alcoa/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/alcoa/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/alcoa/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/alcoa/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/alcoa/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/alcoa/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}


     };

     public AlcoaServerResourceBundle(){
            super();
            addHash(cHash);
     }

}