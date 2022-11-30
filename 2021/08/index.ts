const file = await Deno.readTextFile("1.txt");

const task1 = () => {
  const validLengths = [2, 3, 4, 7];

  const result = file
    .split("\n")
    .flatMap((line) => line.split(" | ")[1].split(" "))
    .filter((entry) => validLengths.includes(entry.length))
    .reduce((acc, _) => acc + 1, 0);

  console.log(`task-1: ${result}`);
};

const intersection = <T>(listA: T[], listB: T[]): T[] =>
  listA.filter((x) => listB.includes(x));

const difference = <T>(listA: T[], listB: T[]): T[] =>
  listA.filter((x) => !listB.includes(x));

const union = <T>(listA: T[], listB: T[]): T[] =>
  listA.reduce((acc, curr) => acc.includes(curr) ? acc : [...acc, curr], listB);

const isEqual = <T>(listA: T[], listB: T[]): boolean =>
  (difference(listA, listB).length === 0) &&
  (difference(listB, listA).length === 0);

const task2 = () => {
  type Segment = "a" | "b" | "c" | "d" | "e" | "f" | "g";
  const segments = new Set(
    ["a", "b", "c", "d", "e", "f", "g"] as const,
  );

  type RawNumber =
    | "abcefg"
    | "cf"
    | "acdeg"
    | "acdfg"
    | "bcdf"
    | "abdfg"
    | "abdefg"
    | "acf"
    | "abcdefg"
    | "abcdfg";

  const numberMappings: { [k in RawNumber]: number } = {
    "abcefg": 0,
    "cf": 1,
    "acdeg": 2,
    "acdfg": 3,
    "bcdf": 4,
    "abdfg": 5,
    "abdefg": 6,
    "acf": 7,
    "abcdefg": 8,
    "abcdfg": 9,
  };

  const [input, output] = file
    .split("\n")
    .map((line) =>
      line
        .split(" | ")
        .map((x) => x.split(" "))
    )
    .reduce((acc, curr) => [
      [...acc[0], curr[0]],
      [...acc[1], curr[1]],
    ], [[], []] as string[][][]);

  const mappings = input
    .map((entry) =>
      entry
        .reduce((acc, curr) => ({
          ...acc,
          [curr.length]: [...acc[curr.length] || [], curr],
        }), {} as { [k: number]: string[] })
    )
    .map((entry) =>
      Object.entries(entry)
        .reduce(
          (acc, [k, v]) => ({
            knownMappings: v.length === 1
              ? { ...acc.knownMappings, [k]: v[0].split("") as Segment[] }
              : acc.knownMappings,
            unknownMappings: v.length !== 1
              ? {
                ...acc.unknownMappings,
                [k]: v.map((x) => x.split("") as Segment[]),
              }
              : acc.unknownMappings,
          }),
          {} as {
            knownMappings: { [k: number]: Segment[] };
            unknownMappings: { [k: number]: Segment[][] };
          },
        )
    )
    .map(({ knownMappings, unknownMappings }) => {
      const result: { [k in Segment]: Segment } = {
        a: difference(knownMappings[3], knownMappings[2])[0],
      } as { [k in Segment]: Segment };

      result["g"] = unknownMappings[5]
        .map((x) => difference(x, union(knownMappings[4], [result["a"]])))
        .filter((x) => x.length === 1)[0][0];

      result["e"] = unknownMappings[5]
        .flatMap((x) =>
          difference(x, union(knownMappings[4], [result["a"], result["g"]]))
        )[0];

      result["b"] = unknownMappings[5]
        .flatMap((x) =>
          difference(knownMappings[4], union(x, knownMappings[2]))
        )[0];

      result["d"] =
        difference(knownMappings[4], union(knownMappings[2], [result["b"]]))[0];

      result["f"] = unknownMappings[6]
        .flatMap((x) =>
          difference(knownMappings[7], union(x, [result["d"], result["e"]]))
        )[0];

      result["c"] = difference(knownMappings["2"], [result["f"]])[0];

      // ??????????
      const temp = result['c']
      result['c'] = result['f']
      result['f'] = temp

      return Object.entries(result).reduce((acc, [k, v]) => ({
        ...acc,
        [v]: k,
      }), {} as { [k in Segment]: Segment });
    });

  console.log(
    output.map((line, index) =>
      line.map((values) => {
        const decoded = values.split("").map((c) =>
          mappings[index][c as Segment]
        );
        return numberMappings[
          (Object.keys(numberMappings) as RawNumber[])
            .filter((x) => isEqual(x.split(""), decoded))[0]
        ];
      })
      .join('')
    )
    .map((line) => parseInt(line))
    .reduce((acc, curr) => acc + curr, 0),
  );
};

task1();
task2();
