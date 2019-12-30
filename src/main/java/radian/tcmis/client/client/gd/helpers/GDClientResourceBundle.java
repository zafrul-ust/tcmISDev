package radian.tcmis.client.client.gd.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GDClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.gd.servlets."},
             {"APP_NAME","General Dynamics"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/gd"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.gd.gui.GDCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","GD"},
             {"MSDS_URL","/tcmIS/gd/ViewMsds?"},
             {"WASTE_URL","/tcmIS/gd/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/gd/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public GDClientResourceBundle(){
            super();
            addHash(cHash);
     }

}