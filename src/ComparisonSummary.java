




public class ComparisonSummary {

    public void displayComparison(
            double rrAvgWT,
            double rrAvgTAT,
            double rrAvgRT,
            double srtfAvgWT,
            double srtfAvgTAT,
            double srtfAvgRT,
            int quantum
    ) {

        System.out.println("\n========================================");
        System.out.println("           Comparison Summary");
        System.out.println("========================================\n");

        // جدول المقارنة الرقمي
        System.out.printf(
                "%-25s %-12s %-12s %-12s%n",
                "Metric",
                "Round Robin",
                "SRTF",
                "Better"
        );

        printMetricComparison(
                "Average Waiting Time",
                rrAvgWT,
                srtfAvgWT
        );

        printMetricComparison(
                "Average Turnaround Time",
                rrAvgTAT,
                srtfAvgTAT
        );

        printMetricComparison(
                "Average Response Time",
                rrAvgRT,
                srtfAvgRT
        );

        // التحليل النصي المطلوب في المشروع
        System.out.println("\n========================================");
        System.out.println("          Analytical Discussion");
        System.out.println("========================================\n");

        // 1. Fairness versus efficiency
        System.out.println("1. Fairness versus Efficiency:");
        System.out.println(
                "- Round Robin is generally fair because all processes "
                + "receive CPU time in cyclic order."
        );
        System.out.println(
                "- SRTF is usually more efficient because it minimizes "
                + "waiting and turnaround time."
        );
        System.out.println();

        // 2. Time slicing vs shortest-job preference
        System.out.println("2. Time Slicing vs Shortest-Job Preference:");
        System.out.println(
                "- Round Robin uses time slicing with quantum = "
                + quantum + "."
        );
        System.out.println(
                "- SRTF always selects the process with the shortest "
                + "remaining time."
        );
        System.out.println();

        // 3. Effect on first response time
        System.out.println("3. Effect on First Response Time:");
        if (rrAvgRT < srtfAvgRT) {
            System.out.println(
                    "- Round Robin provided better average response time."
            );
        } else if (srtfAvgRT < rrAvgRT) {
            System.out.println(
                    "- SRTF provided better average response time."
            );
        } else {
            System.out.println(
                    "- Both algorithms produced the same average response time."
            );
        }
        System.out.println();

        // 4. Effect of quantum size
        System.out.println("4. Effect of Quantum Size on Round Robin:");
        if (quantum <= 2) {
            System.out.println(
                    "- Small quantum improves responsiveness but "
                    + "increases context switching."
            );
        } else if (quantum <= 5) {
            System.out.println(
                    "- Medium quantum balances responsiveness and efficiency."
            );
        } else {
            System.out.println(
                    "- Large quantum makes Round Robin behave similarly to FCFS."
            );
        }
        System.out.println();

        // 5. Advantage to short jobs
        System.out.println("5. Advantage to Short Jobs:");
        if (srtfAvgWT < rrAvgWT) {
            System.out.println(
                    "- SRTF clearly favors short processes and usually "
                    + "gives them significantly lower waiting time."
            );
        } else {
            System.out.println(
                    "- In this workload, SRTF did not show a strong "
                    + "advantage over Round Robin."
            );
        }
        System.out.println();

        // Final Conclusion
        System.out.println("========================================");
        System.out.println("            Final Conclusion");
        System.out.println("========================================\n");

        System.out.println(generateConclusion(
                rrAvgWT,
                rrAvgTAT,
                rrAvgRT,
                srtfAvgWT,
                srtfAvgTAT,
                srtfAvgRT
        ));
    }

    private void printMetricComparison(
            String metricName,
            double rrValue,
            double srtfValue
    ) {

        String better;

        if (rrValue < srtfValue) {
            better = "RR";
        } else if (srtfValue < rrValue) {
            better = "SRTF";
        } else {
            better = "Equal";
        }

        System.out.printf(
                "%-25s %-12.2f %-12.2f %-12s%n",
                metricName,
                rrValue,
                srtfValue,
                better
        );
    }

    private String generateConclusion(
            double rrAvgWT,
            double rrAvgTAT,
            double rrAvgRT,
            double srtfAvgWT,
            double srtfAvgTAT,
            double srtfAvgRT
    ) {

        StringBuilder conclusion = new StringBuilder();

        conclusion.append(
                "SRTF generally achieves lower waiting and turnaround "
                + "times because it always prioritizes the shortest "
                + "remaining job. "
        );

        conclusion.append(
                "Round Robin provides better fairness by ensuring that "
                + "every process receives CPU time regularly. "
        );

        if (rrAvgRT < srtfAvgRT) {
            conclusion.append(
                    "In this workload, Round Robin provided better "
                    + "average response time. "
            );
        } else if (srtfAvgRT < rrAvgRT) {
            conclusion.append(
                    "In this workload, SRTF provided better "
                    + "average response time. "
            );
        } else {
            conclusion.append(
                    "Both algorithms produced the same "
                    + "average response time. "
            );
        }

        conclusion.append(
                "Therefore, SRTF is typically more efficient, while "
                + "Round Robin is generally fairer and more suitable "
                + "for time-sharing systems."
        );

        return conclusion.toString();
    }
}