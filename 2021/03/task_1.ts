const file1 = await Deno.readTextFile("1.txt");

const lines = file1.split("\n");

const result = [
  (a: number, b: number) => a >= b,
  (a: number, b: number) => a < b,
].map((predicate) =>
  parseInt(
    [...Array<number>(lines[0].length).keys()]
      .reduce(
        (
          acc,
          curr,
        ) => [
          ...acc,
          predicate(
              lines.map((line) => line[curr]).reduce(
                (a, c) => c === "1" ? a + 1 : a,
                0,
              ),
              lines.length / 2,
            )
            ? "1"
            : "0",
        ],
        [] as string[],
      )
      .join(""),
    2,
  )
)
  .reduce((acc, curr) => acc * curr, 1);

console.log(result);
