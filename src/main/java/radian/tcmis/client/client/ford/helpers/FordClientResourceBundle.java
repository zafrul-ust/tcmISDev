package radian.tcmis.client.client.ford.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class FordClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ford.servlets."},
             {"APP_NAME","Ford"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ford"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ford.gui.FordCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Ford"},
             {"MSDS_URL","/tcmIS/ford/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ford/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ford/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public FordClientResourceBundle(){
            super();
            addHash(cHash);
     }

}