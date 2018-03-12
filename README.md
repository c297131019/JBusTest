# JBusTest
Kotlin编写的事件总线库
使用步骤：

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

            (s as? BusBean<String>)?.let {

                when(s.t){
                    //判断T的内容
                }

                when(s.code){
                    //判断code的内容
                }

                when(s.msg){
                    //判断Msg的内容
                }

            }

     }

2、这里的目标不单单是Android常用的四大组件，也可以是任意一个类，
   只要这个类有初始化和销毁功能即可，必须要销毁，否则会造成内存泄漏
   但库是基于Android的API开发的，所以也仅支持Android
