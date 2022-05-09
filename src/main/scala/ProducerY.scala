import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import java.net.{Authenticator, PasswordAuthentication}
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Properties

object ProducerY extends App {


  //  Authenticator.setDefault(null)

  //  System.setProperty("http.proxyUser", "")
  //  System.setProperty("http.proxyPassword", "")
  //  System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "Basic")

  //  default for http.proxyUseris null
  //  default http.proxyPassword null
  //  default for jdk.http.auth.tunneling.disabledSchemes is null

  println(s"current value for http.proxyUseris ${System.getProperty("http.proxyUser")}")
  println(s"current value http.proxyPassword ${System.getProperty("http.proxyPassword")}")
  println(s"current value for jdk.http.auth.tunneling.disabledSchemes is ${System.getProperty("jdk.http.auth.tunneling.disabledSchemes")}")

//  System.setProperty("http.proxyUser", null)
//  System.setProperty("http.proxyPassword", null)
//  System.setProperty("jdk.http.auth.tunneling.disabledSchemes", null)

//  println(s"updated value for http.proxyUseris ${System.getProperty("http.proxyUser")}")
//  println(s"updated value http.proxyPassword ${System.getProperty("http.proxyPassword")}")
//  println(s"updated value for jdk.http.auth.tunneling.disabledSchemes is ${System.getProperty("jdk.http.auth.tunneling.disabledSchemes")}")

  val HOST = "rc1a-6kf6ln36s6kk84o2.mdb.yandexcloud.net:9091"
  val TOPIC = "top1"
  val USER = "hw-prod"
  val PASS = "04d6e5e1d2d84ba39d95d7c23cf0248f"
  val TS_FILE = "/etc/security/ssl"
  val TS_PASS = "9201e9363a424eecb55bc57ab04b97b0"

  val jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"
  val jaasCfg = String.format(jaasTemplate, USER, PASS)

  println(s"jaasCfg = $jaasCfg")
  val slr = new StringSerializer //TODO !
  val serializer = slr.getClass.getName //TODO !

  val props = new Properties()
  props.put("bootstrap.servers", HOST)
  props.put("acks", "all")
  props.put("key.serializer", serializer)
  props.put("value.serializer", serializer)
  props.put("security.protocol", "SASL_SSL")
  props.put("sasl.mechanism", "SCRAM-SHA-512")
  props.put("sasl.jaas.config", jaasCfg)
  props.put("ssl.truststore.location", TS_FILE)
  props.put("ssl.truststore.password", TS_PASS)


  val producer = new KafkaProducer(props, new StringSerializer, new StringSerializer)


  def getTime: String = {
    val dtf = DateTimeFormatter.ofPattern("hh:mm:ss:SSS")
    dtf.format(LocalTime.now())
  }


  println(s"[START] [$getTime] generating data and put it to topic ($TOPIC)")
  //producer.send(new ProducerRecord(TOPIC, "qwe", s"$getTime"))

  P1.urls.foreach( url =>
    P1.yParcer(P1.getProxy(url)).foreach {text =>
      producer.send(new ProducerRecord(TOPIC, "t1", text.req))
    }
  )



  //  Range(1, 1000).foreach{ x=>
  //    println(s"[$x] [$getTime] ")
  //
  //    val messages = (Range(1, 1000).map( x=> s"[x][$getTime]")).toList
  //    messages.foreach { m =>
  //      producer.send(new ProducerRecord(TOPIC, m, m))
  //    }
  //  }


  //  val messages = List(
  //
  //    "[message ",
  //    "message2",
  //    "message3",
  //    "message3",
  //  )

  //  messages.foreach { m =>
  //    producer.send(new ProducerRecord(TOPIC, m, m))
  //  }

  producer.close()
  println(s"[DONE] [$getTime]")


}
