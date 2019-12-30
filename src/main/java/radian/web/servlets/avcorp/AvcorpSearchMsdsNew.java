package radian.web.servlets.avcorp;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AvcorpSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Avcorpsearchmsds check it is here....");
      return "Avcorp";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AvcorpServerResourceBundle();
   }
}