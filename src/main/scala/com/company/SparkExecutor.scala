package com.company

import com.company.model.Body
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType

import scala.concurrent.{ExecutionContext, Future}

class SparkExecutor(implicit executionContext: ExecutionContext) {
  def testCsv(spark: SparkSession): Future[String] = Future {
    import spark.implicits._
    import com.company.MyEncoders._

    val schema = StructType
    val data = spark.read.format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("src/main/resources/sample.csv")
      .as[Body]

    val result = data.filter(d => d.height > 1)
    s"total: ${result.count()}"
  }
}
