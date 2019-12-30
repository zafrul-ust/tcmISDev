package radian.web.servlets.seagate;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SeagateServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsSeagate"},
          {"DEBUG","false"},
          {"DB_CLIENT","Seagate"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/seagate"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Seagate"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/seagate/Home"},
          {"HOME_IMAGE","/images/seagate.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/seagate/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/seagate/Register"},
          {"HOME_SERVLET","/tcmIS/seagate/Home"},
          {"JNLP_MAINCLASS","RadianCCSeagate.jar"},
          {"JNLP_DIRECTORY","/jnlp/seagate"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/seagate"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.seagate.gui.SeagateCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/seagate/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/seagate/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/seagate/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/seagate/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/seagate/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/seagate/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/seagate/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/seagate/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/seagate/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/seagate/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/seagate/cataloging?"},
          {"CATALOG_SOURCING_SERVLET","/tcmIS/seagate/sourcing?"},
          {"LABELING_SERVLET","/tcmIS/seagate/labelinfo?"},
          {"PRINT_SCREEN","/tcmIS/seagate/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/seagate/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/seagate/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/seagate/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/seagate/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/seagate/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/seagate/cancel?"},
		  {"WASTE_ORDER","/tcmIS/seagate/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/seagate/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/seagate/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/seagate/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/seagate/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/seagate/shipinfo"},
                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/seagate/ordertrackingmain.do"},
           {"WEB_REGISTRATION","Yes"}

     };

     public SeagateServerResourceBundle(){
            super();
            addHash(cHash);
     }

}