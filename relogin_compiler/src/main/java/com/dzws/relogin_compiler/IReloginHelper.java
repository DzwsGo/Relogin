package com.dzws.relogin_compiler;

import java.util.HashMap;

/**
 * description：
 *
 * @author: Lwang
 * @createTime: 2019-07-22 10:54
 */
public interface IReloginHelper {
  String getReloginClassName();
  int getReloginResponseCode();
  HashMap<String,String> getReloadMethodMap();
}
