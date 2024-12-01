package main

import (
	"bufio"
	"log"
	"os"
	"testing"
)

func test(t *testing.T, expected string, part func(*bufio.Scanner) string) {
	file, err := os.Open("./example.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	result := Part1(scanner)
	if expected != result {
		t.Errorf("Result is %s, not %s", result, expected)
	}
}

func Test1(t *testing.T) {
	test(t, "", Part1)
}

func Test2(t *testing.T) {
	test(t, "", Part2)
}
