package radian.web.servlets.bl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logs"},
          {"DEBUG","false"},
          {"DB_CLIENT","BL"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/bl"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","BL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/bl/Home"},
          {"HOME_IMAGE","/images/bl.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/bl/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/bl/Register"},
          {"HOME_SERVLET","/tcmIS/bl/Home"},
          {"JNLP_MAINCLASS","RadianCCBL.jar"},
          {"JNLP_DIRECTORY","/jnlp/bl"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/bl"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.bl.gui.BLCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/bl/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/bl/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/bl/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/bl/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/bl/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/bl/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/bl/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/bl/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/bl/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/bl/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/bl/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/bl/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/bl/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/bl/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/bl/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/bl/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/bl/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/bl/cancel?"},
		  {"WASTE_ORDER","/tcmIS/bl/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/bl/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/bl/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/bl/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/bl/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/bl/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/bl/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/bl/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/bl/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public BLServerResourceBundle(){
            super();
            addHash(cHash);
     }

}