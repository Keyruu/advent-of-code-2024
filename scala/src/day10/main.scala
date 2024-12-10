package day10

import scala.util.boundary
import scala.collection.mutable

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val grid = parseInput(input)
  val trailheads = findTrailheads(grid)
  val result = trailheads.map(calculateTrailheadScore(grid, _)).sum

  println(s"Result: $result")
  return result.toString()

case class Position(x: Int, y: Int)

case class Grid(cells: Vector[Vector[Int]]):
  val directions = Seq(
    Position(-1, 0), // up
    Position(1, 0), // down
    Position(0, -1), // left
    Position(0, 1) // right
  )

  val rows: Int = cells.length
  val cols: Int = cells.headOption.map(_.length).getOrElse(0)

  def getNeighbors(pos: Position): Seq[Position] =
    directions
      .map(d => Position(pos.x + d.x, pos.y + d.y))
      .filter(isValid)

  def apply(pos: Position): Option[Int] =
    if (isValid(pos)) Some(cells(pos.x)(pos.y))
    else None

  def isValid(pos: Position): Boolean =
    pos.x >= 0 && pos.y < rows && pos.y >= 0 && pos.y < cols

def parseInput(input: String): Grid =
  val lines = input.trim.split("\n")
  Grid(lines.map(_.map(_.asDigit).toVector).toVector)

def findTrailheads(grid: Grid): Seq[Position] =
  (for
    x <- 0 until grid.rows
    y <- 0 until grid.cols
    if grid(Position(x, y)).contains(0)
  yield Position(x, y)).toSeq

def calculateTrailheadScore(grid: Grid, start: Position): Int =
  def findNines(): Set[Position] =
    var visited = Set.empty[Position]
    var queue = List((start, 0)) // (position, current height)
    var reachableNines = Set.empty[Position]

    while queue.nonEmpty do
      val ((pos, height), rest) = (queue.head, queue.tail)
      queue = rest

      if (!visited(pos)) then
        visited += pos

        if (grid(pos).contains(9)) then reachableNines += pos

        val nextHeight = height + 1
        for
          neighbor <- grid.getNeighbors(pos)
          if !visited(neighbor)
          if grid(neighbor).contains(nextHeight)
        do queue = queue :+ (neighbor, nextHeight)

    reachableNines

  findNines().size

def calculateTrailheadRating(grid: Grid, start: Position): Int =
  def countPaths(
      current: Position,
      currentHeight: Int,
      visited: Set[Position]
  ): Int =
    if (currentHeight == 9) then 1
    else
      val neighbors = grid
        .getNeighbors(current)
        .filter(p => grid(p).contains(currentHeight + 1))
        .filterNot(visited.contains)

      neighbors.map { next =>
        countPaths(next, currentHeight + 1, visited + current)
      }.sum

  // Start counting paths from the trailhead
  countPaths(start, 0, Set.empty)

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  val grid = parseInput(input)
  val trailheads = findTrailheads(grid)
  val result = trailheads.map(calculateTrailheadRating(grid, _)).sum

  println(s"Result: ${result.toString()}")
  return result.toString()
