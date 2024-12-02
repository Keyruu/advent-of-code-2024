package main

import (
	"bufio"
	"common"
	"fmt"
	"strconv"
	"strings"
)

func main() {
	common.Solve(Part1)
	common.Solve(Part2)
}

func Part1(scanner *bufio.Scanner) string {
	total := 0
	for scanner.Scan() {
		line := scanner.Text()
		split := strings.Split(line, " ")

		if isSafe(split) {
			total += 1
		}
	}
	return strconv.Itoa(total)
}

func Part2(scanner *bufio.Scanner) string {
	total := 0
	for scanner.Scan() {
		line := scanner.Text()
		split := strings.Split(line, " ")

		if isSafe(split) {
			total += 1
		} else {
			for i := 0; i < len(split); i++ {
				removed, err := remove(split, i)
				common.Check(err)
				if isSafe(removed) {
					total += 1
					break
				}
			}
		}
	}
	return strconv.Itoa(total)
}

func isSafe(numbers []string) bool {
	last := -1
	isSafe := true
	increasing := true
	for i, levelStr := range numbers {
		level, err := strconv.Atoi(levelStr)
		common.Check(err)
		if i == 0 {
			last = level
			continue
		}

		if last == level {
			isSafe = false
			break
		}

		if i == 1 {
			if last > level {
				increasing = false
			}
		}

		if increasing {
			if last > level || level-last > 3 {
				isSafe = false
				break
			}
		} else {
			if last < level || last-level > 3 {
				isSafe = false
				break
			}
		}

		last = level
	}

	return isSafe
}

func remove[T any](slice []T, index int) ([]T, error) {
	// Check if index is valid
	if index < 0 || index >= len(slice) {
		return slice, fmt.Errorf("index %d out of range", index)
	}

	// Create a new slice with same length as original
	result := make([]T, 0, len(slice)-1)

	// Copy all elements except the one at index
	result = append(result, slice[:index]...)
	result = append(result, slice[index+1:]...)

	return result, nil
}

func isIncreasing(last int, level int) bool {
	return last < level && level-last <= 3
}

func isDecreasing(last int, level int) bool {
	return last > level && last-level <= 3
}
