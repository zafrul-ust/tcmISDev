package radian.tcmis.server7.client.tpl_kpt.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.tpl_kpt.helpers.Tpl_KptServerResourceBundle;

	public class Tpl_KptTcmISDBModel  extends TcmISDBModel {
	   public Tpl_KptTcmISDBModel(){
	      this("TPL_KPT");
	   }

	   public Tpl_KptTcmISDBModel(String client){
	      super(client);
	   }

	   public Tpl_KptTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public Tpl_KptTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new Tpl_KptServerResourceBundle();
	   }

	}
