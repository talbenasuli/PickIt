package pickit.com.pickit.Networking.Requests;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;

import pickit.com.pickit.Data.PIListRowData;
import pickit.com.pickit.Data.PISocketIOData;
import pickit.com.pickit.Models.PIModel;
import pickit.com.pickit.Networking.Requests.Parsers.PISocketIOSongIDParser;

/**
 * Created by Tal on 25/05/2017.
 */

public class PISocketIORequest implements Emitter.Listener {

    private PIModel.PISocketIORequestListener listener;

    public void sendSocketIOConnectRequest() {

        try {
            Socket mSocket = IO.socket("http://10.0.0.91:1994/");
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
        PISocketIOData socketIOData = parser.parse((JSONObject) args[0]);
        if (socketIOData.getAction().equals("0")) {
            String songId = socketIOData.getSongId();
            PIListRowData songData = socketIOData.getSongData();
            listener.onSongEnds(songId,songData);
        }

        else if(socketIOData.getAction().equals("1")) {
            String songId = socketIOData.getSongId();
            listener.onPickIt(songId);
        }
    }

    public void setListener(PIModel.PISocketIORequestListener listener) {
        this.listener = listener;
    }
}
