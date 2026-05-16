
import java.util.LinkedList;
import java.util.Queue;

public class RoundRobinReadyQueue {

    private Queue<Process> queue;

    public RoundRobinReadyQueue() {
        queue = new LinkedList<>();
    }
    @Override

    public String toString() {

        if (queue.isEmpty()) {
            return "Ready Queue is empty.";
        }

        StringBuilder sb = new StringBuilder();

        for (Process p : queue) {
            sb.append(p.getName())
                    .append(" (RT=")
                    .append(p.getRemainingTime())
                    .append(")\n");
        }

        return sb.toString();
    }

    // إضافة عملية إلى نهاية الطابور
    public void enqueue(Process process) {
        queue.offer(process);
    }

    // إزالة أول عملية من الطابور
    public Process dequeue() {
        return queue.poll();
    }

    // معرفة أول عملية دون حذفها
    public Process peek() {
        return queue.peek();
    }

    // التحقق إن كان الطابور فارغًا
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // عدد العمليات داخل الطابور
    public int size() {
        return queue.size();
    }

    // عرض محتويات الطابور
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Ready Queue is empty.");
            return;
        }

        System.out.println("Round Robin Ready Queue:");

        for (Process p : queue) {
            System.out.println(
                p.getName()
                + " (Remaining Time = "
                + p.getRemainingTime()
                + ")"
            );
        }
    }

    // الوصول إلى الـ Queue نفسها عند الحاجة
    public Queue<Process> getQueue() {
        return queue;
    }
}