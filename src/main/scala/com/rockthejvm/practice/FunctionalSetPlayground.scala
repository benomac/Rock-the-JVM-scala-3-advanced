package com.rockthejvm.practice

import scala.annotation.tailrec

abstract class FSet[A] extends (A => Boolean) {
  // main api
  def contains(elem: A): Boolean

  def apply(elem: A): Boolean = contains(elem)

  infix def +(elem: A): FSet[A] // add to set

  infix def ++(anotherSet: FSet[A]): FSet[A] // concat sets

  // "classics"
  def map[B](f: A => B): FSet[B]

  def flatMap[B](f: A => FSet[B]): FSet[B]

  def filter(predicate: A => Boolean): FSet[A]

  def foreach(f: A => Unit): Unit

  // utilities
  infix def -(elem: A): FSet[A]

  infix def --(anotherSet: FSet[A]): FSet[A]

  infix def &(anotherSet: FSet[A]): FSet[A]

}

//case class AllInclusiveSet[A]() extends FSet[A] {
//
//}

case class Empty[A]() extends FSet[A]:
  override def contains(elem: A): Boolean = false

  override infix def +(elem: A): FSet[A] = Cons(elem, Empty())

  override infix def ++(anotherSet: FSet[A]): FSet[A] = anotherSet

  override def map[B](f: A => B): FSet[B] = Empty[B]()

  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty[B]()

  override def filter(predicate: A => Boolean): FSet[A] = this

  override def foreach(f: A => Unit): Unit = ()

  override infix def -(elem: A): FSet[A] = this

  override infix def --(anotherSet: FSet[A]): FSet[A] = this

  override infix def &(anotherSet: FSet[A]): FSet[A] = this


case class Cons[A](head: A, tail: FSet[A]) extends FSet[A]:

  override def contains(elem: A): Boolean =
    elem == head || tail.contains(elem)

  override infix def +(elem: A): FSet[A] = {
    if (contains(elem)) this
    else Cons(elem, this)
  }

  override infix def ++(anotherSet: FSet[A]): FSet[A] =
    tail ++ anotherSet + head

  override def map[B](f: A => B): FSet[B] =
    tail.map(f) + f(head)

  override def flatMap[B](f: A => FSet[B]): FSet[B] = tail.flatMap(f) ++ f(head)

  override def filter(predicate: A => Boolean): FSet[A] = {
    if (predicate(head))
      tail.filter(predicate) + head
    else
      tail.filter(predicate)
  }

  override def foreach(f: A => Unit): Unit =
    f(head)
    tail.foreach(f)

  override infix def -(elem: A): FSet[A] =
    if (head == elem) tail
    else tail.-(elem) + head
  //    filter(_ != elem)

  override infix def --(anotherSet: FSet[A]): FSet[A] = filter(x => !anotherSet(x))

  override infix def &(anotherSet: FSet[A]): FSet[A] = filter(x => anotherSet(x))


object FSet {
  def apply[A](values: A*): FSet[A] = {
    def buildSet(valuesSeq: Seq[A], acc: FSet[A]): FSet[A] = {
      if (valuesSeq.isEmpty) acc
      else buildSet(valuesSeq.tail, acc + valuesSeq.head)
    }

    buildSet(values, Empty())
  }
}

object FunctionalSetPlayground {

  val aSet: FSet[Int] = Cons(1, Cons(2, Cons(2, Empty())))

  def main(args: Array[String]): Unit = {
    val first5 = FSet.apply(1, 2, 3, 4, 5)
    val someNumber = FSet(4, 5, 6, 7, 8, 3)
    //    println(first5.contains(5)) // true
    //    println(first5(6)) // false
    //    println((first5 + 10).contains(10)) // true
    //    println(first5.map(_ * 2).contains(10)) // true
    //    println(first5.map(_ % 2).contains(1)) // true
    //    println(first5.flatMap(x => FSet(x, x + 1)).contains(7)) //false
    //    (first5 + 1).foreach(println)

    //    println((first5 - 3).contains(3)) // false

    //    println((first5 & someNumber).contains(4))

    //    (first5 - 1 - 5).foreach(print) // false
    //    println
    //    (first5 -- someNumber).foreach(print)
    //    println
    println(Set(1, 2, 3) -- Set(2, 3))

  }

}
