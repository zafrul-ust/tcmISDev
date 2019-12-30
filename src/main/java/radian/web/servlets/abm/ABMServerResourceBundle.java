package radian.web.servlets.abm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ABMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","ABM"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/abm"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","ABM"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/abm/Home"},
          {"HOME_IMAGE","/images/abm.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/abm/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/abm/Register"},
          {"HOME_SERVLET","/tcmIS/abm/Home"},
          {"JNLP_MAINCLASS","RadianCCABM.jar"},
          {"JNLP_DIRECTORY","/jnlp/abm"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/abm"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.abm.gui.ABMCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/abm/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/abm/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/abm/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/abm/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/abm/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/abm/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/abm/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/abm/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/abm/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/abm/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/abm/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/abm/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/abm/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/abm/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/abm/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/abm/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/abm/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/abm/cancel?"},
		  {"WASTE_ORDER","/tcmIS/abm/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/abm/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/abm/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/abm/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/abm/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/abm/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/abm/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/abm/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/abm/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public ABMServerResourceBundle(){
            super();
            addHash(cHash);
     }

}