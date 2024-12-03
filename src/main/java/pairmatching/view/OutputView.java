package pairmatching.view;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import pairmatching.domain.Crew;

public class OutputView {
    private static final OutputView instance = new OutputView();

    public static OutputView getInstance() {
        return instance;
    }

    private OutputView() {
    }

    public void printExceptionMessage(Exception exception) {
        System.out.println(exception.getMessage());
    }

    public void printFailMatching() {
        System.out.println(Message.OUTPUT_FAIL_PAIR_MATCHING.message);
    }

    public void printPairMatching(List<Set<Crew>> pairMatchingResult) {
        System.out.println(Message.OUTPUT_PAIR_MATCHING_RESULT.message);
        for (Set<Crew> pair : pairMatchingResult) {
            System.out.println(formatPairMatching(pair));
        }
    }

    private String formatPairMatching(Set<Crew> pair) {
        return pair.stream().map(Crew::getName).collect(Collectors.joining(" : "));
    }

    public void printNoMatchingHistory() {
        System.out.println(Message.OUTPUT_NO_MATCHING_HISTORY.message);
    }

    private enum Message {
        OUTPUT_PAIR_MATCHING_RESULT("페어 매칭 결과입니다."),
        OUTPUT_PAIR_INITIALIZED("초기화 되었습니다. "),
        OUTPUT_FAIL_PAIR_MATCHING("매칭에 실패하였습니다. 메인 화면으로 돌아갑니다."),
        OUTPUT_NO_MATCHING_HISTORY("[ERROR] 매칭 이력이 없습니다.");
        private final String message;

        Message(String message) {
            this.message = message;
        }
    }

}
