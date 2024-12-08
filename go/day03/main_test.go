package main

import (
	"common"
	"testing"
)

func Test1(t *testing.T) {
	common.Test(t, "161", Part1)
}

func Benchmark1(b *testing.B) {
	common.Solve(Part1)
}

func Test2(t *testing.T) {
	common.TestWithPath(t, "48", Part2, "example2.txt")
}

func Benchmark2(b *testing.B) {
	common.Solve(Part2)
}
