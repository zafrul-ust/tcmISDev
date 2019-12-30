package radian.web.servlets.siemens;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SiemensBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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