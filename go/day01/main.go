package main

import (
	"bufio"
	"common"
	"math"
	"sort"
	"strconv"
	"strings"
)

func main() {
	common.Solve(Part1)
	common.Solve(Part2)
}

func Part1(scanner *bufio.Scanner) string {
	leftList := []int{}
	rightList := []int{}
	for scanner.Scan() {
		text := scanner.Text()
		split := strings.Split(text, "   ")
		left, err := strconv.Atoi(split[0])
		common.Check(err)
		right, err := strconv.Atoi(split[1])
		common.Check(err)

		leftList = append(leftList, left)
		rightList = append(rightList, right)
	}
	sort.Ints(leftList)
	sort.Ints(rightList)

	totalDistance := 0
	for i := 0; i < len(leftList); i++ {
		totalDistance += int(math.Abs(float64(leftList[i] - rightList[i])))
	}
	return strconv.Itoa(totalDistance)
}

func Part2(scanner *bufio.Scanner) string {
	leftList := []int{}
	rightMap := make(map[int]int)
	for scanner.Scan() {
		text := scanner.Text()
		split := strings.Split(text, "   ")
		left, err := strconv.Atoi(split[0])
		common.Check(err)
		right, err := strconv.Atoi(split[1])
		common.Check(err)

		leftList = append(leftList, left)
		rightMap[right] += 1
	}

	total := 0
	for _, left := range leftList {
		total += left * rightMap[left]
	}
	return strconv.Itoa(total)
}
