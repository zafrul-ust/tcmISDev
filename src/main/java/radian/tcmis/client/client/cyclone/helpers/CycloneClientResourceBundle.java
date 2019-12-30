package radian.tcmis.client.client.cyclone.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class CycloneClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.cyclone.servlets."},
             {"APP_NAME","CYCLONE AVIATION PRODUCTS LTD."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/cyclone"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.cyclone.gui.CycloneCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Cyclone"},
             {"MSDS_URL","/tcmIS/cyclone/ViewMsds?"},
             {"WASTE_URL","/tcmIS/cyclone/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/cyclone/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public CycloneClientResourceBundle(){
            super();
            addHash(cHash);
     }

}