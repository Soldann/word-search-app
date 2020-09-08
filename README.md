# word-search-app

This Android app implements a word search game.

### Features
- 10x10 Search grid
- Includes the following words: ```SWIFT```, ```KOTLIN```, ```OBJECTIVEC```, ```VARIABLE```, ```JAVA```, ```MOBILE```
- Keeps track of words that have been found
- Randomized word placement (Can appear forwards, backwards, upwards, or downwards);
- Click and drag to select words (swipe direction matters)

### Example
When you start the game, you are greeted with a screen like below.
![Start Screen](/resources/start-screen.png)
Simply swipe to select words. Selected words will be highlighted in red.
![Screen with some words highlighted](/resources/words-found.png)
When you have found all the words, you will be prompted to restart the game with a new randomly generated configuration
![Win Screen](/resources/win-screen.png)

### Notes
- If you begin swiping a word and then slide your finger off the grid, the letters you have selected will remain 
regardless of whether or not they are correct. Tapping the Grid again will fix the issue. This is due to the Grid 
no longer receiving touch events when your finger leaves it. 