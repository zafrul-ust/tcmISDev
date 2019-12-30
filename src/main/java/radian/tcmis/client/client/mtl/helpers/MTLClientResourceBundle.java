package radian.tcmis.client.client.mtl.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class MTLClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.mtl.servlets."},
             {"APP_NAME","MICRO TZEVA LTD."},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/mtl"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.mtl.gui.MTLCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","MTL"},
             {"MSDS_URL","/tcmIS/mtl/ViewMsds?"},
             {"WASTE_URL","/tcmIS/mtl/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/mtl/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public MTLClientResourceBundle(){
            super();
            addHash(cHash);
     }

}