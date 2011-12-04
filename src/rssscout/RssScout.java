/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssscout;

import java.io.IOException;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.TrustingSSLSocketFactory;

/**
 *
 * @author JC Denton
 */
public class RssScout extends PircBot {

    /**
     * @param args the command line arguments
     */
    public static MyBot bot = new MyBot();

    public static void main(String[] args) throws IrcException, IOException {

        // check for SSL and Pass
        String ircServer, pass, chan, operLine;
        int ircPort;
        boolean ssl = false;
        boolean password = false;

     
        ircServer = MyBot.getIrcServer();
        pass = MyBot.getPass();
        ircPort = MyBot.getIrcPort();
        ssl = MyBot.getUsingSSL();
        password = MyBot.getUsingPassword();
        chan = MyBot.getChan();
        operLine = MyBot.getOpLine();
        
        
        
        

        if (ssl && password) {
            bot.connect(ircServer, ircPort, pass, new TrustingSSLSocketFactory());
        } else if (ssl) {
            bot.connect(ircServer, ircPort, new TrustingSSLSocketFactory());
        } else {
            bot.connect(ircServer);
        }
        
        if (!operLine.equalsIgnoreCase("none"))
        bot.sendRawLine(operLine);
        
        bot.joinChannel(chan);

    }
}
