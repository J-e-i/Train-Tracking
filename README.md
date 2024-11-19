A Simple overview of my code
Interface: Trackable
   Purpose: To define common methods for trackable entities like trains.
   Methods:
     getId(): Returns the ID of the entity.
     getName(): Returns the name of the entity.
     getStatus(): Fetches the current status of the entity.
Base Class: TrainBase
  Purpose: Provides shared properties (id and name) for trackable entities.
  Key Methods:
    getId(): Getter for id.
    getName(): Getter for name.
Train Class
  Extends TrainBase and implements Trackable.
  Purpose: Represents a specific train.
  Key Methods:
     getStatus(): Calls the TrainTracker.getTrainStatus() method to fetch the train's live status.
TrainTracker Class
  Purpose: Handles all logic related to fetching and processing train status data.
  Key Functions:
    getTrainStatus(String trainNo):
    Connects to the API using the train number.
    Sends an HTTP GET request.
    Processes the response to extract meaningful data.
    parseJSON(String json):
    Parses the JSON response into a structured Map.
    Extracts specific keys (train_name, message, updated_time, and station details).
    extractValue(String json, String key):
    Retrieves the value of a specific key from the JSON string.
    extractArray(String json, String key):
    Extracts the array (e.g., station details) corresponding to a key.
    parseArray(String array):
    Converts the station data into a list of maps for easier processing.
Main Class (samp)
  Purpose: Entry point of the program.
  Key Functionality:
    Displays a welcome message.
    Prompts the user to enter a train number.
    Creates a Train object and fetches its status.
    Formats and displays the train details and station list in a user-friendly tabular format.






Sample Output:
Enter Train Number to Track: 22667
Welcome to Train Tracking System
Enter Train Number to Track: 22667

Tracking Train: 22667 Ncj Cbe Sf Exp Running Status
Crossed Erode Jn at 21:17
Last Updated: Updated few seconds ago

Station Details:
Station Name          Distance   Timing      Delay      Platform   Halt
---------------------------------------------------------------------------
Coimbatore Jn         -          19:32/19:30 2min       4          Source
Tiruppur              51 km      20:15/20:10 5min       2          2min
Erode Jn              101 km     21:17/20:55 22min      1          25min
...
Possible errors and how it handles it:
Error Handling
  Invalid Train Number:
  If the API returns success: false, the program displays an error message.
  Connectivity Issues:
  Catches exceptions during the HTTP request and prints the stack trace.
