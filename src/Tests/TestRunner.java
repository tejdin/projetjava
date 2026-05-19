package Tests;

import Controller.GameController;
import Domain.Card.Card;
import Domain.Card.Couleur;
import Domain.Card.Rank;
import Domain.Hand.HandDetector;
import Domain.Hand.Type;
import Domain.others.Blind;
import Domain.others.Planet;
import Model.Deck;
import Model.GameState;
import Views.View;

import java.util.List;

public class TestRunner {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("=== TEST RANK ET COULEUR ===");
        testRankCouleur();

        System.out.println("\n=== TEST CARD ===");
        testCard();

        System.out.println("\n=== TEST TYPE ===");
        testType();

        System.out.println("\n=== TEST PLANET ===");
        testPlanet();

        System.out.println("\n=== TEST DECK ===");
        testDeck();

        System.out.println("\n=== TEST HAND DETECTOR ===");
        testHandDetector();

        System.out.println("\n=== TEST GAME STATE ===");
        testGameState();

        System.out.println("\n=== TEST BLIND ===");
        testBlind();

        System.out.println("\n=============================");
        System.out.println("RESULTATS : " + testsPassed + " passed, " + testsFailed + " failed");
    }

    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("  [OK] " + testName);
            testsPassed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            testsFailed++;
        }
    }

    private static void testRankCouleur() {
        check("Rank AS symbole = A", Rank.AS.symbole().equals("A"));
        check("Rank AS value = 11", Rank.AS.value() == 11);
        check("Rank AS ordre = 14", Rank.AS.ordre() == 14);
        check("Rank DEUX symbole = 2", Rank.DEUX.symbole().equals("2"));
        check("Rank DEUX value = 2", Rank.DEUX.value() == 2);
        check("Rank DEUX ordre = 2", Rank.DEUX.ordre() == 2);
        check("Rank VALET value = 10", Rank.VALET.value() == 10);
        check("Rank VALET ordre = 11", Rank.VALET.ordre() == 11);
        check("4 couleurs", Couleur.values().length == 4);
    }

    private static void testCard() {
        Card c1 = new Card(Rank.AS, Couleur.COEUR);
        Card c2 = new Card(Rank.ROI, Couleur.PIQUE);
        check("Card toString AS COEUR", c1.toString().equals("ACOEUR"));
        check("Card toString ROI PIQUE", c2.toString().equals("KPIQUE"));
        check("Card rank", c1.rank() == Rank.AS);
        check("Card color", c1.color() == Couleur.COEUR);

        boolean thrown = false;
        try { new Card(null, Couleur.COEUR); } catch (NullPointerException e) { thrown = true; }
        check("Card null rank throws NPE", thrown);

        thrown = false;
        try { new Card(Rank.AS, null); } catch (NullPointerException e) { thrown = true; }
        check("Card null color throws NPE", thrown);
    }

    private static void testType() {
        check("CARTE_HAUTE points = 5", Type.CARTE_HAUTE.points() == 5);
        check("CARTE_HAUTE mult = 1", Type.CARTE_HAUTE.mult() == 1);
        check("PAIRE points = 10", Type.PAIRE.points() == 10);
        check("PAIRE mult = 2", Type.PAIRE.mult() == 2);
        check("QUINTE_FLUSH points = 100", Type.QUINTE_FLUSH.points() == 100);
        check("QUINTE_FLUSH mult = 8", Type.QUINTE_FLUSH.mult() == 8);
        check("CARRE nom = Carré", Type.CARRE.nom().equals("Carré"));
        check("9 types au total", Type.values().length == 9);
    }

    private static void testPlanet() {
        check("PLUTON boost CARTE_HAUTE", Planet.PLUTON.handType() == Type.CARTE_HAUTE);
        check("PLUTON bonusPoint = 10", Planet.PLUTON.bonusPoint() == 10);
        check("PLUTON bonusMult = 1", Planet.PLUTON.bonusMult() == 1);
        check("NEPTUNE boost QUINTE_FLUSH", Planet.NEPTUNE.handType() == Type.QUINTE_FLUSH);
        check("NEPTUNE bonusPoint = 40", Planet.NEPTUNE.bonusPoint() == 40);
        check("NEPTUNE bonusMult = 4", Planet.NEPTUNE.bonusMult() == 4);
        check("9 planetes", Planet.values().length == 9);
    }

    private static void testDeck() {
        Deck deck = new Deck();
        check("Deck initial = 52 cartes", deck.remainingCards() == 52);

        List<Card> drawn = deck.draw(5);
        check("Pioche 5 cartes = taille 5", drawn.size() == 5);
        check("Deck après pioche 5 = 47 cartes", deck.remainingCards() == 47);

        deck.discard(drawn);
        check("Deck après défausse (avant pioche) = 47 cartes", deck.remainingCards() == 47);

        // Piocher tout le reste pour provoquer le remélange
        List<Card> drawnRest = deck.draw(48);
        check("Pioche provoque le remélange (48 cartes demandées, recoit 48, incluant les 5 défaussées)", drawnRest.size() == 48);
        check("Deck après remélange et pioche = 4", deck.remainingCards() == 4);

        // Vider completement
        deck.draw(4);
        check("Deck vide", deck.remainingCards() == 0);

        List<Card> plusRien = deck.draw(1);
        check("Piocher à vide retourne 0 carte", plusRien.size() == 0);

        boolean thrown = false;
        try { deck.draw(-1); } catch (IllegalArgumentException e) { thrown = true; }
        check("Piocher nombre negatif", thrown);

        thrown = false;
        try { deck.discard(null); } catch (NullPointerException e) { thrown = true; }
        check("Defausser liste null", thrown);
    }

    private static void testHandDetector() {
        HandDetector detector = new HandDetector();

        // Carte haute
        List<Card> carteHaute = List.of(
            new Card(Rank.AS, Couleur.COEUR),
            new Card(Rank.NEUF, Couleur.TREFLE),
            new Card(Rank.SIX, Couleur.CARREAU),
            new Card(Rank.TROIS, Couleur.PIQUE),
            new Card(Rank.DEUX, Couleur.COEUR)
        );
        check("Detect CARTE_HAUTE", detector.detect(carteHaute) == Type.CARTE_HAUTE);

        // Paire
        List<Card> paire = List.of(
            new Card(Rank.SEPT, Couleur.COEUR),
            new Card(Rank.SEPT, Couleur.TREFLE),
            new Card(Rank.ROI, Couleur.PIQUE),
            new Card(Rank.QUATRE, Couleur.CARREAU),
            new Card(Rank.DEUX, Couleur.COEUR)
        );
        check("Detect PAIRE", detector.detect(paire) == Type.PAIRE);

        // Double paire
        List<Card> doublePaire = List.of(
            new Card(Rank.SEPT, Couleur.COEUR),
            new Card(Rank.SEPT, Couleur.TREFLE),
            new Card(Rank.ROI, Couleur.PIQUE),
            new Card(Rank.ROI, Couleur.CARREAU),
            new Card(Rank.DEUX, Couleur.COEUR)
        );
        check("Detect DOUBLE_PAIRE", detector.detect(doublePaire) == Type.DOUBLE_PAIRE);

        // Brelan
        List<Card> brelan = List.of(
            new Card(Rank.NEUF, Couleur.COEUR),
            new Card(Rank.NEUF, Couleur.TREFLE),
            new Card(Rank.NEUF, Couleur.PIQUE),
            new Card(Rank.DAME, Couleur.CARREAU),
            new Card(Rank.TROIS, Couleur.COEUR)
        );
        check("Detect BRELAN", detector.detect(brelan) == Type.BRELAN);

        // Suite
        List<Card> suite = List.of(
            new Card(Rank.CINQ, Couleur.COEUR),
            new Card(Rank.SIX, Couleur.TREFLE),
            new Card(Rank.SEPT, Couleur.CARREAU),
            new Card(Rank.HUIT, Couleur.PIQUE),
            new Card(Rank.NEUF, Couleur.COEUR)
        );
        check("Detect SUITE", detector.detect(suite) == Type.SUITE);

        // Suite basse (A-2-3-4-5)
        List<Card> suiteBasse = List.of(
            new Card(Rank.AS, Couleur.COEUR),
            new Card(Rank.DEUX, Couleur.TREFLE),
            new Card(Rank.TROIS, Couleur.CARREAU),
            new Card(Rank.QUATRE, Couleur.PIQUE),
            new Card(Rank.CINQ, Couleur.COEUR)
        );
        check("Detect SUITE basse (A-2-3-4-5)", detector.detect(suiteBasse) == Type.SUITE);

        // Couleur (Flush)
        List<Card> couleur = List.of(
            new Card(Rank.DEUX, Couleur.COEUR),
            new Card(Rank.CINQ, Couleur.COEUR),
            new Card(Rank.NEUF, Couleur.COEUR),
            new Card(Rank.VALET, Couleur.COEUR),
            new Card(Rank.ROI, Couleur.COEUR)
        );
        check("Detect COULEUR", detector.detect(couleur) == Type.COULEUR);

        // Full
        List<Card> full = List.of(
            new Card(Rank.TROIS, Couleur.COEUR),
            new Card(Rank.TROIS, Couleur.TREFLE),
            new Card(Rank.TROIS, Couleur.PIQUE),
            new Card(Rank.ROI, Couleur.CARREAU),
            new Card(Rank.ROI, Couleur.COEUR)
        );
        check("Detect FULL", detector.detect(full) == Type.FULL);

        // Carré
        List<Card> carre = List.of(
            new Card(Rank.DAME, Couleur.COEUR),
            new Card(Rank.DAME, Couleur.TREFLE),
            new Card(Rank.DAME, Couleur.PIQUE),
            new Card(Rank.DAME, Couleur.CARREAU),
            new Card(Rank.DEUX, Couleur.COEUR)
        );
        check("Detect CARRE", detector.detect(carre) == Type.CARRE);

        // Quinte Flush
        List<Card> quinteFlush = List.of(
            new Card(Rank.NEUF, Couleur.PIQUE),
            new Card(Rank.DIX, Couleur.PIQUE),
            new Card(Rank.VALET, Couleur.PIQUE),
            new Card(Rank.DAME, Couleur.PIQUE),
            new Card(Rank.ROI, Couleur.PIQUE)
        );
        check("Detect QUINTE_FLUSH", detector.detect(quinteFlush) == Type.QUINTE_FLUSH);
    }

    private static void testGameState() {
        GameState gs = new GameState();

        // Score
        check("Score initial = 0", gs.score() == 0);
        gs.addScore(50);
        check("Score apres +50 = 50", gs.score() == 50);
        gs.addScore(30);
        check("Score apres +30 = 80", gs.score() == 80);
        gs.resetScore();
        check("Score apres reset = 0", gs.score() == 0);

        // Hands
        check("Hands initial = 4", gs.handsRemaining() == 4);
        gs.decrementHands();
        check("Hands apres decrement = 3", gs.handsRemaining() == 3);
        gs.resetHands(5);
        check("Hands apres reset(5) = 5", gs.handsRemaining() == 5);

        // Blind
        check("currentBlind initial = null", gs.currentBlind() == null);
        Blind b = new Blind("Small Blind", 100);
        gs.setCurrentBlind(b);
        check("currentBlind set", gs.currentBlind().name().equals("Small Blind"));
        check("currentBlind score = 100", gs.currentBlind().score() == 100);

        // isBlindBeaten
        gs.resetScore();
        check("Blind pas battu (score=0)", !gs.isBlindBeaten());
        gs.addScore(99);
        check("Blind pas battu (score=99)", !gs.isBlindBeaten());
        gs.addScore(1);
        check("Blind battu (score=100)", gs.isBlindBeaten());

        // isGameLost
        gs.resetScore();
        gs.resetHands(1);
        gs.decrementHands();
        check("Game lost (hands=0, score=0)", gs.isGameLost());
        gs.addScore(100);
        check("Game not lost (hands=0, score=100)", !gs.isGameLost());

        // Planet bonus
        gs.applyPlanet(Planet.PLUTON);
        check("Bonus chips CARTE_HAUTE = 10", gs.bonusChips(Type.CARTE_HAUTE) == 10);
        check("Bonus mult CARTE_HAUTE = 1", gs.bonusMult(Type.CARTE_HAUTE) == 1);
        gs.applyPlanet(Planet.PLUTON);
        check("Bonus chips CARTE_HAUTE x2 = 20", gs.bonusChips(Type.CARTE_HAUTE) == 20);
        check("Bonus mult CARTE_HAUTE x2 = 2", gs.bonusMult(Type.CARTE_HAUTE) == 2);

        // Cards in hand
        List<Card> cards = List.of(new Card(Rank.AS, Couleur.COEUR), new Card(Rank.ROI, Couleur.PIQUE));
        gs.setCardsInHand(cards);
        check("Cards in hand size = 2", gs.cardsInHand().size() == 2);
    }

    private static void testBlind() {
        Blind b = new Blind("Big Blind", 200);
        check("Blind name", b.name().equals("Big Blind"));
        check("Blind score = 200", b.score() == 200);

        boolean thrown = false;
        try { new Blind(null, 100); } catch (NullPointerException e) { thrown = true; }
        check("Blind null name throws NPE", thrown);

        thrown = false;
        try { new Blind("test", 0); } catch (IllegalArgumentException e) { thrown = true; }
        check("Blind score=0 throws IAE", thrown);

        thrown = false;
        try { new Blind("test", -5); } catch (IllegalArgumentException e) { thrown = true; }
        check("Blind score negatif throws IAE", thrown);
    }

}
