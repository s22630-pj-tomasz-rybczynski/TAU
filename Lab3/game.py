import random
import curses
import os
import csv

class Board:
    def __init__(self, board, start, stop):
        self.board = board
        self.start = start
        self.stop = stop
        self.height = len(board)
        self.width = len(board[0]) if self.height > 0 else 0

    def generate_board(self):
        self.start = (random.randint(0, self.height-1), 0)
        self.stop = (random.randint(0, self.height-1), self.width-1)
        self.board[self.start[0]][self.start[1]] = 'A'
        self.board[self.stop[0]][self.stop[1]] = 'B'
        for _ in range((self.width*self.height)//5):
            obstacle = (random.randint(0, self.height-1), random.randint(0, self.width-1))
            if obstacle != self.start and obstacle != self.stop:
                self.board[obstacle[0]][obstacle[1]] = 'X'

    def display_board(self, stdscr, player_position):
        for i, row in enumerate(self.board):
            for j, cell in enumerate(row):
                if (i, j) == tuple(player_position):
                    stdscr.addstr(i, j*2, 'o')
                else:
                    stdscr.addstr(i, j*2, cell)

    def save_to_csv(self, filename):
        with open(filename, 'w', newline='') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerows(self.board)
            writer.writerow([','.join(map(str, self.start))])
            writer.writerow([','.join(map(str, self.stop))])

    @classmethod
    def load_from_csv(cls, filename):
        with open(filename, 'r') as csvfile:
            reader = csv.reader(csvfile)
            board = list(reader)
        start = None
        stop = None
        for i, row in enumerate(board):
            for j, cell in enumerate(row):
                if cell == 'A':
                    start = (i, j)
                elif cell == 'B':
                    stop = (i, j)
        if start is None or stop is None:
            raise ValueError("CSV file must contain a start cell ('A') and a stop cell ('B')")
        return board, start, stop

class Game:
    def __init__(self, board):
        self.board = board
        self.position = list(board.start)

    def move(self, direction):
        temp_position = list(self.position)
        if direction == 'up':
            self.position[0] -= 1
        elif direction == 'down':
            self.position[0] += 1
        elif direction == 'left':
            self.position[1] -= 1
        elif direction == 'right':
            self.position[1] += 1
        if not self.is_move_valid():
            self.position = temp_position
            return False
        if self.board.board[self.position[0]][self.position[1]] == 'B':
            return None
        return True

    def is_move_valid(self):
        if self.position[0] < 0 or self.position[0] >= self.board.height or self.position[1] < 0 or self.position[1] >= self.board.width:
            return False
        if self.board.board[self.position[0]][self.position[1]] == 'X':
            return False
        return True
    
    def start_game(self, stdscr):
        stdscr.clear()
        key = ''
        while key != 'q':
            self.board.display_board(stdscr, self.position)
            stdscr.refresh()
            key = stdscr.getkey()
            if key == 'KEY_UP':
                move_result = self.move('up')
            elif key == 'KEY_DOWN':
                move_result = self.move('down')
            elif key == 'KEY_LEFT':
                move_result = self.move('left')
            elif key == 'KEY_RIGHT':
                move_result = self.move('right')
            if move_result is None:
                break

if __name__ == "__main__":
    if os.path.exists('board.csv'):
        board = Board.load_from_csv('board.csv')
        board = Board(*board)
    else:
        board = Board(5, 5)
    game = Game(board)
    curses.wrapper(game.start_game)
