package radian.web.servlets.siemens;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SiemensClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Siemens";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SiemensServerResourceBundle();
   }
}