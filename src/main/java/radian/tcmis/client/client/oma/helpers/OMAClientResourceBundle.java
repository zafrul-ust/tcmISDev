package radian.tcmis.client.client.oma.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class OMAClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.oma.servlets."},
             {"APP_NAME","OMA"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/oma"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.oma.gui.OMACmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","OMA"},
             {"MSDS_URL","/tcmIS/oma/ViewMsds?"},
             {"WASTE_URL","/tcmIS/oma/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/oma/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public OMAClientResourceBundle(){
            super();
            addHash(cHash);
     }

}