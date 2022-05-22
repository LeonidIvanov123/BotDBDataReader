package ivanov.leonid.botdbdatareader;

/*Структура хранит данные о такущих подключениях*/
public class SettingsConnection {
    private String  botAddress;
    private String DBAddress;
    private String DBusername;
    private String DBPassword;

    public SettingsConnection(String data) {
        if(!data.equals("")){
            setDBAddress(data.substring((data.lastIndexOf("DBAddress='") + 11), data.indexOf("', DBusername='")));
        }
    }

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

    @Override
    public String toString() {
        return "SettingsConnection{" +
                "botAddress='" + botAddress + '\'' +
                ", DBAddress='" + DBAddress + '\'' +
                ", DBusername='" + DBusername + '\'' +
                ", DBPassword='" + DBPassword + '\'' +
                '}';
    }
}
