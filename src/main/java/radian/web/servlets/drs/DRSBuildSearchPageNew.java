package radian.web.servlets.drs;

import radian.tcmis.server7.share.helpers.*;

public class DRSBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "DRS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DRSServerResourceBundle();
   }
}