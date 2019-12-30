package radian.web.servlets.zwl;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZWLSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ ZWLsearchmsds check it is here....");
      return "ZWL";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZWLServerResourceBundle();
   }
}