package service;

import domain.Game;
import domain.User;
import util.Dice;

import java.util.HashMap;
import java.util.List;

public class GameService {

    private final Game game;
    private final Dice diceService;

    public GameService(Game game, Dice diceService) {
        this.game = game;
        this.diceService = diceService;
    }

    public void startGame() {
        int userCount = game.getUserList().size();
        HashMap<String,Integer> userMoves = game.getUserMoves();
        List<User> userList = game.getUserList();
        if(userCount<=1)
            throw new RuntimeException("Minimum 2 players are required for the game");
        int currentPlayer = 0,currentDiceValue, nextPosition;
        User currentUser;
        while(game.getWinner()==null){
            currentDiceValue = diceService.getRandom();
            currentUser = userList.get(currentPlayer);
            //If user has already started playing
            if(userMoves.containsKey(currentUser.getId())){
                nextPosition = userMoves.get(currentUser.getId())+currentDiceValue;
                if(nextPosition >= game.getBoard().getEnd()) {
                    setWinner(currentUser);
                }else{
                    nextPosition = checkoffLadderOrSnake(nextPosition);
                    userMoves.put(currentUser.getId(),nextPosition);
                }
            }else{
                userMoves.put(currentUser.getId(),currentDiceValue);
            }
            currentPlayer++;
            if(currentPlayer == userCount)
                currentPlayer = 0;
        }
    }

    private int checkoffLadderOrSnake(int nextPosition) {
        if(game.getBoard().getSnakes().containsKey(nextPosition)){
            return game.getBoard().getSnakes().get(nextPosition).getEnd();
        }
        else if(game.getBoard().getLadder().containsKey(nextPosition))
        {
            return game.getBoard().getLadder().get(nextPosition).getEnd();
        }
        else
            return nextPosition;
    }

    private void setWinner(User user){
        game.setWinner(user.getName());
        System.out.println("Winner " + game.getWinner());
    }
}
