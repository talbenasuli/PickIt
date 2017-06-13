package pickit.com.pickit.Networking.Requests;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by Tal on 25/05/2017.
 */

public class PISocketIORequest implements Emitter.Listener {

   public interface PISocketIORequestListener {
        public void shouldUpdateList();
    }

    private PISocketIORequestListener listener;

    public void sendSocketIOConnectRequest() {

        try {
            Socket mSocket = IO.socket("http://192.168.43.36:1994/");
            mSocket.on("true", this);
            mSocket.connect();
        }

        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void call(Object... args) {
        listener.shouldUpdateList();
    }

    public void setListener(PISocketIORequestListener listener) {
        this.listener = listener;
    }
}
