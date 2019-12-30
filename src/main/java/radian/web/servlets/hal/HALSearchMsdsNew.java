package radian.web.servlets.hal;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HALSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ HALsearchmsds check it is here....");
      return "HAL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HALServerResourceBundle();
   }
}