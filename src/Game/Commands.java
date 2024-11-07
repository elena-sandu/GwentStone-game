package Game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public class Commands {
    private Prep game_p;
    private ObjectMapper objectMapper;
    public Commands(Prep game_p) {
        this.game_p = game_p;
        this.objectMapper = new ObjectMapper();
    }
    public void actions(ActionsInput ac, ArrayNode output) {
        if(ac.getCommand().equals("getPlayerDeck")) {
            getPlayerDeck(ac.getPlayerIdx(), output);
        } else if(ac.getCommand().equals("getPlayerHero")) {
            getPlayerHero(ac.getPlayerIdx(), output);
        } else if(ac.getCommand().equals("getPlayerTurn")) {
            getPlayerTurn(output);
        } else if(ac.getCommand().equals("endPlayerTurn")) {
            endPlayerTurn(output);
        } else if(ac.getCommand().equals("placeCard")) {
            placeCard(ac.getHandIdx(), output);
        } else if(ac.getCommand().equals("getCardsInHand")) {
            getCardsInHand(ac.getPlayerIdx(), output);
        } else if(ac.getCommand().equals("getPlayerMana")) {
            getPlayerMana(ac.getPlayerIdx(), output);
        } else if(ac.getCommand().equals("getCardsOnTable")) {
            getCardsOnTable(output);
        } else if(ac.getCommand().equals("cardUsesAttack")) {
            cardUsesAttack(ac, output);
        } else if(ac.getCommand().equals("getCardAtPosition")) {
            getCardAtPosition(ac.getX(), ac.getY(), output);
        } else if(ac.getCommand().equals("cardUsesAbility")) {
            cardUsesAbility(ac, output);
        } else if(ac.getCommand().equals("useAttackHero")) {
            useAttackHero(ac.getX(), ac.getY(), output);
        } else if(ac.getCommand().equals("useHeroAbility")) {
            useHeroAbility(ac.getAffectedRow(), output);
        } else if(ac.getCommand().equals("getFrozenCardsOnTable")) {
            getFrozenCardsOnTable(output);
        }
    }

    public void getPlayerDeck(int index, ArrayNode output) {
        ObjectNode pdeckNode = objectMapper.createObjectNode();
        pdeckNode.put("command", "getPlayerDeck");
        pdeckNode.put("playerIdx", index);
        ArrayNode deckA = objectMapper.createArrayNode();
        if(index == 1) {
            ArrayList<Minions> playerOneMinions = game_p.getPlayer1().getMinions();
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
            ArrayList<Minions> playerTwoMinions = game_p.getPlayer2().getMinions();
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
    }
    public void getPlayerHero(int index, ArrayNode output) {
        ObjectNode heroNode = objectMapper.createObjectNode();
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
    }
    public void getPlayerTurn(ArrayNode output) {
        int index = game_p.getIndex_player_curent();
        ObjectNode turnNode = objectMapper.createObjectNode();
        turnNode.put("command", "getPlayerTurn");
        turnNode.put("output", index);
        output.add(turnNode);
    }
    public void endPlayerTurn(ArrayNode output) {
        int index = game_p.getIndex_player_curent();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        if(index == 1) {
            for(int i = 2; i < 4; i++) {
                for(int j = 0; j < gb.get(i).size(); j++) {
                    Minions minion = gb.get(i).get(j);
                    minion.not_frozen();
                }
            }
            game_p.getBoard().countAttack(index);
            game_p.getBoard().useAbility(index);
            game_p.getPlayer1().getHero().setHasAttacked(0);
            game_p.setIndex_player_curent(2);
            int a = game_p.getPlayer_rounds();
            a++;
            game_p.setPlayer_rounds(a);
        } else {
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < gb.get(i).size(); j++) {
                    Minions minion = gb.get(i).get(j);
                    minion.not_frozen();
                }
            }
            game_p.getBoard().countAttack(index);
            game_p.getBoard().useAbility(index);
            game_p.getPlayer2().getHero().setHasAttacked(0);
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
    }
    public void placeCard(int index, ArrayNode output) {
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
            return;
        }
        carte = player.getHand_card().get(index);
        if (carte.getMana() > player.getMana()) {
            ObjectNode invalidNode = objectMapper.createObjectNode();
            invalidNode.put("command", "placeCard");
            invalidNode.put("handIdx", index);
            invalidNode.put("error", "Not enough mana to place card on table.");
            output.add(invalidNode);
        } else if (game_p.getBoard().fullrow(player_index) == 1) {
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
    }
    public void getCardsInHand(int index, ArrayNode output) {
        ObjectNode CardsInHNode = objectMapper.createObjectNode();
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
    }
    public void getPlayerMana(int index, ArrayNode output) {
        ObjectNode manaNode = objectMapper.createObjectNode();
        manaNode.put("command", "getPlayerMana");
        manaNode.put("playerIdx", index);
        if(index == 1) {
            manaNode.put("output", game_p.getPlayer1().getMana());
        }
        else {
            manaNode.put("output", game_p.getPlayer2().getMana());
        }
        output.add(manaNode);
    }
    public void getCardsOnTable(ArrayNode output) {
        ObjectNode TableNode = objectMapper.createObjectNode();
        TableNode.put("command", "getCardsOnTable");
        ArrayNode CardsA = objectMapper.createArrayNode();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        for(ArrayList<Minions> i : gb) {
            ArrayNode auxrow = objectMapper.createArrayNode();
            for(Minions minion : i) {
                    ObjectNode one_card = objectMapper.createObjectNode();
                    one_card.put("mana", minion.getMana());
                    one_card.put("attackDamage", minion.getAttackDamage());
                    one_card.put("health", minion.getHealth());
                    one_card.put("description", minion.getDescription());
                    ArrayNode colorsNode = objectMapper.createArrayNode();
                    for (String color : minion.getColors()) {
                        colorsNode.add(color);
                    }
                    one_card.put("colors", colorsNode);
                    one_card.put("name", minion.getName());
                    auxrow.add(one_card);
            }
            CardsA.add(auxrow);
        }
        TableNode.set("output", CardsA);
        output.add(TableNode);
    }

    public void cardUsesAttack(ActionsInput ac, ArrayNode output) {
        int player = game_p.getIndex_player_curent();
        int x_attacks = ac.getCardAttacker().getX();
        int y_attacks = ac.getCardAttacker().getY();
        int x_attacked = ac.getCardAttacked().getX();
        int y_attacked = ac.getCardAttacked().getY();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        Minions cardAttacker = gb.get(x_attacks).get(y_attacks);
        if (x_attacked < 0 || x_attacked >= gb.size() || y_attacked < 0 || y_attacked >= gb.get(x_attacked).size()) {
            return;
        }
        Minions cardAttacked = gb.get(x_attacked).get(y_attacked);
        if (game_p.getBoard().isEnemyCard(player, x_attacked, y_attacked) == 0) {
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.put("command", "cardUsesAttack");
            ObjectNode attacksNode = objectMapper.createObjectNode();
            attacksNode.put("x", x_attacks);
            attacksNode.put("y", y_attacks);
            ObjectNode attackedNode = objectMapper.createObjectNode();
            attackedNode.put("x", x_attacked);
            attackedNode.put("y", y_attacked);
            cardsNode.put("cardAttacker", attacksNode);
            cardsNode.put("cardAttacked", attackedNode);
            cardsNode.put("error", "Attacked card does not belong to the enemy.");
            output.add(cardsNode);
            return;
        }
        if (cardAttacker.getAttack() == 1 || cardAttacker.getAbility() == 1) {
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.put("command", "cardUsesAttack");
            ObjectNode attacksNode = objectMapper.createObjectNode();
            attacksNode.put("x", x_attacks);
            attacksNode.put("y", y_attacks);
            ObjectNode attackedNode = objectMapper.createObjectNode();
            attackedNode.put("x", x_attacked);
            attackedNode.put("y", y_attacked);
            cardsNode.put("cardAttacker", attacksNode);
            cardsNode.put("cardAttacked", attackedNode);
            cardsNode.put("error", "Attacker card has already attacked this turn.");
            output.add(cardsNode);
            return;
        }
        if (cardAttacker.getFrozen() == 1) {
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.put("command", "cardUsesAttack");
            ObjectNode attacksNode = objectMapper.createObjectNode();
            attacksNode.put("x", x_attacks);
            attacksNode.put("y", y_attacks);
            ObjectNode attackedNode = objectMapper.createObjectNode();
            attackedNode.put("x", x_attacked);
            attackedNode.put("y", y_attacked);
            cardsNode.put("cardAttacker", attacksNode);
            cardsNode.put("cardAttacked", attackedNode);
            cardsNode.put("error", "Attacker card is frozen.");
            output.add(cardsNode);
            return;
        }
        if (board.isTank(player) >= 0) {
            if (cardAttacked.getIs_tank() == 0) {
                ObjectNode cardsNode = objectMapper.createObjectNode();
                cardsNode.put("command", "cardUsesAttack");
                ObjectNode attacksNode = objectMapper.createObjectNode();
                attacksNode.put("x", x_attacks);
                attacksNode.put("y", y_attacks);
                ObjectNode attackedNode = objectMapper.createObjectNode();
                attackedNode.put("x", x_attacked);
                attackedNode.put("y", y_attacked);
                cardsNode.put("cardAttacker", attacksNode);
                cardsNode.put("cardAttacked", attackedNode);
                cardsNode.put("error", "Attacked card is not of type 'Tank’.");
                output.add(cardsNode);
                return;
                }
        }
        int dif = cardAttacked.getHealth() - cardAttacker.getAttackDamage();
        cardAttacked.setHealth(dif);
        cardAttacker.setAttack(1);
        if (cardAttacked.getHealth() <= 0) {
            board.remove(x_attacked, y_attacked);
        }
    }
    public void getCardAtPosition(int x, int y, ArrayNode output) {
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        if (x < 0 || x >= gb.size() || y < 0 || y >= gb.get(x).size()) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "getCardAtPosition");
            errorNode.put("x", x);
            errorNode.put("y", y);
            errorNode.put("output", "No card available at that position.");
            output.add(errorNode);

        } else {
            Minions card = gb.get(x).get(y);
            ObjectNode afNode = objectMapper.createObjectNode();
            afNode.put("command", "getCardAtPosition");
            afNode.put("x", x);
            afNode.put("y", y);
            ObjectNode cardNode = objectMapper.createObjectNode();
            cardNode.put("mana", card.getMana());
            cardNode.put("attackDamage", card.getAttackDamage());
            cardNode.put("health", card.getHealth());
            cardNode.put("description", card.getDescription());
            ArrayNode colorsNode = objectMapper.createArrayNode();
            for (String color : card.getColors()) {
                colorsNode.add(color);
            }
            cardNode.put("colors", colorsNode);
            cardNode.put("name", card.getName());
            afNode.set("output", cardNode);
            output.add(afNode);
        }
    }
    public void cardUsesAbility(ActionsInput ac, ArrayNode output) {
        int player = game_p.getIndex_player_curent();
        int x_attacks = ac.getCardAttacker().getX();
        int y_attacks = ac.getCardAttacker().getY();
        int x_attacked = ac.getCardAttacked().getX();
        int y_attacked = ac.getCardAttacked().getY();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        Minions cardAttacker = gb.get(x_attacks).get(y_attacks);
        Minions cardAttacked = gb.get(x_attacked).get(y_attacked);
        if(cardAttacker.getFrozen() == 1) {
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.put("command", "cardUsesAbility");
            ObjectNode attacksNode = objectMapper.createObjectNode();
            attacksNode.put("x", x_attacks);
            attacksNode.put("y", y_attacks);
            ObjectNode attackedNode = objectMapper.createObjectNode();
            attackedNode.put("x", x_attacked);
            attackedNode.put("y", y_attacked);
            cardsNode.put("cardAttacker", attacksNode);
            cardsNode.put("cardAttacked", attackedNode);
            cardsNode.put("error", "Attacker card is frozen.");
            output.add(cardsNode);
            return;
        }
        if(cardAttacker.getAttack() == 1 || cardAttacker.getAbility() == 1) {
            ObjectNode cardsNode = objectMapper.createObjectNode();
            cardsNode.put("command", "cardUsesAbility");
            ObjectNode attacksNode = objectMapper.createObjectNode();
            attacksNode.put("x", x_attacks);
            attacksNode.put("y", y_attacks);
            ObjectNode attackedNode = objectMapper.createObjectNode();
            attackedNode.put("x", x_attacked);
            attackedNode.put("y", y_attacked);
            cardsNode.put("cardAttacker", attacksNode);
            cardsNode.put("cardAttacked", attackedNode);
            cardsNode.put("error", "Attacker card has already attacked this turn.");
            output.add(cardsNode);
            return;
        }
        if(cardAttacker.getName().equals("Disciple")) {
            if(game_p.getBoard().isEnemyCard(player, x_attacked, y_attacked) == 1) {
                ObjectNode cardsNode = objectMapper.createObjectNode();
                cardsNode.put("command", "cardUsesAbility");
                ObjectNode attacksNode = objectMapper.createObjectNode();
                attacksNode.put("x", x_attacks);
                attacksNode.put("y", y_attacks);
                ObjectNode attackedNode = objectMapper.createObjectNode();
                attackedNode.put("x", x_attacked);
                attackedNode.put("y", y_attacked);
                cardsNode.put("cardAttacker", attacksNode);
                cardsNode.put("cardAttacked", attackedNode);
                cardsNode.put("error", "Attacked card does not belong to the current player.");
                output.add(cardsNode);
                return;
            }
        } else if(cardAttacker.getName().equals("The Ripper") || cardAttacker.getName().equals("Miraj") || cardAttacker.getName().equals("The Cursed One")) {
            if(game_p.getBoard().isEnemyCard(player, x_attacked, y_attacked) == 0) {
                ObjectNode cardsNode = objectMapper.createObjectNode();
                cardsNode.put("command", "cardUsesAbility");
                ObjectNode attacksNode = objectMapper.createObjectNode();
                attacksNode.put("x", x_attacks);
                attacksNode.put("y", y_attacks);
                ObjectNode attackedNode = objectMapper.createObjectNode();
                attackedNode.put("x", x_attacked);
                attackedNode.put("y", y_attacked);
                cardsNode.put("cardAttacker", attacksNode);
                cardsNode.put("cardAttacked", attackedNode);
                cardsNode.put("error", "Attacked card does not belong to the enemy.");
                output.add(cardsNode);
                return;
            } else if(board.isTank(player) >= 0) {
                if(cardAttacked.getIs_tank() == 0) {
                    ObjectNode cardsNode = objectMapper.createObjectNode();
                    cardsNode.put("command", "cardUsesAbility");
                    ObjectNode attacksNode = objectMapper.createObjectNode();
                    attacksNode.put("x", x_attacks);
                    attacksNode.put("y", y_attacks);
                    ObjectNode attackedNode = objectMapper.createObjectNode();
                    attackedNode.put("x", x_attacked);
                    attackedNode.put("y", y_attacked);
                    cardsNode.put("cardAttacker", attacksNode);
                    cardsNode.put("cardAttacked", attackedNode);
                    cardsNode.put("error", "Attacked card is not of type 'Tank’.");
                    output.add(cardsNode);
                    return;
                }
            }
        }
        if(cardAttacker.getName().equals("The Ripper")) {
            int aux = cardAttacked.getAttackDamage() - 2;
            if(aux < 0) {
                cardAttacked.setAttackDamage(0);
            } else {
                cardAttacked.setAttackDamage(aux);
            }
        } else if(cardAttacker.getName().equals("Miraj")) {
            int aux = cardAttacker.getHealth();
            cardAttacker.setHealth(cardAttacked.getHealth());
            cardAttacked.setHealth(aux);
        } else if(cardAttacker.getName().equals("The Cursed One")) {
            int attack = cardAttacked.getAttackDamage();
            int h = cardAttacked.getHealth();
            cardAttacked.setHealth(attack);
            cardAttacked.setAttackDamage(h);
            if(attack == 0) {
                game_p.getBoard().remove(x_attacked, y_attacked);
            }
        } else if(cardAttacker.getName().equals("Disciple")) {
            int aux = cardAttacked.getHealth();
            cardAttacked.setHealth(aux + 2);
        }
        cardAttacker.setAbility(1);
    }
    public void useAttackHero(int x, int y, ArrayNode output) {
        int player = game_p.getIndex_player_curent();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        if (x < 0 || x >= gb.size() || y < 0 || y >= gb.get(x).size()) {
            return;
        }
        Minions cardAttacker = gb.get(x).get(y);
        if(cardAttacker.getFrozen() == 1) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "useAttackHero");
            ObjectNode atNode = objectMapper.createObjectNode();
            atNode.put("x", x);
            atNode.put("y", y);
            errorNode.put("error", atNode);
            errorNode.put("error", "Attacker card is frozen.");
            output.add(errorNode);
            return;
        }
        if(cardAttacker.getAttack() == 1 || cardAttacker.getAbility() == 1) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "useAttackHero");
            ObjectNode atNode = objectMapper.createObjectNode();
            atNode.put("x", x);
            atNode.put("y", y);
            errorNode.put("error", atNode);
            errorNode.put("error", "Attacker card has already attacked this turn.");
            output.add(errorNode);
            return;
        }
        if(game_p.getBoard().isTank(player) >= 0) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "useAttackHero");
            ObjectNode atNode = objectMapper.createObjectNode();
            atNode.put("x", x);
            atNode.put("y", y);
            errorNode.put("error", atNode);
            errorNode.put("error", "Attacked card is not of type 'Tank’.");
            output.add(errorNode);
            return;
        }
        int aux = cardAttacker.getAttackDamage();
        if(player == 1) {
            int h = game_p.getPlayer2().getHero().getHealth();
            int newH = h - aux;
            game_p.getPlayer2().getHero().setHealth(newH);
            cardAttacker.setAttack(1);
            if(game_p.getPlayer2().getHero().getHealth() <= 0) {
                ObjectNode endNode = objectMapper.createObjectNode();
                endNode.put("gameEnded", "Player one killed the enemy hero.");
                output.add(endNode);
            }
        } else {
            int h = game_p.getPlayer1().getHero().getHealth();
            game_p.getPlayer1().getHero().setHealth(h - aux);
            cardAttacker.setAttack(1);
            if(game_p.getPlayer1().getHero().getHealth() <= 0) {
                ObjectNode endNode = objectMapper.createObjectNode();
                endNode.put("gameEnded", "Player two killed the enemy hero.");
                output.add(endNode);
            }
        }

    }
    public void useHeroAbility(int x, ArrayNode output) {
        int player = game_p.getIndex_player_curent();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        ArrayList<Minions> rowAffected = gb.get(x);
        Players p;
        if(player == 1) {
            p = game_p.getPlayer1();
        }
        else {
            p = game_p.getPlayer2();
        }
        if(p.getMana() < p.getHero().getMana()) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "useHeroAbility");
            errorNode.put("affectedRow", x);
            errorNode.put("error", "Not enough mana to use hero's ability.");
            output.add(errorNode);
            return;
        }
        if(p.getHero().getHasAttacked() == 1) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("command", "useHeroAbility");
            errorNode.put("affectedRow", x);
            errorNode.put("error", "Hero has already attacked this turn.");
            output.add(errorNode);
            return;
        }
        if(p.getHero().getName().equals("Lord Royce") || p.getHero().getName().equals("Empress Thorina")) {
            if(player == 1 && (x == 2 || x == 3)) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("command", "useHeroAbility");
                errorNode.put("affectedRow", x);
                errorNode.put("error", "Selected row does not belong to the enemy.");
                output.add(errorNode);
                return;
            } else if(player == 2 && (x == 0 || x == 1)) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("command", "useHeroAbility");
                errorNode.put("affectedRow", x);
                errorNode.put("error", "Selected row does not belong to the enemy.");
                output.add(errorNode);
                return;
            }
        } else if(p.getHero().getName().equals("General Kocioraw") || p.getHero().getName().equals("King Mudface")) {
            if(player == 1 && (x == 0 || x == 1)) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("command", "useHeroAbility");
                errorNode.put("affectedRow", x);
                errorNode.put("error", "Selected row does not belong to the current player.");
                output.add(errorNode);
                return;
            } else if(player == 2 && (x == 2 || x == 3)) {
                ObjectNode errorNode = objectMapper.createObjectNode();
                errorNode.put("command", "useHeroAbility");
                errorNode.put("affectedRow", x);
                errorNode.put("error", "Selected row does not belong to the current player.");
                output.add(errorNode);
                return;
            }
        }
        if(p.getHero().getName().equals("Lord Royce")) {
            for(Minions m : rowAffected) {
                m.setFrozen(1);
            }
        } else if(p.getHero().getName().equals("Empress Thorina")) {
            Minions maxHealth = rowAffected.get(0);
            int index = 0;
            for(int i = 0; i < rowAffected.size(); i++) {
                if(maxHealth.getHealth() > rowAffected.get(i).getHealth()) {
                    index = i;
                    maxHealth = rowAffected.get(i);
                }
            }
            rowAffected.remove(index);
        } else if(p.getHero().getName().equals("King Mudface")) {
            for(Minions m : rowAffected) {
                int aux = m.getHealth();
                aux++;
                m.setHealth(aux);
            }
        } else if(p.getHero().getName().equals("General Kocioraw")) {
            for(Minions m : rowAffected) {
                int aux = m.getAttackDamage();
                aux++;
                m.setAttackDamage(aux);
            }
        }
        p.getHero().setHasAttacked(1);
        int newMana = p.getMana() - p.getHero().getMana() ;
        p.setMana(newMana);
    }
    public void getFrozenCardsOnTable(ArrayNode output) {
        ObjectNode TableNode = objectMapper.createObjectNode();
        TableNode.put("command", "getFrozenCardsOnTable");
        ArrayNode CardsA = objectMapper.createArrayNode();
        Game_board board = game_p.getBoard();
        ArrayList<ArrayList<Minions>> gb = board.getBoard();
        for(ArrayList<Minions> i : gb) {
            ArrayNode auxrow = objectMapper.createArrayNode();
            for(Minions minion : i) {
                if(minion.getFrozen() == 1) {
                    ObjectNode one_card = objectMapper.createObjectNode();
                    one_card.put("mana", minion.getMana());
                    one_card.put("attackDamage", minion.getAttackDamage());
                    one_card.put("health", minion.getHealth());
                    one_card.put("description", minion.getDescription());
                    ArrayNode colorsNode = objectMapper.createArrayNode();
                    for (String color : minion.getColors()) {
                        colorsNode.add(color);
                    }
                    one_card.put("colors", colorsNode);
                    one_card.put("name", minion.getName());
                    CardsA.add(one_card);
                }
            }
        }
        if (!CardsA.isEmpty()) {
            TableNode.set("output", CardsA);
        } else {
            TableNode.set("output", objectMapper.createArrayNode());
        }
        output.add(TableNode);
    }
}

