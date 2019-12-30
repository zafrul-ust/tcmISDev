package radian.tcmis.server7.client.tpl_ll.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.tpl_ll.helpers.Tpl_llServerResourceBundle;

	public class Tpl_llTcmISDBModel  extends TcmISDBModel {
	   public Tpl_llTcmISDBModel(){
	      this("TPL_LL");
	   }

	   public Tpl_llTcmISDBModel(String client){
	      super(client);
	   }

	   public Tpl_llTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public Tpl_llTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new Tpl_llServerResourceBundle();
	   }

	}
