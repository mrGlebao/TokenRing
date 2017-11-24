package timestamp_writer;

import conf.Settings;
import entities.dto.Message;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class TimestampWriter {

    private String generateRow(Message.Timestamps stamps) {
        return stamps.getGenerated()+" "
                +stamps.getSent()+ " "
                +stamps.getReceived()+ " "
                +stamps.getReturned()+ " ";
    }

    public void write(List<Message.Timestamps> stampsList) {
        System.out.println("Start writing!");
        try (PrintWriter writer = new PrintWriter(generateFileName(), "UTF-8")) {
            writer.println("created sent received returned");
            for(Message.Timestamps stamps : stampsList) {
                writer.println(generateRow(stamps));
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Success!");
    }

    private String formatDate(Date date) {
        return ""+date.getDay()+date.getHours()+date.getMinutes();
    }

    public String generateFileName() {
        return new StringBuilder()
                .append(formatDate(new Date()))
                .append("top")
                .append(Settings.TOPOLOGY_SIZE)
                .append("messnum")
                .append(Settings.MESSAGES_TO_RECEIVE)
                .append("rush")
                .append(Settings.RUSH_MODE)
                .append("sleepnanos")
                .append(Settings.OPERATOR_SLEEP_DEFAULT_MILLIS*1_000_000+Settings.OPERATOR_SLEEP_DEFAULT_NANOS)
                .append("er")
                .append(Settings.EARLY_RELEASE)
                .append("tok")
                .append(Settings.TOKENS_SENT)
                .append(".txt")
                .toString();
    }
}

