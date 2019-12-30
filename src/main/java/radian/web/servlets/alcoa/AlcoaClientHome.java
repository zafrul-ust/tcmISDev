package radian.web.servlets.alcoa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlcoaClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Alcoa";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlcoaServerResourceBundle();
   }
}