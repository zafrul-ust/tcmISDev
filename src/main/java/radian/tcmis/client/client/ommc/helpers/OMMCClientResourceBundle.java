package radian.tcmis.client.client.ommc.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class OMMCClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ommc.servlets."},
             {"APP_NAME","OHIO Module Manufacturing CO, LLC"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ommc"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ommc.gui.OMMCCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","OMMC"},
             {"MSDS_URL","/tcmIS/ommc/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ommc/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ommc/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public OMMCClientResourceBundle(){
            super();
            addHash(cHash);
     }

}