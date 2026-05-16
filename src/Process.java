public class Process {

    private String name;
    private int arrivalTime;
    private int burstTime;

    private int remainingTime;
    private boolean completed;
    private int completionTime;
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime = -1;   
    private int firstStartTime = -1;

    public Process(
            String name,
            int arrivalTime,
            int burstTime
    ) {

        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;

        this.remainingTime = burstTime;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void executeOneUnit() {
        remainingTime--;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public int getCompletionTime() {
    return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getFirstStartTime() {
        return firstStartTime;
    }
    
    public void setFirstStartTime(int firstStartTime) {
        this.firstStartTime = firstStartTime;
    }

    
    public void display() {

        System.out.println(
                name +
                " | AT: " + arrivalTime +
                " | BT: " + burstTime +
                " | RT: " + remainingTime
        );
    }
    
    
    public void displayStatistics() {
    System.out.printf(
        "%-5s AT=%d BT=%d CT=%d TAT=%d WT=%d RT=%d%n",
        name,
        arrivalTime,
        burstTime,
        completionTime,
        turnaroundTime,
        waitingTime,
        responseTime
    );
}
}