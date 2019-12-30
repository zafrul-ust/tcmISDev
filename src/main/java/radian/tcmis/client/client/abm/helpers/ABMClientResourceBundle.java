package radian.tcmis.client.client.abm.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class ABMClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.abm.servlets."},
             {"APP_NAME","AZULAI BUILDING MATERIAL"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/abm"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.abm.gui.ABMCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","ABM"},
             {"MSDS_URL","/tcmIS/abm/ViewMsds?"},
             {"WASTE_URL","/tcmIS/abm/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/abm/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public ABMClientResourceBundle(){
            super();
            addHash(cHash);
     }

}