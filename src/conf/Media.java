package conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.Serializable;

public class Media  implements Serializable {
	
    private static Map players = new HashMap();

    static {
        Properties props = new Properties();
        props.setProperty("source", "resources/media/DIGIPLAN.swf");
        props.setProperty("style", "width:927px; height:436px;");
        props.setProperty("autoStartParamName", "play");
        props.setProperty("autoStartParamValue", "true");
        props.setProperty("controlsParamName", "menu");
        props.setProperty("controlsParamValue", "true");
        players.put("flash", props);

       
    }

    private String selectedPlayer = "flash";
    Properties playerProps = (Properties) players.get(selectedPlayer);

    public Media() {
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(String selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
        playerProps = (Properties) players.get(selectedPlayer);
    }

    public String getSource() {
        if (playerProps != null) return playerProps.getProperty("source");
        // ICE-4749
        selectedPlayer = "flash";
        return "resources/media/DIGIPLAN.swf";
    }

    public String getStyle() {
        return playerProps.getProperty("style");
    }

    public String getAutoStartParamName() {
        return playerProps.getProperty("autoStartParamName");
    }

    public String getAutoStartParamValue() {
        return playerProps.getProperty("autoStartParamValue");
    }

    public String getControlsParamName() {
        return playerProps.getProperty("controlsParamName");
    }

    public String getControlsParamValue() {
        return playerProps.getProperty("controlsParamValue");
    }
}
