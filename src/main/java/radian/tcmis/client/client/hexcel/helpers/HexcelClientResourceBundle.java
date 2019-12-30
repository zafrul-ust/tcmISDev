package radian.tcmis.client.client.hexcel.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class HexcelClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.hexcel.servlets."},
             {"APP_NAME","Hexcel Corporation"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/hexcel"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.hexcel.gui.HexcelCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Hexcel"},
             {"MSDS_URL","/tcmIS/hexcel/ViewMsds?"},
             {"WASTE_URL","/tcmIS/hexcel/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/hexcel/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public HexcelClientResourceBundle(){
            super();
            addHash(cHash);
     }

}