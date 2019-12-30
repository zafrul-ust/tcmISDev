package radian.web.servlets.dcx;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DCXBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "DCX";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DCXServerResourceBundle();
   }
}