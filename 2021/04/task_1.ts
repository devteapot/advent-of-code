const file = await Deno.readTextFile("1.txt");
const lines = file.split("\n");

type Board = {
  rows: number[][];
  cols: number[][];
};

const stringToInt = (s: string): number => parseInt(s.trim());

const draws = lines[0].split(",").map(stringToInt);
const boardInput = lines.slice(2);

const EMPTY_BOARD: Board = { rows: [], cols: [[], [], [], [], []] };

const boards = [...boardInput, ""].reduce((acc, curr) => {
  if (curr === "") {
    return {
      boards: [...acc.boards, acc.currentBoard],
      currentBoard: EMPTY_BOARD,
    };
  }

  const row = curr.trim().split(/\s+/).map(stringToInt);

  return {
    boards: acc.boards,
    currentBoard: {
      rows: [...acc.currentBoard.rows, row],
      cols: acc.currentBoard.cols.map((col, index) => [...col, row[index]]),
    },
  };
}, { boards: [] as Board[], currentBoard: EMPTY_BOARD }).boards;

const isEmpty = <T>(list: T[]) => list.length === 0;

const simulation = draws.reduce((acc, curr) => {
  if (acc.winnerBoard !== undefined) return acc;

  const winnerBoard = acc.boards.reduce(
    (a, c, i) =>
      a !== undefined ? a : ([c.cols, c.rows].reduce(
          (a1, c2) => a1 || c2.filter(isEmpty).length > 0,
          false,
        )
        ? i
        : undefined),
    undefined as number | undefined,
  );

  if (winnerBoard) {
    return {
      ...acc,
      winnerBoard: winnerBoard,
    };
  }

  return {
    ...acc,
    drawn: [...acc.drawn, curr],
    boards: acc.boards.map((board) => ({
      rows: board.rows.map((row) => row.filter((n) => n !== curr)),
      cols: board.cols.map((col) => col.filter((n) => n !== curr)),
    })),
  };
}, {
  drawn: [] as number[],
  boards,
  winnerBoard: undefined as number | undefined,
});

const result =
  simulation.boards[simulation.winnerBoard!].rows.flat().reduce(
    (acc, curr) => acc + curr,
    0,
  ) * simulation.drawn[simulation.drawn.length - 1];
console.log(result);
