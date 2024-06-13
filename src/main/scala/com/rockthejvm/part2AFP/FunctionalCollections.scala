package com.rockthejvm.part2AFP

object FunctionalCollections {

  // sets are functions A => Boolean
  val aSet: Set[String] = Set("I", "love", "Scala")
  val setContainsScala: Boolean = aSet("Scala") //true

  // Seq are PartialFunctions[Int, A]
  val aSeq: Seq[Int] = Seq(1, 2, 3, 4)
  val anElement = aSeq(2) // 3
  // val aNonExistingElement = aSeq(10) // throw OutOfBoundsException

  // Map[K, V] "extends" PartialFunction[K, V]
  val aPhoneBook: Map[String, Int] = Map(
    "ben" -> 123,
    "dawn" -> 321,
    "Heath" -> 987
  )
  val bensNumber = aPhoneBook("ben")
  //val danielsNumer = aPhoneBook("daniel") // throw NoSuchElementException

  def main(args: Array[String]): Unit = {
    println()
  }
}
