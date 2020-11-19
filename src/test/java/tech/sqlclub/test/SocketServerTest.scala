package tech.sqlclub.test

import tech.sqlclub.common.net.NetUtils.{getLocalServerIp, portAvailableAndReturn}
import tech.sqlclub.common.net.{Request, SocketClient, SocketServer, SocketTransfer}

/**
  *
  * Created by songgr on 2020/03/17.
  */
object SocketServerTest {

 def main(args: Array[String]): Unit = {

  val host = getLocalServerIp
  //许多Linux内核使用32768至61000范围做临时端口
  val port = portAvailableAndReturn(host, 32768, 61000)

  val func = (transfer:SocketTransfer) => {
   while (transfer.isReady) {
    println("read----")
    val data = transfer.readData[Data](classOf[Data])
    data.foreach(println)
   }
  }

  val socketServer = new SocketServer(host, port, func)


  val client = new SocketClient(socketServer.getHost, socketServer.getPort)

  val client2 = new SocketClient(socketServer.getHost, socketServer.getPort)

  while (true) {
//   client.sendData(List(Data("i'm fine!"), Data("i'm ok")))
   client.sendData(List())
   println("send1------")

   //      client2.sendData(Data("i'm fine! 222"))
   //      println("send2------")
   Thread.sleep(3000)
  }


 }

 case class Data(str:String) extends Request {
  override def wrap: Any = this
 }
}
