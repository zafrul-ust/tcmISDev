package radian.web.servlets.cmm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CMMServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","CMM"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/cmm"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","CMM"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/cmm/Home"},
          {"HOME_IMAGE","/images/cmm.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/cmm/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/cmm/Register"},
          {"HOME_SERVLET","/tcmIS/cmm/Home"},
          {"JNLP_MAINCLASS","RadianCCCMM.jar"},
          {"JNLP_DIRECTORY","/jnlp/cmm"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/cmm"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.cmm.gui.CMMCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/cmm/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/cmm/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/cmm/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/cmm/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/cmm/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/cmm/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/cmm/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/cmm/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/cmm/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/cmm/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/cmm/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/cmm/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/cmm/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/cmm/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/cmm/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/cmm/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/cmm/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/cmm/cancel?"},
		  {"WASTE_ORDER","/tcmIS/cmm/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/cmm/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/cmm/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/cmm/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/cmm/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/cmm/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/cmm/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/cmm/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/cmm/catalog.do"},

           {"WEB_REGISTRATION","Yes"}

     };

     public CMMServerResourceBundle(){
            super();
            addHash(cHash);
     }

}