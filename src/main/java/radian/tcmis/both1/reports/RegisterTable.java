package radian.tcmis.both1.reports;

import java.util.*;
public class RegisterTable {
  Vector data;
  String name;
  Vector methods;
  String where;

  public RegisterTable(Vector data,String name,Vector methods,String where) {
    this.data = data;
    this.name = new String(name);
    this.methods = methods;
    this.where = where;
  }
  public Vector getData() {
    return data;
  }
  public String getName() {
    return name;
  }
  public Vector getMethods() {
    return methods;
  }
  public String getWhere() {
    return where;
  }
}