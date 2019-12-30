package radian.web.servlets.cmm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CMMClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "CMM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CMMServerResourceBundle();
   }
}