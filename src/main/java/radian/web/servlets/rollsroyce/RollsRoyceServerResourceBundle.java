package radian.web.servlets.rollsroyce;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RollsRoyceServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","ROLLS_ROYCE"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/rollsroyce"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","ROLLS_ROYCE"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/rollsroyce/home.do"},
          {"HOME_IMAGE","/images/rollsroyce.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/rollsroyce/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/rollsroyce/Register"},
          {"HOME_SERVLET","/tcmIS/rollsroyce/home.do"},
          {"JNLP_MAINCLASS","RadianCCRollsRoyce.jar"},
          {"JNLP_DIRECTORY","/jnlp/rollsroyce"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/rollsroyce"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.rollsroyce.gui.RollsRoyceCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/rollsroyce/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/rollsroyce/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/rollsroyce/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/rollsroyce/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/rollsroyce/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/rollsroyce/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/rollsroyce/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/rollsroyce/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/rollsroyce/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/rollsroyce/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/rollsroyce/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/rollsroyce/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","Yes"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/rollsroyce/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/rollsroyce/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/rollsroyce/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/rollsroyce/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/rollsroyce/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/rollsroyce/cancel?"},
		  {"WASTE_ORDER","/tcmIS/rollsroyce/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/rollsroyce/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/rollsroyce/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/rollsroyce/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/rollsroyce/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/rollsroyce/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/rollsroyce/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/rollsroyce/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/rollsroyce/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}

     };

     public RollsRoyceServerResourceBundle(){
            super();
            addHash(cHash);
     }

}