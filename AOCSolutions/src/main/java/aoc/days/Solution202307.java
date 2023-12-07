package aoc.days;

import aoc.framework.Day;
import aoc.framework.DaySolution;
import aoc.framework.model.Pair;

import java.util.*;

@DaySolution(year = 2023, day = 7)
public class Solution202307 extends Day {

    private static final String INPUT_FILE = "202307/input.txt";

    private static final List<Character> CARDS = Arrays.asList('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');
    private static final List<Character> CARDS_PART_2 = Arrays.asList('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J');

    private enum HandType {FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD}

    public Solution202307(boolean printOutput) {
        super(printOutput, INPUT_FILE);
    }

    @Override
    public String solvePart1() {
        List<Long> orderedBids = parseCards().stream()
                .sorted(this::compareHands)
                .map(Pair::getSecond)
                .toList();
        long result = 0L;
        for(int i = 0; i<orderedBids.size(); i++) {
            result += orderedBids.get(i)* (i+1);
        }
        return ""+result;
    }

    @Override
    public String solvePart2() {
        List<Pair<List<Character>, Long>> orderedBids = parseCards().stream()
                .sorted(this::compareHandsWithJoker)
                .toList();
        long result = 0L;
        for(int i = 0; i<orderedBids.size(); i++) {
            printToOutput("Order:" + orderedBids.get(i));
            result += orderedBids.get(i).getSecond()* (i+1);
        }
        return ""+result;
    }

    List<Pair<List<Character>, Long>> parseCards() {
        List<Pair<List<Character>, Long>> result = new LinkedList<>();
        for (String line: parsedInput) {
            long bid = Long.parseLong(line.split(" ")[1]);
            List<Character> hand = parseHand(line);
            result.add(new Pair<>(hand, bid));
        }
        return result;
    }

    private int compareHandsWithJoker(Pair<List<Character>, Long> first, Pair<List<Character>, Long> second) {
        HandType handTypeFirst = getHandTypeWithJoker(first.getFirst());
        HandType handTypeSecond = getHandTypeWithJoker(second.getFirst());

        if (handTypeFirst.equals(handTypeSecond)) {
            return compareHandBasedOnCardValues(first, second, CARDS_PART_2);
        }
        return (int) (scoreHandType(getHandTypeWithJoker(first.getFirst())) - scoreHandType(getHandTypeWithJoker(second.getFirst())));
    }

    private int compareHands(Pair<List<Character>, Long> first, Pair<List<Character>, Long> second) {
        if (getHandType(first.getFirst()).equals(getHandType(second.getFirst()))) {
            return compareHandBasedOnCardValues(first, second, CARDS);

        }
        return (int) (scoreHandType(getHandType(first.getFirst())) - scoreHandType(getHandType(second.getFirst())));
    }

    private Integer compareHandBasedOnCardValues(Pair<List<Character>, Long> first, Pair<List<Character>, Long> second, List<Character> cards) {
        for (int i = 0; i<first.getFirst().size(); i++){
            if(!first.getFirst().get(i).equals(second.getFirst().get(i))) {
                return cards.indexOf(second.getFirst().get(i)) - cards.indexOf(first.getFirst().get(i));
            }
        }
        return 0;
    }

    private List<Character> parseHand (String line) {
        return Arrays.stream(line.split(" ")[0].split(""))
                .map(s -> s.charAt(0))
                .toList();
    }

    private HandType getHandTypeWithJoker(List<Character> cards) {

        char numberDistinct = countOccurrences(cards).entrySet().stream()
                .filter(entry -> !entry.getKey().equals('J') )
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('2');


        List<Character> upgradedCards = cards.stream()
                .map(character -> character.equals('J') ? numberDistinct: character)
                .toList();

        return getHandType(upgradedCards);
    }

    private long scoreHandType(HandType handType) {
        return switch (handType) {
            case FIVE_OF_A_KIND -> 7;
            case FOUR_OF_A_KIND -> 6;
            case FULL_HOUSE -> 5;
            case THREE_OF_A_KIND -> 4;
            case TWO_PAIR -> 3;
            case ONE_PAIR -> 2;
            case HIGH_CARD -> 1;
        };
    }

    private HandType getHandType(List<Character> cards) {
        Set<Character> filtered = new HashSet<>(cards);

        long numberDistinct = countOccurrences(cards).values().stream()
                .max(Long::compare).orElse(0L);


        return switch (filtered.size()) {
            case 1 -> HandType.FIVE_OF_A_KIND;
            case 3 -> numberDistinct == 3? HandType.THREE_OF_A_KIND: HandType.TWO_PAIR;
            case 2 -> numberDistinct == 4? HandType.FOUR_OF_A_KIND: HandType.FULL_HOUSE;
            case 4 -> HandType.ONE_PAIR;
            default -> HandType.HIGH_CARD;
        };
    }

    private  Map<Character, Long> countOccurrences(List<Character> cards){
        Map<Character, Long> counts = new HashMap<>();
        cards.forEach(c-> counts.merge(c, 1L, Long::sum));
        return counts;

    }
}
