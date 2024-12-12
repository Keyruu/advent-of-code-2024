package day11

import scala.collection.mutable

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val stones = input.trim().split(" ").filter(_ != "").map(_.toLong).toVector

  val result = solve(stones, 25)

  println(s"Result: $result")
  return result.toString()

def solve(stones: Vector[Long], blinks: Int): Int =
  var total = 0
  var currentStones = stones

  for _ <- 0 until blinks do
    currentStones = currentStones.flatMap { stone =>
      if stone == 0 then Vector(1)
      else if stone.toString().length() % 2 == 0 then
        val str = stone.toString()
        val (left, right) = str.toString().splitAt(str.length() / 2)
        Vector(left.toInt, right.toInt)
      else Vector(stone * 2024)
    }

  currentStones.size

def solve2(stones: Vector[Long], blinks: Int): Long =
  var fishMap = stones
    .groupBy(identity)
    .view
    .mapValues(_.size.toLong)
    .toMap
    .withDefaultValue(0L)

  for _ <- 0 until blinks do
    println(s"Current state: $fishMap")
    val newFishMap = mutable.Map[Long, Long]().withDefaultValue(0L)

    fishMap.foreach { (stone, count) =>
      if stone == 0 then newFishMap(1) = newFishMap(1) + count
      else if stone.toString().length() % 2 == 0 then
        val str = stone.toString
        val (left, right) = str.splitAt(str.length / 2)
        val leftNum = left.toInt
        val rightNum = right.toInt
        newFishMap(leftNum) = newFishMap(leftNum) + count
        newFishMap(rightNum) = newFishMap(rightNum) + count
      else newFishMap(stone * 2024L) = newFishMap(stone * 2024L) + count
    }

    fishMap = newFishMap.toMap

  fishMap.values.sum.toLong

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val stones = input.trim().split(" ").filter(_ != "").map(_.toLong).toVector

  val result = solve2(stones, 75)

  println(s"Result: $result")
  return result.toString()
