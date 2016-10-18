package com.cimb.chatbot.controller

import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import breeze.linalg._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import org.apache.spark.SparkContext._
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream

object Word2VecSparkContext {
    private val batchDuration = Seconds(1)
    val conf = new SparkConf().setAppName("CIMBWord2VecChatbot").setMaster("local[2]").set("spark.driver.allowMultipleContexts", "true").set("spark.executor.memory", "16g").set("spark.driver.memory", "16g")
    val sc = new SparkContext(conf)
        /** Create Streaming Context */
  //  val ssc = new StreamingContext(conf, batchDuration)
}