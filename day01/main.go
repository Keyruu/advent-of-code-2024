package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"sort"
	"strconv"
	"strings"
)

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func main() {
	file, err := os.Open("./.input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	// optionally, resize scanner's capacity for lines over 64K, see next example
	fmt.Printf("Result: %s", Part2(scanner))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}

func Part1(scanner *bufio.Scanner) string {
	leftList := []int{}
	rightList := []int{}
	for scanner.Scan() {
		text := scanner.Text()
		split := strings.Split(text, "   ")
		left, err := strconv.Atoi(split[0])
		check(err)
		right, err := strconv.Atoi(split[1])
		check(err)

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
		check(err)
		right, err := strconv.Atoi(split[1])
		check(err)

		leftList = append(leftList, left)
		rightMap[right] += 1
	}

	total := 0
	for _, left := range leftList {
		total += left * rightMap[left]
	}
	return strconv.Itoa(total)
}
