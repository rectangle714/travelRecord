import 'dart:developer';

import 'dart:io' show HttpHeaders, Platform;
import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'travel_board.dart' as travel_board;
import 'travelSpot/travelSpot.dart' as travelSpot;
//import 'kakaoLogin.dart';
import 'login-form.dart';

void main() {
  runApp(const MyApp());
}

var protocol = 'http://';
var host;
var port = ':8080';
var url;
var dioApiObj;
var loginBtn;
final storage = FlutterSecureStorage();
var tokenString;

dioApi(String hostUrl) async {
  print('dio obj 생성 시작');
  var options = BaseOptions(
    baseUrl: hostUrl,
    headers: {'authorization': tokenString},
    connectTimeout: 5000,
    receiveTimeout: 3000,
  );

  dioApiObj = Dio(options);
  print('${await dioApiObj.options.baseUrl}');
}

void userToken() async {
  // log('######'+'${await storage.read(key: 'token')}');
  // Map<String,String> tokenMap = await storage.readAll();
  tokenString = '${await storage.read(key: 'token')}';
}

void whatHost() {
  if (kIsWeb) {
    host = '127.0.0.1';
  } else {
    /**
       * 안드로이드 url 설정해주기
       * 안드로이드 에뮬레이터로 실행 시 에뮬레이터 자체가 하나의 localhost가 되는 것이어서 url을 localhost로 하면 작동을 안함
       * => 에뮬레이터의 10.0.2.2 == 개발하는 머신(컴퓨터)의 127.0.0.1
       * 
       * ios는 해당 컴퓨터의 localhost를 사용한다고 해서 안드로이드 외에는 127.0.0.1로 해두었는데
       * 제대로 동작할지는 모르겠네요! ios는 mac xcode에서 해봐야 테스트 가능하다고 합니당
       */
    host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1';
    log('not web');
  }
}

void testAjax() async {
  log(host);
  var url = Uri.parse('http://$host:8080/main');
  //var url = Uri.http('$host:8080','main');
  log('http://$host:8080/main');
  var response = await http.post(url);

  print('response.statusCode : ${response.statusCode}');
  print('response.body : ${response.body}');
}

void secTest() async {
  var response = dioApiObj.get('/sec');
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
  // This widget is the root of your application.

  @override
  Widget build(BuildContext context) {
    whatHost();
    userToken();
    dioApi(protocol + host + port);

    return MaterialApp(
      title: '메인화면',
      debugShowCheckedModeBanner: false,
      initialRoute: 'travelRecord',
      home: const MyHomePage(title: '메인 화면'),
      routes: {'/second': (context) => travel_board.TravelBoardPage()},
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  void testAjax() async {
    var host;
    if (kIsWeb) {
      host = '127.0.0.1';
    } else {
      /**
       * 안드로이드 url 설정해주기
       * 안드로이드 에뮬레이터로 실행 시 에뮬레이터 자체가 하나의 localhost가 되는 것이어서 url을 localhost로 하면 작동을 안함
       * => 에뮬레이터의 10.0.2.2 == 개발하는 머신(컴퓨터)의 127.0.0.1
       * 
       * ios는 해당 컴퓨터의 localhost를 사용한다고 해서 안드로이드 외에는 127.0.0.1로 해두었는데
       * 제대로 동작할지는 모르겠네요! ios는 mac xcode에서 해봐야 테스트 가능하다고 합니당
       */
      host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1';
      log('not web');
    }
    log(host);
    var url = Uri.parse('http://$host:8080/main');
    //var url = Uri.http('$host:8080','main');
    log('http://$host:8080/main');
    var response = await http.post(url);

    print('response.statusCode : ${response.statusCode}');
    print('response.body : ${response.body}');
  }

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<String> fetch() async {
    var tokenOrNot = await storage.read(key: "token");
    print(tokenOrNot);

    if (tokenOrNot == null) {
      loginBtn = ElevatedButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => Login()),
          );
        },
        child: Text("login"),
      );
    } else {
      loginBtn = ElevatedButton(
        onPressed: () {
          storage.deleteAll();
          Navigator.pushReplacement(context,
              MaterialPageRoute(builder: (BuildContext context) => MyApp()));
        },
        child: Text("logout"),
      );
      showToast('로그인 실패');
    }
    return tokenOrNot.toString();
  }

  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('타이틀 적는곳'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            // ElevatedButton(
            //   onPressed: widget.testAjax,
            //   child: Text("api 통신 테스트 버튼"),
            // ),
            ElevatedButton(
              child: const Text("게시판으로 이동"),
              onPressed: () {
                Navigator.pushNamed(context, '/second');
              },
            ),
            ElevatedButton(
              child: Text("페이지 이동"),
              onPressed: () {
                Navigator.push(context,
                    MaterialPageRoute(builder: (_) => travelSpot.travelSpot()));
              },
            ),
            ElevatedButton(
              onPressed: testAjax,
              child: Text("api 통신 테스트 버튼"),
            ),
            ElevatedButton(
                // onPressed:() {
                //   Navigator.push(
                //     context,
                //     MaterialPageRoute(builder: (context) => KakaoLoginPage()),
                //   );
                // },
                // child: Text("카카오 로그인"),
                onPressed: secTest,
                child: Text('sec test')),
            FutureBuilder(
                future: fetch(),
                builder: (BuildContext context, AsyncSnapshot snapshot) {
                  if (!snapshot.hasData) {
                    return Text('로그인체크');
                  } else {
                    return loginBtn;
                  }
                }),
            Padding(
              child: TextField(
                decoration: InputDecoration(
                  labelText: 'ID',
                ),
              ),
              padding: EdgeInsets.all(20.0),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}

class HexColor extends Color {
  HexColor(final String hexColor) : super(_getColorFromHex(hexColor));

  static int _getColorFromHex(String hexColor) {
    hexColor = hexColor.toUpperCase().replaceAll('#', '');
    if (hexColor.length == 6) {
      hexColor = 'FF' + hexColor;
    }
    return int.parse(hexColor, radix: 16);
  }
}
