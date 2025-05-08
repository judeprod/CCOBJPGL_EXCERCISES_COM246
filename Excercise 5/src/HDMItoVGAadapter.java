class HDMItoVGAadapter implements VGA{
    private HDMI HDMIcable;

    public HDMItoVGAadapter(HDMI HDMIcable){
        this.HDMIcable = HDMIcable;
    }

    @Override
    public void connectwVGA() {
        System.out.println("Adapting HDMI to VGA... ");
        HDMIcable.connectHDMI();
    }
}