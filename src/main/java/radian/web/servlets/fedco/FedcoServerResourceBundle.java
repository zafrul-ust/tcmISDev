package radian.web.servlets.fedco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FedcoServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Fedco"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/fedco"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Fedco"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/fedco/Home"},
          {"HOME_IMAGE","/images/fedco.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/fedco/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/fedco/Register"},
          {"HOME_SERVLET","/tcmIS/fedco/Home"},
          {"JNLP_MAINCLASS","RadianCCFedco.jar"},
          {"JNLP_DIRECTORY","/jnlp/fedco"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/fedco"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.fedco.gui.FedcoCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/fedco/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/fedco/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/fedco/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/fedco/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/fedco/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/fedco/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/fedco/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/fedco/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/fedco/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/fedco/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/fedco/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/fedco/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/fedco/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/fedco/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/fedco/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/fedco/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/fedco/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/fedco/cancel?"},
		  {"WASTE_ORDER","/tcmIS/fedco/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/fedco/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/fedco/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/fedco/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/fedco/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/fedco/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/fedco/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/fedco/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/fedco/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public FedcoServerResourceBundle(){
            super();
            addHash(cHash);
     }

}