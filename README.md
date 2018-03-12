# JBusTest
Kotlin编写的事件总线库
使用步骤：

添加库：

1、在你项目下的build.gradle添加仓库引用

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    
2、在你APP模块下添加依赖
    
    compile 'com.github.c297131019:JBusTest:1.0'

1、先给当前类注册，比如Activity

     override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_main)
             JBus.registerEvent(this::class.java, this)
      }

2、给当前类注销，必须要销毁，否则会造成内存泄漏

     override fun onDestroy() {
             super.onDestroy()
             JBus.unregisterEvent(this::class.java)
     }

3、实现消息收听接口

    override fun <T> onMsg(s: T) {

    }

4.1、给指定目标发送消息(只有注册过的目标才能收到消息)

    JBus.sendMsg(MainActivity::class.java,"Hello Main")

4.2、给所有目标发送消息(只有注册过的目标才能收到消息)

    JBus.sendMsg("All Hello")

更多使用建议：

1、自定义一个消息载体，比如：

    //这种格式很常见了，网络请求数据最外层一般都用这种格式
    
    data class BusBean<T>(
                    var code: Int = 0 ,
                    var t : T? = null ,
                    var msg : String = ""
                 )

    //接收的时候就可以根据值来判断不同的操作

     override fun <T> onMsg(s: T) {

            (s as? BusBean<String>)?.l
