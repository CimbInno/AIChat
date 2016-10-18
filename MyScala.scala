package com.cimb.chatbot.controller;

 object MyScala {
  var firstname = "John"
  def hello = (s: String) => s + firstname
  
   def hello123(w: String): String = {
       return w
      }

  def main(args: Array[String]) {
    println(firstname)
    println(hello("Hello "))
    firstname = "Hans" // [2]
    println(hello("Hallo "))

    val ciao = hello
    println(ciao("Ciao "))
  }
 }