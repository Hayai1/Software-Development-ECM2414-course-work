# Multi-Threaded Card Playing Simulation

## Overview

This project implements a multi-threaded card-playing simulation in Java. The game involves `n` players and `n` decks of cards, where each player tries to collect four cards of the same value to win the game. Players draw and discard cards in a round-robin fashion, and the game runs using the **pair programming development approach**. The implementation involves multiple threads to simulate each player's actions concurrently.

## Features

- **Thread-Safe Classes**: The `Card` and `Player` classes are designed to be thread-safe.
- **Game Logic**: Each player draws a card, discards a card, and attempts to form a winning hand of four identical cards.
- **Multi-Threading**: Players' actions are handled concurrently in separate threads.
- **File-Based Input/Output**: 
  - Input: The game uses a pack of cards read from a text file.
  - Output: Game progress and player actions are written to individual output files for each player.

## Requirements

- Java 11 or higher
- JUnit 5 for testing

## How to Run the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/card-game-simulation.git
   cd card-game-simulation
