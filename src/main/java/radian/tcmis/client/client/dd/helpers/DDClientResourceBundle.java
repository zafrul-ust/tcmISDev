package radian.tcmis.client.client.dd.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class DDClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.dd.servlets."},
             {"APP_NAME","Detroit Diesel"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/dd"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.dd.gui.DDCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","DD"},
             {"MSDS_URL","/tcmIS/dd/ViewMsds?"},
             {"WASTE_URL","/tcmIS/dd/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/dd/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public DDClientResourceBundle(){
            super();
            addHash(cHash);
     }

}