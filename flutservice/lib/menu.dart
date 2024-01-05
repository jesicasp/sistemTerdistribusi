import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

import 'mahasiswa/mahasiswa.dart';
import 'matakuliah/matakuliah.dart';
import 'nilai/nilai.dart';

class MenuOption extends StatefulWidget {
  const MenuOption({super.key});

  @override
  State<MenuOption> createState() => _MenuOptionState();
}

class _MenuOptionState extends State<MenuOption> {

  
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Flutter Service", style: TextStyle(fontSize: 22, color: Colors.white, fontWeight: FontWeight.bold)),
        centerTitle: true,
        backgroundColor: Colors.indigo,
      ),
        body: Column(
          children: [
            Padding(
              padding: const EdgeInsets.only(
                top: 20,
                bottom: 30,
              ),
              child: Center(child: Text("Service Sistem Terdistribusi", style: TextStyle(fontSize: 24, color: Colors.indigo, fontWeight: FontWeight.bold),)),
            ), 
            Expanded(
              child: ListView.builder(
              itemCount: 3,
              itemBuilder: (context, index) {
                return InkWell(
                  onTap: () {
                    if (index == 0) {
                      Navigator.push(context, MaterialPageRoute(builder: (context) => DataMahasiswa()));
                    } else if (index == 1) {
                      Navigator.push(context, MaterialPageRoute(builder: (context) => DataMatakuliah()));
                    } else if (index == 2) {
                      Navigator.push(context, MaterialPageRoute(builder: (context) => DataNilai()));
                    }
                  },
                  child: Container(
                    padding: EdgeInsets.all(16.0),
                    margin: EdgeInsets.all(8.0),
                    decoration: BoxDecoration(
                      border: Border.all(),
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          index == 0 ? 'Mahasiswa' : (index == 1 ? 'Matakuliah' : 'Nilai'),
                          style: TextStyle(fontSize: 18.0, fontWeight: FontWeight.bold),
                        ),
                        Icon(
                          index == 0 ? Icons.person : (index == 1 ? Icons.book : Icons.star), color: Colors.indigo,
                          size: 30.0,
                        ),
                      ],
                    ),
                  ),
                );
              },
                  ),
            ),
          ],
        ),
    );
  }
}
