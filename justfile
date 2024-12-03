currentDay := shell("date '+%d'")

[private]
default:
  just --list

new day=currentDay:
  cp -R template/ day{{day}}/
  sed -i '' 's/dayxx/day{{day}}/g' day{{day}}/go.mod
  go work use day{{day}}

test day=currentDay:
  go test day{{day}}

run day=currentDay:
  cd day{{day}}; go run main.go

benchmark day=currentDay:
  go test day{{day}} -bench=.
