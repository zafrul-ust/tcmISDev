package radian.web.servlets.hexcel;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HexcelBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Hexcel";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HexcelServerResourceBundle();
   }
}