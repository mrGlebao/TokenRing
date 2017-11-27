package timestamp_writer;

import conf.Settings;
import entities.dto.Message;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * Class, which generates a dataset from timestamp list, collected by topology overseer.
 *
 * @See TopologyOverseer
 */
public class TimestampWriter {

    private static String generateRow(Message.Timestamps stamps) {
        return stamps.getGenerated() + " "
                + stamps.getSent() + " "
                + stamps.getReceived() + " "
                + stamps.getReturned() + " ";
    }

    public static void write(int topSize, int tokens, List<Message.Timestamps> stampsList) {
        System.out.println("Start writing!");
        try (PrintWriter writer = new PrintWriter(generateFileName(topSize, tokens), "UTF-8")) {
            writer.println("created sent received returned");
            for (Message.Timestamps stamps : stampsList) {
                writer.println(generateRow(stamps));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Success!");
    }

    private static String formatDate(Date date) {
        return "" + date.getMonth() + date.getDay() + date.getHours() + date.getMinutes() + date.getSeconds();
    }

    public static String generateFileName(int topSize, int tokens) {
        return new StringBuilder()
                .append("D:\\Git\\TokenRing\\research\\data\\")
                .append(formatDate(new Date()))
                .append("top")
                .append(topSize)
                .append("tok")
                .append(tokens)
                .append("mess")
                .append(Settings.MESSAGES_TO_RECEIVE)
                .append("rush")
                .append(Settings.RUSH_MODE)
                .append("sil")
                .append(Settings.SILENT_MODE)
                .append("s_m")
                .append(Settings.OPERATOR_SLEEP_DEFAULT_MILLIS)
                .append("s_n")
                .append(Settings.OPERATOR_SLEEP_DEFAULT_NANOS)
                .append("er")
                .append(Settings.EARLY_RELEASE)
                .append("EOS")
                .append(".txt")
                .toString();
    }
}

