package radian.tcmis.client.client.baz.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class BAZClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.baz.servlets."},
             {"APP_NAME","BAZ LTD"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/baz"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.baz.gui.BAZCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","BAZ"},
             {"MSDS_URL","/tcmIS/baz/ViewMsds?"},
             {"WASTE_URL","/tcmIS/baz/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/baz/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public BAZClientResourceBundle(){
            super();
            addHash(cHash);
     }

}