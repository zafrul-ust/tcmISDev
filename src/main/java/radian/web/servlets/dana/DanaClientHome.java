package radian.web.servlets.dana;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DanaClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Dana";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DanaServerResourceBundle();
   }
}