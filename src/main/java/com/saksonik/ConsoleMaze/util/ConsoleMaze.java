package com.saksonik.ConsoleMaze.util;

import com.saksonik.ConsoleMaze.generation.Generator;
import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import com.saksonik.ConsoleMaze.solving.Solver;
import com.saksonik.ConsoleMaze.view.Renderer;
import com.saksonik.ConsoleMaze.view.console.ConsoleRenderer;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleMaze {
    private static final String NUMBER_REGEX = "^\\d+$";
    private final Scanner scanner;
    private final Random generationRandom;
    private final Renderer renderer = new ConsoleRenderer();
    private Maze maze;
    private List<Cell> path;
    private Generator generator;
    private Solver solver;

    public ConsoleMaze() {
        scanner = new Scanner(System.in);
        generationRandom = new Random();
    }

    public ConsoleMaze(Scanner scanner, Random generationRandom) {
        this.scanner = scanner;
        this.generationRandom = generationRandom;
    }

    public void run() {
        List<Integer> input;

        while (true) {
            displaySuggestionToSelectAGenerationAlgorithm();
            input = formatInput(InputType.SelectGenerator);
            if (input.get(0) == 0) {
                break;
            }
            setGenerator(input);

            displaySuggestionToSelectAMazeSize();
            input = formatInput(InputType.EnterMazeSize);
            if (input.get(0) == 0) {
                break;
            }
            generateTheMaze(input);
            displayGeneratedMaze();

            displaySuggestionToSelectASolvingAlgorithm();
            input = formatInput(InputType.SelectSolver);
            if (input.get(0) == 0) {
                break;
            }
            setSolver(input);
            solveTheMaze();
            displaySolvedMaze();
        }

        displayFarewellMessage();
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private List<Integer> formatInput(InputType type) {
        while (true) {
            try {
                String[] lines = scanner.nextLine().split(" ");
                if (lines.length < 1 || lines.length > 2) {
                    System.out.println(ApplicationProperties.INVALID_NUMBER_INPUT_MESSAGE);
                    continue;
                }

                if (!Arrays.stream(lines).allMatch(s -> s.matches(NUMBER_REGEX))) {
                    System.out.println(ApplicationProperties.INVALID_NUMBER_INPUT_MESSAGE);
                    continue;
                }

                if (lines.length == 1 && Integer.parseInt(lines[0]) == 0) {
                    return List.of(0);
                }

                switch (type) {
                    case SelectGenerator -> {
                        if (isCorrectInputForSelectionGenerator(lines)) {
                            return List.of(Integer.parseInt(lines[0]));
                        }
                    }
                    case SelectSolver -> {
                        if (isCorrectInputForSelectionSolver(lines)) {
                            return List.of(Integer.parseInt(lines[0]));
                        }
                    }
                    case EnterMazeSize -> {
                        if (isCorrectInputForCreatingMaze(lines)) {
                            return lines.length == 2
                                    ? List.of(Integer.parseInt(lines[0]), Integer.parseInt(lines[1]))
                                    : List.of(Integer.parseInt(lines[0]), Integer.parseInt(lines[0]));
                        }
                    }
                    default -> throw new IllegalArgumentException();
                }
                System.out.println(ApplicationProperties.INVALID_NUMBER_INPUT_MESSAGE);
            } catch (InputMismatchException e) {
                System.out.println(ApplicationProperties.INVALID_NUMBER_INPUT_MESSAGE);
            }
        }
    }

    private boolean isCorrectInputForSelectionGenerator(String[] lines) {
        int choice = Integer.parseInt(lines[0]);
        ApplicationProperties.GenerationAlgorithm[] generationAlgorithms =
                ApplicationProperties.GenerationAlgorithm.values();

        return choice >= 1 && choice <= generationAlgorithms.length && lines.length != 2;
    }

    private boolean isCorrectInputForSelectionSolver(String[] lines) {
        int choice = Integer.parseInt(lines[0]);
        ApplicationProperties.SolvingAlgorithm[] solvingAlgorithms =
                ApplicationProperties.SolvingAlgorithm.values();

        return choice >= 1 && choice <= solvingAlgorithms.length && lines.length != 2;
    }

    private boolean isCorrectInputForCreatingMaze(String[] lines) {
        int height;
        int width;

        if (lines.length == 2) {
            height = Integer.parseInt(lines[0]);
            width = Integer.parseInt(lines[1]);
        } else {
            height = Integer.parseInt(lines[0]);
            width = Integer.parseInt(lines[0]);
        }

        return height >= ApplicationProperties.SIZE_LOWER_BOUND && width >= ApplicationProperties.SIZE_LOWER_BOUND;
    }

    private void setGenerator(List<Integer> choice) {
        generator = ApplicationProperties
                .GenerationAlgorithm
                .values()[choice.get(0) - 1]
                .getGenerator(generationRandom);
    }

    private void setSolver(List<Integer> choice) {
        solver = ApplicationProperties
                .SolvingAlgorithm
                .values()[choice.get(0) - 1]
                .getSolver();
    }

    private void generateTheMaze(List<Integer> params) {
        maze = generator.generate(params.get(0), params.get(1));
    }

    private void solveTheMaze() {
        path = solver.solve(maze, maze.getEntrance(), maze.getExit());
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displaySuggestionToSelectAGenerationAlgorithm() {
        System.out.println(ApplicationProperties.SELECT_A_MAZE_GENERATION_ALGORITHM_MESSAGE);
        ApplicationProperties.GenerationAlgorithm[] algorithms = ApplicationProperties.GenerationAlgorithm.values();

        for (int i = 1; i <= algorithms.length; i++) {
            System.out.println(i + ". " + algorithms[i - 1]);
        }
        System.out.println(ApplicationProperties.ENTER_COMMAND_TO_EXIT_MESSAGE);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displaySuggestionToSelectASolvingAlgorithm() {
        System.out.println(ApplicationProperties.SELECT_A_MAZE_SOLVING_ALGORITHM_MESSAGE);
        ApplicationProperties.SolvingAlgorithm[] algorithms = ApplicationProperties.SolvingAlgorithm.values();

        for (int i = 1; i <= algorithms.length; i++) {
            System.out.println(i + ". " + algorithms[i - 1]);
        }
        System.out.println(ApplicationProperties.ENTER_COMMAND_TO_EXIT_MESSAGE);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displaySuggestionToSelectAMazeSize() {
        System.out.println(ApplicationProperties.SUGGESTION_TO_SELECT_A_MAZE_SIZE_MESSAGE);
        System.out.println(ApplicationProperties.ENTER_COMMAND_TO_EXIT_MESSAGE);
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displayGeneratedMaze() {
        System.out.println(renderer.render(maze));
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displaySolvedMaze() {
        System.out.println(renderer.render(maze, path));
    }

    @SuppressWarnings("RegexpSinglelineJava")
    private void displayFarewellMessage() {
        System.out.println(ApplicationProperties.FAREWELL_MESSAGE);
    }

    enum InputType {
        SelectGenerator,
        SelectSolver,
        EnterMazeSize
    }
}
