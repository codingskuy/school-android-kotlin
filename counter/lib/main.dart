import 'package:counter/home_binding.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

import 'my_home_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: .fromSeed(seedColor: Colors.deepPurple),
      ),
      initialBinding: HomeBinding(),
      home: MyHomePage(),
    );
  }
}


