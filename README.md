# Wordle Solver

Wordle solver harness that evaluates the efficacy of any given algorithm.

This code tests *every* answer against any given algorithm to see how fast it solves them, then spits out a histogram.

*Got an algorithm you want to try? Open a pull request!*

## Usage

Just hit `$ ./gradlew run` to get things started.

Warning: some of the algorithms can be pretty slow when iterated over every answer!

## Results

### Knuth

Based on [Knuth's Mastermind algorithm](https://en.wikipedia.org/wiki/Mastermind_%28board_game%29#Worst_case:_Five-guess_algorithm). 

Normal:

> Solved in 1 guess(es): 1  
> Solved in 2 guess(es): 53  
> Solved in 3 guess(es): 990  
> Solved in 4 guess(es): 1162  
> Solved in 5 guess(es): 107  
> Solved in 6 guess(es): 2  
> Failed to solve: 0  
> Average # guesses: 3.5732181425485963

Hard:
> Solved in 1 guess(es): 1  
> Solved in 2 guess(es): 89  
> Solved in 3 guess(es): 907  
> Solved in 4 guess(es): 1054  
> Solved in 5 guess(es): 209  
> Solved in 6 guess(es): 40  
> Failed to solve: 15  
> Average # guesses: 3.652608695652174
