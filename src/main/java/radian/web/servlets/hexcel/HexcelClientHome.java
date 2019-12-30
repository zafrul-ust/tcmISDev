package radian.web.servlets.hexcel;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HexcelClientHome extends radian.web.servlets.share.ClientHome
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