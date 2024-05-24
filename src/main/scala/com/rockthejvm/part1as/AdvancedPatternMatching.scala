package com.rockthejvm.part1as

object AdvancedPatternMatching {
  /*
  PM:
  - constants
  - objects
  - wild cards
  - variables
  - infix patterns
  - case classes
   */

  class Person(val name: String, val age: Int)

  object Person {
    // unapply will also work with case classes
    def unapply(person: Person): Option[(String, Int)] = // person match { case Person(String, Int) => ... }
      Some(person.name, person.age)

    def unapply(age: Int): Option[String] = // int match { case Person(String) => ... }
      if(age < 18) Some("not allowed to drink")
      else Some("allowed to drink")
  }

  val ben = new Person("ben", 41)
  val bensPatternMatch = ben match {
    case Person(n, a) => s"Hi there, I'm $n" // "Person" refers to the Person object,
    // "ben" is the arg for the unapply method, "n" and "a" are what is returned from the tuple in the option
  }

  val bensLegalStatus = ben.age match {
    case Person(status) => s"Ben's legal drinking status is $status"
  }

  // boolean patterns
  /*
  aNumber match {
  case prop1 =>
  case prop2 =>
  case ...
  }
   */
  object Even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }
  object SingleDigit {
    def unapply(arg: Int): Boolean = arg > -10 && arg < 10
  }
  val n: Int = 32

  def mathProp(n: Int) = n match {
    case Even() => "an even number"
    case SingleDigit() => "single digit int"
    case _ => "no special properties"
  }

  // infix patterns
  infix case class Or[A, B](a: A, b: B)
  val anEither = Or(2, "two")
  val humanDescriptionEither = anEither match {
    case n Or s => s"$n is written as $s"
  }

  val aList = List(1, 2, 3)
  val listPatternMatching: String = aList match {
    case 1 :: next => "a list starting with 1"
    case _ => "a list not starting with 1"
  }

  // decompose sequences
  val varArg = aList match {
    case List(1, _*) => "List starting with 1"
    case _ => "some other list"
  }

  abstract class MyList[A] {
    def head: A = throw new NoSuchElementException
    def tail: MyList[A] = throw new NoSuchElementException
  }

  case class Empty[A]() extends MyList[A ]
  case class Cons[A](override val head: A, override  val tail: MyList[A]) extends MyList[A]

  object MyList {
    def unapplySeq[A](list: MyList[A]): Option[Seq[A]] = {
      if (list == Empty()) Some(Seq.empty)
      else unapplySeq(list.tail).map(restOfSeq => list.head +: restOfSeq)
    }
  }

  val myList = Cons(1, Cons(2, Cons(3, Empty())))
  val varargCustom = myList match {
    case MyList(1, _*) => "list starting with 1"
    case _ => "some other list"
  }
  val empty = Empty()

  // custom return type for unapply
  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String] {
      override def isEmpty: Boolean = false

      override def get: String = person.name
    }
  }

  val weirdPersonPM = ben match {
    case PersonWrapper(name) => s"Hey my name is $name"
  }

  def main(args: Array[String]): Unit = {
    println(mathProp(n))
    println(empty.head)
    println(varargCustom)
  }
}
