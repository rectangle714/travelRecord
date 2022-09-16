import 'dart:convert';
import 'dart:developer';
import 'dart:io' show Platform;
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

void main() {
  runApp(const travelSpot());
}

class travelSpot extends StatelessWidget {
  const travelSpot({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: '메인 화면'),
    );
  }
}

List<dynamic> spotList = [];

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  sendKeyword(keyword) async {
    var host;
    if (kIsWeb) {
      host = '127.0.0.1';
    } else {
      host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1';
      log('not web');
    }
    log(host);
    var url = Uri.parse(
        'http://$host:8080/travel/keywordList?keyword=' + '${keyword}');

    var response = await http.get(url);

    print("statusCode: ${response.statusCode}");
    spotList = jsonDecode(response.body);

    print(spotList);
  }

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('FIND YOUR SPOT')),
      body: SingleChildScrollView(
        child: Padding(
            padding: const EdgeInsets.all(20),
            child: Column(
              children: [
                Row(
                  children: [
                    Flexible(
                      child: TextField(
                        decoration: const InputDecoration(
                            labelText: '여행하고 싶은 장소를 검색하세요.',
                            helperText: 'Search your spot',
                            hintText: 'Enter Keyword',
                            border: OutlineInputBorder(),
                            contentPadding: EdgeInsets.all(3)),
                        onSubmitted: (value) async {
                          setState(() {
                            widget.sendKeyword(value);
                          });
                        },
                      ),
                    )
                  ],
                ),
                Column(
                  children: <Widget>[
                    SizedBox(
                        height: 500,
                        child: ListView.builder(
                            shrinkWrap: true,
                            itemCount: spotList.length,
                            itemBuilder: (BuildContext context, int index) {
                              return Container(
                                margin: EdgeInsets.only(top: 20),
                                width: 100,
                                height: 200,
                                padding: EdgeInsets.all(20),
                                child: SingleChildScrollView(
                                  scrollDirection: Axis.horizontal,
                                  child: Row(children: [
                                    SingleChildScrollView(
                                      child: Column(
                                        children: [
                                          Image.network(
                                              spotList[index]['firstimage'] ??
                                                  'lib/asstes/noImage.jpg',
                                              height: 100,
                                              width: 100, errorBuilder:
                                                  ((BuildContext context,
                                                      Object error,
                                                      stackTrace) {
                                            return Image.asset(
                                              'lib/asstes/noImage.jpg',
                                              fit: BoxFit.cover,
                                            );
                                          })),
                                        ],
                                      ),
                                    ),
                                    Column(
                                      children: [
                                        Text(spotList[index]['title']),
                                        Text(spotList[index]['addr1'] ??
                                            '' + spotList[index]['addr12'] ??
                                            ''),
                                        Text(spotList[index]['tel'] ?? ''),
                                      ],
                                    ),
                                  ]),
                                ),
                              );
                            }))
                  ],
                )
              ],
            )),
      ),
    );
  }
}
