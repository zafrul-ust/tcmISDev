package radian.tcmis.client.share.gui.nchem;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

import radian.tcmis.both1.beans.PartCategoryCriteriaBean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CreateNodesProcess {
  private Collection collection;

  public CreateNodesProcess(Collection c) {
    collection = c;
  }

  public String[] auditSelectedNode(int selectedIndex) {
    String[] result = new String[2];
    result[0] = "OK";

    return result;
  } //end of method

  public int getCategoryPosition(String category) {
    Iterator iterator = collection.iterator();
    int count = 0;
    while (iterator.hasNext()) {
      PartCategoryCriteriaBean bean = new PartCategoryCriteriaBean();
      bean = (PartCategoryCriteriaBean) iterator.next();
      if (category.equals(bean.getCategory())) {
        break;
      }
      count++;
    } //end of loop
    return count;
  } //end of method

  public String getSelectedTreeNode(int selectedIndex) {
    Iterator iterator = collection.iterator();
    int count = 0;
    String category = "";
    while (iterator.hasNext()) {
      PartCategoryCriteriaBean bean = new PartCategoryCriteriaBean();
      bean = (PartCategoryCriteriaBean) iterator.next();
      if (count == selectedIndex) {
        category = bean.getCategory();
        break;
      }
      count++;
    } //end of loop
    return category;
  } //end of method

  public void addNodes(TcmISDefaultMutableTreeNode top) {
    TcmISDefaultMutableTreeNode previousNode = null;

    Iterator iterator = collection.iterator();
    Stack stack = new Stack();
    Stack nodeStack = new Stack();

    while (iterator.hasNext()) {
      PartCategoryCriteriaBean bean = new PartCategoryCriteriaBean();
      bean = (PartCategoryCriteriaBean) iterator.next();
      //figure out where on the tree the bean goes
      if (bean.getParent() == null || bean.getParent().equalsIgnoreCase("")) {
        //this is the top element (except for the root element)
        TcmISDefaultMutableTreeNode node = new TcmISDefaultMutableTreeNode(bean.getDisplay(), bean.getCategory());
        top.add(node);
        stack.push(bean.getCategory());
        nodeStack.push(node);
        previousNode = node;
      } else if (bean.getParent() != null &&
                 bean.getParent().equalsIgnoreCase( (String) stack.peek())) {
        //this element has the same parent as the previous element
        TcmISDefaultMutableTreeNode node = new TcmISDefaultMutableTreeNode(bean.getDisplay(), bean.getCategory());
        ( (TcmISDefaultMutableTreeNode) nodeStack.peek()).add(node);
        previousNode = node;
      } else if (bean.getParent() != null &&
                 !bean.getParent().equalsIgnoreCase( (String) stack.peek())) {
        //this element has different parent than the previous element
        //search the stack and if not found add parent to stack
        if (stack.search(bean.getParent()) > 0) {
          //parent found in stack
          //pop stacks until parent is found
          while (!bean.getParent().equalsIgnoreCase( (String) stack.peek())) {
            stack.pop();
            nodeStack.pop();
          }
          TcmISDefaultMutableTreeNode node = new TcmISDefaultMutableTreeNode(bean.getDisplay(), bean.getCategory());
          ( (TcmISDefaultMutableTreeNode) nodeStack.peek()).add(node);
          previousNode = node;
        } else {
          //parent is not in the stack, add it
          stack.push(bean.getParent());
          TcmISDefaultMutableTreeNode node = new TcmISDefaultMutableTreeNode(bean.getDisplay(), bean.getCategory());
          nodeStack.push(previousNode);
          ( (TcmISDefaultMutableTreeNode) nodeStack.peek()).add(node);
          previousNode = node;
        }
      }
    }
  }
}