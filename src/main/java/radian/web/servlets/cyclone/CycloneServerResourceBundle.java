package radian.web.servlets.cyclone;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CycloneServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Cyclone"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/cyclone"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Cyclone"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/cyclone/Home"},
          {"HOME_IMAGE","/images/cyclone.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/cyclone/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/cyclone/Register"},
          {"HOME_SERVLET","/tcmIS/cyclone/Home"},
          {"JNLP_MAINCLASS","RadianCCCyclone.jar"},
          {"JNLP_DIRECTORY","/jnlp/cyclone"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/cyclone"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.cyclone.gui.CycloneCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cyclone/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/cyclone/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/cyclone/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/cyclone/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/cyclone/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/cyclone/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/cyclone/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/cyclone/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/cyclone/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/cyclone/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/cyclone/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/cyclone/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/cyclone/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/cyclone/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/cyclone/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/cyclone/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/cyclone/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/cyclone/cancel?"},
		  {"WASTE_ORDER","/tcmIS/cyclone/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/cyclone/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/cyclone/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/cyclone/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/cyclone/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/cyclone/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/cyclone/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/cyclone/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/cyclone/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public CycloneServerResourceBundle(){
            super();
            addHash(cHash);
     }

}