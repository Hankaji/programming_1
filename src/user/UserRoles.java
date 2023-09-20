package user;

public enum UserRoles {
    ADMIN(Admin.class.getName()),
    PORT_MANAGER(PortManager.class.getName());

    private final String value;

    private UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
