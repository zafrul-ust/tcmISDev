package radian.web.servlets.imco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IMCOServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","IMCO"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/imco"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","IMCO"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/imco/Home"},
          {"HOME_IMAGE","/images/imco.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/imco/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/imco/Register"},
          {"HOME_SERVLET","/tcmIS/imco/Home"},
          {"JNLP_MAINCLASS","RadianCCIMCO.jar"},
          {"JNLP_DIRECTORY","/jnlp/imco"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/imco"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.imco.gui.IMCOCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/imco/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/imco/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/imco/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/imco/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/imco/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/imco/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/imco/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/imco/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/imco/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/imco/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/imco/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/imco/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/imco/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/imco/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/imco/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/imco/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/imco/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/imco/cancel?"},
		  {"WASTE_ORDER","/tcmIS/imco/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/imco/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/imco/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/imco/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/imco/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/imco/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/imco/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/imco/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/imco/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public IMCOServerResourceBundle(){
            super();
            addHash(cHash);
     }

}