package com.cimb.chatbot.controller

import org.apache.spark._
import org.apache.spark.rdd._
import org.apache.spark.SparkContext._
import org.apache.spark.mllib.feature.Word2VecModel
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import breeze.linalg.DenseVector
import breeze.linalg._
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

object CIMBWord2Vec {
    
   
  //  def CIMBWord2Vec() ={
   // }
      println("---------------Loading Word2VecMOdel ---------------------!")
  //    val ois = new ObjectInputStream(new FileInputStream("/home/cimb/chatbotmodel.txt"))
  //    val ois = new ObjectInputStream(new FileInputStream("/home/cimb/chatbotmodel7.obj"))
   //   val model = ois.readObject().asInstanceOf[org.apache.spark.mllib.feature.Word2VecModel]
   //   ois.close()
    //  model.asInstanceOf[Word2VecModel]
     
 
  //    val conf = new SparkConf().setAppName("Word2VecChotbot").setMaster("local[2]").set("spark.driver.allowMultipleContexts", "true");
   //   val sc = new SparkContext(conf)
        val sc = Word2VecSparkContext.sc
        val model = Word2VecModel.load(sc, "/home/cimb/chatbotmodel10")
  //    val model = Word2VecModel.load(sc, "/home/cimb/chatbotmodel.txt")
  
     def wordToVector(w: String, m: Word2VecModel): Vector = {
        try {
          return m.transform(w)
        } catch {
          case e: Exception => return Vectors.zeros(50)
        }
      }
  

  def getVector(w: String): String = {
       return w
      }
  
   def main(args: Array[String]): Unit = {
     // var z = Array("airasia","balance")
      var z = Array("kwik", "account", "last","transaction")
      println("kwik---"+getVector(z))
   }
  def getVector(word: Array[String]) : Vector = {
   
    var i = 0;
    var s = new Array[Double](50);
    val total = new DenseVector(s.toArray);
    //  println("-----before model--");
    for (i <- 0 to (word.length - 1)) {
  //    println("word is---" + word(i).toLowerCase())
      val tokenVec = wordToVector(word(i).toLowerCase(), model)
    //  println("tokenVec1" + tokenVec)
      val bv = new DenseVector(tokenVec.toArray)
    //  println("tokenVec" + bv)
      total += bv;
    }
    //  println("-----after model--");
    val finalVal = Vectors.dense(total.toArray)
 //   println("---final vector---" + finalVal);
    return finalVal
  }
  
}