# Relogin
Relogin for Android

### 引入Relogin
`` implementation 'com.dzws:relogin:1.0.0'
``

``annotationProcessor 'com.dzws:relogin_compiler:1.0.0'
``

1、初始化Relogin


``Relogin.init(Application);``
* 设置模式，默认为MODEL_FINISH
 * Relogin.MODEL_FINISH 跳转至Login页面后finish其他activiy
 * Relogin.MODEL_REFRESH 完成登陆后回到之前页面并进行刷新
``Relogin.getInstance().setModel(Relogin.MODEL_FINISH);``

2、如果需要使用自动刷新功能
  *  ``ReloadInheritable`` 注解,可继承
  
  
    @ReloadInheritable(reloadMethod = "get")
    public abstract class BaseActivity extends AppCompatActivity {
        public void get(){}
    }
  
  *   ``Reload`` 注解，单独配置刷新方法，Reload优先级大于ReloadInheritable
  
  
    @Reload
    public void comeOn() {
        Log.d(TAG, "MainActivity comeOn : " + this);
        Toast.makeText(MainActivity.this, TAG + " comeOn " + this, Toast.LENGTH_SHORT).show();
    }
       
3、设置重新登陆Activity，RE_LOGIN值为true时，代表是重新登陆


    @Relogin(reloginCode = 401)
    public class LoginActivity extends BaseActivity {
      private String TAG = getClass().getSimpleName();
      @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        if(intent != null) {
          boolean reLogin = intent.getBooleanExtra("RE_LOGIN", false);
          Log.d(TAG,"reLogin : " + reLogin);
        }
      }

      @Override
      public void get() {
        super.get();

      }
    }

4、relogin，调用两个方法同样的效果

    *  ReloginController.getInstance().toLogin();
    *  ReloginController.getInstance().setResponseCode(code)；
    
5、Relogin在组件化中的应用
 * base module中 ``api 'com.dzws:relogin:1.0.0'``
 * 需要注解的Module中引入 ``annotationProcessor 'com.dzws:relogin_compiler:1.0.0'`` 即可
 
