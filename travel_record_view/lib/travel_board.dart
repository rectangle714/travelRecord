import 'dart:developer';
import 'package:flutter/foundation.dart';
import 'dart:io' show Platform;
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'page_change_b.dart' as page_change_b;

class TravelBoardPage extends StatefulWidget {
  @override
  State<TravelBoardPage> createState() => TravelBoardState();
}

class TravelBoardState extends State<TravelBoardPage> {
  var host;
  List data = [];

  Future<String> getList() async {
    if (kIsWeb) {
      host = '127.0.0.1';
    } else {
      host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1';
    }

    var response = await http.post(Uri.parse('http://$host:8080/diary/list'),
        body: {'title': 'testTitle'});
    if (response.statusCode == 200) {
      data = json.decode(response.body);
      log('성공');
      log('${data}');
      return '성공';
    } else {
      throw Exception('API 호출에러');
    }
  }

  @override
  void initState() {
    super.initState();
    this.getList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("여행기"),
      ),
      body: Container(
        color: Colors.black12,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Expanded(
              child: Container(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 0),
                color: Colors.blue,
                width: 420,
                height: 5,
                child: Text('다가오는 여행'),
              ),
            ),
            Expanded(
              child: Container(
                color: Colors.red,
                width: 420,
                height: 5,
                child: Text('지난 여행기록'),
              ),
            ),
            // Expanded(child: Text('지난 여행기록')),
            // ElevatedButton(
            //     child: const Text('메인화면'),
            //     onPressed: () {
            //       Navigator.pop(context);
            //     }),
            // ElevatedButton(
            //     child: const Text("페이지 이동"),
            //     onPressed: () {
            //       Navigator.push(
            //           context,
            //           MaterialPageRoute(
            //               builder: (_) => page_change_b.page_change_b()));
            //     }),
            Expanded(
                child: ListView.builder(
              itemCount: data == null ? 0 : data.length,
              itemBuilder: (BuildContext context, int index) {
                return new Text(data[index]["content"]);
              },
            ))
          ],
        ),
      ),
    );
  }
}
