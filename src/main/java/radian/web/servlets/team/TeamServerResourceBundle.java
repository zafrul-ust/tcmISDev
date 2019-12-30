package radian.web.servlets.team;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TeamServerResourceBundle  extends ServerResourceBundle {

     static final Object[][] cHash = {
          {"LOG_DIR","/logsUTC"},
          {"DEBUG","false"},
          {"DB_CLIENT","Team"},
          {"ORACLE_DRIVER",""},
          {"ORACLE_URL",""},
          {"DB_USER",""},

          {"RELATIVE_HTML","/tcmIS/msds/team"},
          {"DB_PASS",""},
          {"DB_CLIENT_NAME","Team"},
          {"WEBS_HOME_WWWS",""},
          {"WEBS_HOME_WWW",""},

          {"HOME_PAGE","/tcmIS/team/home.do"},
          {"HOME_IMAGE","/images/team.gif"},
          {"HELP_HTML_PATH","/tcmIS/help/new/"},

          {"VIEW_MSDS","/tcmIS/team/ViewMsds?"},
          {"REGISTER_SERVLET","/tcmIS/team/Register"},
          {"HOME_SERVLET","/tcmIS/team/home.do"},
          {"JNLP_MAINCLASS","RadianCCTeam.jar"},
          {"JNLP_DIRECTORY","/jnlp/team"},
          {"TEST_JNLP_DIRECTORY","/prereleasejnlp/team"},
          {"TCMIS_START_CLASS","radian.tcmis.client.client.team.gui.TeamCmisApp"},
          {"BATCH_REPORT_SERVLET","/tcmIS/team/batchreport?"},

          {"ADDUSER_SERVLET","/tcmIS/team/Register?useraction=AddUser"},
          {"CHGUSER_SERVLET","/tcmIS/team/Register?useraction=ChgUser"},
          {"ADD_SERVLET","/tcmIS/team/Register?useraction=Add"},
          {"LOGINREG_SERVLET","/tcmIS/team/Register?useraction=Login"},
          {"CHANGE_SERVLET","/tcmIS/team/Register?useraction=Change"},
          {"REGISTER_HOME_SERVLET","/tcmIS/team/Register?"},
          {"TEST_INSTALL_SERVLET","/tcmIS/team/Register?useraction=startingtcmis"},
          {"BAR_BUILD_SERVLET","/tcmIS/team/Msds?limit=yes"},
          {"BUILD_SERVLET","/tcmIS/team/Msds?"},
          {"SERVLET_DIR_WWW",""},
          {"ERW_TEMPLATE_DIR","/webdata/html/template/"},
          {"BASE_DIR_TCM_WWW","/webdata/html/"},
          {"ERW_REPORT_DIR_ACTIVE","/webdata/html/reports/temp/"},
          {"ERW_REPORT_DIR_BATCH","/webdata/html/reports/"},
          {"WEBS_HOME_WWW_ERW_ACTIVE","/reports/temp/"},
          {"WEBS_HOME_WWW_ERW_BATCH","/reports/"},
          {"CATALOGING_SERVLET","/tcmIS/team/cataloging?"},
          {"PRINT_SCREEN","/tcmIS/team/printscreen"},
          {"HELP_SERVLET_PATH","/tcmIS/client/help"},

          {"DROPRECEIVING","No"},
          {"RELABELING","No"},
          {"DROPRECEIVING_SERVLET","/tcmIS/team/dropshipreceivingmain.do"},
          {"DROPRECEIVING_SERVLET_HEADER","/tcmIS/team/dropshipreceivingmain.do"},
          {"RELABELING_SERVLET","/tcmIS/team/Relabeling?session=Active"},

		  {"COST_REPORT","/tcmIS/team/betacostreport?"},
		  {"SCHEDULDE_DELIVERY","/tcmIS/team/schdelivery?"},
		  {"CANCEL_MR","/tcmIS/team/cancel?"},
		  {"WASTE_ORDER","/tcmIS/team/wasteorder?"},
		  {"WASTE_ADHOC_REPORT","/tcmIS/team/wastereport?"},
		  {"USAGE_ADHOC_REPORT","/tcmIS/team/usagereport?"},
		  {"MATERIAL_MATRIX_REPORT","/tcmIS/team/matrixreport?"},

		  {"SHIPPINGINFO","No"},
		  {"SHIPPINGINFO_SERVLET","/tcmIS/team/shipinfo?session=Active"},
		  {"SHIPPINGINFO_SERVLET_HEADER","/tcmIS/team/shipinfo"},

                  {"WEB_ORDER_TRACKING","Yes"},
                  {"WEB_ORDER_TRACKING_SERVLET","/tcmIS/team/ordertrackingmain.do"},
                  {"WEB_INVENTORY","Yes"},
                  {"WEB_INVENTORY_SERVLET","/tcmIS/team/inventorymain.do"},
                  {"WEB_CATALOG","Yes"},
                  {"WEB_CATALOG_SERVLET","/tcmIS/team/catalog.do"},

           {"WEB_REGISTRATION","Yes"},
			{"NEW_WEB_APPLICATION","Yes"}

	  };

     public TeamServerResourceBundle(){
            super();
            addHash(cHash);
     }

}