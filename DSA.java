import java.util.*;

class Event {
    int id;
    String name;
    String type;

    Event(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return id + " | " + name + " | " + type;
    }
}

class Node {
    Event data;
    Node next;

    Node(Event data) {
        this.data = data;
        next = null;
    }
}

public class SmartEventSystem {

    static Node head = null;

    static HashMap<Integer, Event> eventMap = new HashMap<>();

    static Queue<String> participantQueue = new LinkedList<>();

    static Stack<Event> undoStack = new Stack<>();

    static PriorityQueue<Event> priorityEvents =
            new PriorityQueue<>(Comparator.comparing(e -> e.name));

    static Scanner sc = new Scanner(System.in);

    // ADD EVENT
    static void addEvent(Event e) {

        Node newNode = new Node(e);

        if (head == null)
            head = newNode;
        else {
            Node temp = head;
            while (temp.next != null)
                temp = temp.next;
            temp.next = newNode;
        }

        eventMap.put(e.id, e);
        priorityEvents.add(e);
        undoStack.push(e);

        System.out.println("Event Added Successfully");
    }

    // DISPLAY EVENTS
    static void displayEvents() {

        if (head == null) {
            System.out.println("No events available");
            return;
        }

        Node temp = head;

        while (temp != null) {
            System.out.println(temp.data);
            temp = temp.next;
        }
    }

    // LINEAR SEARCH
    static void linearSearch(int id) {

        Node temp = head;

        while (temp != null) {

            if (temp.data.id == id) {
                System.out.println("Event Found: " + temp.data);
                return;
            }

            temp = temp.next;
        }

        System.out.println("Event not found");
    }

    // BINARY SEARCH
    static void binarySearch(int id) {

        ArrayList<Event> list = new ArrayList<>();

        Node temp = head;

        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }

        list.sort(Comparator.comparingInt(e -> e.id));

        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (list.get(mid).id == id) {
                System.out.println("Event Found: " + list.get(mid));
                return;
            }

            if (list.get(mid).id < id)
                low = mid + 1;
            else
                high = mid - 1;
        }

        System.out.println("Event not found");
    }

    // BUBBLE SORT
    static void bubbleSort() {

        ArrayList<Event> list = new ArrayList<>();

        Node temp = head;

        while (temp != null) {
            list.add(temp.data);
            temp = temp.next;
        }

        for (int i = 0; i < list.size() - 1; i++) {

            for (int j = 0; j < list.size() - i - 1; j++) {

                if (list.get(j).name.compareTo(list.get(j + 1).name) > 0) {

                    Event t = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, t);
                }
            }
        }

        System.out.println("Sorted Events:");
        for (Event e : list)
            System.out.println(e);
    }

    // REGISTER PARTICIPANT (QUEUE)
    static void registerParticipant() {

        sc.nextLine(); // clear buffer

        System.out.print("Enter Participant Name: ");
        String name = sc.nextLine();

        participantQueue.add(name);

        System.out.println("Participant Registered");
    }

    // PROCESS PARTICIPANT
    static void processParticipant() {

        if (participantQueue.isEmpty())
            System.out.println("No participants");
        else
            System.out.println("Processing: " + participantQueue.remove());
    }

    // UNDO EVENT
    static void undoEvent() {

        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }

        Event e = undoStack.pop();
        eventMap.remove(e.id);

        Node temp = head;
        Node prev = null;

        while (temp != null) {

            if (temp.data.id == e.id) {

                if (prev == null)
                    head = temp.next;
                else
                    prev.next = temp.next;

                break;
            }

            prev = temp;
            temp = temp.next;
        }

        System.out.println("Undo Event: " + e.name);
    }

    // SHOW SERVICES
    static void showServices() {

        System.out.println("\nAvailable Services:");
        System.out.println("1. Venue Booking");
        System.out.println("2. Catering");
        System.out.println("3. Decoration");
        System.out.println("4. Photography");
        System.out.println("5. Sound & Lighting");
    }

    // SHOW PRIORITY EVENT
    static void showPriorityEvent() {

        if (priorityEvents.isEmpty()) {
            System.out.println("No priority events");
            return;
        }

        System.out.println("Top Priority Event:");
        System.out.println(priorityEvents.peek());
    }

    // LOAD DEFAULT EVENTS
    static void loadEvents() {

        addEvent(new Event(1, "Birthday Functions", "Birthday"));
        addEvent(new Event(2, "Marriage / Wedding", "Wedding"));
        addEvent(new Event(3, "Dhoti & Saree Functions", "Traditional"));
        addEvent(new Event(4, "Haldi Ceremony", "Wedding Ritual"));
        addEvent(new Event(5, "College Events", "College"));
        addEvent(new Event(6, "Parties", "Party"));
        addEvent(new Event(7, "Concerts", "Music"));
        addEvent(new Event(8, "Festive Cultural Events", "Festival"));
    }

    public static void main(String[] args) {

        loadEvents();

        int choice;

        do {

            System.out.println("\nSMART EVENT MANAGEMENT SYSTEM");
            System.out.println("1 Display Events");
            System.out.println("2 Search Event (Linear)");
            System.out.println("3 Search Event (Binary)");
            System.out.println("4 Sort Events");
            System.out.println("5 Register Participant");
            System.out.println("6 Process Participant");
            System.out.println("7 Undo Last Event");
            System.out.println("8 Show Services");
            System.out.println("9 Show Priority Event");
            System.out.println("10 Exit");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayEvents();
                    break;

                case 2:
                    System.out.print("Enter Event ID: ");
                    linearSearch(sc.nextInt());
                    break;

                case 3:
                    System.out.print("Enter Event ID: ");
                    binarySearch(sc.nextInt());
                    break;

                case 4:
                    bubbleSort();
                    break;

                case 5:
                    registerParticipant();
                    break;

                case 6:
                    processParticipant();
                    break;

                case 7:
                    undoEvent();
                    break;

                case 8:
                    showServices();
                    break;

                case 9:
                    showPriorityEvent();
                    break;

            }

        } while (choice != 10);
    }
}