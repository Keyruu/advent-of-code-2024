package day12

import scala.collection.immutable.Queue

@main
def part1(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")
  val grid = parseInput(input)
  var visited = Set.empty[Position]
  val result = (for
    x <- 0 until grid.rows
    y <- 0 until grid.cols
  yield
    val curr = Position(x, y)
    if !visited(curr) then
      val region = grid.bfs(curr)
      val perimeter = (for pos <- region yield
        visited += pos
        grid.getFences(pos).size
      ).sum

      println(
        s"letter: ${grid(curr).get}, region: ${region.size}, perimeter: $perimeter"
      )
      region.size * perimeter
    else 0
  ).sum

  println(s"Result: $result")
  return result.toString()

case class Position(x: Int, y: Int)

case class Grid(cells: Vector[Vector[String]]):
  val directions = Seq(
    Position(-1, 0), // up
    Position(1, 0), // down
    Position(0, -1), // left
    Position(0, 1) // right
  )

  val rows: Int = cells.length
  val cols: Int = cells.headOption.map(_.length).getOrElse(0)

  def getFences(pos: Position): Seq[Position] =
    directions
      .map(d => Position(pos.x + d.x, pos.y + d.y))
      .filter((next) => isNotValid(pos, next))

  def getRegion(pos: Position): Int =
    bfs(pos).size

  def apply(pos: Position): Option[String] =
    if (isValid(pos)) Some(cells(pos.x)(pos.y))
    else None

  def isValid(pos: Position): Boolean =
    pos.x >= 0 && pos.x < rows && pos.y >= 0 && pos.y < cols

  def isNotValid(current: Position, pos: Position): Boolean =
    this(pos) == None || this(current) != this(pos)

  def bfs(start: Position): Seq[Position] =
    var queue = Queue.empty[Position]
    var visited = Set.empty[Position]

    queue = queue.enqueue(start)
    visited += start

    while (queue.nonEmpty) do
      val (current, rest) = queue.dequeue
      queue = rest

      for d <- directions
      do
        val neighbor = Position(current.x + d.x, current.y + d.y)
        if isValid(neighbor) && !visited(neighbor)
          && this(current) == this(neighbor)
        then
          queue = queue.enqueue(neighbor)
          visited += neighbor

    visited.toSeq

def parseInput(input: String): Grid =
  val lines = input.trim.split("\n")
  Grid(lines.map(_.split("").toVector).toVector)

@main
def part2(path: String): String =
  val input = os.read(os.pwd / "inputs" / s"$path.txt")

  return ""
