package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
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
	fmt.Printf("Result: %s", Part2(scanner))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}

func Part1(scanner *bufio.Scanner) string {
}

func Part2(scanner *bufio.Scanner) string {
}
