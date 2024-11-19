
# Train Tracking System

A Java-based Train Tracking System that fetches real-time train details and displays them in a user-friendly format.

---

## Features
- Accepts train numbers as input.
- Fetches train status and station details from an API.
- Displays the details in a tabular format.
- Handles errors gracefully (e.g., invalid train numbers, connectivity issues).

---

## Code Structure

### Interface: Trackable
- **Purpose**: Defines common methods for trackable entities like trains.
- **Methods**:
  - `getId()`: Returns the ID of the entity.
  - `getName()`: Returns the name of the entity.
  - `getStatus()`: Fetches the current status of the entity.

---

### Base Class: TrainBase
- **Purpose**: Provides shared properties (`id` and `name`) for trackable entities.
- **Key Methods**:
  - `getId()`: Getter for `id`.
  - `getName()`: Getter for `name`.

---

### Train Class
- **Extends**: `TrainBase`
- **Implements**: `Trackable`
- **Purpose**: Represents a specific train.
- **Key Methods**:
  - `getStatus()`: Calls the `TrainTracker.getTrainStatus()` method to fetch the train's live status.

---

### TrainTracker Class
- **Purpose**: Handles all logic related to fetching and processing train status data.
- **Key Functions**:
  1. **`getTrainStatus(String trainNo)`**:
     - Connects to the API using the train number.
     - Sends an HTTP GET request.
     - Processes the response to extract meaningful data.
  2. **`parseJSON(String json)`**:
     - Parses the JSON response into a structured `Map`.
     - Extracts specific keys like `train_name`, `message`, `updated_time`, and station details.
  3. **`extractValue(String json, String key)`**:
     - Retrieves the value of a specific key from the JSON string.
  4. **`extractArray(String json, String key)`**:
     - Extracts the array (e.g., station details) corresponding to a key.
  5. **`parseArray(String array)`**:
     - Converts the station data into a list of maps for easier processing.

---

### Main Class (samp)
- **Purpose**: Entry point of the program.
- **Key Functionality**:
  1. Displays a welcome message.
  2. Prompts the user to enter a train number.
  3. Creates a `Train` object and fetches its status.
  4. Formats and displays the train details and station list in a user-friendly tabular format.

---

## Sample Output

**Input**:
```
Enter Train Number to Track: 22667
```

**Output**:
```
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
```

---

## Error Handling

1. **Invalid Train Number**:
   - If the API returns `success: false`, the program displays an error message.

2. **Connectivity Issues**:
   - Catches exceptions during the HTTP request and prints the stack trace.

---

## Setup and Usage

### Prerequisites
- Java Development Kit (JDK) installed on your system.
- Internet connection for API access.

### Steps to Run
1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd <repository-folder>
   ```
2. Compile the program:
   ```bash
   javac samp.java
   ```
3. Run the program:
   ```bash
   java samp
   ```

---

## Possible Enhancements
- **Input Validation**:
  - Add validation for train numbers.
- **Error Logging**:
  - Log errors to a file instead of printing stack traces.
- **UI**:
  - Develop a graphical user interface for better usability.

---

## License
This project is open-source and available under the [MIT License](LICENSE).
