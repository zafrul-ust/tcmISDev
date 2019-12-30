package radian.web.servlets.hexcel;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HexcelServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Hexcel"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/hexcel"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Hexcel"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/hexcel/home.do"},
          {"HOME_IMAGE","/images/hexcel.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/hexcel/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/hexcel/Register"},
          {"HOME_SERVLET","/tcmIS/hexcel/home.do"},
          {"JNLP_MAINCLASS","RadianCCHexcel.jar"},
          {"JNLP_DIRECTORY","/jnlp/hexcel"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/hexcel"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.hexcel.gui.HexcelCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/hexcel/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/hexcel/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/hexcel/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/hexcel/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/hexcel/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/hexcel/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/hexcel/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/hexcel/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/hexcel/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/hexcel/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/hexcel/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/hexcel/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/hexcel/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/hexcel/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/hexcel/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/hexcel/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/hexcel/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/hexcel/cancel?"},
		  {"WASTE_ORDER","/tcmIS/hexcel/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/hexcel/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/hexcel/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/hexcel/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/hexcel/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/hexcel/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/hexcel/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/hexcel/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/hexcel/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public HexcelServerResourceBundle(){
            super();
            addHash(cHash);
     }

}