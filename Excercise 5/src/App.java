public class App {
    public static void main(String[] args) throws Exception {
        HDMI olddisplay = new HDMI();

        VGA adapter = new HDMItoVGAadapter(olddisplay);

        Monitor monitor = new Monitor();
        monitor.display(adapter);
    }
}
