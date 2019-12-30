package radian.web.servlets.nalco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NalcoSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Nalcosearchmsds check it is here....");
      return "Nalco";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NalcoServerResourceBundle();
   }
}