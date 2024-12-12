package day07

import scala.collection.mutable.ArrayBuffer
import scala.util.boundary

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")
  val lines = input.split("\n")
  val operators = Array("+", "*")

  val result = lines
    .map(line =>
      val split = line.split(": ")
      val target = split(0).toLong
      val equation = split(1).split(" ").map(_.toLong)

      if tryOperators(target, equation, operators) then target
      else 0
    )
    .reduce((a, b) => a + b)

  println(s"Result: $result")
  return result.toString()

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")
  val lines = input.split("\n")
  val operators = Array("+", "*", "||")

  val result = lines
    .map(line =>
      val split = line.split(": ")
      val target = split(0).toLong
      val equation = split(1).split(" ").map(_.toLong)

      if tryOperators(target, equation, operators) then target
      else 0
    )
    .reduce((a, b) => a + b)

  println(s"Result: $result")
  return result.toString()

def tryOperators(
    target: Long,
    equation: Array[Long],
    operators: Array[String]
): Boolean =
  def evaluate(tryOps: Array[String]): Long =
    var result = equation(0)
    for (op, i) <- tryOps.zipWithIndex do
      val num = equation(i + 1)
      op match {
        case "+"  => result += num
        case "*"  => result *= num
        case "||" => result = result.toString.concat(num.toString).toLong
      }

    result

  def tryOperations(pos: Int, ops: Array[String]): Boolean =
    if (pos == equation.length - 1) {
      return evaluate(ops.toArray) == target
    }

    boundary {
      for op <- operators do
        ops(pos) = op
        if (tryOperations(pos + 1, ops)) boundary.break(true)
    } match {
      case true => return true
      case _    => return false
    }

  val operations = new Array[String](equation.length - 1)
  tryOperations(0, operations)
