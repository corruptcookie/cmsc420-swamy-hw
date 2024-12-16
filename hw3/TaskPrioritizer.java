import java.lang.String;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * TaskPrioritizer class that returns the most urgent
 * available task
 *
 * @author Shasank S. Patel
 */

class Task {
    String taskId;
    int urgencyLevel;
    ArrayList<String> dependencies;
    ArrayList<String> dependants;
    boolean resolved;
    int index;

    public Task(String taskId, int urgencyLevel, ArrayList<String> dependencies, int index) {
        this.taskId = taskId;
        this.urgencyLevel = urgencyLevel;
        this.dependencies = dependencies;
        this.dependants = new ArrayList<>();
        this.resolved = false;
        this.index = index;
    }
}

class HMap {
    int capacity = 16;
    double loadFactor = 0.75;
    int size = 0;
    LinkedList<Task>[] taskSlots;

    public HMap() {
        taskSlots = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            taskSlots[i] = new LinkedList<>();
        }
    }

    int getIndex(String taskId) {
        int numberPart = Integer.parseInt(taskId.substring(1));
        return (numberPart % capacity);
    }

    public void put(Task task) {
        int index = getIndex(task.taskId);
        taskSlots[index].add(task);
        size++;

        if ((double) size / (double) capacity > loadFactor) {
            resize();
        }
    }

    public Task get(String taskId) {
        int index = getIndex(taskId);
        for (Task task : taskSlots[index]) {
            if (task.taskId.equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    private void resize() {
        LinkedList<Task>[] oldTaskSlots = taskSlots;
        taskSlots = new LinkedList[capacity * 2];
        capacity *= 2;
        size = 0;

        for (int i = 0; i < capacity; i++) {
            taskSlots[i] = new LinkedList<>();
        }

        for (LinkedList<Task> tasks : oldTaskSlots) {
            for (Task task : tasks) {
                put(task);
            }
        }
    }

}


class MHeap {
    ArrayList<Task> heap;

    public MHeap() {
        heap = new ArrayList<>();
    }

    public void add(Task task) {
        heap.add(task);
        percolateUp(heap.size() - 1);
    }

    public Task pop() {
        if (heap.isEmpty()) {
            return null;
        }

        Task top = heap.get(0);
        Task replacement = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, replacement);
            percolateDown(0);
        }

        return top;
    }

    private void percolateUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (heap.get(index).urgencyLevel > heap.get(parent).urgencyLevel) {
                swap(index, parent);
                index = parent;
            } else if (heap.get(index).urgencyLevel == heap.get(parent).urgencyLevel) {
                if (heap.get(index).index < heap.get(parent).index) {
                    swap(index, parent);
                    index = parent;
                } else {
                    break;
                }
            } else {
                break;
            }

        }
    }

    private void percolateDown(int index) {
        int size = heap.size();

        while (true) {
            int curr = index;
            int lChild = index * 2 + 1;
            int rChild = index * 2 + 2;

            if (lChild < size) {
                if (heap.get(lChild).urgencyLevel > heap.get(curr).urgencyLevel) {
                    curr = lChild;
                } else if (heap.get(lChild).urgencyLevel == heap.get(curr).urgencyLevel) {
                    if (heap.get(lChild).index < heap.get(curr).index) {
                        curr = lChild;
                    }
                }
            }

            if (rChild < size) {
                if (heap.get(rChild).urgencyLevel > heap.get(curr).urgencyLevel) {
                    curr = rChild;
                } else if (heap.get(rChild).urgencyLevel == heap.get(curr).urgencyLevel) {
                    if (heap.get(rChild).index < heap.get(curr).index) {
                        curr = rChild;
                    }
                }
            }

            if (curr == index) {
                break;
            }

            swap(index, curr);
            index = curr;
        }
    }

    private void swap(int i, int j) {
        Task temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void update(Task task, int oldUrgencyLevel) {
        int index = heap.indexOf(task);

        if (oldUrgencyLevel < task.urgencyLevel) {
            percolateUp(index);
        } else if (oldUrgencyLevel > task.urgencyLevel) {
            percolateDown(index);
        }
    }
}


public class TaskPrioritizer {
    /**
     * Constructor to initialize the TaskPrioritizer
     */
    HMap taskMap;
    MHeap priorityHeap;
    int index;

    public TaskPrioritizer() {
        this.taskMap = new HMap();
        this.priorityHeap = new MHeap();
        this.index = 0;
    }

    /**
     * A method to add a new task
     *
     * @param taskId       The string taskId of the task we want to add
     * @param urgencyLevel The integer urgencyLevel of the task we want to add
     * @param dependencies The array of taskIds of tasks the added task depends on
     */
    public void add(String taskId, int urgencyLevel, String[] dependencies) {
        // TODO
        index = index + 1;
        ArrayList<String> newDependencies = new ArrayList<>();
        for (String dependency : dependencies) {
            Task task = taskMap.get(dependency);
            if (task != null) {
                task.dependants.add(taskId);
            } else {
                newDependencies.add(dependency);
            }
            if (task != null && !task.resolved) {
                newDependencies.add(taskId);
            }
        }

        Task newTask = new Task(taskId, urgencyLevel, newDependencies, index);
        taskMap.put(newTask);

        if (newDependencies.isEmpty()) {
            priorityHeap.add(newTask);
        }
    }

    /**
     * A method to change the urgency of a task
     *
     * @param taskId       The string taskId of the task we want to change the
     *                     urgency of
     * @param newUrgencyLevel The new integer urgencyLevel of the task
     */
    public void update(String taskId, int newUrgencyLevel) {
        // TODO
        Task task = taskMap.get(taskId);
        if (task != null) {
            int oldUrgencyLevel = task.urgencyLevel;
            task.urgencyLevel = newUrgencyLevel;
            if (task.dependencies.isEmpty() && !task.resolved) {
                priorityHeap.update(task, oldUrgencyLevel);
            }
        }
    }

    /**
     * A method to resolve the greatest urgency task which has had all of its
     * dependencies satisfied
     *
     * @return The taskId of the resolved task
     * @return null if there are no unresolved tasks left
     */
    public String resolve() {
        // TODO
        Task removedTask = priorityHeap.pop();
        if (removedTask == null) {
            return null;
        }

        for (String dependant : removedTask.dependants) {
            Task task = taskMap.get(dependant);
            task.dependencies.remove(dependant);
            if (task.dependencies.isEmpty() && !task.resolved) {
                priorityHeap.add(task);
            }
        }

        removedTask.resolved = true;

        return removedTask.taskId;
    }
}