package radian.tcmis.client.client.cat.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class CaterpillarClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.cat.servlets."},
             {"APP_NAME","Caterpillar"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/cat"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.cat.gui.CaterpillarCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","Caterpillar"},
             {"MSDS_URL","/tcmIS/cat/ViewMsds?"},
             {"WASTE_URL","/tcmIS/cat/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/cat/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public CaterpillarClientResourceBundle(){
            super();
            addHash(cHash);
     }

}