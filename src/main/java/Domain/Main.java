package Domain;/*
 * Copyright (C) 2015 Neo Visionaries Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
import java.io.*;
import java.time.LocalDateTime;

import com.neovisionaries.ws.client.*;

/**
 * A sample WebSocket client application using nv-websocket-client
 * library.
 *
 * <p>
 * This application connects to the echo server on websocket.org
 * ({@code ws://echo.websocket.org}) and repeats to (1) read a
 * line from the standard input, (2) send the read line to the
 * server and (3) print the response from the server, until
 * {@code exit} is entered.
 * </p>
 *
 * @see <a href="https://github.com/TakahikoKawasaki/nv-websocket-client"
 *      >nv-websocket-client</a>
 *
 * @author Takahiko Kawasaki
 */

public class Main
{

    public static void main(String[] args) throws Exception
    {
        Config config = new Config();
        config.load();
        Log log = new Log();
        log.load();

        boolean connectAvaliable = true;

        WebSocket ws = null;

        // Connect to the echo server
        while (ws == null)
        {
            try {
                ws = connect(config.getAdress(), config.getTimeout());
            }
            catch (WebSocketException ex) {
                log.exceptionLog(ex.getMessage());
                System.out.println(ex.getMessage() + LocalDateTime.now());
                Thread.sleep(config.getTimeout());
            }
        }

        log.start();

        // The standard input via BufferedReader.
        BufferedReader in = getInput();

        // A text read from the standard input.
        String text = "{\"deviceId\":" + config.getId() + ",\"value\":1}";

        while (0 == 0)
        {
            Thread.sleep(config.getDelay());

            if (!ws.isOpen()){

                if(connectAvaliable) {
                    log.disconnect();
                    connectAvaliable = false;
                }

                try {
                    ws = connect(config.getAdress(), config.getTimeout());
                }
                catch (WebSocketException ex) {
                    log.exceptionLog(ex.getMessage());
                    System.out.println(ex.getMessage() + LocalDateTime.now());
                    Thread.sleep(config.getTimeout());
                }
            }
            else{
                if(!connectAvaliable) {
                    // Log connection restore and send message
                    log.connectionRestored();
                    connectAvaliable = true;
                    ws.sendText(text);
                }
                else
                    // Send the text to the server.
                    ws.sendText(text);
            }


        }
    }

    /**
     * Connect to the server.
     */
    private static WebSocket connect(String adress, Integer timeout) throws Exception
    {
        final String SERVER = adress;
        final int TIMEOUT = timeout;

        return new WebSocketFactory()
                .setConnectionTimeout(TIMEOUT)
                .createSocket(SERVER)
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) {
                        System.out.println(message);
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }

    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException
    {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}