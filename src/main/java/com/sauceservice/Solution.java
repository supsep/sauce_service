package com.sauceservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Solution {
  // Sauce Labs Server URL
  private static final String resourceUrl = "http://localhost:12345";

  // Max Historical data points for graph
  private static final int HISTORICAL_DATA_POINTS_MAX = 25;

  // Value for consecutive failures raising alarm
  private static final int CONSECUTIVE_FAILURES_UNHEALTHY = 3;
  private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

  private static RestTemplate restTemplate = new RestTemplate();
  /**
   * The main method is a REPL for monitoring Sauce Labs server
   * @param args
   */
  public static void main(String[] args) {
    Logger.getLogger("").setLevel(Level.OFF);


    final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

        solution(interval);

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


  public static void solution(int interval) throws RestClientException, InterruptedException {
    List<Boolean> history = new ArrayList<>();
    DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    String startTime = dateFormat.format(new Date());

    BigInteger totalFailures = BigInteger.ZERO;
    BigInteger totalSuccesful = BigInteger.ZERO;


    while (true) {

      // Make get request
      boolean successful = getRequest();

      // Set time for request
      String recentResponse = dateFormat.format(new Date());

      // Add request response to history
      history.add(successful);

      // Increment lifetime historical values
      if (successful) {
        totalSuccesful = totalSuccesful.add(BigInteger.ONE);
      } else {
        totalFailures = totalFailures.add(BigInteger.ONE);
      }

      // Maintain history without exceeding max elements set
      if (history.size() > HISTORICAL_DATA_POINTS_MAX) {
        history.remove(0);
      }

      printGraph(history);
      ensureServerIsHealthy(history.subList(Math.max(history.size() - CONSECUTIVE_FAILURES_UNHEALTHY, 0), history.size()));
      printInformation(startTime, recentResponse, totalSuccesful, totalFailures);
      Thread.sleep(interval * 1000L);
    }
  }

  public static boolean getRequest() {
    ResponseEntity<String> response = null;
    try {
      response = restTemplate.getForEntity(resourceUrl, String.class);
    } catch (RestClientException rce) {
      return false;
    }

    return response != null && HttpStatus.OK.equals(response.getStatusCode());
  }

  private static void printInformation(String startTime, String recentResponse, BigInteger totalSuccesful, BigInteger totalFailures) {
    System.out.println("Started monitoring server on: " + startTime);
    System.out.println("Most recent attempted probe: " + recentResponse);
    System.out.println("Total successful probes: " + totalSuccesful);
    System.out.println("Total failed probes: " + totalFailures);
  }

  private static void ensureServerIsHealthy(List<Boolean> history) {
    // Make sure we have history of at least CONSECUTIVE_FAILURES_UNHEALTHY length before checking for failures
    if (history.size() >= CONSECUTIVE_FAILURES_UNHEALTHY) {
      boolean healthyServer = false;
      for (boolean successful : history) {
        if (successful) healthyServer = true;
      }

      if (!healthyServer) {
        // If reached here, requests have failed for the past CONSECUTIVE_FAILURES_UNHEALTHY count
        System.err.println("WARNING: SAUCE LABS SERVER IS UNRESPONSIVE");
        System.err.println("WARNING: SAUCE LABS SERVER IS UNRESPONSIVE");
        System.err.println("WARNING: SAUCE LABS SERVER IS UNRESPONSIVE");
        System.err.println("WARNING: SAUCE LABS SERVER IS UNRESPONSIVE");
        System.err.println("WARNING: SAUCE LABS SERVER IS UNRESPONSIVE");
      }
    }
  }

  private static void printGraph(List<Boolean> input) {
      // Set max X and max Y
      int maxX = HISTORICAL_DATA_POINTS_MAX;
      int maxY = 2;

      // Create graph 2D matrix
      char[][] graph = new char[maxY + 1][maxX + 2];

      // Draw X and Y axis and origin point
      Arrays.fill(graph[0], '-');
      graph[0][0] = '+';
      for (int i = 1; i < graph.length; i++) {
        Arrays.fill(graph[i], ' ');
      }
      graph[1][0] = '|';
      graph[2][0] = '|';

      // Plot values
      int count = 1;
      for (boolean succesful : input) {
        graph[succesful ? 1 : 2][count] = succesful ? 'P' : 'F';
        count++;
      }

      // Print graph upside-down
    System.out.println("SAUCE LABS SERVER HISTORICAL GRAPH ");
    System.out.println("");
      for (int i = graph.length - 1; i >= 0; i--) {
        System.out.println(graph[i]);
      }
    }
}
