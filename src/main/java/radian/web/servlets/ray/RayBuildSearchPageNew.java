package radian.web.servlets.ray;

import radian.tcmis.server7.share.helpers.*;

public class RayBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
{
   public String getClient()
   {
      return "Ray";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new RayServerResourceBundle();
   }
}