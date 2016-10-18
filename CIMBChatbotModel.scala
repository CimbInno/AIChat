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
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.DStream

object CIMBChatbotModel {
  
   def main(args: Array[String]): Unit = {
  
    println("------------Creating Word2vec MOdel----------------------------!")
  //  val conf = new SparkConf().setAppName("Word2VecChotbot").setMaster("local[2]").set("spark.driver.allowMultipleContexts", "true");
  //  val sc = new SparkContext(conf)
    val sc = Word2VecSparkContext.sc
 //   val ssc = Word2VecSparkContext.ssc
    /** Train Word2Vec model */
    val input = sc.textFile("/home/cimb/corpusdata.txt").map(line => line.split(" ").toSeq)
  //  val input = ssc.textFileStream("/home/cimb/corpusdata.txt").map(line => line.split(" ").toSeq)
    
     
    val word2vec = new Word2Vec()
    word2vec.setVectorSize(50)
    val model = word2vec.fit(input)
    model.save(sc, "/home/cimb/chatbotmodel10")
  //  model.save(ssc, "/home/cimb/chatbotmodel11")

    
    
  //  val oos = new ObjectOutputStream(new FileOutputStream("/home/cimb/chatbotmodel8.txt"));
  //  oos.writeObject(model);
   // oos.close();
   // val oos = new ObjectOutputStream(new FileOutputStream("/home/cimb/chatbotmodel.txt"));
   // oos.writeObject(model);
   // oos.close();
    println("------------Word2vec MOdel saved successfully--------------------!")
  }

}
