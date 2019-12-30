package radian.web.servlets.ford;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FordSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Fordsearchmsds check it is here....");
      return "Ford";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FordServerResourceBundle();
   }
}