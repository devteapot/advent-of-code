const file2 = await Deno.readTextFile("1.txt");

// didn't know you could do parseInt("01010", 2) .-.
const binaryStringToDecimal = (binaryString: string): number => 
  [...binaryString]
    .reverse()
    .map((char, index) => Math.pow(2, index) * (char === '1' ? 1 : 0))
    .reduce((acc, curr) => acc + curr, 0);

const filterByPredicate = (
  list: string[], 
  digitToCheck: number,
  predicate: (a: number, b: number) => boolean
): string[] => {
  if (list.length === 1) return list;

  const mostCommon = predicate(
    list.reduce((acc, curr) => curr[digitToCheck] === '1' ? acc + 1 : acc, 0),
    list.length / 2
  ) ? '1' : '0';

  return filterByPredicate(
    list.filter((elem) => elem[digitToCheck] === mostCommon),
    digitToCheck + 1,
    predicate
  )
}

const oxigen = filterByPredicate(file2.split('\n'), 0, (a, b) => a >= b)[0];
const co2 = filterByPredicate(file2.split('\n'), 0, (a, b) => a < b)[0];

console.log("oxigen: ", oxigen);
console.log("CO2: ", co2);

console.log("result: ", binaryStringToDecimal(oxigen) * binaryStringToDecimal(co2));
