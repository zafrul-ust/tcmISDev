package radian.web.servlets.siemens;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SiemensSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Siemenssearchmsds check it is here....");
      return "Siemens";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SiemensServerResourceBundle();
   }
}