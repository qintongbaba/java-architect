> 以数据为中心，非面向连接，不安全，数据可能丢失，效率高
- DatagramSocket
- DatagramPacket
```xml
1.客户端
    a.创建客户端 `DatagramSocket`类
    b.准备数据（字节数组）
    c.打包`DatagramPacket`+发送的位置(服务器和端口)
    d.发送
    e.释放资源
2.服务端
    a.创建客户端 `DatagramSocket`类
    b.准备接收数据（字节数组）
    c.打包`DatagramPacket`
    d.接收
    e.释放资源
```