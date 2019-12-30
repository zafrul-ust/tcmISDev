package radian.tcmis.client.client.getrag.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class GetragClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.getrag.servlets."},
             {"APP_NAME","Getrag Getriebe und Zahnradfbrik Hermann Hagenmeyer GmbH"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/getrag"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.getrag.gui.GetragCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Getrag"},
             {"MSDS_URL","/tcmIS/getrag/ViewMsds?"},
             {"WASTE_URL","/tcmIS/getrag/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/getrag/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public GetragClientResourceBundle(){
            super();
            addHash(cHash);
     }

}