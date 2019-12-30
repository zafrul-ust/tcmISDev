//Title:        TCM - CMIS
//Version:
//Copyright:    Copyright (c) 1997
//Author:       Rodrigo Zerlotti
//Company:      Radian International
//Description:  TCM - CMIS - Gui components

package radian.tcmis.client.share.gui.nchem;


import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;
import javax.swing.tree.TreeSelectionModel;

import radian.tcmis.client.share.helpers.*;
import radian.tcmis.both1.helpers.*;
import radian.tcmis.both1.helpers.resource.ResourceLoader;
import radian.tcmis.client.share.httpCgi.*;
import com.borland.jbcl.layout.*;
import radian.tcmis.client.share.gui.*;

public class CategoryDlg extends JDialog {
  CmisApp cmis;
  JButton okB = new JButton();
  JButton cancelB = new JButton();
  JPanel paneM = new JPanel();
  JPanel treePanel = new JPanel();
  JPanel buttonPanel = new JPanel();

  //create the tree
  TcmISDefaultMutableTreeNode top;
  TcmISDefaultMutableTreeNode top1;
  TcmISDefaultMutableTreeNode top2;
  TcmISDefaultMutableTreeNode top3;
  TcmISDefaultMutableTreeNode top4;
  TcmISDefaultMutableTreeNode top5;
  TcmISDefaultMutableTreeNode top6;
  TcmISDefaultMutableTreeNode top7;
  TcmISDefaultMutableTreeNode top8;
  TcmISDefaultMutableTreeNode top9;

  JTree categoryTree;
  JTree categoryTree1;
  JTree categoryTree2;
  JTree categoryTree3;
  JTree categoryTree4;
  JTree categoryTree5;
  JTree categoryTree6;
  JTree categoryTree7;
  JTree categoryTree8;
  JTree categoryTree9;

  JScrollPane treeView;
  JScrollPane treeView1;
  JScrollPane treeView2;
  JScrollPane treeView3;
  JScrollPane treeView4;
  JScrollPane treeView5;
  JScrollPane treeView6;
  JScrollPane treeView7;
  JScrollPane treeView8;
  JScrollPane treeView9;

  JTabbedPane categoryDetailTab = new JTabbedPane();

  DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  CreateNodesProcess createNodes;
  Collection selectedCategories;
  boolean categoryEditable = false;
  boolean okFlag = false;
  String categoryDelimiter = "";

  //comments
  JScrollPane commentJSP = new JScrollPane();
  JTextArea commentT = new JTextArea();
  StaticJLabel commentL = new StaticJLabel();
  String lastSelectedTab = "";
  Hashtable commentH = new Hashtable(11);

  public CategoryDlg(CmisApp cmis, String title, boolean modal, Collection selectedCategories, String categoryDelimiter, boolean categoryEditable, Hashtable selectedCategoryComment) {
      super(cmis.getMain(), title, modal);
      this.cmis = cmis;
      this.selectedCategories = selectedCategories;
      this.categoryDelimiter = categoryDelimiter;
      this.categoryEditable = categoryEditable;
      this.commentH = selectedCategoryComment;
      try {
        jbInit();
        getContentPane().add(paneM);
        pack();
        CenterComponent.centerCompOnScreen(this);

         //colors and fonts
         ClientHelpObjs.makeDefaultColors(this);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
  }

  public void setCmisApp(CmisApp cmis) {
    cmis = cmis;
  }

  public String getCategoryDelimiter() {
    return categoryDelimiter;
  }

  public void setCategoryDelimiter(String s) {
    categoryDelimiter = s;
  }

  public void jbInit() throws Exception{
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });

    paneM.setMaximumSize(new Dimension(700, 500));
    paneM.setPreferredSize(new Dimension(700, 500));
    paneM.setMinimumSize(new Dimension(700, 500));

    treePanel.setMaximumSize(new Dimension(690, 400));
    treePanel.setPreferredSize(new Dimension(690, 400));
    treePanel.setMinimumSize(new Dimension(690, 400));

    buttonPanel.setMaximumSize(new Dimension(690, 40));
    buttonPanel.setPreferredSize(new Dimension(690, 40));
    buttonPanel.setMinimumSize(new Dimension(690, 40));

    okB.setMaximumSize(new Dimension(120, 25));
    okB.setPreferredSize(new Dimension(120, 25));
    okB.setMinimumSize(new Dimension(120, 25));
    okB.setText("Ok");
    okB.setIcon(ResourceLoader.getImageIcon("images/button/ok.gif"));
    okB.addActionListener(new CategoryDlg_okB_actionAdapter(this));
    okB.addKeyListener(new CategoryDlg_okB_keyAdapter(this));
    cancelB.setMaximumSize(new Dimension(120, 25));
    cancelB.setPreferredSize(new Dimension(120, 25));
    cancelB.setMinimumSize(new Dimension(120, 25));
    if (!categoryEditable) {
      cancelB.setText("Close");
    }else {
      cancelB.setText("Cancel");
    }
    cancelB.setIcon(ResourceLoader.getImageIcon("images/button/cancel.gif"));
    cancelB.addActionListener(new CategoryDlg_cancelB_actionAdapter(this));
    cancelB.addKeyListener(new CategoryDlg_cancelB_keyAdapter(this));

