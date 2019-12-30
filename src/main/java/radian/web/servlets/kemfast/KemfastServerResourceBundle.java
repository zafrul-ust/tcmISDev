package radian.web.servlets.kemfast;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KemfastServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Kemfast"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/kemfast"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Kemfast"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/kemfast/Home"},
          {"HOME_IMAGE","/images/kemfast.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/kemfast/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/kemfast/Register"},
          {"HOME_SERVLET","/tcmIS/kemfast/Home"},
          {"JNLP_MAINCLASS","RadianCCKemfast.jar"},
          {"JNLP_DIRECTORY","/jnlp/kemfast"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/kemfast"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.kemfast.gui.KemfastCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/kemfast/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/kemfast/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/kemfast/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/kemfast/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/kemfast/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/kemfast/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/kemfast/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/kemfast/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/kemfast/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/kemfast/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/kemfast/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/kemfast/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/kemfast/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/kemfast/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/kemfast/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/kemfast/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/kemfast/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/kemfast/cancel?"},
		  {"WASTE_ORDER","/tcmIS/kemfast/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/kemfast/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/kemfast/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/kemfast/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/kemfast/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/kemfast/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/kemfast/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/kemfast/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/kemfast/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public KemfastServerResourceBundle(){
            super();
            addHash(cHash);
     }

}