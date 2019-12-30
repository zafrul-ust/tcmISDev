package radian.web.servlets.cat;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CaterpillarBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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