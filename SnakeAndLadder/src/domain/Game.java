package domain;

import java.util.HashMap;
import java.util.List;

public class Game {

    Board board;
    List<User> userList;
    HashMap<String,Integer> userMoves;
    String winner;
    String currentPlayer;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public HashMap<String, Integer> getUserMoves() {
        return userMoves;
    }

    public void setUserMoves(HashMap<String, Integer> users) {
        this.userMoves = users;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Game(Board board, List<User> userList, HashMap<String, Integer> users, String winner, String currentPlayer) {
        this.board = board;
        this.userList = userList;
        this.userMoves = users;
        this.winner = winner;
        this.currentPlayer = currentPlayer;
    }
}
