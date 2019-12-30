package radian.tcmis.client.client.ge.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GEClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ge.servlets."},
             {"APP_NAME","GE"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ge"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ge.gui.GECmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","GE"},
             {"MSDS_URL","/tcmIS/ge/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ge/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ge/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public GEClientResourceBundle(){
            super();
            addHash(cHash);
     }

}