import org.apache.spark._
import org.apache.spark.SparkContext._

// Simple Spark Word Count based on
// https://github.com/databricks/learning-spark/blob/master/mini-complete-example/src/main/scala/com/oreilly/learningsparkexamples/mini/scala/WordCount.scala
object WordCount {
    def main(args: Array[String]) {
      val inputFile = args(0)
      val outputFile = args(1)
      val conf = new SparkConf().setAppName("wordCount").setMaster("local[2]");
      // Create a Scala Spark Context.
      val sc = new SparkContext(conf)
      // Get the filesystem url (works even if not actually HDFS)
      val basePath = sc.hadoopConfiguration.get("fs.defaultFS")
      // Load our input data.
      val input =  sc.textFile(s"$basePath/$inputFile")
      // Split up into words.
      val words = input.flatMap(line => line.split(" "))
      // Transform into word and count. Note concise "underscore" notation for lambda
      val counts = words.map(word => (word, 1)).reduceByKey( _ + _)
      // Save the word count back out to a text file, causing evaluation.
      counts.saveAsTextFile(s"$basePath/$outputFile")
    }
}
