package main

import (
	"bufio"
	"common"
	"strconv"
)

func main() {
	common.Solve(Part1)
	common.Solve(Part2)
}

func Part1(scanner *bufio.Scanner) string {
	total := 0
	matrix := [][]string{}
	for scanner.Scan() {
		line := scanner.Text()
		lineArray := make([]string, len(line))
		for i, char := range line {
			lineArray[i] = string(char)
		}
		matrix = append(matrix, lineArray)
	}
	for i := 0; i < len(matrix); i++ {
		for j := 0; j < len(matrix[i]); j++ {
			total += getXmas(matrix, i, j)
		}
	}
	return strconv.Itoa(total)
}

func getXmas(matrix [][]string, i int, j int) int {
	total := 0
	letter := matrix[i][j]

	if letter == "X" {
		xLayer := matrix[i]

		if j >= 3 {
			if xLayer[j-1] == "M" && xLayer[j-2] == "A" && xLayer[j-3] == "S" {
				total++
			}
		}
		if len(xLayer)-1-j >= 3 {
			if xLayer[j+1] == "M" && xLayer[j+2] == "A" && xLayer[j+3] == "S" {
				total++
			}
		}

		if i >= 3 {
			total += checkOtherLayers(matrix, i, j, true)
		}

		if len(matrix)-1-i >= 3 {
			total += checkOtherLayers(matrix, i, j, false)
		}
	}

	return total
}

func checkOtherLayers(matrix [][]string, i int, j int, up bool) int {
	xLayer := matrix[i]
	total := 0

	var mLayer []string
	var aLayer []string
	var sLayer []string

	if up {
		mLayer = matrix[i-1]
		aLayer = matrix[i-2]
		sLayer = matrix[i-3]
	} else {
		mLayer = matrix[i+1]
		aLayer = matrix[i+2]
		sLayer = matrix[i+3]
	}

	if mLayer[j] == "M" && aLayer[j] == "A" && sLayer[j] == "S" {
		total++
	}
	if j >= 3 {
		if mLayer[j-1] == "M" && aLayer[j-2] == "A" && sLayer[j-3] == "S" {
			total++
		}
	}
	if len(xLayer)-1-j >= 3 {
		if mLayer[j+1] == "M" && aLayer[j+2] == "A" && sLayer[j+3] == "S" {
			total++
		}
	}
	return total
}

func Part2(scanner *bufio.Scanner) string {
	total := 0
	matrix := [][]string{}
	for scanner.Scan() {
		line := scanner.Text()
		lineArray := make([]string, len(line))
		for i, char := range line {
			lineArray[i] = string(char)
		}
		matrix = append(matrix, lineArray)
	}
	for i := 0; i < len(matrix); i++ {
		for j := 0; j < len(matrix[i]); j++ {
			total += getMas(matrix, i, j)
		}
	}
	return strconv.Itoa(total)
}

func getMas(matrix [][]string, i int, j int) int {
	letter := matrix[i][j]

	if letter == "A" {
		aLayer := matrix[i]

		if i > 0 && len(matrix)-1-i > 0 {
			if j > 0 && len(aLayer)-1-j > 0 {
				upLayer := matrix[i-1]
				bottomLayer := matrix[i+1]

				upperLeft := upLayer[j-1]
				upperRight := upLayer[j+1]
				bottomRight := bottomLayer[j+1]
				bottomLeft := bottomLayer[j-1]

				if upperLeft == "M" && bottomRight == "S" || upperLeft == "S" && bottomRight == "M" {
					if upperRight == "M" && bottomLeft == "S" || upperRight == "S" && bottomLeft == "M" {
						return 1
					}
				}
			}
		}
	}
	return 0
}
