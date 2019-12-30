package radian.web.servlets.baz;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAZSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BAZsearchmsds check it is here....");
      return "BAZ";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAZServerResourceBundle();
   }
}