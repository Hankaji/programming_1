package user;
import menu.Menu;
import port.Port;

public class PortManager extends User {
    private Port currentPort;


    public PortManager(String name, String password, Port workingPort) {
        super(name, password);
        this.currentPort = workingPort;
        this.userMenu = new Menu();
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ", password='" + password + "'}" +
                "\nCurrent Port: " + currentPort.toString();
    }
}
