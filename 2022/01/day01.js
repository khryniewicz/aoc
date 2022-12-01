const fs = require("fs");
const path = require("path");

const input = fs
  .readFileSync(path.join(__dirname, "input.txt"))
  .toString()
  .trim()
  .split("\n\n")
  .map((e) => e.split("\n").map((x) => parseInt(x)));

const sums = input.map((e) => e.reduce((a, c) => a + c));

// answer1
console.log(sums.reduce((a, c) => Math.max(a, c)));

sums.sort((a, b) => b - a);

// answer2
console.log(sums.slice(0, 3).reduce((a, c) => a + c));
