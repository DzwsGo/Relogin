package com.dzws.relogin;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-01 18:28
 */

public class ReLoginBean {
  private String className;
  public ReLoginBean(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  @Override public String toString() {
    return "ReLoginBean{" +
        "className='" + className + '\'' +
        '}';
  }
}
