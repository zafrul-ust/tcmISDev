package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.*;

public class SWAClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "SWA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }
}