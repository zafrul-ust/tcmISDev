package radian.web.servlets.goodrich;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GoodrichSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Goodrichsearchmsds check it is here....");
      return "Goodrich";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GoodrichServerResourceBundle();
   }
}