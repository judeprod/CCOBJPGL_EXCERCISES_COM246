

class Monitor {
    public void display(VGA display){
        System.out.println("Monitor expecting VGA");
        display.connectwVGA();
        System.out.println("Display Connected");
    }
}