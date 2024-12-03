package pairmatching;

import com.sun.tools.javac.Main;
import pairmatching.controller.MainController;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new MainController(InputView.getInstance(), OutputView.getInstance());
        mainController.service();
    }
}
