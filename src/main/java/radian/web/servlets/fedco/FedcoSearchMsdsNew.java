package radian.web.servlets.fedco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FedcoSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Fedcosearchmsds check it is here....");
      return "Fedco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FedcoServerResourceBundle();
   }
}