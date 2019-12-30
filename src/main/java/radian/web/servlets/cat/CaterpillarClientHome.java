package radian.web.servlets.cat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CaterpillarClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Caterpillar";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CaterpillarServerResourceBundle();
   }
}