package main

import (
	"bufio"
	"common"
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

		last := 0
		for last != -1 {
			line = line[last:]
			res := 0
			res, last = mul(line)
			total += res
		}
	}
	return strconv.Itoa(total)
}

func mul(s string) (int, int) {
	last := -1
	index := strings.Index(s, "mul(")
	if index != -1 {
		maybeStart := index + 4
		afterComma := false
		first := ""
		second := ""
		end := false
		for i := 0; end == false; i++ {
			last = maybeStart + i
			maybeNumber := stringAtIndex(s, maybeStart+i)
			if isNumeric(maybeNumber) {
				if afterComma {
					second += maybeNumber
				} else {
					first += maybeNumber
				}
			} else if isComma(maybeNumber) && first != "" {
				afterComma = true
			} else if maybeNumber == ")" && afterComma == true && first != "" && second != "" {
				firstNumber, err := strconv.Atoi(first)
				common.Check(err)
				secondNumber, err := strconv.Atoi(second)
				common.Check(err)

				return firstNumber * secondNumber, last
			} else {
				end = true
			}
		}
	}
	return 0, last

}

func isNumeric(number string) bool {
	_, err := strconv.ParseFloat(number, 64)
	return err == nil
}

func isComma(comma string) bool {
	return comma == ","
}

func stringAtIndex(s string, i int) string {
	return string([]rune(s)[i])
}

func Part2(scanner *bufio.Scanner) string {
	total := 0
	data := ""
	for scanner.Scan() {
		line := scanner.Text()
		data += line + "\n"
	}

	for {
		dont := strings.Index(data, "don't()")
		if dont == -1 {
			break
		}
		do := strings.Index(data[dont+7:], "do()")
		if do == -1 {
			data = data[:dont]
			break
		}

		doAbsolutePos := dont + 7 + do

		data = data[:dont] + data[doAbsolutePos+4:]
	}

	last := 0
	for last != -1 {
		data = data[last:]
		res := 0
		res, last = mul(data)
		total += res
	}

	return strconv.Itoa(total)
}
