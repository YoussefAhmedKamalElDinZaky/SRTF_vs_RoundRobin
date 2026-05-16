
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        InputValidator validator = new InputValidator();
        ArrayList<Process> processList = new ArrayList<>();

        // إدخال عدد العمليات
        int numberOfProcesses =
                validator.getValidNumberOfProcesses();

        // إدخال بيانات العمليات
        for (int i = 0; i < numberOfProcesses; i++) {

            System.out.println("\nProcess " + (i + 1));

            String name =
                    validator.getValidProcessName();

            int arrivalTime =
                    validator.getValidArrivalTime();

            int burstTime =
                    validator.getValidBurstTime();

            Process process =
                    new Process(
                            name,
                            arrivalTime,
                            burstTime
                    );

            processList.add(process);
        }

        // إدخال Time Quantum
        int quantum = validator.getValidQuantum();

        // تشغيل الخوارزميات
        SimulationRunner runner
                = new SimulationRunner();

        runner.runSRTF(processList);

        runner.runRoundRobin(processList, quantum);
        runner.displayComparison(quantum);
    }
}