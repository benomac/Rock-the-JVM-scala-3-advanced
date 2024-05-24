package com.rockthejvm.part1as

import scala.annotation.targetName
import scala.collection.immutable.ArraySeq
import scala.util.Try

object DarkSugars {

  // 1 - sugar for methods with 1 arg

  def singleArgMethod(arg: Int): Int = arg + 1
  val aMethodCall = singleArgMethod({
    // long code
    42
  })

  val aMethodCall_v2 = singleArgMethod {
    // long code
    42
  }

  // examples: Try, Future
  val aTryInstance = Try {
    throw new RuntimeException
  }

  //works with HOFS
  val anIncrementedList = List(1, 2, 3).map { x =>
    //code block
    x + 1
  }

  // 2 - single abstract method pattern (since scala 2.12)
  trait Action {
    // Can also have other implemented fields/methods
    def act(x: Int): Int
  }

  val anAction: Action = new Action {
    override def act(x: Int): Int = x + 1
  }
  val anotherAction: Action = (x: Int) => x + 1

  // example: Runnable
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("Hi scala, from another thread.")
  })

  val anotherThread = new Thread( () => println("Hi, Scala"))

  // 3 - methods ending in a : are RIGHT-ASSOCIATIVE (reverses the order of evaluation)
  val list = List(1, 2, 3)
  val aPrependedList = 0 :: list // == list.::(0)
  val aBigList = 0 :: 1 :: 2 :: List(3, 4) // == List(3, 4).::(2).::(1).::(0)

  class MyStream[T] {

    infix def -->:(value: T): MyStream[T] = this
  }
  val myStream = 1 -->: 2 -->: 3 -->: 4 -->: new MyStream[Int]

  // 4 - multi-word
  class Talker(name: String) {
    infix def `and then said`(gossip: String) = s"$name said $gossip"
  }

  // example: HTTP libraries
  object `Content-Type` {
    val `application/json` = "application/JSON"
  }

  // 5 - infix Types
  @targetName("Arrow")
  infix class -->[A, B]

  val compositeType: Int --> String = new --> [Int, String]

  // 6 - update()
  val anArray = Array(1, 2, 3, 4)
  anArray.update(2, 45)
  anArray(2) = 45 // same as above

  // 7 -mutable fields
  class Mutable {
    private var internalMember: Int = 0
    def membr = internalMember // "getter"
    def membr_=(value: Int): Unit =
      internalMember = value // "setter"
  }
  val aMutableContainer = new Mutable
  aMutableContainer.membr = 42 // aMutableContainer.member_=(42)

  // 8 - var args
  def methodWithVarargs(args: Int*) = {
    args.length
  }
  val callWithZeroArgs = methodWithVarargs()
  val callWithOneArg = methodWithVarargs(78)
  val callWithTwoArg = methodWithVarargs(78, 99)

  val aList2 = List(1, 2, 3, 4)
  val callWithDynamicArgs = methodWithVarargs(aList2*)


  def main(args: Array[String]): Unit = {
    val talker = new Talker("Ben")
    println(talker `and then said` "hello")
    println(callWithDynamicArgs)
  }

}
