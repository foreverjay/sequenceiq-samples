package com.sequenceiq.spark.job

import org.apache.spark.rdd.RDD

abstract class CorrelationJob {

  def computeCorrelation(input: RDD[String]) : Array[(Int, Int, Double)]

  def d2d(d: Double) : Double = d.isNaN match {
    case false => new java.text.DecimalFormat("#.######").format(d).toDouble
    case _ => d
  }

}
