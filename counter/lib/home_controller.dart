import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:get/get_state_manager/get_state_manager.dart';

class HomeController extends GetxController {
  var counter = 0.obs;
  String title = "Counter Bilangan Genap";

  void incrementCounter() {
    counter += 2;
  }
}