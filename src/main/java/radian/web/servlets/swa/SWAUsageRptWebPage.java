package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.*;
import radian.web.servlets.swa.SWAServerResourceBundle;

public class SWAUsageRptWebPage  extends radian.web.servlets.share.UsageRptWebPage {
   public String getClient(){
      return "SWA";
   }
   protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }
}