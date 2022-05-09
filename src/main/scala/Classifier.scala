import org.apache.spark.ml.feature.{Normalizer, Tokenizer}
import org.apache.spark.sql.SparkSession

object Classifier {
  def main(args: Array[String]): Unit = {
    val in =  Seq(
      "скачать метроном",
     " не прервётся связь поколений 2022",
      "матч рейнджерс рб лейпциг "
    )

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("Spark SQL basic example")
      .getOrCreate()

    import spark.implicits._

    val df = in.toDF("text")
    df.show()

    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")

    val tokenized = tokenizer.transform(df)

    tokenized.show()

    val normalizer = new Normalizer()
      .setInputCol("token")
      .setOutputCol("normalized")

    val normalized = normalizer.transform(tokenized)

    normalized.show
  }
}
