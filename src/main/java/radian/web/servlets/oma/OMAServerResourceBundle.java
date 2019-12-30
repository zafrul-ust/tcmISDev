package radian.web.servlets.oma;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMAServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","OMA"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/oma"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","OMA"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/oma/Home"},
          {"HOME_IMAGE","/images/oma.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/oma/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/oma/Register"},
          {"HOME_SERVLET","/tcmIS/oma/Home"},
          {"JNLP_MAINCLASS","RadianCCOMA.jar"},
          {"JNLP_DIRECTORY","/jnlp/oma"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/oma"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.oma.gui.OMACmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/oma/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/oma/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/oma/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/oma/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/oma/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/oma/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/oma/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/oma/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/oma/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/oma/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/oma/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/oma/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/oma/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/oma/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/oma/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/oma/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/oma/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/oma/cancel?"},
		  {"WASTE_ORDER","/tcmIS/oma/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/oma/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/oma/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/oma/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/oma/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/oma/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/oma/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/oma/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/oma/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public OMAServerResourceBundle(){
            super();
            addHash(cHash);
     }

}