package radian.web.servlets.nrg;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NRGServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","NRG"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/nrg"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","NRG"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/nrg/home.do"},
          {"HOME_IMAGE","/images/nrg.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/nrg/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/nrg/Register"},
          {"HOME_SERVLET","/tcmIS/nrg/home.do"},
          {"JNLP_MAINCLASS","RadianCCNRG.jar"},
          {"JNLP_DIRECTORY","/jnlp/nrg"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/nrg"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.nrg.gui.NRGCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/nrg/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/nrg/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/nrg/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/nrg/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/nrg/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/nrg/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/nrg/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/nrg/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/nrg/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/nrg/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/nrg/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/nrg/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/nrg/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/nrg/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/nrg/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/nrg/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/nrg/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/nrg/cancel?"},
		  {"WASTE_ORDER","/tcmIS/nrg/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/nrg/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/nrg/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/nrg/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/nrg/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/nrg/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/nrg/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/nrg/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/nrg/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}

     };

     public NRGServerResourceBundle(){
            super();
            addHash(cHash);
     }

}