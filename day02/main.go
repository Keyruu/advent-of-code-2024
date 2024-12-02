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
		last := -1
		isSafe := true
		increasing := true
		for i, levelStr := range split {
			level, err := strconv.Atoi(levelStr)
			common.Check(err)
			fmt.Printf("last: %d, level: %d\n", last, level)
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

		if isSafe {
			total += 1
			fmt.Println("is safe")
		}
	}
	return strconv.Itoa(total)
}

func Part2(scanner *bufio.Scanner) string {
	total := 0
	wrongOnes := [][]string{}
	for scanner.Scan() {
		line := scanner.Text()
		split := strings.Split(line, " ")

		if isSafe(0, split, true) {
			total += 1
			fmt.Println("is safe")
		} else {
			wrongOnes = append(wrongOnes, split)
		}
	}
	for _, wrong := range wrongOnes {
		if isSafe(1, wrong, false) {
			total += 1
			fmt.Println("is safe now")
		}
	}
	return strconv.Itoa(total)
}

func isSafe(startAt int, numbers []string, secondChances bool) bool {
	lastIncreasing := -1
	lastDecreasing := -1
	isSafeIncreasing := true
	isSafeDecreasing := true
	secondChanceIncreasing := secondChances
	secondChanceDecreasing := secondChances
	for i, levelStr := range numbers[startAt:] {
		level, err := strconv.Atoi(levelStr)
		common.Check(err)

		fmt.Printf("Index: %d, Current level: %d, LastInc: %d, LastDec: %d\n",
			i, level, lastIncreasing, lastDecreasing)

		if i == 0 {
			lastDecreasing = level
			lastIncreasing = level
			continue
		}

		skipIncreasing := false
		skipDecreasing := false
		if isIncreasing(lastIncreasing, level) == false && isSafeIncreasing {
			if secondChanceIncreasing {
				secondChanceIncreasing = false
				skipIncreasing = true
				fmt.Printf("Used increasing second chance at index %d\n", i)
			} else {
				isSafeIncreasing = false
				fmt.Printf("No longer safe increasing at index %d\n", i)
			}
		}
		if isDecreasing(lastDecreasing, level) == false && isSafeDecreasing {
			if secondChanceDecreasing {
				secondChanceDecreasing = false
				skipDecreasing = true
				fmt.Printf("Used decreasing second chance at index %d\n", i)
			} else {
				isSafeDecreasing = false
				fmt.Printf("No longer safe decreasing at index %d\n", i)
			}
		}

		if skipDecreasing == false {
			lastDecreasing = level
		}
		if skipIncreasing == false {
			lastIncreasing = level
		}

		fmt.Printf("After processing - SafeInc: %v, SafeDec: %v, SecondChanceInc: %v, SecondChanceDec: %v\n",
			isSafeIncreasing, isSafeDecreasing, secondChanceIncreasing, secondChanceDecreasing)

		if isSafeDecreasing == false && isSafeIncreasing == false {
			fmt.Println("Breaking loop - both sequences invalid")
			break
		}
	}
	return isSafeDecreasing || isSafeIncreasing
}

func isIncreasing(last int, level int) bool {
	return last < level && level-last <= 3
}

func isDecreasing(last int, level int) bool {
	return last > level && last-level <= 3
}
