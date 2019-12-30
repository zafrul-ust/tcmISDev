package radian.tcmis.client.client.dcx.helpers;

import radian.tcmis.client.share.helpers.ClientResourceBundle;

public class DCXClientResourceBundle  extends ClientResourceBundle {

     static final Object[][] cHash = {
             {"SERVLET_PACKAGE","radian.tcmis.server7.client.dcx.servlets."},
             {"APP_NAME","DaimlerChrysler"},
             {"SHOW_LOGON_FRAME","No"},        //10-29-01 whether to show the logon frame or not
             {"USE_GLOBAL_PASSWORD","No"},
             {"GLOBAL_PASSWORD","t"},
             {"LOG_DIR","/temp/logs/dcx"},
             {"VERSION","1.8.05"},
             {"DOWNLOAD_DIR",""},
             {"EXEC_FILE","tcmISRun.exe"},
             {"CONFIG_FILE","tcmis.cfg"},
             {"DEBUG_FILE","cmisapp.deb"},
             {"START_CLASS","radian.tcmis.client.client.dcx.gui.DCXCmisApp"},
             {"DEBUG","false"},
             {"CLIENT_INITIALS","DCX"},
             {"MSDS_URL","/tcmIS/dcx/ViewMsds?"},
             {"WASTE_URL","/tcmIS/dcx/wasteprofile?"},
             {"PRINT_SCREEN_URL","/tcmIS/dcx/printscreen?"},
             {"WORK_AREA_NAME","Work Area"},
             {"WORK_AREA_NAME_PLURAL","Work Areas"}
     };

     public DCXClientResourceBundle(){
            super();
            addHash(cHash);
     }

}