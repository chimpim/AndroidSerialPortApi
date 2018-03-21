package android_serialport_api;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort implements ISerialPort {
    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    private SerialPort() {
    }


    public static ISerialPort open(@NonNull String port, int baudRate,
                                   @NonNull ArgEnums.DataBit dataBit,
                                   @NonNull ArgEnums.CheckBit checkBit,
                                   @NonNull ArgEnums.StopBit stopBit,
                                   int flag) throws SerialPortException, IOException, SecurityException {
        SerialPort serialPort = new SerialPort();
        serialPort.open0(port, baudRate, dataBit, checkBit, stopBit, flag);
        return serialPort;
    }

    private void open0(@NonNull String port, int baudRate,
                       @NonNull ArgEnums.DataBit dataBit,
                       @NonNull ArgEnums.CheckBit checkBit,
                       @NonNull ArgEnums.StopBit stopBit,
                       int flag) throws SerialPortException, IOException, SecurityException {
        File device = new File(port);
        if ((!device.canRead()) || (!device.canWrite())) {
            Process localProcess = Runtime.getRuntime().exec("/system/bin/su");
            String str = "chmod 666 " + device.getAbsolutePath() + "\n" + "exit\n";
            localProcess.getOutputStream().write(str.getBytes());
            try {
                if ((localProcess.waitFor() != 0) || (!device.canRead()) || (!device.canWrite())) {
                    throw new SecurityException();
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException: " + e.getMessage());
                throw new SerialPortException(e);
            }
        }
        this.mFd = open(device.getAbsolutePath(), baudRate, dataBit.val, checkBit.val, stopBit.val, flag);
        if (this.mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new SerialPortException("native open returns null");
        }
        this.mFileInputStream = new FileInputStream(this.mFd);
        this.mFileOutputStream = new FileOutputStream(this.mFd);
    }

    @Override
    public boolean isOpen() {
        return mFd != null;
    }

    @Override
    public void shutdown() throws IOException {
        if (mFileInputStream != null) {
            mFileInputStream.close();
            mFileInputStream = null;
        }
        if (mFileOutputStream != null) {
            mFileOutputStream.close();
            mFileOutputStream = null;
        }
        mFd = null;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    static {
        System.loadLibrary("serial_port");
    }

    private static native FileDescriptor open(String path, int baudrate, int dataBits,
                                              int checkBit, int stopBit, int flag);

    public native void close();

}
