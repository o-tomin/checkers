package com.chess.analytics;

import com.chess.entity.Cell;
import com.chess.entity.Field;
import com.chess.util.BiPredicateExtension;

import java.util.*;

class CommonFunctions {

    static long countValues(Map<Cell, List<Cell>> map) {
        return map.keySet().stream()
                .map(map::get)
                .mapToLong(List::size)
                .sum();
    }

    static void takeStockOfFigurePossibleSteps(Field field,
                                               Cell figure,
                                               int[] leftVector,
                                               int[] rightVector,
                                               Map<Cell, List<Cell>> figureToPossibleStepCells) {
        List<Cell> possibleSteps = new ArrayList<>(2);

        field.calculateNextFigureValidCell(figure, leftVector)
                .filter(field::isBlackCell).ifPresent(possibleSteps::add);
        field.calculateNextFigureValidCell(figure, rightVector)
                .filter(field::isBlackCell).ifPresent(possibleSteps::add);

        if (!possibleSteps.isEmpty()) {
            figureToPossibleStepCells.put(figure, possibleSteps);
        }
    }

    static void takeStockOfQueenPossibleSteps(Field field,
                                              Cell queen,
                                              int[] forwardLeftVector,
                                              int[] forwardRightVector,
                                              int[] backwardLeftVector,
                                              int[] backwardRightVector,
                                              Map<Cell, List<Cell>> queenToPossibleStepCells) {
        List<Cell> possibleSteps = new ArrayList<>();
        for (int[] vector : Arrays.asList(forwardLeftVector, forwardRightVector, backwardLeftVector, backwardRightVector)) {
            Optional<Cell> next = Optional.of(queen);
            do {
                next = field.calculateNextFigureValidCell(next.get(), vector).filter(field::isBlackCell);
                next.ifPresent(possibleSteps::add);
            } while (next.isPresent());
        }
        if (!possibleSteps.isEmpty()) {
            queenToPossibleStepCells.put(queen, possibleSteps);
        }
    }

    static void takeStockOfPossibleAttacks(Cell killer,
                                           List<Cell> potentialVictims,
                                           BiPredicateExtension<Cell, Cell> isReachableEnemy,
                                           BiPredicateExtension<Cell, Cell> isAttackPossible,
                                           Map<Cell, List<Cell>> killerToVictimsPossibleAttacks) {
        Map<Cell, List<Cell>> possibleAttacks = KillerToVictimsStreamHolder
                .streamFor(killer)
                .streamOf(potentialVictims)
                .filter(isReachableEnemy)
                .filter(isAttackPossible)
                .collect();

        killerToVictimsPossibleAttacks.putAll(possibleAttacks);
    }

}
