object StreamingExample extends  App{



  import org.apache.spark.sql.functions._
  import org.apache.spark.sql.SparkSession

  val spark = SparkSession
    .builder
    .master("local[*]")
    .appName("StructuredNetworkWordCount")
    .getOrCreate()

  import spark.implicits._

  // Create DataFrame representing the stream of input lines from connection to localhost:9999
  val lines = spark.readStream
    .format("socket")
    .option("host", "localhost")
    .option("port", 9998)
    .load()

  // Split the lines into words
  val words = lines.as[String].flatMap(_.split(" "))

  // Generate running word count
  val wordCounts = words.groupBy("value").count()

  // Start running the query that prints the running counts to the console
  val query = wordCounts.writeStream
    .outputMode("complete")
    .format("console")
    .start()

  query.awaitTermination()

  query.stop()

}
