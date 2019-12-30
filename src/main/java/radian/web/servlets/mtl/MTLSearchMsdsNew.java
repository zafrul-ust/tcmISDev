package radian.web.servlets.mtl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MTLSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ MTLsearchmsds check it is here....");
      return "MTL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MTLServerResourceBundle();
   }
}