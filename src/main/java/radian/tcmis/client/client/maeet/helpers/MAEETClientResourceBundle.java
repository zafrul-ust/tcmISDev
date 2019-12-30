package radian.tcmis.client.client.maeet.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class MAEETClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.maeet.servlets."},
             {"APP_NAME","Maeet2 S.r.l."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/maeet"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.maeet.gui.MAEETCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","MAEET"},
             {"MSDS_URL","/tcmIS/maeet/ViewMsds?"},
             {"WASTE_URL","/tcmIS/maeet/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/maeet/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public MAEETClientResourceBundle(){
            super();
            addHash(cHash);
     }

}