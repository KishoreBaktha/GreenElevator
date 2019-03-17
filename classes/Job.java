import java.util.Comparator;

public class Job{
    int destinationFloor;
    int direction = 0;

    public Job(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    //setDirection - Sets the direction (Only used on floor jobs)
    public void setDirection(int direction) {
        this.direction = direction;
    }

    //Comparator to sort jobs on destination floor ascending
    public static class JobComparatorByFloorAscending implements Comparator<Job> {

        @Override
        public int compare(Job lhs, Job rhs) {
            System.out.println("reached here");
            if (lhs.destinationFloor > rhs.destinationFloor) {
                System.out.println("1");
                return 1;
            }
            else if (lhs.destinationFloor < rhs.destinationFloor) {
                System.out.println(lhs.destinationFloor);
                System.out.println(rhs.destinationFloor);
                System.out.println("-1");
                return -1;
            }
            else {
                System.out.println("0");
                return 0;
            }
        }
    }

    //Comparator to sort jobs on destination floor descending
    public static class JobComparatorByFloorDescending implements Comparator<Job> {

        @Override
        public int compare(Job lhs, Job rhs) {
            if (lhs.destinationFloor < rhs.destinationFloor) {
                return 1;
            }
            else if (lhs.destinationFloor > rhs.destinationFloor) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    public String toString() {
        return "Destinationfloor: " + destinationFloor + " Direction: " + direction;
    }
}

