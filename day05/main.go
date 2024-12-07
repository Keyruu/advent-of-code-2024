package main

import (
	"bufio"
	"common"
	"fmt"
	"math"
	"slices"
	"strconv"
	"strings"
)

func main() {
	common.Solve(Part1)
	common.Solve(Part2)
}

func Part1(scanner *bufio.Scanner) string {
	total := 0
	beforeMap := make(map[int][]int)
	newline := false
	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			newline = true
			continue
		}

		if !newline {
			split := strings.Split(line, "|")
			before, err := strconv.Atoi(split[0])
			common.Check(err)
			after, err := strconv.Atoi(split[1])
			common.Check(err)

			beforeMap[before] = append(beforeMap[before], after)
		} else {
			beforeArr := []int{}
			correct := true
			pages := strings.Split(line, ",")
			for _, char := range pages {
				page, err := strconv.Atoi(string(char))
				common.Check(err)

				afterPages := beforeMap[page]
				for _, before := range beforeArr {
					if slices.Contains(afterPages, before) {
						correct = false
						break
					}
				}

				if !correct {
					break
				}

				beforeArr = append(beforeArr, page)
			}

			if correct {
				index := int(math.Floor(float64(len(pages)) / 2.0))
				page, err := strconv.Atoi(pages[index])
				common.Check(err)

				total += page
			}
		}
	}
	return strconv.Itoa(total)
}

func Part2(scanner *bufio.Scanner) string {
	total := 0
	beforeMap := make(map[int][]int)
	newline := false
	for scanner.Scan() {
		line := scanner.Text()

		if line == "" {
			fmt.Printf("map: %v\n", beforeMap)
			newline = true
			continue
		}

		if !newline {
			split := strings.Split(line, "|")
			before, err := strconv.Atoi(split[0])
			common.Check(err)
			after, err := strconv.Atoi(split[1])
			common.Check(err)

			beforeMap[before] = append(beforeMap[before], after)
		} else {
			beforeArr := []int{}
			correct := true
			pages := strings.Split(line, ",")
			fmt.Printf("pages: %v\n", pages)
			for _, char := range pages {
				page, err := strconv.Atoi(string(char))
				common.Check(err)

				beforePages := beforeMap[page]
				for _, before := range beforeArr {
					if slices.Contains(beforePages, before) {
						fmt.Printf("page: %v, beforeArr: %v, beforePages: %v\n", page, beforeArr, beforePages)
						correct = false
						break
					}
				}

				if !correct {
					break
				}

				beforeArr = append(beforeArr, page)
			}

			if !correct {
				slices.SortFunc(pages, func(left string, right string) int {
					leftInt, err := strconv.Atoi(left)
					common.Check(err)
					rightInt, err := strconv.Atoi(right)
					common.Check(err)

					leftAfterPages := beforeMap[leftInt]

					if slices.Contains(leftAfterPages, rightInt) {
						return -1
					} else {
						return 1
					}
				})

				index := int(math.Floor(float64(len(pages)) / 2.0))
				page, err := strconv.Atoi(pages[index])
				common.Check(err)

				fmt.Printf("correct: %v, page: %v\n", line, page)
				total += page
			}
		}
	}
	return strconv.Itoa(total)
}
