package radian.web.servlets.algat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlgatServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Algat"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/algat"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Algat"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/algat/Home"},
          {"HOME_IMAGE","/images/algat.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/algat/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/algat/Register"},
          {"HOME_SERVLET","/tcmIS/algat/Home"},
          {"JNLP_MAINCLASS","RadianCCAlgat.jar"},
          {"JNLP_DIRECTORY","/jnlp/algat"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/algat"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.algat.gui.AlgatCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/algat/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/algat/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/algat/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/algat/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/algat/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/algat/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/algat/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/algat/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/algat/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/algat/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/algat/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/algat/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/algat/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/algat/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/algat/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/algat/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/algat/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/algat/cancel?"},
		  {"WASTE_ORDER","/tcmIS/algat/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/algat/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/algat/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/algat/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/algat/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/algat/shipinfo"},


                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/algat/ordertrackingmain.do"},

                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/algat/inventorymain.do"},

                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/algat/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public AlgatServerResourceBundle(){
            super();
            addHash(cHash);
     }

}