package com.rockthejvm.part2AFP

object PartialFunctions {

  val aFunction: Int => Int = x => x + 1

  val aFussyFunction = (x: Int) =>
    if (x == 1) 42
    else if (x == 2) 56
    else if (x == 5) 999
    else throw new RuntimeException("no suitable cases found")

  val aFussyFunction_v2: Int => Int = (x: Int) => x match {
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  // partial function
  val aPartialFunction: PartialFunction[Int, Int] = { // x => x match { ... }
    case 1 => 42
    case 2 => 56
    case 5 => 999
  }

  // utilities on partial functions
  val canCallOn37 = aPartialFunction.isDefinedAt(37)
  val liftedPF = aPartialFunction.lift // Int => Option[Int]
  val anotherPf: PartialFunction[Int, Int] = {
    case 45 => 86
  }
  val pfChain = aPartialFunction.orElse[Int, Int](anotherPf)

  // HOF accept partial functions as args
  val aList = List(1, 2, 3, 4)

  val aChangedList = aList.map { x => x match
    case 1 => 4
    case 2 => 3
    case 3 => 45
    case 4 => 67
    case _ => 0
  }

  val aChangedList_v2 = aList.map({ // possible because PartialFunction[A, B] extends Function1[A, B]
    case 1 => 4
    case 2 => 3
    case 3 => 45
    case 4 => 67
    case _ => 0
  })

  val aChangedList_v3 = aList.map { // possible because PartialFunction[A, B] extends Function1[A, B]
    case 1 => 4
    case 2 => 3
    case 3 => 45
    case 4 => 67
    case _ => 0
  }

  case class Person(name: String, age: Int)

  val someKids = List(
    Person("alice", 3),
    Person("bobby", 5),
    Person("jane", 4)
  )

  val kidsAged = someKids.map(person => Person(person.name, person.age + 1))

  val kidsAged_v2 = someKids.map {
    case Person(name, age) => Person(name, age + 1)
  }

  def main(args: Array[String]): Unit = {
//    println(pfChain(45))
    println(aFussyFunction_v2(65461))
//    println(liftedPF(1)) // Some(42)
//    println(liftedPF(37)) // None
  }

}
