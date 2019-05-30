package com.sauceservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Solution {

  /**
   * The main method is a REPL for monitoring Sauce Labs server
   * @param args
   */
  public static void main(String[] args) {
    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    final ServerProbeService serverProbeService = new ServerProbeServiceImpl();

    while (true) {
      System.out.println("Enter interval in seconds to monitor Sauce Labs server (every X seconds, between 1 <-> 60): ");
      final String intervalInput;

      try {
        // Read and validate input
        intervalInput = br.readLine();
        int interval = Integer.valueOf(intervalInput);

        if (interval < 0 || interval > 60) {
          throw new NumberFormatException();
        }

        serverProbeService.monitor(interval);

        System.out.println("Monitoring finished");
      } catch (IOException e) {
        System.err.println("ERROR: Input/Output error. Exiting");
        System.exit(1);
      } catch (NumberFormatException nfe) {
        System.err.println("ERROR: Value must be between 1 and 60.");
      } catch (Exception e) {
        System.err.println("ERROR: " + e);
      }
    }
  }

}
