
import java.util.ArrayList;

public class SimulationRunner {

    private double srtfAvgWT;
    private double srtfAvgTAT;
    private double srtfAvgRT;

    private double rrAvgWT;
    private double rrAvgTAT;
    private double rrAvgRT;

    // تشغيل SRTF
    public void runSRTF(
            ArrayList<Process> originalProcesses
    ) {

        System.out.println(
                "\n================================="
        );
        System.out.println(
                "       SRTF Scheduling"
        );
        System.out.println(
                "=================================\n"
        );

        ArrayList<Process> copiedProcesses
                = cloneProcesses(originalProcesses);

        SRTFScheduler scheduler
                = new SRTFScheduler(copiedProcesses);

        scheduler.run();
        scheduler.getGanttChart().display();
        AverageResults results
                = printResults(copiedProcesses);

        srtfAvgWT = results.getAverageWT();
        srtfAvgTAT = results.getAverageTAT();
        srtfAvgRT = results.getAverageRT();
    }

    // تشغيل Round Robin
    public void runRoundRobin(
            ArrayList<Process> originalProcesses,
            int quantum
    ) {

        System.out.println(
                "\n================================="
        );
        System.out.println(
                "     Round Robin Scheduling"
        );
        System.out.println(
                "================================="
        );
        System.out.println(
                "Time Quantum = " + quantum + "\n"
        );

        ArrayList<Process> copiedProcesses
                = cloneProcesses(originalProcesses);

        RoundRobinScheduler scheduler
                = new RoundRobinScheduler(
                        copiedProcesses,
                        quantum
                );

        scheduler.run();
        scheduler.getGanttChart().display();

        AverageResults results
                = printResults(copiedProcesses);

        rrAvgWT = results.getAverageWT();
        rrAvgTAT = results.getAverageTAT();
        rrAvgRT = results.getAverageRT();
    }

    // نسخ العمليات حتى لا تتأثر خوارزمية بأخرى
    private ArrayList<Process> cloneProcesses(
            ArrayList<Process> original
    ) {

        ArrayList<Process> copy =
                new ArrayList<>();

        for (Process p : original) {

            copy.add(
                    new Process(
                            p.getName(),
                            p.getArrivalTime(),
                            p.getBurstTime()
                    )
            );
        }

        return copy;
    }

    // عرض النتائج والمتوسطات
    private AverageResults printResults(
            ArrayList<Process> processes
    ) 
         {

        System.out.println("\nFinal Statistics:\n");

        System.out.printf(
                "%-8s %-5s %-5s %-5s %-5s %-5s %-5s%n",
                "Process",
                "AT",
                "BT",
                "CT",
                "TAT",
                "WT",
                "RT"
        );

        double totalWT = 0;
        double totalTAT = 0;
        double totalRT = 0;

        for (Process p : processes) {

            System.out.printf(
                    "%-8s %-5d %-5d %-5d %-5d %-5d %-5d%n",
                    p.getName(),
                    p.getArrivalTime(),
                    p.getBurstTime(),
                    p.getCompletionTime(),
                    p.getTurnaroundTime(),
                    p.getWaitingTime(),
                    p.getResponseTime()
            );

            totalWT += p.getWaitingTime();
            totalTAT += p.getTurnaroundTime();
            totalRT += p.getResponseTime();
        }

        int n = processes.size();

        System.out.printf(
                "\nAverage Waiting Time      = %.2f%n",
                totalWT / n
        );

        System.out.printf(
                "Average Turnaround Time   = %.2f%n",
                totalTAT / n
        );

        System.out.printf(
                "Average Response Time     = %.2f%n",
                totalRT / n
        );
        return new AverageResults(
                totalWT / n,
                totalTAT / n,
                totalRT / n
        );
    }

    public void displayComparison(int quantum) {

        ComparisonSummary comparison
                = new ComparisonSummary();

        comparison.displayComparison(
                rrAvgWT,
                rrAvgTAT,
                rrAvgRT,
                srtfAvgWT,
                srtfAvgTAT,
                srtfAvgRT,
                quantum
        );
    }
}