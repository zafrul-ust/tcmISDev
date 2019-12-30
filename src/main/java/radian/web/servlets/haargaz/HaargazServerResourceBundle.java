package radian.web.servlets.haargaz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HaargazServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Haargaz"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/haargaz"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Haargaz"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/haargaz/Home"},
          {"HOME_IMAGE","/images/haargaz.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/haargaz/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/haargaz/Register"},
          {"HOME_SERVLET","/tcmIS/haargaz/Home"},
          {"JNLP_MAINCLASS","RadianCCHaargaz.jar"},
          {"JNLP_DIRECTORY","/jnlp/haargaz"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/haargaz"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.haargaz.gui.HaargazCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/haargaz/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/haargaz/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/haargaz/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/haargaz/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/haargaz/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/haargaz/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/haargaz/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/haargaz/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/haargaz/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/haargaz/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/haargaz/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/haargaz/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/haargaz/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/haargaz/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/haargaz/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/haargaz/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/haargaz/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/haargaz/cancel?"},
		  {"WASTE_ORDER","/tcmIS/haargaz/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/haargaz/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/haargaz/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/haargaz/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/haargaz/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/haargaz/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/haargaz/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/haargaz/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/haargaz/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public HaargazServerResourceBundle(){
            super();
            addHash(cHash);
     }

}