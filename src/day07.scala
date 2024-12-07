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

      tryOperators(target, equation, operators) match {
        case Some(result) => result
        case None         => 0
      }
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

      tryOperators(target, equation, operators) match {
        case Some(result) => result
        case None         => 0
      }
    )
    .reduce((a, b) => a + b)

  println(s"Result: $result")
  return result.toString()

def tryOperators(
    target: Long,
    equation: Array[Long],
    operators: Array[String]
): Option[Long] =

  val operatorCombinations = List
    .fill(equation.length - 1)(operators)
    .foldLeft(List(List.empty[String])) { (acc, ops) =>
      for {
        existing <- acc
        op <- ops
      } yield existing :+ op
    }

  operatorCombinations.find { ops =>
    val result = ops
      .zip(equation.tail)
      .foldLeft(equation.head) { case (acc, (op, num)) =>
        op match {
          case "+"  => acc + num
          case "*"  => acc * num
          case "||" => acc.toString.concat(num.toString).toLong
        }
      }
    result == target
  } match {
    case Some(_) => Some(target)
    case None    => None
  }
