package radian.web.servlets.am;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AMClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "AM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AMServerResourceBundle();
   }
}