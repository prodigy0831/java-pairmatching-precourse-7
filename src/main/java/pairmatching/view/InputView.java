package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.option.PairingOption;
import pairmatching.domain.option.RematchOption;
import pairmatching.domain.util.Util;
import pairmatching.domain.option.MainOption;

public class InputView {
    private static final InputView instance = new InputView();

    public static InputView getInstance() {
        return instance;
    }

    private InputView() {
    }

    public MainOption readMainOption() {
        try {
            System.out.println(Message.INPUT_MAIN_OPTION.message);
            return MainOption.from(Util.removeSpace(Console.readLine()));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return readMainOption();
        }
    }

    public PairingOption readPairingOption() {
        try {
            System.out.println(Message.INPUT_PAIRING_OPTION.message);
            return getPairingOption();
        }catch(IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            return readPairingOption();
        }
    }

    private static PairingOption getPairingOption() {
        List<String> pairingOption = formatPairingOptionInput();
        Course course = Course.from(pairingOption.get(0));
        Level level = Level.from(pairingOption.get(1));
        Mission mission = new Mission(pairingOption.get(2));
        return new PairingOption(course, level, mission);
    }

    private static List<String> formatPairingOptionInput() {
        return Util.splitByComma(Util.removeSpace(Console.readLine()));
    }

    public RematchOption readRematchOption() {
        System.out.println(Message.INPUT_REMATCH.message);
        return RematchOption.from(Util.removeSpace(Console.readLine()));
    }

    private enum Message {
        INPUT_MAIN_OPTION("기능을 선택하세요.\n"
                + "1. 페어 매칭\n"
                + "2. 페어 조회\n"
                + "3. 페어 초기화\n"
                + "Q. 종료"),
        INPUT_PAIRING_OPTION("#############################################\n"
                + "과정: 백엔드 | 프론트엔드\n"
                + "미션:\n"
                + "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n"
                + "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n"
                + "  - 레벨3: \n"
                + "  - 레벨4: 성능개선 | 배포\n"
                + "  - 레벨5: \n"
                + "############################################\n"
                + "과정, 레벨, 미션을 선택하세요.\n"
                + "ex) 백엔드, 레벨1, 자동차경주"),
        INPUT_REMATCH("매칭 정보가 있습니다. 다시 매칭하시겠습니까?\n"
                + "네 | 아니오");

        private final String message;

        Message(String message) {
            this.message = message;
        }
    }
}
