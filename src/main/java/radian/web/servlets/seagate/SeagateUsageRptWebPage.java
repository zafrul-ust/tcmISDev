package radian.web.servlets.seagate;

import radian.tcmis.server7.share.helpers.*;
import radian.web.servlets.seagate.SeagateServerResourceBundle;

public class SeagateUsageRptWebPage  extends radian.web.servlets.share.UsageRptWebPage {
   public String getClient(){
      return "Seagate";
   }
   protected ServerResourceBundle getBundle(){
      return (ServerResourceBundle) new SeagateServerResourceBundle();
   }
}