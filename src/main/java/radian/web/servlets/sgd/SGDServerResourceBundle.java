package radian.web.servlets.sgd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SGDServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","SGD"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/sgd"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","SGD"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/sgd/Home"},
          {"HOME_IMAGE","/images/sgd.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/sgd/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/sgd/Register"},
          {"HOME_SERVLET","/tcmIS/sgd/Home"},
          {"JNLP_MAINCLASS","RadianCCSGD.jar"},
          {"JNLP_DIRECTORY","/jnlp/sgd"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/sgd"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.sgd.gui.SGDCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/sgd/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/sgd/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/sgd/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/sgd/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/sgd/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/sgd/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/sgd/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/sgd/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/sgd/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/sgd/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/sgd/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/sgd/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/sgd/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/sgd/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/sgd/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/sgd/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/sgd/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/sgd/cancel?"},
		  {"WASTE_ORDER","/tcmIS/sgd/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/sgd/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/sgd/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/sgd/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/sgd/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/sgd/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/sgd/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/sgd/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/sgd/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public SGDServerResourceBundle(){
            super();
            addHash(cHash);
     }

}