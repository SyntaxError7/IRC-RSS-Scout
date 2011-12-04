/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssscout;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import org.jibble.pircbot.PircBot;

public class MyBot extends PircBot {

    public static String botNick, botIdent, ipAddy, pass, joinChan, operBot, operLine, commandChar, lastReadLoc;
    public static int port, messageDelay, maxResults, timeBetweenSearches;
    public static boolean usingSSL;
    public static ArrayList<String> urlAndSearchTerms = new ArrayList();
    public static ArrayList<RssObject> RssObj = new ArrayList<RssObject>();
    static Properties config = new Properties();
    // url + search1 + search2 + search3...

    public MyBot() {


        try {
            config.load(new FileInputStream("scout.conf"));
        } catch (IOException ioex) {
            System.err.println("Error loading config file: jbot.conf");
            System.exit(0);
        }


        botNick = config.getProperty("botName");
        botIdent = config.getProperty("botIdent");
        ipAddy = config.getProperty("ircServer");
        joinChan = config.getProperty("chan");
        port = Integer.parseInt(config.getProperty("port"));
        pass = config.getProperty("pass");
        commandChar = config.getProperty("commandChar");
        usingSSL = Boolean.parseBoolean(config.getProperty("usingSSL"));
        messageDelay = Integer.parseInt(config.getProperty("messageDelay"));
        operLine = config.getProperty("operLine");
        lastReadLoc = config.getProperty("lastReadLoc");
        maxResults = Integer.parseInt(config.getProperty("maxResults"));
        timeBetweenSearches = Integer.parseInt(config.getProperty("timeBetweenSearches"));



        this.setName(botNick);
        this.setVerbose(true);
        this.joinChannel(joinChan);
        this.sendRawLine(operLine);
        this.setMessageDelay(messageDelay);

        fillRssLinkArray();
        createRssObjects();


        new CheckStatusTimer();




    }

    
    public static int getTimeBetweenSearches()
    {
        return timeBetweenSearches;
    }
    public static int getMaxResults()
    {
        return maxResults;
    }
    public static ArrayList<RssObject> getRssObject()
    {
        return RssObj;
    }
    public static String getOpLine() {
        return operLine;
    }

    public static String getPass() {
        return pass;
    }

    public static String getIrcServer() {
        return ipAddy;
    }

    public static int getIrcPort() {
        return port;

    }

    public static String getLastReadLoc() {

        return lastReadLoc;
    }

    public static boolean getUsingPassword() {
        if (pass.equalsIgnoreCase("non")) {
            return false;
        }
        return true;

    }

    public static boolean getUsingSSL() {
        return usingSSL;
    }

    public static String getChan() {
        return joinChan;
    }

    public static ArrayList getRssUrlAndSearchList() {
        return urlAndSearchTerms;
    }

    private void fillRssLinkArray() {



        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream("rssLinks.txt");

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                urlAndSearchTerms.add(strLine);
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }



    }

    private void createRssObjects() {
// using traditional for loop
        for (int i = 0; i < urlAndSearchTerms.size(); i++) {
            System.out.println(urlAndSearchTerms.get(i));
            RssObject newRssObj = new RssObject(urlAndSearchTerms.get(i));
            RssObj.add(newRssObj);
        }

    }
}
