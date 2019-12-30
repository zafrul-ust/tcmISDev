package radian.tcmis.client.client.aeropia.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class AeropiaClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.aeropia.servlets."},
             {"APP_NAME","Aeropia"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/aeropia"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.aeropia.gui.AeropiaCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Aeropia"},
             {"MSDS_URL","/tcmIS/aeropia/ViewMsds?"},
             {"WASTE_URL","/tcmIS/aeropia/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/aeropia/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public AeropiaClientResourceBundle(){
            super();
            addHash(cHash);
     }

}