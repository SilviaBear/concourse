/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2015 Jeff Nelson, Cinchapi Software Collective
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.cinchapi.concourse.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import com.google.common.base.Throwables;

/**
 * Utilities for dealing with networking.
 * 
 * @author jnelson
 */
public final class Networking {

    /**
     * Return the <em>companion</em> port for the specified one. The companion
     * port is one between {@value #MIN_PORT} and {@value #MAX_PORT} that is
     * guaranteed to be different from {@code port}.
     * 
     * @param port
     * @return the companion port
     */
    public static int getCompanionPort(int port) {
        return port < PORT_RANGE ? MIN_PORT + port : (port % PORT_RANGE)
                + MIN_PORT;
    }

    /**
     * Return the host address for the local machine.
     * 
     * @return the host address
     */
    public static String getHostAddress() {
        if(HOST_ADDRESS == null) {
            try {
                Enumeration<NetworkInterface> nets = NetworkInterface
                        .getNetworkInterfaces();
                while (nets.hasMoreElements()) {
                    NetworkInterface net = nets.nextElement();
                    if(!net.isLoopback()) {
                        Enumeration<InetAddress> ips = net.getInetAddresses();
                        while (ips.hasMoreElements()) {
                            String ip = ips.nextElement().getHostAddress();
                            if(ip.matches(IP_REGEX)) {
                                HOST_ADDRESS = ip;
                            }
                        }
                    }
                }
            }
            catch (Exception e) {
                throw Throwables.propagate(e);
            }
        }
        return HOST_ADDRESS;
    }

    /**
     * Get an open port on the local machine in the port range between
     * {@value #MIN_PORT} and {@value #MAX_PORT}.
     * 
     * @return the port
     */
    public static int getOpenPort() {
        int port = RAND.nextInt(MIN_PORT) + (PORT_RANGE);
        return isOpenPort(port) ? port : getOpenPort();
    }

    /**
     * Return {@code true} if the {@code port} is available on the local
     * machine.
     * 
     * @param port
     * @return {@code true} if the port is available
     */
    private static boolean isOpenPort(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        }
        catch (SocketException e) {
            return false;
        }
        catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    /**
     * A cache of the host address returned in the {@link #getHostAddress()}.
     */
    private static String HOST_ADDRESS = null;

    /**
     * Regex to match an IP Address.
     */
    private static final String IP_REGEX = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

    /**
     * The max port number that can be assigned from the
     * {@link #getCompanionPort(int)} method.
     */
    private static final int MAX_PORT = 65535;

    /**
     * The min port number that can be assigned from the
     * {@link #getCompanionPort(int)} method.
     */
    private static final int MIN_PORT = 49152;

    /**
     * The number of available ports that can be considered "companion" ports.
     */
    private static final int PORT_RANGE = MAX_PORT - MIN_PORT;

    /**
     * Class wide random number generator
     */
    private static final Random RAND = new Random();

    private Networking() {/* noop */}

}