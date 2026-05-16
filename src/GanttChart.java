



import java.util.ArrayList;

public class GanttChart {

    private ArrayList<GanttEntry> entries;

    public GanttChart() {
        entries = new ArrayList<>();
    }

    // إضافة مقطع جديد إلى المخطط
    public void addEntry(
            String processName,
            int startTime,
            int endTime
    ) {
        // إذا كان آخر مقطع لنفس العملية ومتصلاً زمنياً،
        // ندمجه بدل إنشاء مقطع جديد.
        if (!entries.isEmpty()) {

            GanttEntry last =
                    entries.get(entries.size() - 1);

            if (last.getProcessName().equals(processName)
                    && last.getEndTime() == startTime) {

                last.setEndTime(endTime);
                return;
            }
        }

        entries.add(
                new GanttEntry(
                        processName,
                        startTime,
                        endTime
                )
        );
    }

    public ArrayList<GanttEntry> getEntries() {
        return entries;
    }

    // عرض المخطط نصيًا
    public void display() {

        if (entries.isEmpty()) {
            System.out.println("Gantt Chart is empty.");
            return;
        }

        System.out.println("\nGantt Chart:\n");

        // السطر العلوي
        for (GanttEntry entry : entries) {
            System.out.print("|  "
                    + entry.getProcessName()
                    + "  ");
        }
        System.out.println("|");

        // السطر الزمني
        System.out.print(entries.get(0).getStartTime());

        for (GanttEntry entry : entries) {
            System.out.print(
                    String.format("%6d",
                            entry.getEndTime())
            );
        }

        System.out.println("\n");
    }
}