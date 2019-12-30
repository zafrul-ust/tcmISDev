package radian.tcmis.client.client.ablaero.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class ABLaeroClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.ablaero.servlets."},
             {"APP_NAME","ABL AEROSPACE"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/ablaero"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.ablaero.gui.ABLaeroCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","ABLaero"},
             {"MSDS_URL","/tcmIS/ablaero/ViewMsds?"},
             {"WASTE_URL","/tcmIS/ablaero/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/ablaero/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public ABLaeroClientResourceBundle(){
            super();
            addHash(cHash);
     }

}