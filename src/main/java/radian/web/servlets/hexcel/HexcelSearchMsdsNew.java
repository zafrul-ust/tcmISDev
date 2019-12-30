package radian.web.servlets.hexcel;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class HexcelSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Hexcelsearchmsds check it is here....");
      return "Hexcel";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new HexcelServerResourceBundle();
   }
}