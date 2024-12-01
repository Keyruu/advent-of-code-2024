package main

import (
	"bufio"
	"log"
	"os"
	"testing"
)

func Test1(t *testing.T) {
	file, err := os.Open("./example.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	if "11" != Part1(scanner) {
		t.Error("Not 11")
	}
}

func Test2(t *testing.T) {
	file, err := os.Open("./example.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)

	result := Part2(scanner)
	if "31" != result {
		t.Errorf("Result is %s, not 31", result)
	}
}
