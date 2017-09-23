package pickit.com.pickit.Data;

/**
 * Created by Omer on 22/09/2017.
 */

public class PISocketIOData {
    String action;
    String songId;
    PIListRowData songData;

    public PISocketIOData(String action, String songId, PIListRowData songData) {
        this.action = action;
        this.songId = songId;
        this.songData = songData;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public void setSongData(PIListRowData songData) {
        this.songData = songData;
    }

    public String getAction() {
        return action;
    }

    public String getSongId() {
        return songId;
    }

    public PIListRowData getSongData() {
        return songData;
    }
}
