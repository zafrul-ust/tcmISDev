package radian.web.servlets.timken;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class TimkenClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Timken";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new TimkenServerResourceBundle();
   }
}