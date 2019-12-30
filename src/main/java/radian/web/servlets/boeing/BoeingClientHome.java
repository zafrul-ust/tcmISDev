package radian.web.servlets.boeing;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Boeing";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BoeingServerResourceBundle();
   }
}