package radian.web.servlets.siemens;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SiemensServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Siemens"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/siemens"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Siemens"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/siemens/home.do"},
          {"HOME_IMAGE","/images/siemens.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/siemens/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/siemens/Register"},
          {"HOME_SERVLET","/tcmIS/siemens/home.do"},
          {"JNLP_MAINCLASS","RadianCCSiemens.jar"},
          {"JNLP_DIRECTORY","/jnlp/siemens"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/siemens"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.siemens.gui.SiemensCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/siemens/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/siemens/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/siemens/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/siemens/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/siemens/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/siemens/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/siemens/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/siemens/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/siemens/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/siemens/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/siemens/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/siemens/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/siemens/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/siemens/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/siemens/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/siemens/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/siemens/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/siemens/cancel?"},
		  {"WASTE_ORDER","/tcmIS/siemens/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/siemens/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/siemens/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/siemens/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/siemens/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/siemens/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/siemens/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/siemens/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/siemens/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
         {"NEW_WEB_APPLICATION","Yes"}

     };

     public SiemensServerResourceBundle(){
            super();
            addHash(cHash);
     }

}