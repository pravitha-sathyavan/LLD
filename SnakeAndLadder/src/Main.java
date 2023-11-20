import domain.*;
import service.GameService;
import util.Dice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        int start = 1;
        int end = 40;
        HashMap<Integer,Snake> snakeList = new HashMap<>();
        HashMap<Integer,Ladder> ladderList = new HashMap<>();

        int noOfLadders;
        int noOfSnakes;
        System.out.println("Enter Ladder Size");
        Scanner scanner = new Scanner(System.in);
        noOfLadders = scanner.nextInt();
        for (int i = 0; i < noOfLadders; i++) {
            System.out.println("Enter Ladder Value");
            Ladder ladder = new Ladder(scanner.nextInt(), scanner.nextInt());
            ladderList.put(ladder.getStart(),ladder);
        }
        System.out.println("Enter Snake Size");
        noOfSnakes = scanner.nextInt();
        for (int i = 0; i < noOfSnakes; i++) {
            System.out.println("Enter Snake Size");
            Snake snake = new Snake(scanner.nextInt(), scanner.nextInt());
            snakeList.put(snake.getStart(),snake);
        }

        Board board = new Board(start,end,snakeList,ladderList);
        List<User> userList = new ArrayList<>();
        int noOfUsers;
        System.out.println("Enter User Size");
        noOfUsers = scanner.nextInt();
        for (int i = 0; i < noOfUsers; i++) {
            System.out.println("Enter User Details");
            userList.add(new User(scanner.next(), scanner.next()));
        }

        HashMap<String,Integer> users = new HashMap<String, Integer>();
        String winner = null;
        String currentPlayer = new String();

        Game game = new Game(board,userList,users,winner,currentPlayer);
        try{
            GameService gameService = new GameService(game, new Dice());
            gameService.startGame();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}