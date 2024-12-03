package pairmatching.controller.subController;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import pairmatching.domain.PairMatchingResult;
import pairmatching.domain.option.PairingOption;
import pairmatching.domain.repository.PairMatchingResults;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class PairMatchingController implements Controllable {
    private final InputView inputView;
    private final OutputView outputView;

    private final Map<PairMatchingStatus, Supplier<PairMatchingStatus>> pairMatchingGuide;
    private PairingOption pairingOption;
    private PairMatchingResult result;
    private int attempts = 1;

    private enum PairMatchingStatus {
        SELECT_PAIRING_OPTION,
        HANDLE_PREVIOUS_MATCHING,
        ATTEMPT_PAIR_MATCHING,
        HANDLE_DUPLICATED_PAIRS,
        SUCCESS_MATCHING,
        FAIL_MATCHING,
        EXIT_PAIR_MATCHING;

        boolean continuePairMatching() {
            return this != EXIT_PAIR_MATCHING;
        }
    }

    public PairMatchingController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.pairMatchingGuide = new EnumMap<>(PairMatchingStatus.class);
        initializePairMatchingGuide();
    }

    private void initializePairMatchingGuide() {
        pairMatchingGuide.put(PairMatchingStatus.SELECT_PAIRING_OPTION, this::selectPairingOption);
        pairMatchingGuide.put(PairMatchingStatus.HANDLE_PREVIOUS_MATCHING, this::handlePreviousMatching);
        pairMatchingGuide.put(PairMatchingStatus.ATTEMPT_PAIR_MATCHING, this::attemptPairMatching);
        pairMatchingGuide.put(PairMatchingStatus.HANDLE_DUPLICATED_PAIRS, this::handleDuplicatedPairs);
        pairMatchingGuide.put(PairMatchingStatus.SUCCESS_MATCHING, this::successMatching);

    }

    private PairMatchingStatus successMatching() {
        PairMatchingResults.addPairMatchingResult(pairingOption, result);
        outputView.printPairMatching(result.getPairMatchingResult());
        return PairMatchingStatus.EXIT_PAIR_MATCHING;
    }

    private PairMatchingStatus handleDuplicatedPairs() {
        addAttempts();
        if (isFail()) {
            outputView.printFailMatching();
            return PairMatchingStatus.FAIL_MATCHING;
        }
        return PairMatchingStatus.ATTEMPT_PAIR_MATCHING;
    }

    private boolean isFail() {
        return attempts == 3;
    }

    private void addAttempts() {
        attempts++;
    }

    private PairMatchingStatus attemptPairMatching() {
        result = new PairMatchingResult(pairingOption);
        if (PairMatchingResults.hasDuplicatedPairsInSameLevel(result)) {
            return PairMatchingStatus.HANDLE_DUPLICATED_PAIRS;
        }
        return PairMatchingStatus.SUCCESS_MATCHING;
    }

    private PairMatchingStatus handlePreviousMatching() {
        if (inputView.readRematchOption().isNo()) {
            return PairMatchingStatus.SELECT_PAIRING_OPTION;
        }
        return PairMatchingStatus.ATTEMPT_PAIR_MATCHING;
    }

    private PairMatchingStatus selectPairingOption() {
        pairingOption = inputView.readPairingOption();
        if (PairMatchingResults.hasPreviousMatching(pairingOption)) {
            return PairMatchingStatus.HANDLE_PREVIOUS_MATCHING;
        }
        return PairMatchingStatus.ATTEMPT_PAIR_MATCHING;
    }

    @Override
    public void process() {
        PairMatchingStatus pairMatchingStatus = PairMatchingStatus.SELECT_PAIRING_OPTION;
        while (pairMatchingStatus.continuePairMatching()) {
            pairMatchingStatus = pairMatchingGuide.get(pairMatchingStatus).get();
        }
    }

}
