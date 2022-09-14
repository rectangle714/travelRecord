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

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: '메인 화면'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  void sendKeyword(keyword) async {
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
      appBar: AppBar(title: Text('FIND YOUR SPOT')),
      body: Center(
        child: Padding(
          //상하좌우로 띄워주기 (아이콘 왼쪽과 텍스트 필드 오른쪽이 화면 테두리와 조금 떨어져 있다.)
          padding: const EdgeInsets.all(20),
          child: TextField(
            decoration: InputDecoration(
                labelText: '여행하고 싶은 장소를 검색하세요.',
                helperText: 'Search your spot',
                hintText: 'Enter Keyword', //글자를 입력하면 사라진다.
                icon: Icon(Icons.android),
                border: OutlineInputBorder(),
                contentPadding: EdgeInsets.all(3)),
            onSubmitted: (value) async {
              widget.sendKeyword(value);
            },
            //키보드로 엔터 클릭 시 호출
          ),
        ),
      ),
    );
  }
}
