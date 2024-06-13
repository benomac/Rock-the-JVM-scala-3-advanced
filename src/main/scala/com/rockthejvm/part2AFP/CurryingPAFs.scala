package com.rockthejvm.part2AFP

object CurryingPAFs {

  // currying
  val superAdder: Int => Int => Int =
    x => y => x + y
  val add3: Int => Int = superAdder(3)
  val added3: Int = add3(2) // 5
  val added_V2 = superAdder(3)(2)

  // curried methods
  def curriedAdder(x: Int)(y: Int): Int = x + y

  // methods != function values

  // converting methods to functions
  val add4: Int => Int = curriedAdder(4) // eta-expansion (Partially Applied Function) PAF
  val nine = add4(5) // 9

  def inc(x: Int) = x + 1
  val list = List(1, 2, 3)
  val anIncList = list.map(inc) // eta-expansion

  //underscores are powerful
  def concatenator(a: String, b: String, c: String): String = a + b + c
  val insertName = concatenator("hello, my name is ", _: String, ", I'm going to show you a nice scala trick.")
  // the same as above
  val insertName_v2 = x => concatenator("hello, my name is ", x, ", how are you?")

  val fillInTheBlanks = concatenator(_: String, " ben ", _: String) // (x, y) => concatenator(x, " ben ", y)

  // exercises
  val simpleAddFunction = (x: Int, y:Int) => x + y
  def simpleAddMethod(x :Int, y: Int) = x + y
  def curriedMethod(x: Int)(y: Int) = x + y

  // add7
  def add7 = simpleAddMethod(7, 4)
  def add7b = simpleAddMethod(_: Int, 7)
  def add7c = simpleAddMethod(7, _: Int)
  def add7d = simpleAddFunction(7, _: Int)
  def add7e = simpleAddFunction(_: Int, 7)
  val add7f = (x: Int) => curriedMethod(x)(7)

  //2 - process a list of numbers and return their string rep under different formats
  val piWith2Decimals = "%8.6f".format(Math.PI)
  // step 1. create a curried formatting method with a formatting string and a value
  // step 2. process a list of numbers with various formats
  def processNumbers(stringFormat: String)(value: Double): String =
    stringFormat.format(value)

  val format1 = processNumbers("%4.2f")

  val numList = List(1.264563342, 2.23464564324, 3.42546546543, 6.43523465354, 6.687456756)

  // methods vs functions + by-name vs 0-lambdas
  def byName(n: => Int) = n + 1
  def byLambda(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  byName(23) // ok
  byName(method) // eta-expanded? NO - method is INVOKED in this case
  byName(parenMethod()) // simple


  def main(args: Array[String]): Unit = {
    println(insertName("ben"))
    println(insertName_v2("ben"))
    println(fillInTheBlanks("hi", "how are you?"))
    println(piWith2Decimals)
    println(numList.map(processNumbers("%4.2f")))
    println(numList.map(processNumbers("%8.6f")))
    println(numList.map(processNumbers("%14.12f")))
  }
}
