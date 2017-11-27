package conf;

public class Settings {

    /**
     * Allows to conduct series of measurements.
     * The program tooks measurements from Settings.TOPOLOGY_SIZE_MIN to Settings.TOPOLOGY_SIZE_MAX nodes
     * and from 1 to Settings.TOPOLOGY_SIZE_MAX tokens sent.
     * (Uses parameters:
     * Settings.TOPOLOGY_SIZE_MIN
     * Settings.TOPOLOGY_SIZE_MAX
     * Settings.TOPOLOGY_SIZE_STEP
     * Settings.TOKENS_SENT_STEP
     *
     */
    public static final boolean SERIES_MODE = true;

    /**
     * Step to change tokens number.
     * Only used if Settings.SERIES_MODE == true
     */
    public static final int TOKENS_SENT_STEP = 1;

    /**
     * Minimal topology size of measurement series.
     * Leads to exception if value is less than 2.
     * Only used if Settings.SERIES_MODE == true
     */
    public static final int TOPOLOGY_SIZE_MIN = 2;

    /**
     * Maximal topology size of measurement series.
     * Only used if Settings.SERIES_MODE == true
     */
    public static final int TOPOLOGY_SIZE_MAX = 2;

    /**
     * Step to change topology size.
     * Only used if Settings.SERIES_MODE == true
     */
    public static final int TOPOLOGY_SIZE_STEP = 1;

    /**
     * Actual size of topology.
     * Only used if Settings.SERIES_MODE == false
     */
    public static final int TOPOLOGY_SIZE = 8;

    /**
     * Actual number of tokens to sent.
     * Only used if Settings.SERIES_MODE == false
     */
    public static final int TOKENS_SENT = 4;

    /**
     * True if want to turn in early token release strategy,
     * false for default strategy
     */
    public static final boolean EARLY_RELEASE = true;

    /**
     * when nodes receive Settings.MESSAGES_TO_RECEIVE messages in total, the iteration of measurment stops.
     * @See TopologyOverseer
     */
    public static final int MESSAGES_TO_RECEIVE = 1000;

    /**
     * True if want to generate messages non-stop by node operators (high network loading)
     * False otherwise (low network loading)
     */
    public static final boolean RUSH_MODE = true;

    /**
     * Tune this parameter to make node operators generate messages more/less often
     * Node operators sleep after attempt of generating message.
     * Only used if Settings.RUSH_MODE == false
     */
    public static final int OPERATOR_SLEEP_DEFAULT_MILLIS = 10;

    /**
     * Tune this parameter to make node operators generate messages more/less often
     * Node operators sleep after attempt of generating message.
     * Only used if Settings.RUSH_MODE == false
     */
    public static final int OPERATOR_SLEEP_DEFAULT_NANOS = 10;

    /**
     * False if want nodes to log their events
     * False otherwise
     * @See Utils#log()
     */
    public static final boolean SILENT_MODE = true;


}