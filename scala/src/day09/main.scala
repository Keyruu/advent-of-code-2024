package day09

import scala.collection.mutable.ArrayBuffer
import java.util.Collections
import scala.util.boundary

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val blocks = input
    .split("")
    .filter(!_.isBlank)
    .zipWithIndex
    .flatMap { case (char, index) =>
      val count = char.toInt
      val symbol = if ((index + 1) % 2 == 0) then "." else (index / 2).toString
      List.fill(count)(symbol)
    }

  val blocksBuffer = ArrayBuffer.from(blocks)
  var lastIndex = blocksBuffer.length - 1
  boundary {
    for
      (char, i) <- blocksBuffer.zipWithIndex
      if char == "."
    do
      boundary {
        for
          j <- lastIndex to 0 by -1
          if blocks(j) != "." || j == 0
        do
          lastIndex = j
          boundary.break()
      }
      if i < lastIndex && lastIndex != 0 then
        blocksBuffer(i) = blocks(lastIndex).toString
        blocksBuffer(lastIndex) = "."
        lastIndex = lastIndex - 1
      else boundary.break()
  }

  val result = blocksBuffer
    .filter(_ != ".")
    .zipWithIndex
    .foldLeft(0L) { case (acc, (str, i)) =>
      acc + (i.toLong * str.toLong)
    }
    .toString()

  println(s"Result: ${result.toString()}")
  return result.toString()

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val blocks = input
    .split("")
    .filter(!_.isBlank)
    .zipWithIndex
    .flatMap { case (char, index) =>
      val count = char.toInt
      val symbol = if ((index + 1) % 2 == 0) then "." else (index / 2).toString
      List.fill(count)(symbol)
    }

  val blocksBuffer = ArrayBuffer.from(blocks)
  var lastJ = blocksBuffer.length - 1
  var count = 0
  boundary:
    for
      j <- lastJ to 0 by -1
      if blocks(j) != "."
    do
      if blocks(j) == blocks(lastJ)
      then count = count + 1
      else
        var dotCount = 1
        var lastI = 0
        boundary:
          for
            (char, i) <- blocksBuffer.zipWithIndex
            if char == "."
          do
            if dotCount == count then
              if dotCount == 1 then lastI = i
              for swapCount <- 0 to count - 1 do
                blocksBuffer(lastI - swapCount) =
                  blocksBuffer(lastJ - swapCount)
                blocksBuffer(lastJ - swapCount) = "."
              boundary.break()

            if lastI + 1 == i
            then dotCount = dotCount + 1
            else dotCount = 1

            lastI = i

            if i > lastJ then
              lastI = -1
              boundary.break()
        lastJ = j
        count = 1
        if lastI > lastJ then boundary.break()

  val result = blocksBuffer.zipWithIndex
    .foldLeft(0L) { case (acc, (str, i)) =>
      if str != "." then acc + (i.toLong * str.toLong)
      else acc
    }
    .toString()

  println(s"Result: ${result.toString()}")
  return result.toString()