    paneM.setLayout(borderLayout1);
    treePanel.setBorder(ClientHelpObjs.groupBox(""));
    treePanel.setLayout(borderLayout2);
    buttonPanel.setBorder(ClientHelpObjs.groupBox(""));
    buttonPanel.setLayout(gridBagLayout1);

    //tree section
    paneM.add(treePanel,BorderLayout.NORTH);
    treePanel.add(categoryDetailTab);
    //comment section
    commentJSP.setMaximumSize(new Dimension(690, 50));
    commentJSP.setPreferredSize(new Dimension(690, 50));
    commentJSP.setMinimumSize(new Dimension(690, 50));
    commentJSP.setBorder(ClientHelpObjs.groupBox("Comments"));
    commentT.setLineWrap(true);
    commentT.setWrapStyleWord(true);
    paneM.add(commentJSP,BorderLayout.CENTER);
    commentJSP.getViewport().add(commentT, null);

    //buttons section
    paneM.add(buttonPanel,BorderLayout.SOUTH);
    buttonPanel.add(okB, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    buttonPanel.add(cancelB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    this.setResizable(false);
  } //end of method

  public void loadIt(){
    CategoryDlgLoadThread c = new CategoryDlgLoadThread(this);
    c.start();
  }
  void loadData(){
    ClientHelpObjs.setEnabledContainer(this.getContentPane(),false);
    try{
      TcmisHttpConnection cgi = new TcmisHttpConnection("NChemObj",cmis);
      Hashtable query = new Hashtable();
      query.put("ACTION","GET_CATEGORY");
      Hashtable result = cgi.getResultHash(query);
      if(result == null){
        GenericDlg.showAccessDenied(cmis.getMain());
        return;
      }

      Vector categoryData = new Vector((Collection)result.get("CATEGORY"));
      Iterator categoryName = ((Collection)categoryData.lastElement()).iterator();
      int count = 1;
      while (categoryName.hasNext()) {
        String categorySystem = (String)categoryName.next();
        displayCategorySystem(categoryData,categorySystem,count++);
      }
      this.repaint();
      ClientHelpObjs.playUrl((new ClientResourceBundle()).getString("DONE_AU"));
    }catch(Exception e){
      e.printStackTrace();
    }

    categoryDetailTab.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        tabChanged();
      }
    });

    String tmpSelectedTab = categoryDetailTab.getTitleAt(categoryDetailTab.getSelectedIndex());
    if (commentH.containsKey(tmpSelectedTab)) {
      commentT.setText((String)commentH.get(tmpSelectedTab));
    }else {
      commentT.setText("");
    }
    lastSelectedTab = tmpSelectedTab;

    ClientHelpObjs.setEnabledContainer(this.getContentPane(),true);
    okB.setEnabled(categoryEditable);
  } //end of method

  void tabChanged() {
    commentH.put(lastSelectedTab,commentT.getText().trim());
    String tmpSelectedTab = categoryDetailTab.getTitleAt(categoryDetailTab.getSelectedIndex());
    if (commentH.containsKey(tmpSelectedTab)) {
      commentT.setText((String)commentH.get(tmpSelectedTab));
    }else {
      commentT.setText("");
    }
    lastSelectedTab = tmpSelectedTab;
  } //end of method

  void displayCategorySystem(Vector categoryData,String categorySystem, int categoryCount) {
    Vector v = new Vector(selectedCategories);
    String selectedCategory = "";
    //COMMENT OUT WHEN COMPILE IN JRE1.3 AND UNCOMMENT IN JRE1.4
    try {
      for (int i = 0; i < v.size(); i++) {
        String tmpVal = (String) v.elementAt(i);
        String[] tmpData = tmpVal.split(categoryDelimiter);
        if (categorySystem.equals(tmpData[0])) {
          selectedCategory = tmpData[1];
          break;
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
      selectedCategory = "";
    }
     //END OF CONCERN AREA

    switch (categoryCount) {
        case 1:
          top =  new TcmISDefaultMutableTreeNode("","");
          categoryTree = new JTree(top);
          treeView = new JScrollPane();
          treeView.getViewport().add(categoryTree);
          categoryDetailTab.add(categorySystem,treeView);
          createNodes(categoryTree,top,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree,selectedCategory);
          break;
        case 2:
          top1 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree1 = new JTree(top1);
          treeView1 = new JScrollPane();
          treeView1.getViewport().add(categoryTree1);
          categoryDetailTab.add(categorySystem,treeView1);
          createNodes(categoryTree1,top1,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree1,selectedCategory);
          break;
        case 3:
          top2 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree2 = new JTree(top2);
          treeView2 = new JScrollPane();
          treeView2.getViewport().add(categoryTree2);
          categoryDetailTab.add(categorySystem,treeView2);
          createNodes(categoryTree2,top2,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree2,selectedCategory);
          break;
        case 4:
          top3 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree3 = new JTree(top3);
          treeView3 = new JScrollPane();
          treeView3.getViewport().add(categoryTree3);
          categoryDetailTab.add(categorySystem,treeView3);
          createNodes(categoryTree3,top3,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree3,selectedCategory);
          break;
        case 5:
          top4 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree4 = new JTree(top4);
          treeView4 = new JScrollPane();
          treeView4.getViewport().add(categoryTree4);
          categoryDetailTab.add(categorySystem,treeView4);
          createNodes(categoryTree4,top4,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree4,selectedCategory);
          break;
        case 6:
          top5 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree5 = new JTree(top5);
          treeView5 = new JScrollPane();
          treeView5.getViewport().add(categoryTree5);
          categoryDetailTab.add(categorySystem,treeView5);
          createNodes(categoryTree5,top5,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree5,selectedCategory);
          break;
        case 7:
          top6 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree6 = new JTree(top6);
          treeView6 = new JScrollPane();
          treeView6.getViewport().add(categoryTree6);
          categoryDetailTab.add(categorySystem,treeView6);
          createNodes(categoryTree6,top6,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree6,selectedCategory);
          break;
        case 8:
          top7 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree7 = new JTree(top7);
          treeView7 = new JScrollPane();
          treeView7.getViewport().add(categoryTree7);
          categoryDetailTab.add(categorySystem,treeView7);
          createNodes(categoryTree7,top7,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree7,selectedCategory);
          break;
        case 9:
          top8 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree8 = new JTree(top8);
          treeView8 = new JScrollPane();
          treeView8.getViewport().add(categoryTree8);
          categoryDetailTab.add(categorySystem,treeView8);
          createNodes(categoryTree8,top8,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree8,selectedCategory);
          break;
        case 10:
          top9 =  new TcmISDefaultMutableTreeNode("","");
          categoryTree9 = new JTree(top9);
          treeView9 = new JScrollPane();
          treeView9.getViewport().add(categoryTree9);
          categoryDetailTab.add(categorySystem,treeView9);
          createNodes(categoryTree9,top9,(Collection)categoryData.elementAt(categoryCount-1));
          setSelectedTreePath(categoryTree9,selectedCategory);
          break;
        default:
          System.out.println("Not a category system");
          break;
    }
  } //end of method

  public void createNodes(JTree myTree, TcmISDefaultMutableTreeNode top, Collection c) {
    createNodes = new CreateNodesProcess(c);
    createNodes.addNodes(top);
    myTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
  }

  void okB_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (auditOK()) {
        okFlag = true;
        this.setVisible(false);
      }
    }
  }

  void okB_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      commentH.put(lastSelectedTab,commentT.getText().trim());
      if (auditOK()) {
        okFlag = true;
        this.setVisible(false);
      }
  } //end of method

  boolean auditOK() {
    boolean result = true;
    Vector tmpV = new Vector(categoryDetailTab.getTabCount());
    String errorMsg = "";
    int errorIndex = -1;
    int errorCount = 0;
    boolean fatalErrorFlag = false;
    for (int i = 0; i < categoryDetailTab.getTabCount(); i++) {
      TcmISDefaultMutableTreeNode treeM = null;
      String tmpCategorySystem = categoryDetailTab.getTitleAt(i);
      if (i == 0) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 1) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree1.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 2) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree2.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 3) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree3.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 4) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree4.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 5) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree5.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 6) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree6.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 7) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree7.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 8) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree8.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      }else if (i == 9) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree1.getLastSelectedPathComponent();
        if (treeM == null) {
          errorMsg += ++errorCount +") You did not select a category for "+tmpCategorySystem+"\n";
          if (errorIndex == -1) {
            errorIndex = i;
          }
        }else {
          if (!treeM.isLeaf()) {
            errorMsg += ++errorCount +") The selected category has subcategory.  Please select a category for "+tmpCategorySystem+"\n";
            fatalErrorFlag = true;
            if (errorIndex == -1) {
              errorIndex = i;
            }
          }
        }
      } //end of else if i == 9
    } //end of for loop

    if (errorMsg.length() > 1) {
      if (fatalErrorFlag) {
        DisplayTextDlg gd = new DisplayTextDlg(cmis.getMain(),"Information",true);
        gd.setMsg(errorMsg);
        gd.setVisible(true);
        result = false;
        categoryDetailTab.setSelectedIndex(errorIndex);
      }else {
        ConfirmNewDlg md = new ConfirmNewDlg(cmis.getMain(),"Category Confirmation",true);
        md.setMsg(errorMsg);
        md.setVisible(true);
        if(md.getConfirmationFlag()){
          result = true;
        }else {
          result = false;
          categoryDetailTab.setSelectedIndex(errorIndex);
        }
      }
    }

    //make sure that comments is less than 500 chars
    Enumeration enum1 = commentH.keys();
    while (enum1.hasMoreElements()) {
      String key = enum1.nextElement().toString();
      String tmpVal = commentH.get(key).toString();
      if (tmpVal.length() > 500) {
        DisplayTextDlg gd = new DisplayTextDlg(cmis.getMain(),"Error",true);
        gd.setMsg("Comments for "+key+" are "+tmpVal.length()+" characters long. Comments must be 500 characters or less.");
        gd.setVisible(true);
        result = false;
        break;
      }
    }

    return result;
  } //end of method

  public Hashtable getCategoryComments() {
    return commentH;
  } //end of method

  public Collection getSelectedCategory() {
    Collection result = new Vector(categoryDetailTab.getTabCount());
    for (int i = 0; i < categoryDetailTab.getTabCount(); i++) {
      TcmISDefaultMutableTreeNode treeM = null;
      String tmpCategorySystem = categoryDetailTab.getTitleAt(i);
      if (i == 0) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 1) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree1.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 2) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree2.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 3) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree3.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 4) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree4.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 5) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree5.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 6) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree6.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 7) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree7.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 8) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree8.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }else if (i == 9) {
        treeM = (TcmISDefaultMutableTreeNode)categoryTree9.getLastSelectedPathComponent();
        if (treeM == null) continue;
        result.add(tmpCategorySystem+categoryDelimiter+treeM.getID());
      }
    }
    return result;
  } //end of method

  void expandAll (JTree myTree, boolean expand) {
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)myTree.getModel().getRoot();
    //traverse from root
    expandAll(myTree,new TreePath(root),expand);
  }
  void expandAll(JTree myTree, TreePath parent, boolean expand) {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements();) {
        DefaultMutableTreeNode n = (DefaultMutableTreeNode)e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        expandAll(myTree,path,expand);
      }
    }
    //expansion or collapse must be done botton up
    if (expand) {
      myTree.expandPath(parent);
    }else {
      myTree.collapsePath(parent);
    }
  } //end of method

  void setSelectedTreePath(JTree myTree, String category) {
    if (category.length() > 0) {
      int selectedRow = createNodes.getCategoryPosition(category);
      expandAll(myTree,true);
      TreePath tp = myTree.getPathForRow(selectedRow + 1);
      expandAll(myTree,false);
      myTree.expandPath(tp.getParentPath());
      myTree.setSelectionPath(tp);
    }else {
      myTree.expandRow(0);
      myTree.expandRow(1);
    }
  } //end of method


  void this_windowClosing(WindowEvent e) {
    okFlag = false;
    this.setVisible(false);
  }

  void cancelB_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
       okFlag = false;
       this.setVisible(false);
    }
  }

  void cancelB_actionPerformed(ActionEvent e) {
      ClientHelpObjs.playSound(ClientHelpObjs.SOUND_BUTTON_ACTION);
      okFlag = false;
      this.setVisible(false);
  }

} //end of class

