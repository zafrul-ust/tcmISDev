package radian.tcmis.client.client.utc.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class UTCClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.utc.servlets."},
             {"APP_NAME","United Technologies"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/utc"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.utc.gui.UTCCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","UTC"},
             {"MSDS_URL","/tcmIS/utc/ViewMsds?"},
             {"WASTE_URL","/tcmIS/utc/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/utc/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public UTCClientResourceBundle(){
            super();
            addHash(cHash);
     }

}