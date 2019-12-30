package radian.web.servlets.sgd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SGDSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ SGDsearchmsds check it is here....");
      return "SGD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SGDServerResourceBundle();
   }
}