class CategoryDlg_okB_keyAdapter extends java.awt.event.KeyAdapter {
  CategoryDlg adaptee;

  CategoryDlg_okB_keyAdapter(CategoryDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.okB_keyPressed(e);
  }
}

class CategoryDlg_okB_actionAdapter implements java.awt.event.ActionListener {
  CategoryDlg adaptee;

  CategoryDlg_okB_actionAdapter(CategoryDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okB_actionPerformed(e);
  }
}

class CategoryDlg_cancelB_keyAdapter extends java.awt.event.KeyAdapter {
  CategoryDlg adaptee;

  CategoryDlg_cancelB_keyAdapter(CategoryDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void keyPressed(KeyEvent e) {
    adaptee.cancelB_keyPressed(e);
  }
}

class CategoryDlg_cancelB_actionAdapter implements java.awt.event.ActionListener {
  CategoryDlg adaptee;

  CategoryDlg_cancelB_actionAdapter(CategoryDlg adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelB_actionPerformed(e);
  }
}

class CategoryDlgLoadThread extends Thread {
  CategoryDlg parent;
  public CategoryDlgLoadThread(CategoryDlg parent){
     super("CategoryDlgLoadThread");
     this.parent = parent;
  }
  public void run(){
     parent.loadData();
  }
}

