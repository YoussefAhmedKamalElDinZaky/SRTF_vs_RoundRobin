



import java.util.ArrayList;

public class RoundRobinScheduler {

    private ArrayList<Process> processes;
    private GanttChart ganttChart;
    private int quantum;

    public RoundRobinScheduler(
            ArrayList<Process> processes,
            int quantum
    ) {
        this.processes = processes;
        this.ganttChart = new GanttChart();

        this.quantum = quantum;
    }
   

    public void run() {

        RoundRobinReadyQueue readyQueue =
                new RoundRobinReadyQueue();

        int currentTime = 0;
        int completedCount = 0;

        // لمنع إضافة نفس العملية أكثر من مرة عند الوصول
        boolean[] addedToQueue =
                new boolean[processes.size()];

        while (completedCount < processes.size()) {

            // إضافة كل العمليات التي وصلت حتى الزمن الحالي
            for (int i = 0; i < processes.size(); i++) {

                Process p = processes.get(i);

                if (!addedToQueue[i]
                        && p.getArrivalTime() <= currentTime) {

                    readyQueue.enqueue(p);
                    addedToQueue[i] = true;
                }
            }

            // إذا لا توجد عمليات جاهزة
            if (readyQueue.isEmpty()) {
                System.out.println(
                        "Time "
                        + currentTime
                        + " -> CPU Idle"
                );

                currentTime++;
                continue;
            }

            // الحصول على أول عملية في الطابور
            Process currentProcess =
                    readyQueue.dequeue();

            // تسجيل أول مرة تبدأ فيها العملية
            if (currentProcess.getFirstStartTime() == -1) {

                currentProcess.setFirstStartTime(
                        currentTime
                );

                currentProcess.setResponseTime(
                        currentTime
                        - currentProcess.getArrivalTime()
                );
            }

            // الزمن الذي ستعمله العملية في هذا الدور
            int executionTime =
                    Math.min(
                            quantum,
                            currentProcess.getRemainingTime()
                    );

            System.out.println(
                    "Time "
                    + currentTime
                    + " -> Running "
                    + currentProcess.getName()
                    + " for "
                    + executionTime
                    + " unit(s)"
            );
            int startTime = currentTime;

            // تنفيذ العملية وحدة بوحدة زمنية
            for (int t = 0; t < executionTime; t++) {

                currentProcess.executeOneUnit();
                currentTime++;

                // أثناء التنفيذ قد تصل عمليات جديدة
                for (int i = 0; i < processes.size(); i++) {

                    Process p = processes.get(i);

                    if (!addedToQueue[i]
                            && p.getArrivalTime()
                            <= currentTime) {

                        readyQueue.enqueue(p);
                        addedToQueue[i] = true;
                    }
                }
            }
            ganttChart.addEntry(
                    currentProcess.getName(),
                    startTime,
                    currentTime
            );

            // إذا انتهت العملية
            if (currentProcess.getRemainingTime() == 0) {

                currentProcess.setCompleted(true);
                completedCount++;

                // Completion Time
                currentProcess.setCompletionTime(
                        currentTime
                );

                // Turnaround Time
                currentProcess.setTurnaroundTime(
                        currentProcess.getCompletionTime()
                        - currentProcess.getArrivalTime()
                );

                // Waiting Time
                currentProcess.setWaitingTime(
                        currentProcess.getTurnaroundTime()
                        - currentProcess.getBurstTime()
                );

                System.out.println(
                        currentProcess.getName()
                        + " completed at time "
                        + currentTime
                );
            }
            // إذا لم تنته تُعاد إلى نهاية الطابور
            else {
                readyQueue.enqueue(currentProcess);
            }
        }

        System.out.println(
                "\nRound Robin Scheduling Finished"
        );
    }
    public GanttChart getGanttChart() {
    return ganttChart;
}
}