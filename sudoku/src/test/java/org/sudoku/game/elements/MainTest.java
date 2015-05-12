package org.sudoku.game.elements;

import org.junit.Test;
import org.sudoku.game.strategies.ResolveSubstitution;
import org.sudoku.game.strategies.SearchPossibleSubstitution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainTest {

    static final Element EMPTY = Element.EMPTY_ELEMENT;
    static final Element ONE = new Element(1);
    static final Element TWO = new Element(2);
    static final Element THREE = new Element(3);
    static final Element FOUR = new Element(4);
    static final Element FIVE = new Element(5);
    static final Element SIX = new Element(6);
    static final Element SEVEN = new Element(7);
    static final Element EIGHT = new Element(8);
    static final Element NINE = new Element(9);

    @Test
    public void mainTest()
            throws Exception {
        Element[][] elements = new Element[][] {
                {EIGHT.clone(), FOUR.clone(), EMPTY.clone(), EMPTY.clone(), FIVE.clone(), EMPTY.clone(), EMPTY.clone(), EMPTY.clone(), TWO.clone()},
                {EMPTY.clone(), FIVE.clone(), EMPTY.clone(), EMPTY.clone(), EMPTY.clone(), NINE.clone(), EMPTY.clone(), THREE.clone(), SIX.clone()},
                {EMPTY.clone(), EMPTY.clone(), TWO.clone(), EIGHT.clone(), EMPTY.clone(), SIX.clone(), FOUR.clone(), EMPTY.clone(), EMPTY.clone()},
                {EMPTY.clone(), SIX.clone(), EIGHT.clone(), EMPTY.clone(), ONE.clone(), EMPTY.clone(), FIVE.clone(), EMPTY.clone(), EMPTY.clone()},
                {NINE.clone(), EMPTY.clone(), EMPTY.clone(), FIVE.clone(), EMPTY.clone(), EIGHT.clone(), EMPTY.clone(), EMPTY.clone(), FOUR.clone()},
                {EMPTY.clone(), EMPTY.clone(), THREE.clone(), EMPTY.clone(), SIX.clone(), EMPTY.clone(), SEVEN.clone(), ONE.clone(), EMPTY.clone()},
                {EMPTY.clone(), EMPTY.clone(), NINE.clone(), ONE.clone(), EMPTY.clone(), SEVEN.clone(), THREE.clone(), EMPTY.clone(), EMPTY.clone()},
                {TWO.clone(), ONE.clone(), EMPTY.clone(), FOUR.clone(), EMPTY.clone(), EMPTY.clone(), EMPTY.clone(), EIGHT.clone(), EMPTY.clone()},
                {SEVEN.clone(), EMPTY.clone(), EMPTY.clone(), EMPTY.clone(), EIGHT.clone(), EMPTY.clone(), EMPTY.clone(), TWO.clone(), NINE.clone()}
        };
        GameField gameField = new GameField.Builder(elements).build();
        System.out.println("Game field = \n" + gameField);
        ExecutorService executorService = Executors.newFixedThreadPool(18);
        int number = 0;
        while (!gameField.isFilled()) {
            int i = number%GameField.NUMBER_OF_SUBSTITUTABLE_BLOCKS;
            int j = number - i*GameField.NUMBER_OF_SUBSTITUTABLE_BLOCKS;
            SubstitutableBlock block = gameField.get(i, j);
            Callable<Element> searchPossibleSubstitution = new SearchPossibleSubstitution(block);
            Future<Element> possibleSubstitutionFuture = executorService.submit(searchPossibleSubstitution);
            Element possibleSubstitution = possibleSubstitutionFuture.get();
            if(possibleSubstitution != Element.EMPTY_ELEMENT) {
                Runnable substitutionResolver = new ResolveSubstitution(block, possibleSubstitution);
                executorService.submit(substitutionResolver);
            }
            System.out.println("Game field = \n" + gameField);
        }

    }

}
