package radian.web.servlets.mtl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MTLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","MTL"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/mtl"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","MTL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/mtl/Home"},
          {"HOME_IMAGE","/images/mtl.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/mtl/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/mtl/Register"},
          {"HOME_SERVLET","/tcmIS/mtl/Home"},
          {"JNLP_MAINCLASS","RadianCCMTL.jar"},
          {"JNLP_DIRECTORY","/jnlp/mtl"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/mtl"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.mtl.gui.MTLCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/mtl/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/mtl/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/mtl/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/mtl/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/mtl/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/mtl/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/mtl/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/mtl/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/mtl/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/mtl/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/mtl/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/mtl/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/mtl/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/mtl/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/mtl/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/mtl/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/mtl/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/mtl/cancel?"},
		  {"WASTE_ORDER","/tcmIS/mtl/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/mtl/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/mtl/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/mtl/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/mtl/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/mtl/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/mtl/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/mtl/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/mtl/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public MTLServerResourceBundle(){
            super();
            addHash(cHash);
     }

}