import unittest
import csv
from game import Board, Game

def load_board_from_csv(filename):
    with open(filename, 'r') as csvfile:
        reader = csv.reader(csvfile)
        board = list(reader)
    return board

class TestGame(unittest.TestCase):
    def setUp(self):
        board = load_board_from_csv('board.csv')
        self.board = Board(board, (4, 0), (0, 4))
        self.game = Game(self.board)

    def test_move_valid(self):
        self.game.position = [4, 0]
        self.assertTrue(self.game.is_move_valid())

    def test_move_invalid_out_of_bounds(self):
        self.game.position = [-1, 0]
        self.assertFalse(self.game.is_move_valid())
        self.game.position = [0, -1]
        self.assertFalse(self.game.is_move_valid())
        self.game.position = [5, 0]
        self.assertFalse(self.game.is_move_valid())
        self.game.position = [0, 5]
        self.assertFalse(self.game.is_move_valid())

    def test_move_invalid_obstacle(self):
        self.game.position = [1, 2]
        self.assertFalse(self.game.is_move_valid())

if __name__ == '__main__':
    unittest.main()
