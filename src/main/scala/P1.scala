import java.net.{Authenticator, InetAddress, InetSocketAddress, Proxy}
import com.typesafe.config._
import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, TextNode}

import java.io.{BufferedWriter, File, FileWriter}
import java.net.PasswordAuthentication
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object  P1 {

  private val config = ConfigFactory.load("app.conf").getConfig("parser")
  private val target = config.getString("target")
  private val login = config.getString("login")
  private val password = config.getString("password")
 val urls: Array[String] = config.getStringList("urls").toArray().map(_.toString)
  private val port = config.getInt("port")

  case class SearchRequest(resCnt: Long, req: String)

  def yParcer(p: Proxy) = {
    println(s"[${ProducerY.getTime}] currentProxy ${p.address()}")
    Jsoup
      .connect(target)
      .proxy(p)
      .get()
      .getElementsByTag("item")
      .toArray
      .map(_.asInstanceOf[Element])
      .map(x => SearchRequest(x.attributes().get("found").toLong, x.childNodes().get(0).asInstanceOf[TextNode].text()))
  }

  def getProxy(url: String)={
    System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "")
    Authenticator.setDefault(new Authenticator() {
      override def getPasswordAuthentication = new PasswordAuthentication(login, password.toCharArray)
    })
    new Proxy(Proxy.Type.HTTP, new InetSocketAddress(urls(1), port))
  }


  def main(args: Array[String]): Unit = {


//    System.setProperty("http.proxyUser", "")
//    System.setProperty("http.proxyPassword", "")


 val p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(urls(1), port))

//    for (i <- urls.indices){
//      val dtf = DateTimeFormatter.ofPattern("hh:mm:ss")
//      val now = dtf.format(LocalTime.now())
//      val path = "/home/nyansus/Documents/l13hw/src/main/resources/out"
//      val name = s"${i}_$now"
//      val fileName = s"$path/$name"
//
//      println(s"[${now}] using proxy[#$i] ${urls(i)}")
//      writeFile(fileName,
//        yParcer(urls(i)).map(x => s"${x.resCnt} | ${x.req}"))
//      Thread.sleep(1000)
//    }


   //TODO cхранить руками в файл и сравнить несколько разных файлов в блокноте



//      .toArray().map(x => x.asInstanceOf[Element])
  //  println(s"t = ${t.mkString(",")}")
//    urls.foreach(url =>
//
//    )



//    val URL = target //TODO sticky code
//    val doc = Jsoup
//      .connect(URL)
//      .proxy(p)
//      .get()
//
//   // println(doc.body().data())
//
//    val documentItems = doc.getElementsByTag("item") //this
//    documentItems.forEach(println)
  }


  def writeFile(filename: String, lines: Seq[String]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(s"$line\n")
    }
    bw.close()
  }
}
