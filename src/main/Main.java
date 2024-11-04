package main;

import Game.*;
import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayList<GameInput> games =  inputData.getGames();
        ArrayNode output = objectMapper.createArrayNode();
        ObjectMapper mapper = new ObjectMapper();
        for (GameInput aux : games) {
            StartGameInput game = aux.getStartGame();
            Prep game_p = new Prep();
            //carti player 1
            int shuffleSeed = aux.getStartGame().getShuffleSeed();
            ArrayList<Minions> playerOneMinions = new ArrayList<>();
            ArrayList<ArrayList<CardInput>> deck1 = inputData.getPlayerOneDecks().getDecks();
            ArrayList<CardInput> un_deck1 = deck1.get(game.getPlayerOneDeckIdx());
            Collections.shuffle(un_deck1, new Random(shuffleSeed));
            ArrayList<Minions> hands = new ArrayList<>();
            CardInput hand_card = un_deck1.get(0);
            un_deck1.remove(0);
            for(CardInput c : un_deck1) {
                Minions min = new Minions(c.getMana(),c.getHealth(),c.getAttackDamage(),c.getDescription(),c.getColors(),c.getName());
                playerOneMinions.add(min);
            }
            //carti player 2
            ArrayList<Minions> playerTwoMinions = new ArrayList<>();
            ArrayList<ArrayList<CardInput>> deck2 = inputData.getPlayerTwoDecks().getDecks();
            ArrayList<CardInput> un_deck = deck2.get(game.getPlayerTwoDeckIdx());
            Collections.shuffle(un_deck, new Random(shuffleSeed));
            CardInput hand_card2 = un_deck.get(0);
            ArrayList<Minions> hands2 = new ArrayList<>();
            un_deck.remove(0);
            for(CardInput c : un_deck) {
                Minions min = new Minions(c.getMana(),c.getHealth(),c.getAttackDamage(),c.getDescription(),c.getColors(),c.getName());
                playerTwoMinions.add(min);
            }
            game_p.start(game);
            game_p.setPlayer_rounds(0);
            game_p.getPlayer1().setMinions(playerOneMinions);
            Minions card1 = new Minions(hand_card.getMana(), hand_card.getHealth(), hand_card.getAttackDamage(), hand_card.getDescription(), hand_card.getColors(), hand_card.getName());
            game_p.getPlayer1().addHand_card(card1);
            Minions card2 = new Minions(hand_card2.getMana(), hand_card2.getHealth(), hand_card2.getAttackDamage(), hand_card2.getDescription(), hand_card2.getColors(), hand_card2.getName());
            game_p.getPlayer2().setMinions(playerTwoMinions);
            game_p.getPlayer2().addHand_card(card2);

            ArrayList<ActionsInput> actions = aux.getActions();
            for(ActionsInput ac : actions){
                if(ac.getCommand().equals("getPlayerDeck")) {
                    int index = ac.getPlayerIdx();
                    ObjectNode pdeckNode = mapper.createObjectNode();
                    pdeckNode.put("command", "getPlayerDeck");
                    pdeckNode.put("playerIdx", index);
                    ArrayNode deckA = mapper.createArrayNode();
                    if(index == 1) {
                        for(Minions c : playerOneMinions) {
                            ObjectNode cardNode = objectMapper.createObjectNode();
                            cardNode.put("mana", c.getMana());
                            cardNode.put("attackDamage", c.getAttackDamage());
                            cardNode.put("health", c.getHealth());
                            cardNode.put("description", c.getDescription());
                            ArrayNode colorsNode = objectMapper.createArrayNode();
                            for(String color : c.getColors()) {
                                colorsNode.add(color);
                            }
                            cardNode.put("colors", colorsNode);
                            cardNode.put("name", c.getName());
                            deckA.add(cardNode);

                        }
                        pdeckNode.set("output", deckA);
                        output.add(pdeckNode);
                    }
                    else {
                        for(Minions c : playerTwoMinions) {
                            ObjectNode cardNode = objectMapper.createObjectNode();
                            cardNode.put("mana", c.getMana());
                            cardNode.put("attackDamage", c.getAttackDamage());
                            cardNode.put("health", c.getHealth());
                            cardNode.put("description", c.getDescription());
                            ArrayNode colorsNode = objectMapper.createArrayNode();
                            for(String color : c.getColors()) {
                                colorsNode.add(color);
                            }
                            cardNode.put("colors", colorsNode);
                            cardNode.put("name", c.getName());
                            deckA.add(cardNode);
                        }
                        pdeckNode.set("output", deckA);
                        output.add(pdeckNode);
                    }

                } else if(ac.getCommand().equals("getPlayerHero")) {
                    int index = ac.getPlayerIdx();
                    ObjectNode heroNode = mapper.createObjectNode();
                    heroNode.put("command", "getPlayerHero");
                    heroNode.put("playerIdx", index);
                    ObjectNode heroA = objectMapper.createObjectNode();
                    if(index == 1) {
                        heroA.put("mana", game_p.getPlayer1().getHero().getMana());
                        heroA.put("description", game_p.getPlayer1().getHero().getDescription());
                        ArrayNode colorsNode = objectMapper.createArrayNode();
                        for(String color : game_p.getPlayer1().getHero().getColors()) {
                            colorsNode.add(color);
                        }
                        heroA.put("colors", colorsNode);
                        heroA.put("name", game_p.getPlayer1().getHero().getName());
                        heroA.put("health", game_p.getPlayer1().getHero().getHealth());
                    } else {
                        heroA.put("mana", game_p.getPlayer2().getHero().getMana());
                        heroA.put("description", game_p.getPlayer2().getHero().getDescription());
                        ArrayNode colorsNode = objectMapper.createArrayNode();
                        for(String color : game_p.getPlayer2().getHero().getColors()) {
                            colorsNode.add(color);
                        }
                        heroA.put("colors", colorsNode);
                        heroA.put("name", game_p.getPlayer2().getHero().getName());
                        heroA.put("health", game_p.getPlayer2().getHero().getHealth());
                    }
                    heroNode.set("output", heroA);
                    output.add(heroNode);
                } else if(ac.getCommand().equals("getPlayerTurn")) {
                    int index = game.getStartingPlayer();
                    ObjectNode turnNode = mapper.createObjectNode();
                    turnNode.put("command", "getPlayerTurn");
                    turnNode.put("output", index);
                    output.add(turnNode);
                } else if(ac.getCommand().equals("endPlayerTurn")) {
                    int index = game_p.getIndex_player_curent();
                    if(index == 1) {
                        for (Minions m : playerOneMinions) {
                            m.not_frozen();
                        }
                        game_p.setIndex_player_curent(2);
                        int a = game_p.getPlayer_rounds();
                        a++;
                        game_p.setPlayer_rounds(a);
                    } else {
                        for (Minions m : playerTwoMinions) {
                            m.not_frozen();
                        }
                        game_p.setIndex_player_curent(1);
                        int a = game_p.getPlayer_rounds();
                        a++;
                        game_p.setPlayer_rounds(a);
                    }
                    int a = game_p.getPlayer_rounds();
                    if(a == 2) {
                        game_p.StartRound();
                        game_p.setPlayer_rounds(0);

                    }
                } else if(ac.getCommand().equals("placeCard")) {
                    int index = ac.getHandIdx();
                    int player_index = game_p.getIndex_player_curent();
                    Minions carte;
                    Players player;
                    if(player_index == 1) {
                        player = game_p.getPlayer1();
                    }
                    else {
                        player = game_p.getPlayer2();
                    }
                    if (index < 0 || index >= player.getHand_card().size()) {
                        ;
                    }
                    else {
                        carte = player.getHand_card().get(index);
                        if (carte.getMana() <= player.getMana()) {
                            if (game_p.getBoard().fullrow(player_index) == 1) {
                                ObjectNode invalidNode = objectMapper.createObjectNode();
                                invalidNode.put("command", "placeCard");
                                invalidNode.put("handIdx", index);
                                invalidNode.put("error", "Cannot place card on table since row is full.");
                                output.add(invalidNode);
                            } else {
                                game_p.getBoard().addCard(player_index, carte);
                                player.setMana(player.getMana() - carte.getMana());
                                player.getHand_card().remove(index);
                            }
                        } else {
                            ObjectNode invalidNode = objectMapper.createObjectNode();
                            invalidNode.put("command", "placeCard");
                            invalidNode.put("handIdx", index);
                            invalidNode.put("error", "Not enough mana to place card on table.");
                            output.add(invalidNode);
                        }
                    }
                } else if(ac.getCommand().equals("getCardsInHand")) {
                        int index = ac.getPlayerIdx();
                        ObjectNode CardsInHNode = mapper.createObjectNode();
                        CardsInHNode.put("command", "getCardsInHand");
                        CardsInHNode.put("playerIdx", index);
                        ArrayNode CardsA = objectMapper.createArrayNode();
                        ArrayList<Minions> cardshand;
                        if(index == 1) {
                            cardshand = game_p.getPlayer1().getHand_card();
                        }
                        else {
                            cardshand = game_p.getPlayer2().getHand_card();
                        }
                        for(Minions m : cardshand) {
                            ObjectNode one_card = objectMapper.createObjectNode();
                            one_card.put("mana", m.getMana());
                            one_card.put("attackDamage", m.getAttackDamage());
                            one_card.put("health", m.getHealth());
                            one_card.put("description", m.getDescription());
                            ArrayNode colorsNode = objectMapper.createArrayNode();
                            for(String color : m.getColors()) {
                                colorsNode.add(color);
                            }
                            one_card.put("colors", colorsNode);
                            one_card.put("name", m.getName());
                            CardsA.add(one_card);
                        }
                        CardsInHNode.set("output", CardsA);
                        output.add(CardsInHNode);
                } else if(ac.getCommand().equals("getPlayerMana")) {
                    int index = ac.getPlayerIdx();
                    ObjectNode manaNode = mapper.createObjectNode();
                    manaNode.put("command", "getPlayerMana");
                    manaNode.put("playerIdx", index);
                    if(index == 1) {
                        manaNode.put("output", game_p.getPlayer1().getMana());
                    }
                    else {
                        manaNode.put("output", game_p.getPlayer2().getMana());
                    }
                    output.add(manaNode);
                } else if(ac.getCommand().equals("getCardsOnTable")) {
                    ObjectNode TableNode = mapper.createObjectNode();
                    TableNode.put("command", "getCardsOnTable");
                    ArrayNode CardsA = objectMapper.createArrayNode();
                    Game_board board = game_p.getBoard();
                    Minions[][] gb = board.getBoard();
                    for(int i = 0; i < 4; i++) {
                        ArrayNode auxrow = objectMapper.createArrayNode();
                        for(int j = 0; j < 5; j++) {
                            if(gb[i][j] != null) {
                                ObjectNode one_card = objectMapper.createObjectNode();
                                one_card.put("mana", gb[i][j].getMana());
                                one_card.put("attackDamage", gb[i][j].getAttackDamage());
                                one_card.put("health", gb[i][j].getHealth());
                                one_card.put("description", gb[i][j].getDescription());
                                ArrayNode colorsNode = objectMapper.createArrayNode();
                                for (String color : gb[i][j].getColors()) {
                                    colorsNode.add(color);
                                }
                                one_card.put("colors", colorsNode);
                                one_card.put("name", gb[i][j].getName());
                                auxrow.add(one_card);
                            }
                        }
                        CardsA.add(auxrow);
                    }
                    TableNode.set("output", CardsA);
                    output.add(TableNode);
                }
            }


        }



          //TODO Implement your function here

          //How to add output to the output array?
         // There are multiple ways to do this, here is one example:

          //ObjectMapper mapper = new ObjectMapper();

          //ObjectNode objectNode = mapper.createObjectNode();
          //objectNode.put("field_name", "field_value");

          //ArrayNode arrayNode = mapper.createArrayNode();
          //arrayNode.add(objectNode);

          //output.add(arrayNode);
          //output.add(objectNode);



        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
