package tech.sqlclub.common.net

import java.io.{DataInputStream, DataOutputStream}
import java.net.{InetAddress, ServerSocket, Socket}
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors
import tech.sqlclub.common.log.Logging
import tech.sqlclub.common.utils.JacksonUtils
import scala.collection.mutable.ArrayBuffer

/**
  *
  * Created by songgr on 2020/03/16.
  */

abstract class SocketTransfer extends Logging {
  protected def socket : Socket

  private lazy val _socket = socket
  private lazy val dIn = new DataInputStream(_socket.getInputStream)
  private lazy val dOut = new DataOutputStream(_socket.getOutputStream)

  def isReady = _socket.isConnected

  def shutdown = {
    if (_socket != null && !_socket.isClosed) {_socket.close()}
    if (dIn != null) { dIn.close()}
    if (dOut != null) { dOut.flush(); dOut.close()}
  }

  def sendData(data:SocketMessage):Unit = {
    dOut.writeInt(SocketReplyMark.LEN_DATA.id)
    val bytes = data.json.getBytes(StandardCharsets.UTF_8)
    dOut.writeInt(bytes.length)
    dOut.write(bytes)
    dOut.flush()
  }


  def sendData(data:Iterable[SocketMessage]):Unit = {
    dOut.writeInt(SocketReplyMark.HEAD.id)
    data.foreach{
      mess =>
        dOut.writeInt(SocketReplyMark.LEN_DATA.id)
        val bytes = mess.json.getBytes(StandardCharsets.UTF_8)
        dOut.writeInt(bytes.length)
        dOut.write(bytes)
    }
    dOut.writeInt(SocketReplyMark.END.id)
    dOut.flush()
  }

  def readData[T<:SocketMessage](`class`: Class[T]):Iterator[T] = {
    val mark = dIn.readInt()
    var firstRead = true
    if (SocketReplyMark(mark) == SocketReplyMark.LEN_DATA) {
      val len = dIn.readInt()
      val bytes = new Array[Byte](len)
      dIn.readFully(bytes, 0, len)
      val message = new String(bytes, StandardCharsets.UTF_8)

      val response = JacksonUtils.fromJson[T](message, `class`)
      List(response).iterator
    } else {
      new Iterator[T]() {
        private var dataOrMark = mark
        private var nextObj: T = _
        private var eos = false

        override def hasNext: Boolean = nextObj != null || {
          if (!eos) {
            dataOrMark = if(firstRead) mark else dIn.readInt
            if (firstRead) firstRead = false

            def next(mark: Int): Boolean = {
              SocketReplyMark(mark) match {
                case SocketReplyMark.END =>
                  eos = true // eos=true !!!
                  false // 结束位
                case SocketReplyMark.HEAD => // 开始位 需要继续读一位
                  dataOrMark = dIn.readInt // mark or end
                  next(dataOrMark)
                case SocketReplyMark.LEN_DATA =>
                  val len = dIn.readInt()
                  val bytes = new Array[Byte](len)
                  dIn.readFully(bytes, 0, len)
                  val response = JacksonUtils.fromJson[T](new String(bytes, StandardCharsets.UTF_8), `class`: Class[T])
                  nextObj = response
                  true
              }
            }
            next(dataOrMark)

          } else {
            false
          }
        }

        override def next(): T = {
          if (hasNext) {
            val obj = nextObj
            nextObj = null.asInstanceOf[T]
            obj
          } else {
            Iterator.empty.next()
          }
        }
      }
    }
  }

}


class SocketClient(host:String, port:Int) extends SocketTransfer {
  override def socket: Socket = new Socket(host, port)
}

class SocketServer(host:String, port:Int, func:SocketTransfer => Any) extends Logging {
  private lazy final val serverSocket:ServerSocket = new ServerSocket(port,1, InetAddress.getByName(host))
  private lazy final val threadPool = Executors.newFixedThreadPool(100)
  private val connections = new ArrayBuffer[SocketTransfer]()

  def getHost= host
  def getPort= port

  new Thread() {
    setDaemon(true)
    override def run(): Unit = {
      // Close the socket if no connection in 5 min
      serverSocket.setSoTimeout(1000 * 60 * 5)
      while (true) {
        val _socket = serverSocket.accept() // 阻塞等待客户端连接
        _socket.setKeepAlive(true)
        logInfo(s"accept a client socket...")
        // 一个客户端对应一个socket管道
        // 使用线程池优化服务端并发能力
        threadPool.submit(new Runnable {
          override def run(): Unit = {
            val transfer = new SocketTransfer {
              override def socket: Socket = _socket
            }
            connections += transfer
            func(transfer)
            transfer.shutdown
          }
        })
      }
    }
  }.start()


  def shutdownAll = {
    logInfo("shunt down all socket with client!")
   connections.foreach(_.shutdown)
  }

}


