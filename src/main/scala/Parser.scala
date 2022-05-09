import org.jsoup.Jsoup

import scala.xml.XML

object Parser {

  def main(args: Array[String]): Unit = {
    val URL = "https://export.yandex.ru/last/last20x.xml"
    val doc = Jsoup
      .connect(URL)
//      .proxy()
      .get()
    println(doc.body().data())
    doc.body().children().forEach{ x=>
      println("child")
      println(x)
      doc.childNodes().get(2)
      doc.getElementsByTag("item") //this
    }



  }
}
