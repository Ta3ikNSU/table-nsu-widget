import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ooad2021.domain.Subject;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class FrontApp extends Application {

    private static final RestTemplate restTemplate = new RestTemplate();

    Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setResizable(true);
        AtomicReference<String> numberGroup = new AtomicReference<>();
        TextField textField = new TextField("");
        textField.setPrefColumnCount(9);

        Button button = new Button("Enter");
        button.setOnAction(event -> {
            numberGroup.set(textField.getText());
            requestToBackend(numberGroup.get());
        });

        FlowPane root = new FlowPane(Orientation.HORIZONTAL, 10, 10, textField, button);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 200, 50);
        stage.setTitle("group number getter");
        stage.setScene(scene);
        stage.show();
    }

    @Scheduled(cron = "* * * * *")
    private void requestToBackend(String group) {
        this.stage.close();
        RequestEntity<?> request = RequestEntity
                        .get("http://localhost:8080/widget/student?groupNumber=" + group)
                        .accept(MediaType.APPLICATION_JSON).build();


        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Vector<Subject>>>(){}.getType();
        String test = response.getBody(); //удолить subjects
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(response.getBody());
            JSONObject inside = (JSONObject) obj.get("subjects");
            Map<String, Vector<Subject>> mapWithData = gson.fromJson(inside.toJSONString(), type);
            paintData(mapWithData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void paintData(Map<String, Vector<Subject>> mapWithData) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] dayName = daysInit();
//        Vector<Subject> today = mapWithData.get(dayName[dayOfWeek-1]);
        Vector<Subject> today = mapWithData.get(dayName[3]);

        FlowPane root = new FlowPane(Orientation.VERTICAL, 1, 1);

        for(int i = 0; i < today.size(); i++){
            Text label = new Text();
            Text tutor = new Text();
            Text room = new Text();
            Rectangle r = new Rectangle();
            r.setWidth(250);
            r.setHeight(1);
            label.setText(today.get(i).getLabel());
            tutor.setText(today.get(i).getTutor());
            room.setText(today.get(i).getRoom());
            root.getChildren().add(new Text(getTime(i)));
            root.getChildren().add(label);
            root.getChildren().add(tutor);
            root.getChildren().add(room);
            root.getChildren().add(new Text(" "));
            root.getChildren().add(r);
        }
        this.stage.setTitle("TABLE");
        this.stage.setResizable(false);
        Scene scene = new Scene(root, 250, 595);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private String[] daysInit() {
        String[] dayName = new String[7];
        dayName[0] = "Sunday";
        dayName[1] = "Monday";
        dayName[2] = "Tuesday";
        dayName[3] = "Wednesday";
        dayName[4] = "Thursday";
        dayName[5] = "Friday";
        dayName[6] = "Saturday";
        return dayName;
    }
    private String getTime(int index) {
        return switch (index) {
            case 0 -> "9:00";
            case 1 -> "10:50";
            case 2 -> "12:40";
            case 3 -> "14:30";
            case 4 -> "16:20";
            case 5 -> "18:10";
            case 6 -> "20:00";
            default -> " ";
        };
    }



    public static void main(String[] args){
        Application.launch();
    }
}
