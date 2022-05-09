import ProducerY.{PASS, USER, jaasCfg}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.{OutputMode, Trigger}

/**
 * This is not about consumer API,
 * here used structured streaming
 */
object Consumer extends App {

  private val config = ConfigFactory.load("app.conf").getConfig("kafka")
  private val cluster = config.getString("cluster")
  private val host = config.getString("host")
  private val topic = config.getString("topic")
  private val producer = config.getString("producer")
  private val prodPass = config.getString("prodPass")
  private val consumer = config.getString("consumer")
  private val consPass = config.getString("consPass")
  private val tsFile = config.getString("tsFile")
  private val tsPass = config.getString("tsPass")

  val jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";"
  val jaasCfg = String.format(jaasTemplate, consumer, consPass)

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Spark SQL basic example")
    .getOrCreate()

  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", host)
    .option("subscribe", topic)
    .option("kafka.security.protocol", "SASL_SSL")
    .option("kafka.sasl.mechanism", "SCRAM-SHA-512")
    .option("kafka.ssl.truststore.location", tsFile)
    .option("kafka.ssl.truststore.password", tsPass)
    .option("kafka.sasl.jaas.config", jaasCfg)
    .load()
  // df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")


  //  df.writeStream
  //    .outputMode("complete")
  //    .format("console")
  //    .start()

  //  df.writeStream
  //    .trigger(Trigger.Once)
  //    .format("parquet")
  //    .start("/home/nyansus/Documents/l13hw/src/main/resources/out/f1")
  //}

  println("*******")
  println("DF")
  println("*******")
  df.show(25)

  val parquetQuery = df
    .writeStream
    .format("parquet")
    .option("path", "/home/nyansus/Documents/l13hw/src/main/resources/out/f1")
    .trigger(Trigger.Once)
    .outputMode(OutputMode.Append())
    .start

  parquetQuery.stop()

}