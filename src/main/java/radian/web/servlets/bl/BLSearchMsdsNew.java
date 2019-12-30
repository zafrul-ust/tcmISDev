package radian.web.servlets.bl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BLSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BLsearchmsds check it is here....");
      return "BL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BLServerResourceBundle();
   }
}