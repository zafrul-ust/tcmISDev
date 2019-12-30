package radian.web.servlets.zwl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZWLServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","ZWL"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/zwl"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","ZWL"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/zwl/home.do"},
          {"HOME_IMAGE","/images/zwl.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/zwl/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/zwl/Register"},
          {"HOME_SERVLET","/tcmIS/zwl/home.do"},
          {"JNLP_MAINCLASS","RadianCCZWL.jar"},
          {"JNLP_DIRECTORY","/jnlp/zwl"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/zwl"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.zwl.gui.ZWLCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/zwl/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/zwl/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/zwl/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/zwl/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/zwl/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/zwl/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/zwl/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/zwl/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/zwl/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/zwl/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/zwl/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/zwl/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/zwl/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/zwl/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/zwl/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/zwl/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/zwl/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/zwl/cancel?"},
		  {"WASTE_ORDER","/tcmIS/zwl/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/zwl/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/zwl/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/zwl/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/zwl/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/zwl/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/zwl/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/zwl/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/zwl/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}

     };

     public ZWLServerResourceBundle(){
            super();
            addHash(cHash);
     }

}