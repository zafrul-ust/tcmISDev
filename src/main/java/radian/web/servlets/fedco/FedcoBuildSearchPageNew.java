package radian.web.servlets.fedco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FedcoBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Fedco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FedcoServerResourceBundle();
   }
}