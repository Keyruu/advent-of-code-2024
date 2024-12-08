package day08

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.boundary

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")
  val grid = input.split("\n").map(_.split(""))
  val map: Map[String, Array[Position]] = grid.zipWithIndex
    .flatMap { case (line, i) =>
      line.zipWithIndex.collect {
        case (char, j) if char != "." =>
          char -> Position(i, j)
      }
    }
    .groupMap { case (str, _) => str } { case (_, pos) => pos }

  val pairs = map.values.flatMap { positions =>
    positions.combinations(2).map { case Array(pos1, pos2) =>
      (pos1, pos2)
    }
  }.toSet

  var antinodes = mutable.Set.empty[Position]
  val result = pairs.foldLeft(0) { case (acc, (pos1, pos2)) =>
    var count = 0
    calculateDistance(grid, pos1, pos2).foreach { pos =>
      if !antinodes.contains(pos) then
        antinodes.add(pos)
        count += 1
    }
    calculateDistance(grid, pos2, pos1).foreach { pos =>
      if !antinodes.contains(pos) then
        antinodes.add(pos)
        count += 1
    }

    acc + count
  }

  println(s"Result: $result")
  return result.toString()

def calculateDistance(
    grid: Array[Array[String]],
    first: Position,
    second: Position
): Option[Position] =
  val next =
    Position(first.x + (first.x - second.x), first.y + (first.y - second.y))
  if isValidPosition(next.x, next.y, grid) then Some(next) else None

def calculateLine(
    grid: Array[Array[String]],
    first: Position,
    second: Position
): Array[Position] =
  var arr = ArrayBuffer.empty[Position]

  var next = first
  var previous = second
  boundary {
    while true do
      calculateDistance(grid, next, previous) match
        case Some(pos) =>
          previous = next
          next = pos
          arr.append(pos)
        case None => boundary.break()
  }

  return arr.toArray

def isValidPosition(
    x: Int,
    y: Int,
    grid: Array[Array[String]]
): Boolean =
  x < grid.length && x >= 0 && y < grid(0).length && y >= 0

case class Position(val x: Int, val y: Int)

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")
  val grid = input.split("\n").map(_.split(""))
  val map: Map[String, Array[Position]] = grid.zipWithIndex
    .flatMap { case (line, i) =>
      line.zipWithIndex.collect {
        case (char, j) if char != "." =>
          char -> Position(i, j)
      }
    }
    .groupMap { case (str, _) => str } { case (_, pos) => pos }

  val pairs = map.values.flatMap { positions =>
    positions.combinations(2).map { case Array(pos1, pos2) =>
      (pos1, pos2)
    }
  }.toSet

  var antinodes = mutable.Set.empty[Position]
  val result = pairs.foldLeft(0) { case (acc, (pos1, pos2)) =>
    var count = 0
    if !antinodes.contains(pos1) then
      antinodes.add(pos1)
      count += 1

    if !antinodes.contains(pos2) then
      antinodes.add(pos2)
      count += 1

    calculateLine(grid, pos1, pos2).foreach { pos =>
      if !antinodes.contains(pos) then
        antinodes.add(pos)
        println(pos)
        count += 1
    }
    calculateLine(grid, pos2, pos1).foreach { pos =>
      if !antinodes.contains(pos) then
        antinodes.add(pos)
        println(pos)
        count += 1
    }

    acc + count
  }

  println(s"Result: $result")
  return result.toString()
