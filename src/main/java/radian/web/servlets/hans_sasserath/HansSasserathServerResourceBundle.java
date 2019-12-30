package radian.web.servlets.hans_sasserath;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HansSasserathServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Hans_Sasserath"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/hanssasserath"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","HansSasserath"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/hanssasserath/home.do"},
          {"HOME_IMAGE","/images/hanssasserath.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/hanssasserath/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/hanssasserath/Register"},
          {"HOME_SERVLET","/tcmIS/hanssasserath/home.do"},
          {"JNLP_MAINCLASS","RadianCCHansSasserath.jar"},
          {"JNLP_DIRECTORY","/jnlp/hanssasserath"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/hanssasserath"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.hans_sasserath.gui.HansSasserathCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hanssasserath/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/hanssasserath/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/hanssasserath/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/hanssasserath/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/hanssasserath/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/hanssasserath/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/hanssasserath/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/hanssasserath/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/hanssasserath/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/hanssasserath/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/hanssasserath/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/hanssasserath/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/hanssasserath/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/hanssasserath/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/hanssasserath/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/hanssasserath/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/hanssasserath/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/hanssasserath/cancel?"},
		  {"WASTE_ORDER","/tcmIS/hanssasserath/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/hanssasserath/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/hanssasserath/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/hanssasserath/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/hanssasserath/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/hanssasserath/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/hanssasserath/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/hanssasserath/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/hanssasserath/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}


     };

     public HansSasserathServerResourceBundle(){
            super();
            addHash(cHash);
     }

}