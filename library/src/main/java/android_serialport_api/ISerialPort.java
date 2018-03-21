package android_serialport_api;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ISerialPort {

    boolean isOpen();

    void shutdown() throws IOException;

    OutputStream getOutputStream();

    InputStream getInputStream();

}
