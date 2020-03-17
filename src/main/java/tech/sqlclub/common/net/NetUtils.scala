package tech.sqlclub.common.net

import java.io.IOException
import java.net._
import java.util.concurrent.atomic.AtomicInteger

object NetUtils {

  def getLocalServerIp: String = try {
    java.net.InetAddress.getLocalHost.getHostAddress
  } catch {
    case e: UnknownHostException =>
      e.printStackTrace()
      null
  }


  def getLocalServerIpv4List: List[String] = {
    try {
      import scala.collection.JavaConversions._
      NetworkInterface.getNetworkInterfaces.toList.filter(_.isUp)
        .flatMap(_.getInetAddresses)
        .filter {
          iaddr =>
            iaddr.isInstanceOf[Inet4Address] && !iaddr.isLoopbackAddress
        }.map(_.getHostAddress)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        List.empty[String]
    }
  }

  def portAvailableAndReturn(hostname: String, port_min_number: Int, port_max_number: Int):Int = {
    var bindSuccess: Boolean = false
    val start: AtomicInteger = new AtomicInteger(port_min_number)

    while (!bindSuccess && start.get() <= port_max_number) {
      bindSuccess = portAvailable(hostname, start.get())
      if (!bindSuccess){
        start.set(start.get() + 1)
      }
    }
    if (bindSuccess) start.get() else -1
  }


  def portAvailable(hostName:String, port:Int)= {
    var ss: Socket = null
    var ss1: Socket = null
    var bindSuccess = false
    try {
      ss = new Socket()
      ss.bind(new InetSocketAddress(hostName, port))
      ss.close()
      if (hostName != "0.0.0.0") {
        ss1 = new Socket()
        ss1.bind(new InetSocketAddress("0.0.0.0", port))
        ss1.close()
      }
      bindSuccess = true
    } catch {
      case e:IOException =>

    } finally {
      socketClose(ss)
      socketClose(ss1)
    }

    bindSuccess
  }


  def socketClose(s:Socket)= {
    if (s != null && !s.isClosed){
      try {
        s.close()
      } catch {
        case e:IOException =>
          e.printStackTrace()
      }
    }
  }

}
