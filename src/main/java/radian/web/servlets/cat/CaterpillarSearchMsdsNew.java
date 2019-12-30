package radian.web.servlets.cat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CaterpillarSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Caterpillarsearchmsds check it is here....");
      return "Caterpillar";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CaterpillarServerResourceBundle();
   }
}