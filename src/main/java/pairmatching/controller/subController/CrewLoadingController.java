package pairmatching.controller.subController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.repository.Crews;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class CrewLoadingController implements Controllable{

    public static final String BACK_END_CREW_PATH = "src/main/resources/backend-crew.md";
    public static final String FRONT_END_CREW_PATH = "src/main/resources/frontend-crew.md";
    private final InputView inputView;
    private final OutputView outputView;

    public CrewLoadingController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void process() {
        try{
            readCrews(BACK_END_CREW_PATH, Course.BACKEND);
            readCrews(FRONT_END_CREW_PATH,Course.FRONTEND);
        }catch(IOException exception){
            outputView.printExceptionMessage(exception);
            throw new RuntimeException(exception);
        }
    }

    private static void readCrews(String pathname,Course course) throws IOException {
        File crews = new File(pathname);
        BufferedReader crewsReader = new BufferedReader(new FileReader(crews));
        String crew;
        while((crew=crewsReader.readLine())!=null){
            Crews.addCrew(new Crew(course,crew));
        }
    }
}
