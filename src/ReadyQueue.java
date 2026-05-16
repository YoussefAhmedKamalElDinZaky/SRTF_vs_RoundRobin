





import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {

    private Queue<Process> queue;

    public ReadyQueue() {

        queue = new LinkedList<>();
    }

    // Add process to queue
    public void enqueue(Process process) {

        queue.add(process);
    }

    // Remove process from queue
    public Process dequeue() {

        return queue.poll();
    }

    // Display queue
    public void displayQueue() {

        for(Process p : queue) {
            p.display();
        }
    }

    // Check if queue is empty
    public boolean isEmpty() {

        return queue.isEmpty();
    }
}