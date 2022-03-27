package ivanov.leonid.botdbdatareader;

/*Структура хранит данные о такущих подключениях*/
public class SettingsConnection {
    private String  botAddress; //not used
    private String DBAddress;
    private String DBPort;
    private String DBusername;
    private String DBPassword;

    public String getBotAddress() {
        return botAddress;
    }

    public void setBotAddress(String botAddress) {
        this.botAddress = botAddress;
    }

    public String getDBAddress() {
        return DBAddress;
    }

    public void setDBAddress(String DBAddress) {
        this.DBAddress = DBAddress;
    }

    public String getDBPort() {
        return DBPort;
    }

    public void setDBPort(String DBPort) {
        this.DBPort = DBPort;
    }

    public String getDBusername() {
        return DBusername;
    }

    public void setDBusername(String DBusername) {
        this.DBusername = DBusername;
    }

    public String getDBPassword() {
        return DBPassword;
    }

    public void setDBPassword(String DBPassword) {
        this.DBPassword = DBPassword;
    }
}
