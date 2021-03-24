package lesson_125344.WebChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class IO {
    private DataInputStream inStream;
    private DataOutputStream outStream;


    protected DataInputStream getInStream() {
        return inStream;
    }

    protected void setInStream(DataInputStream inStream) {
        this.inStream = inStream;
    }

    protected DataOutputStream getOutStream() {
        return outStream;
    }

    protected void setOutStream(DataOutputStream outStream) {
        this.outStream = outStream;
    }

    protected void waitMessage() throws IOException{
        while (true){
            processIncomingMessage(inStream.readUTF().trim());
        }
    }

    protected abstract void processIncomingMessage(String string) throws IOException;

    public void sendMessage(String message) throws IOException{
        try {
            outStream.writeUTF(message);
        }
        catch (IOException e){
            System.out.println("Client unavailable");
            e.printStackTrace();
            throw new IOException(e.getMessage(), e);
        }
    }
}
