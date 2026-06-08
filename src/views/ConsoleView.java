// package views;

// import domain.card.Card;
// import domain.hand.Type;
// import domain.others.Blind;
// import domain.others.Planet;
// import model.GameState;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Objects;
// import java.util.stream.IntStream;

// public class ConsoleView implements View {

//     private static final int MAX_SELECTION = 5;

//     @Override
//     public void displayWelcome() {
//         IO.println("==================================================");
//         IO.println("         BIENVENUE DANS LE JEU BALATRI !          ");
//         IO.println("==================================================");
//         IO.println("     Préparez-vous à affronter les Blinds !       ");
//         IO.println("");
//     }

//     @Override
//     public void displayGameState(GameState state) {
//         Objects.requireNonNull(state, "Le paramètre state ne doit pas être null");
//         IO.println("\n--- État de la partie ---");
//         IO.println("Score : " + state.score());
//         IO.println("Mains restantes : " + state.handsRemaining());
//         if (state.currentBlind() != null) {
//             IO.println("Blind actuel : " + state.currentBlind().name() + " (Objectif : " + state.currentBlind().score() + ")");
//         }
//         IO.println("-------------------------");
//     }

//     @Override
//     public void displayDrawnCards(List<Card> cards) {
//         Objects.requireNonNull(cards, "La liste de cartes ne doit pas être null");
//         IO.println("\nCartes piochées :");
//         IntStream.range(0, cards.size())
//                 .forEach(i -> IO.println((i + 1) + ". " + cards.get(i)));
//     }

//     @Override
//     public List<Integer> requestSelection(List<Card> cards) {
//         Objects.requireNonNull(cards, "La liste de cartes ne doit pas être null");
//         while (true) {
//             try {
//                 var input = IO.readln("\nSélectionnez vos cartes (entrez les numéros séparés par des espaces, de 1 à " + cards.size() + ") : ");
//                 if (input == null || input.isBlank()) continue;

//                 var indices = Arrays.stream(input.trim().split("\\s+"))
//                         .filter(p -> !p.isEmpty())
//                         .map(p -> Integer.parseInt(p) - 1)
//                         .toList();

//                 if (indices.stream().anyMatch(i -> i < 0 || i >= cards.size())) {
//                     IO.println("Numéro invalide. Veuillez réessayer.");
//                     continue;
//                 }
//                 if (indices.size() != MAX_SELECTION) {
//                     IO.println("Vous devez sélectionner exactement " + MAX_SELECTION + " cartes. Veuillez réessayer.");
//                     continue;
//                 }
//                 return new ArrayList<>(indices);
//             } catch (NumberFormatException e) {
//                 IO.println("Saisie invalide. Entrez uniquement des nombres. Veuillez réessayer.");
//             }
//         }
//     }

//     @Override
//     public void displayHandType(Type type, int score) {
//         Objects.requireNonNull(type, "Le type de main ne doit pas être null");
//         if (score < 0) throw new IllegalArgumentException("Le score ne peut pas être négatif");
//         IO.println("Combinaison jouée : " + type.nom() + " ! Score obtenu : " + score);
//     }

//     @Override
//     public void displayBlindBeaten(Blind blind) {
//         Objects.requireNonNull(blind, "Le blind ne doit pas être null");
//         IO.println("\nFélicitations ! Vous avez battu le blind : " + blind.name() + " !");
//     }

//     @Override
//     public void displayObtainedPlanet(Planet planet) {
//         Objects.requireNonNull(planet, "La planète ne doit pas être null");
//         IO.println("Vous avez obtenu la planète : " + planet.name() + " !");
//         IO.println("Elle améliore la combinaison : " + planet.handType().nom());
//     }

//     @Override
//     public void displayVictory() {
//         IO.println("\n=========================================");
//         IO.println("   VICTOIRE ! Vous avez gagné la partie !  ");
//         IO.println("=========================================");
//     }

//     @Override
//     public void displayDefeat() {
//         IO.println("\n=========================================");
//         IO.println("                 DÉFAITE                 ");
//         IO.println("      Vous n'avez plus de mains...       ");
//         IO.println("=========================================");
//     }

//     @Override
//     public void displayMessage(String message) {
//         Objects.requireNonNull(message, "Le message ne doit pas être null");
//         IO.println(message);
//     }
// }
