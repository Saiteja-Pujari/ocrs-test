package classes;

public enum SubscriptionChangeType {
  All, //These are the total data
  Insert, //Insert given data at given position
  Update, //Update given data
  Delete, //Delete data from given position
  Refresh; //Re-Request for new data
}
