package radian.web.servlets.dcx;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DCXClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "DCX";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DCXServerResourceBundle();
   }
}