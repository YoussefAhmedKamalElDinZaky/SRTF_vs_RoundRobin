



import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SRTFScheduler {

    private ArrayList<Process> processes;
    private GanttChart ganttChart;

    public SRTFScheduler(
            ArrayList<Process> processes
    ) {

        this.processes = processes;
        this.ganttChart = new GanttChart();

    }

    // البحث عن أقصر عملية وصلت
    private Process getShortestArrivedProcess(
            int currentTime
    ) {

        Process shortest = null;

        for(Process p : processes) {

            if(
                !p.isCompleted()
                &&
                p.getArrivalTime()
                <= currentTime
                &&
                p.getRemainingTime() > 0
            ) {

                if(
                    shortest == null
                    ||
                    p.getRemainingTime()
                    <
                    shortest.getRemainingTime()
                ) {

                    shortest = p;
                }
            }
        }

        return shortest;
    }

    // البحث عن أقصر remaining time داخل الـ queue
    private Process getShortestProcess(
            Queue<Process> readyQueue
    ) {

        Process shortest = null;

        for(Process p : readyQueue) {

            if(
                shortest == null
                ||
                p.getRemainingTime()
                <
                shortest.getRemainingTime()
            ) {

                shortest = p;
            }
        }

        return shortest;
    }

    public void run() {

        Queue<Process> readyQueue =
                new LinkedList<>();

        int currentTime = 0;

        int completedCount = 0;

        Process currentProcess;

        while(
            completedCount
            <
            processes.size()
        ) {

            // اختيار أقصر عملية وصلت
            currentProcess =
                    getShortestArrivedProcess(
                            currentTime
                    );

            // إذا لا توجد عملية جاهزة
            if(currentProcess == null) {

                System.out.println(
                        "Time "
                        + currentTime
                        + " -> CPU Idle"
                );

                currentTime++;

                continue;
            }
            if (currentProcess.getFirstStartTime() == -1) {
                currentProcess.setFirstStartTime(currentTime);
                currentProcess.setResponseTime(
                        currentTime - currentProcess.getArrivalTime()
                );
            }

            // تنفيذ العملية الحالية
            System.out.println(
                    "Time "
                    + currentTime
                    + " -> Running "
                    + currentProcess.getName()
            );
            int startTime = currentTime;

            currentProcess.executeOneUnit();

            currentTime++;

            ganttChart.addEntry(
                    currentProcess.getName(),
                    startTime,
                    currentTime
            );

            // فحص العمليات الجديدة
            for(Process p : processes) {

                if(
                    p != currentProcess
                    &&
                    !p.isCompleted()
                    &&
                    p.getArrivalTime()
                    == currentTime
                ) {

                    System.out.println(
                            p.getName()
                            + " arrived"
                    );

                    // مقارنة remaining time
                    if(
                        p.getRemainingTime()
                        <
                        currentProcess.getRemainingTime()
                    ) {

                        System.out.println(
                                "Immediate Preemption!"
                        );

                        readyQueue.add(
                                currentProcess
                        );

                        currentProcess = p;

                    } else {

                        readyQueue.add(p);
                    }
                }
            }

            // إذا انتهت العملية
            if(
                currentProcess.getRemainingTime()
                == 0
            ) {

                currentProcess.setCompleted(true);

                completedCount++;
                // Completion Time
                currentProcess.setCompletionTime(currentTime);

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

                // إزالة العملية إن كانت موجودة بالـ queue
                readyQueue.remove(currentProcess);

                // اختيار العملية التالية
                if(!readyQueue.isEmpty()) {

                    Process shortest =
                            getShortestProcess(
                                    readyQueue
                            );

                    readyQueue.remove(shortest);
                }
            }
        }

        System.out.println(
                "\nSRTF Scheduling Finished"
        );
    }
    public GanttChart getGanttChart() {
    return ganttChart;
}
}