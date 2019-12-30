package radian.web.servlets.ray;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class RaySearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Raysearchmsds check it is here....");
      return "Ray";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new RayServerResourceBundle();
   }
}