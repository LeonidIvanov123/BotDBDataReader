package ivanov.leonid.botdbdatareader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {

    File filesettings;
    SettingsConnection sconnection;
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

    public void saveSettings(ActionEvent actionEvent) throws IOException {
        if(addrportSettings.getText().equals("")){
            loglableSettings.setText("Не введен адрес и порт БД");
        }else {
            filesettings = new File("./filesetting");
            if(!filesettings.exists())
                filesettings.createNewFile();
            sconnection = new SettingsConnection("");
            sconnection.setDBAddress(addrportSettings.getText());
            sconnection.setDBusername(usernameSettings.getText());
            sconnection.setDBPassword(passwordSettings.getText());
            FileWriter fw = new FileWriter(filesettings,true);
            fw.append(sconnection.toString() + '\n');
            fw.close();
            loglableSettings.setText("Информация обновлена..." + filesettings.getAbsolutePath());
        }
    }

    public void initData(SettingsConnection sc){
        SettingsConnection settingsConnection = sc;
    }
}
