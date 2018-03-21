package android_serialport_api;

public class SerialPortException extends Exception {

    private static final long serialVersionUID = 4385467367954006479L;

    public SerialPortException(String message) {
        super(message);
    }

    public SerialPortException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerialPortException() {
        super();
    }

    public SerialPortException(Throwable cause) {
        super(cause);
    }

}
