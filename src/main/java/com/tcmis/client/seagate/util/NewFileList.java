/*
 * Created on Dec 21, 2004
 */
package com.tcmis.client.seagate.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

/**
 * @author chuckls
 */
public class NewFileList implements List {
  private static Logger log = Logger.getLogger(NewFileFilter.class);
  private static boolean APPEND = true;
  private File folder, history;
  private FileFilter filter;
  private File[] newfiles;
  private List newfileList;

  /**
   * @throws Exception
   */
  public NewFileList(File folder, File history) throws Exception {
    super();
    this.folder = folder;
    this.history = history;
    filter = (FileFilter) new NewFileFilter(folder, history);
    newfiles = folder.listFiles(filter);
    newfileList = Arrays.asList(newfiles);
  }

  /**
   * @param history
   * @throws IOException
   */
  private void addAllToHistory() throws IOException {
    BufferedWriter historyOut = new BufferedWriter(new FileWriter(history, APPEND));
    Iterator it = newfileList.iterator();
    while(it.hasNext()) {
      File newfile = (File) it.next();
      historyOut.write(newfile.getName());
      historyOut.newLine();
    }
    historyOut.flush();
    historyOut.close();
  }

  public void addToHistory(File f) throws IOException {
    BufferedWriter historyOut = new BufferedWriter(new FileWriter(history, APPEND));
    historyOut.write(f.getName());
    historyOut.newLine();
    historyOut.flush();
    historyOut.close();
  }

  /**
   * @param index
   * @param element
   */
  public void add(int index, Object element) {
    newfileList.add(index, element);
  }
  /**
   * @param o
   * @return
   */
  public boolean add(Object o) {
    return newfileList.add(o);
  }
  /**
   * @param index
   * @param c
   * @return
   */
  public boolean addAll(int index, Collection c) {
    return newfileList.addAll(index, c);
  }
  /**
   * @param c
   * @return
   */
  public boolean addAll(Collection c) {
    return newfileList.addAll(c);
  }
  /**
   *
   */
  public void clear() {
    newfileList.clear();
  }
  /**
   * @param o
   * @return
   */
  public boolean contains(Object o) {
    return newfileList.contains(o);
  }
  /**
   * @param c
   * @return
   */
  public boolean containsAll(Collection c) {
    return newfileList.containsAll(c);
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    return newfileList.equals(obj);
  }
  /**
   * @param index
   * @return
   */
  public Object get(int index) {
    return newfileList.get(index);
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  public int hashCode() {
    return newfileList.hashCode();
  }
  /**
   * @param o
   * @return
   */
  public int indexOf(Object o) {
    return newfileList.indexOf(o);
  }
  /**
   * @return
   */
  public boolean isEmpty() {
    return newfileList.isEmpty();
  }
  /**
   * @return
   */
  public Iterator iterator() {
    return newfileList.iterator();
  }
  /**
   * @param o
   * @return
   */
  public int lastIndexOf(Object o) {
    return newfileList.lastIndexOf(o);
  }
  /**
   * @return
   */
  public ListIterator listIterator() {
    return newfileList.listIterator();
  }
  /**
   * @param index
   * @return
   */
  public ListIterator listIterator(int index) {
    return newfileList.listIterator(index);
  }
  /**
   * @param index
   * @return
   */
  public Object remove(int index) {
    return newfileList.remove(index);
  }
  /**
   * @param o
   * @return
   */
  public boolean remove(Object o) {
    return newfileList.remove(o);
  }
  /**
   * @param c
   * @return
   */
  public boolean removeAll(Collection c) {
    return newfileList.removeAll(c);
  }
  /**
   * @param c
   * @return
   */
  public boolean retainAll(Collection c) {
    return newfileList.retainAll(c);
  }
  /**
   * @param index
   * @param element
   * @return
   */
  public Object set(int index, Object element) {
    return newfileList.set(index, element);
  }
  /**
   * @return
   */
  public int size() {
    return newfileList.size();
  }
  /**
   * @param fromIndex
   * @param toIndex
   * @return
   */
  public List subList(int fromIndex, int toIndex) {
    return newfileList.subList(fromIndex, toIndex);
  }
  /**
   * @return
   */
  public Object[] toArray() {
    return newfileList.toArray();
  }
  /**
   * @param a
   * @return
   */
  public Object[] toArray(Object[] a) {
    return newfileList.toArray(a);
  }
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return newfileList.toString();
  }
  /**
   * @return Returns the folder.
   */
  public File getFolder() {
    return folder;
  }
  /**
   * @return Returns the history.
   */
  public File getHistory() {
    return history;
  }
}
