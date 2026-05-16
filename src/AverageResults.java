



public class AverageResults {

    private double averageWT;
    private double averageTAT;
    private double averageRT;

    public AverageResults(
            double averageWT,
            double averageTAT,
            double averageRT
    ) {
        this.averageWT = averageWT;
        this.averageTAT = averageTAT;
        this.averageRT = averageRT;
    }

    public double getAverageWT() {
        return averageWT;
    }

    public double getAverageTAT() {
        return averageTAT;
    }

    public double getAverageRT() {
        return averageRT;
    }
}