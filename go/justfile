currentDay := shell("date '+%d'")

[private]
default:
  just --list

new day=currentDay:
  cp -r template/ day{{day}}
  go work use day{{day}}

fetch day=currentDay:
    #!/usr/bin/env bash
    if [ ! -f .env ]; then
        echo "Please create .env file with AOC_SESSION=your_session_cookie"
        exit 1
    fi
    source .env
    curl --cookie "session=$AOC_SESSION" "https://adventofcode.com/2024/day/$(echo {{day}} | sed 's/^0*//')/input" > "day{{day}}/input.txt"

test day=currentDay:
  go test day{{day}}

run day=currentDay:
  cd day{{day}}; go run main.go

benchmark day=currentDay:
  go test day{{day}} -bench=.
