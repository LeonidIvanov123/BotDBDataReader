package ivanov.leonid.botdbdatareader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    @FXML
    Label loglableSettings;
    @FXML
    TextField addrportSettings,
            usernameSettings,
            passwordSettings,
            telebotSettings;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loglableSettings.setText("Информация о БД, Telegram-боте");

    }

    public void saveSettings(ActionEvent actionEvent) {


        loglableSettings.setText("Информация обновлена...");
    }

    public void initData(SettingsConnection sc){
        SettingsConnection settingsConnection = sc;
    }
}
