
# Sauce Labs Service  
Monitor the health of the Sauce Labs web service  
  
      
## Getting Started      
 ### Prerequisites      
 * Java 8  
 * Gradle 5  
      
### Running      
 To run the program:      
      
``` ./gradlew run ```  
  
It will then ask how often you would like to probe the server.  
``` Enter interval in seconds to monitor Sauce Labs server (every X seconds, between 1 <-> 60): ```  
  
### Report
The monitor provides a graph, where P represents a Pass (HTTP status 200) and F represents a fail (HTTP status 500).

```
SAUCE LABS SERVER HISTORICAL GRAPH  
  
|F FFF FF   F F F FFFF  F  
| P   P  PPP P P P    PP P  
+--------------------------  
SAUCE LABS SERVER STATISTICS  
  
Started monitoring server on: 2019/05/29 17:44:25  
Most recent attempted probe: 2019/05/29 17:44:52  
Total successful probes: 13  
Total failed probes: 14
```  
  
### Unresponsive Server Alert:  
If the server returns 500 **three** times in a row, then the server is considered unresponsive. The monitor will report very loudly:  
```
WARNING: SAUCE LABS SERVER IS UNRESPONSIVE
WARNING: SAUCE LABS SERVER IS UNRESPONSIVE
WARNING: SAUCE LABS SERVER IS UNRESPONSIVE
WARNING: SAUCE LABS SERVER IS UNRESPONSIVE
  ```
  
  
 ## Complexity      
#### Space complexity    
* There is a doubly nested while loop: one for REPL and one for server probes. Time complexity is **O(1)**.
     
#### Space complexity    
* Since historical data is hard coded to previous 25 requests. Space complexity is **O(1)**.
    
 #### Execution time    
  * Until program is terminated
   
  
    
      
## Input criteria      
-  Interval to monitor must be a integer between 0 and 60 (inclusive).
      
## Running the tests      
      
 To run the unit tests:      
``` ./gradlew test ```      
      
 ## Built With      
* Gradle      
* Java 8      
* Junit 4      
* IntellJ IDEA CE      
      
## Resources      
 *   https://www.baeldung.com/rest-template
      
## TODO  
* Unit Tests  
* Component Tests  
* Implement user input for unresponsive server alert. Currently hard coded to 3 (CONSECUTIVE_FAILURES_UNHEALTHY).  
* Allow users to input amount of data points for the graph (HISTORICAL_DATA_POINTS_MAX)  
* Disable debug logging for RestTemplate  
      
      
## License      
 This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
