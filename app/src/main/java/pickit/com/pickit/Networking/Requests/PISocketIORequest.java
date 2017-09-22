package pickit.com.pickit.Networking.Requests;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PISocketIOSongIDParser;

/**
 * Created by Tal on 25/05/2017.
 */

public class PISocketIORequest implements Emitter.Listener {

    private PIModel.PISocketIORequestListener listener;

    public void sendSocketIOConnectRequest() {

        try {
            Socket mSocket = IO.socket("http://10.0.0.16:1994/");
            mSocket.on("true", this);
            mSocket.connect();
        }

        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void call(Object... args) {
        PISocketIOSongIDParser parser = new PISocketIOSongIDParser();
        String songId = parser.parse(args[0].toString());
        listener.updateList(songId.toString());
    }

    public void setListener(PIModel.PISocketIORequestListener listener) {
        this.listener = listener;
    }
}
