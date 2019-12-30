package radian.tcmis.client.client.pepsi.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class PepsiClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.pepsi.servlets."},
             {"APP_NAME","PEPSI"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/pepsi"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.pepsi.gui.PepsiCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Pepsi"},
             {"MSDS_URL","/tcmIS/pepsi/ViewMsds?"},
             {"WASTE_URL","/tcmIS/pepsi/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/pepsi/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public PepsiClientResourceBundle(){
            super();
            addHash(cHash);
     }

}