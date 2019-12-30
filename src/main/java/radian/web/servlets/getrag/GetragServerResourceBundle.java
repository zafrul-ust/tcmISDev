package radian.web.servlets.getrag;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GetragServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Getrag"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/getrag"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Getrag"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/getrag/home.do"},
          {"HOME_IMAGE","/images/getrag.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/getrag/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/getrag/Register"},
          {"HOME_SERVLET","/tcmIS/getrag/home.do"},
          {"JNLP_MAINCLASS","RadianCCGetrag.jar"},
          {"JNLP_DIRECTORY","/jnlp/getrag"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/getrag"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.getrag.gui.GetragCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/getrag/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/getrag/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/getrag/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/getrag/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/getrag/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/getrag/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/getrag/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/getrag/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/getrag/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/getrag/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/getrag/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/getrag/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/getrag/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/getrag/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/getrag/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/getrag/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/getrag/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/getrag/cancel?"},
		  {"WASTE_ORDER","/tcmIS/getrag/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/getrag/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/getrag/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/getrag/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/getrag/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/getrag/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/getrag/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/getrag/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/getrag/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}


     };

     public GetragServerResourceBundle(){
            super();
            addHash(cHash);
     }

}