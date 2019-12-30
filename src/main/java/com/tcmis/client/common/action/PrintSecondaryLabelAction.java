package  com.tcmis.client.common.action;

import com.tcmis.client.common.beans.MaterialBean;
import com.tcmis.client.common.process.PrintSecondaryLabelProcess;
import com.tcmis.client.common.process.SecondaryLabelInformationProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class PrintSecondaryLabelAction
	extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping,
									 ActionForm form,
									 HttpServletRequest request,
									 HttpServletResponse response) throws
	  BaseException, Exception {

	if (!this.isLoggedIn(request)) {
			request.setAttribute("requestedPage", "secondarylabelinformation");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
    }

    if (!this.isLoggedIn(request)) return mapping.findForward("nopermissions");
    PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
    String facilityId = request.getParameter("facilityId");
    String materialId = request.getParameter("materialId");
    String labelQty = request.getParameter("labelQty");

    SecondaryLabelInformationProcess process = new SecondaryLabelInformationProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
    PrintSecondaryLabelProcess labelProcess = new PrintSecondaryLabelProcess(this.getDbUser(request), this.getTcmISLocaleString(request));
    MaterialBean materialDataBean = process.searchMaterialInfo(materialId,facilityId);
    materialDataBean.setMaterialId(materialId);
    Collection secondaryLabelDataColl =  process.getPrintSecondaryLabelData(materialId,facilityId);
    Collection iconColl = process.getPrintLabelIcons(materialId,facilityId);

    String labelUrl = "";
    labelUrl = labelProcess.buildSecondaryLabelsZpl(secondaryLabelDataColl,iconColl,materialDataBean,labelQty);
	if (labelUrl.length() > 0)
   {
    request.setAttribute("filePath", labelUrl);
	return mapping.findForward("viewfile");
  }
  else
  {
	return mapping.findForward("systemerrorpage");
  }
}
}//end of class