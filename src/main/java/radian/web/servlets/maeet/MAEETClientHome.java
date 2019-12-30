package radian.web.servlets.maeet;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MAEETClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "MAEET";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MAEETServerResourceBundle();
   }
}