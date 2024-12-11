#!/usr/bin/env bash
# yoinked from [joinemm](https://github.com/joinemm)

days=$(for i in $(seq 6 "$1"); do printf "%02d\n" $i; done)

for i in $days; do
  scala-cli --power package --native-image src/day${i}/main.scala -o .bin/day${i}part1 --toolkit 0.6.0 --main-class day${i}.part1 -f
  scala-cli --power package --native-image src/day${i}/main.scala -o .bin/day${i}part2 --toolkit 0.6.0 --main-class day${i}.part2 -f
done

commands=()
for d in $days; do
  commands+=(".bin/day${d}part1 day${d}.input" ".bin/day${d}part2 day${d}.input")
done

hyperfine "${commands[@]}" -N --warmup 1 --export-csv bench.csv

echo '```scala' >README.md

tail -n+2 bench.csv |
    awk -F, 'BEGIN { OFS="," } { gsub(".bin/", "Day ", $1); $2 = sprintf("%.2f", $2 * 1000); print $0 }' |
    uplot bar -d, --xscale log --width 75 --xlabel "Mean runtime in ms [log]" -o >>README.md

echo '```' >>README.md

rm bench.csv
