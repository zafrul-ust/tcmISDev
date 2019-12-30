package radian.tcmis.internal.admin.beans;

import radian.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ColumnBean <br>
 * @version: 1.0, Mar 17, 2004 <br>
 *****************************************************************************/

public class ColumnBean
    extends BaseDataBean {

  private String catalogName;
  private String className;
  private String name;
  private String label;
  private String typeName;
  private String schemaName;
  private String tableName;
  private int type;
  private int size;
  private int precision;
  private int scale;
  private boolean isAutoIncrement;
  private boolean isCaseSensitive;
  private boolean isCurrency;
  private boolean isDefinitelyWritable;
  private int isNullable;
  private boolean isReadOnly;
  private boolean isSearchable;
  private boolean isSigned;
  private boolean isWritable;

  //constructor
  public ColumnBean() {
    super();
  }

  //setters
  public void setType(int type) {
    this.type = type;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public void setPrecision(int precision) {
    this.precision = precision;
  }

  public void setScale(int scale) {
    this.scale = scale;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setIsAutoIncrement(boolean isAutoIncrement) {
    this.isAutoIncrement = isAutoIncrement;
  }

  public void setIsCaseSensitive(boolean isCaseSensitive) {
    this.isCaseSensitive = isCaseSensitive;
  }

  public void setIsCurrency(boolean isCurrency) {
    this.isCurrency = isCurrency;
  }

  public void setIsDefinitelyWritable(boolean isDefinitelyWritable) {
    this.isDefinitelyWritable = isDefinitelyWritable;
  }

  public void setIsNullable(int isNullable) {
    this.isNullable = isNullable;
  }

  public void setIsReadOnly(boolean isReadOnly) {
    this.isReadOnly = isReadOnly;
  }

  public void setIsSearchable(boolean isSearchable) {
    this.isSearchable = isSearchable;
  }

  public void setIsSigned(boolean isSigned) {
    this.isSigned = isSigned;
  }

  public void setIsWritable(boolean isWritable) {
    this.isWritable = isWritable;
  }


  //getters
  public int getType() {
    return type;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public String getClassName() {
    return className;
  }


  public String getName() {
    return name;
  }

  public String getLabel() {
    return label;
  }

  public String getTypeName() {
    return typeName;
  }

  public int getSize() {
    return size;
  }

  public int getPrecision() {
    return precision;
  }

  public int getScale() {
    return scale;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public String getTableName() {
    return tableName;
  }

  public boolean getIsAutoIncrement() {
    return isAutoIncrement;
  }

  public boolean getIsCaseSensitive() {
    return isCaseSensitive;
  }

  public boolean getIsCurrency() {
    return isCurrency;
  }

  public boolean getIsDefinitelyWritable() {
    return isDefinitelyWritable;
  }

  public int getIsNullable() {
    return isNullable;
  }

  public boolean getIsReadOnly() {
    return isReadOnly;
  }

  public boolean getIsSearchable() {
    return isSearchable;
  }

  public boolean getIsSigned() {
    return isSigned;
  }

  public boolean getIsWritable() {
    return isWritable;
  }
}