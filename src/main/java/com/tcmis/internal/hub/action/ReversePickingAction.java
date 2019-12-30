package com.tcmis.internal.hub.action;



import java.math.BigDecimal;

import java.util.Collection;

import java.util.Iterator;

import java.util.List;

import java.util.Vector;

import javax.servlet.http.*;



import org.apache.commons.beanutils.DynaBean;

import org.apache.commons.beanutils.LazyDynaBean;

import org.apache.struts.action.*;

import com.tcmis.common.admin.beans.PersonnelBean;

import com.tcmis.common.admin.beans.PermissionBean;

import com.tcmis.common.exceptions.*;

import com.tcmis.common.framework.*;

import com.tcmis.common.util.*;

import com.tcmis.internal.hub.beans.*;

import com.tcmis.internal.hub.process.PickingQcProcess;



/******************************************************************************

 * Controller for reverse picking 

 * @version 1.0

     ******************************************************************************/

public class ReversePickingAction extends TcmISBaseAction {



  public ActionForward executeAction(ActionMapping mapping,

                                     ActionForm form,

                                     HttpServletRequest request,

                                     HttpServletResponse response) throws

      BaseException, Exception {

      

      String forward = "success";

      if (!this.isLoggedIn(request)) {

         request.setAttribute("requestedPage", "reversepick");

         //This is to save any parameters passed in the URL, so that they

         //can be acccesed after the login

         request.setAttribute("requestedURLWithParameters",this.getRequestedURLWithParameters(request));

         return (mapping.findForward("login"));

      }



      PersonnelBean personnelBean = ((PersonnelBean)this.getSessionObject(request, "personnelBean"));

      BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());



      PermissionBean permissionBean = personnelBean.getPermissionBean();

      if (! permissionBean.hasFacilityPermission("Picking QC",null,null)) {

        return mapping.findForward("nopermissions");

      }      

      

      if (form == null) {

        // this.saveTcmISToken(request); 

         log.debug("form is null");

      }

      else {

        //copy data from dyna form to the data form

        PickingInputBean bean = new PickingInputBean();

        BeanHandler.copyAttributes(form, bean);

        

        if (bean.getReverse() != null &&

           bean.getReverse().trim().length() > 0) {        

            

           if (!this.isTcmISTokenValid(request, true)) {

             BaseException be = new BaseException("Duplicate form submission");

             be.setMessageKey("error.submit.duplicate");

             throw be;

           }

           this.saveTcmISToken(request);              

           PickingQcProcess process = new PickingQcProcess(this.getDbUser(request));

           Collection result = process.reversePick(bean);

           request.setAttribute("successBean",result);

        } else {

           this.saveTcmISToken(request);

        }

        request.setAttribute("reverseBean",bean);    

      }



      return (mapping.findForward(forward));

    }

}

