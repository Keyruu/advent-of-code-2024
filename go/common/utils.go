package common

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"testing"
)

func Solve(part func(*bufio.Scanner) string) {
	file, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	fmt.Printf("Result: %s\n", part(scanner))

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}

func Test(t *testing.T, expected string, part func(*bufio.Scanner) string) {
	TestWithPath(t, expected, part, "example.txt")
}

func TestWithPath(t *testing.T, expected string, part func(*bufio.Scanner) string, path string) {
	file, err := os.Open(path)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	result := part(scanner)
	if expected != result {
		t.Errorf("Result is %s, not %s", result, expected)
	}
}

func Check(e error) {
	if e != nil {
		panic(e)
	}
}
