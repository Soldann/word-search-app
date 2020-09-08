package com.landson.wordsearch;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model extends ViewModel {

    int size; //grid size
    ArrayList<ArrayList<Character>> grid;
    ArrayList<Word> words;
    ArrayList<ArrayList<Direction>> selectionArray;
    boolean fillEmptySpace = true; //boolean to not fill empty space when debugging

    Random random = new Random();

    public Model(){
        size = 10; //set default wordsearch size to 10
        grid = new ArrayList<>(size); //initialize empty arraylist
        for (int i = 0; i < size; ++i){
            grid.add(i, new ArrayList<Character>(Collections.nCopies(size,(Character) null)));
        }
        words = new ArrayList<>();
        selectionArray = new ArrayList<>();
    }

    public void addWord(String w){
        words.add(new Word(w));
    }

    public boolean generate(){
        boolean res = generateHelper(0);
        fillEmpty();
        return res;
    }

    private boolean generateHelper(int tries){ //return true if successful, false if not

        if (tries < 500){
            Collections.shuffle(words);

            //reset grid
            for (int i = 0; i < size; ++i){
                grid.set(i, new ArrayList<Character>(Collections.nCopies(size,(Character) null)));
            }

            for (Word w : words){
                if (!placeWord(w)){
                    return generateHelper(tries + 1); //try again
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean placeWord(Word w){
        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(new Direction(-1,0)); //left
        directions.add(new Direction(1,0)); //right
        directions.add(new Direction(0,-1)); //up
        directions.add(new Direction(0,1)); //down

        Collections.shuffle(directions); //randomize direction

        Direction position = new Direction(random.nextInt(size), random.nextInt(size));

        //try every direction
        for (Direction tryDirection : directions){
            //check there is no overlap
            if (tryWord(w,position,tryDirection)){
                for (int i = 0; i < w.word.length(); ++i){
                    Direction tryPosition = new Direction(position.x + i*tryDirection.x, position.y + i*tryDirection.y);
                    grid.get(tryPosition.y).set(tryPosition.x,w.word.charAt(i));
                }
                return true; //able to place word
            }
        }

        return false; //unable to place word
    }

    private boolean tryWord(Word w, Direction position, Direction wordDirection){
        for (int i = 0; i < w.word.length(); ++i){
            Direction tryPosition = new Direction(position.x + i*wordDirection.x, position.y + i*wordDirection.y);
            if (tryPosition.x < 0 || tryPosition.y < 0 || tryPosition.x >= size || tryPosition.y >= size
                    || grid.get(tryPosition.y).get(tryPosition.x) != null){
                return false;
            }
        }
        return true;
    }

    public void startSelection(int x, int y){
        ArrayList<Direction> selection = new ArrayList<>();
        selection.add(new Direction(x,y));
        selectionArray.add(selection);
    }

    public boolean setLastSelectionEnd(int x, int y){ //return true if modified
        if (selectionArray.size() - 1 >= 0){
            ArrayList<Direction> lastSelection = selectionArray.get(selectionArray.size() - 1);
            Direction start = lastSelection.get(0);

            ArrayList<Direction> newSelection = new ArrayList<>();
            newSelection.add(start);

            selectionArray.remove(lastSelection);
            selectionArray.add(newSelection);

            if (Math.abs(start.x - x) > Math.abs(start.y - y)){
                for (int i = Math.min(x, start.x); i <= Math.max(x,start.x); ++i){
                    newSelection.add(new Direction(i,start.y));
                }
            } else {
                for (int i = Math.min(y, start.y); i <= Math.max(y,start.y); ++i){
                    newSelection.add(new Direction(start.x,i));
                }
            }

            if (newSelection.size() == lastSelection.size()){
                for (int i = 0; i < newSelection.size(); ++i){
                    if (newSelection.get(i).x != lastSelection.get(i).x || newSelection.get(i).y != lastSelection.get(i).y){
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean cancelSelection(){ //return true if modified
        if (selectionArray.size() - 1 >= 0){
            ArrayList<Direction> deleted = selectionArray.get(selectionArray.size() - 1);
            selectionArray.remove(selectionArray.size() - 1);
            return true;
        }
        return false;
    }

    public boolean validateSelection(){ //return true if modified
        if (selectionArray.size() - 1 >= 0){
            ArrayList<Direction> lastSelection = selectionArray.get(selectionArray.size() - 1);
            StringBuilder selectedWordBuilder = new StringBuilder();
            for (int i = 1; i < lastSelection.size(); ++i){ //skip first letter since that's the start value of the selection
                Direction letter = lastSelection.get(i);
                selectedWordBuilder.append(grid.get(letter.y).get(letter.x));
            }
            String selectedWord = selectedWordBuilder.toString();
            String reverseSelectedWord = selectedWordBuilder.reverse().toString();

            for (Word word : words){
                Log.d("wordcheck", "checking " + selectedWord);
                if (word.word.equals(selectedWord) || word.word.equals(reverseSelectedWord)){
                    Log.d("wordcheck", "Found word "+ selectedWord);
                    word.found = true;
                    return false; //no need to update view, word is correct
                }
            }
        }
        return cancelSelection(); //word does not match or invalid selection
    }

    public boolean checkVictory(){
        for (Word word : words){
            if (!word.found){
                return false;
            }
        }
        return true;
    }


    public boolean restart(){
        for (Word w : words){
            w.found = false;
        }
        grid.clear();
        for (int i = 0; i < size; ++i){
            grid.add(i, new ArrayList<Character>(Collections.nCopies(size,(Character) null)));
        }
        selectionArray.clear();

        return generate();
    }

    private void fillEmpty(){
        for (ArrayList<Character> row : grid){
            for (int i = 0; i < row.size(); ++i){
                if (row.get(i) == null){
                    row.set(i,(char) (random.nextInt(26) + 'A'));
                }
            }
        }
    }

}
