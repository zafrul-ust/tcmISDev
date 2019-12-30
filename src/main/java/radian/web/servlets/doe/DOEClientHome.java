package radian.web.servlets.doe;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DOEClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "DOE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DOEServerResourceBundle();
   }
}