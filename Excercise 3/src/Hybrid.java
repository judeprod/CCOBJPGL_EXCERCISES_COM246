class Hybrid implements InternalCombustion, Electric {

    @Override
        public void chargebattery(){
            System.out.println("charging battery");
        }
    @Override
        public void fillgas(){
            System.out.println("refueling gas");
        }
    

}