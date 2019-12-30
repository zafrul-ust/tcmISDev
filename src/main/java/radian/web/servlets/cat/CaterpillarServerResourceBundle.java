package radian.web.servlets.cat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CaterpillarServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Caterpillar"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/cat"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Caterpillar"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/cat/Home"},
          {"HOME_IMAGE","/images/cat.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/cat/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/cat/Register"},
          {"HOME_SERVLET","/tcmIS/cat/Home"},
          {"JNLP_MAINCLASS","RadianCCCaterpillar.jar"},
          {"JNLP_DIRECTORY","/jnlp/cat"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/cat"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.cat.gui.CaterpillarCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cat/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/cat/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/cat/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/cat/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/cat/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/cat/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/cat/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/cat/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/cat/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/cat/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/cat/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/cat/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/cat/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/cat/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/cat/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/cat/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/cat/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/cat/cancel?"},
		  {"WASTE_ORDER","/tcmIS/cat/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/cat/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/cat/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/cat/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/cat/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/cat/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/cat/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/cat/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/cat/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public CaterpillarServerResourceBundle(){
            super();
            addHash(cHash);
     }

}