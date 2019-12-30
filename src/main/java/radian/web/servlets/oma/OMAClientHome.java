package radian.web.servlets.oma;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMAClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "OMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMAServerResourceBundle();
   }
}