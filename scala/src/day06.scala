package day06

import os.Path
import scala.runtime.Arrays
import java.{util => ju}
import scala.util.boundary
import scala.compiletime.ops.boolean
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable
import upack.Arr

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  var start = Route(0, 0, Direction.Up)
  val lines = input.split("\n")
  var visited = ArrayBuffer.empty[Route]
  val grid = lines.zipWithIndex.map { case (line, i) =>
    line
      .split("")
      .zipWithIndex
      .map { case (point, j) =>
        if point == "^" then
          start = Route(i, j, Direction.Up)
          visited.append(start)
        point
      }
      .to(ArrayBuffer)
  }

  var current = start
  boundary:
    while true do
      val next = move(grid, current)
      if next == current
      then boundary.break()

      current = next
      if !visited.exists(r => r.x == current.x && r.y == current.y) then
        visited.append(current)

  println(visited.length)
  return visited.length.toString()

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  var start = Route(0, 0, Direction.Up)
  val lines = input.split("\n")
  val grid = lines.zipWithIndex
    .map { case (line, i) =>
      line
        .split("")
        .zipWithIndex
        .map { case (point, j) =>
          if point == "^" then start = Route(i, j, Direction.Up)
          point
        }
        .to(ArrayBuffer)
    }

  val (loops, _) = navigate(grid, start, true)
  println(loops)
  return s"$loops"

def navigate(
    grid: Array[ArrayBuffer[String]],
    start: Route,
    checkLoop: Boolean
): Tuple2[Int, Boolean] =
  val gridLen = grid.length
  val lineLen = grid(0).length
  var current = Route(start.x, start.y, start.direction)
  var loops = ArrayBuffer.empty[Route]

  while true do
    val next = move(grid, current)
    if next == current
    then return (loops.length, false)

    current = next
    if !(current.x == start.x && current.y == start.y) &&
      checkLoop &&
      !loops.exists(r => r.x == current.x && r.y == current.y)
    then
      var scopedGrid = grid.map { _.clone() }
      scopedGrid(current.x)(current.y) = "#"
      if detectLoopFloyd(
          scopedGrid,
          extraObstacle = Route(current.x, current.y, current.direction),
          start = Route(start.x, start.y, start.direction)
        )
      then loops.append(current)

  return (0, false)

def move(grid: Array[ArrayBuffer[String]], r: Route): Route =
  val (dx, dy) = Direction.matrix(r.direction)
  val nextX = r.x + dx
  val nextY = r.y + dy

  if !isValidPosition(nextX, nextY, grid) then r
  else if grid(nextX)(nextY) == "#"
  then move(grid, Route(r.x, r.y, Direction.next(r.direction)))
  else Route(nextX, nextY, r.direction)

def isValidPosition(
    x: Int,
    y: Int,
    grid: Array[ArrayBuffer[String]]
): Boolean =
  !(x > grid.length - 1 || x < 0 || y > grid(0).length - 1 || y < 0)

def detectLoopFloyd(
    grid: Array[ArrayBuffer[String]],
    extraObstacle: Route,
    start: Route
): Boolean =
  var tortoise = move(grid, start)
  var hare = move(grid, move(grid, start))

  while tortoise != hare do
    if (hare == move(grid, hare)) return false // Stuck in place
    tortoise = move(grid, tortoise)
    hare = move(grid, move(grid, hare))

  true

enum Direction:
  case Up, Right, Down, Left

object Direction:
  def matrix(d: Direction): (Int, Int) =
    d match {
      case Up    => (-1, 0)
      case Right => (0, 1)
      case Down  => (1, 0)
      case Left  => (0, -1)
    }

  def next(d: Direction): Direction =
    d match
      case Up    => Right
      case Right => Down
      case Down  => Left
      case Left  => Up

case class Route(var x: Int, var y: Int, var direction: Direction):
  override def toString = s"($x, $y, $direction)"
