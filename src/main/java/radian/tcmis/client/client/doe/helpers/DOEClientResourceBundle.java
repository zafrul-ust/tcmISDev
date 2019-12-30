package radian.tcmis.client.client.doe.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class DOEClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.doe.servlets."},
             {"APP_NAME","Department of Energy"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/doe"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.doe.gui.DOECmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","DOE"},
             {"MSDS_URL","/tcmIS/doe/ViewMsds?"},
             {"WASTE_URL","/tcmIS/doe/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/doe/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public DOEClientResourceBundle(){
            super();
            addHash(cHash);
     }

}