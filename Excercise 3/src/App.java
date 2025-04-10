import java.util.ArrayList;

public class App {
        public static void main(String[] args) throws Exception{

            Hybrid Hybrid1 = new Hybrid();
            Hybrid1.fillgas();
            Hybrid1.chargebattery();


            HybridVehicle car1 = new HybridSedan();
            car1.carname = "Lexus ES 300h";

            HybridVehicle car2 = new HybridPickup();
            car2.carname = "Toyota Tundra";

            ArrayList<HybridVehicle> cars = new ArrayList<>();
            cars.add(car1);
            cars.add(car2);

            for (HybridVehicle car : cars) {
                car.getCarname();
            }

            CarWash c1 = new CarWash();

            c1.wash(car1);

            c1.wash(car2);
            

        }
    }

