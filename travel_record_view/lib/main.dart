import 'dart:developer';

import 'dart:io' show HttpHeaders, Platform;
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'kakaoLogin.dart';
import 'login-form.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);
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
  void testAjax() async {
    
    var host;
    if(kIsWeb){
      host = '127.0.0.1';
    }
    else{
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

  static final storage = FlutterSecureStorage();
  var loginBtn;

  Future<String> fetch() async {
    var tokenOrNot = await storage.read(key: "token");
    print(tokenOrNot);

    if (tokenOrNot == null) {
      loginBtn = ElevatedButton(
              onPressed:() {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => Login()),
                );
              },
              child: Text("login"),
            );
    }else{
      loginBtn = ElevatedButton(
              onPressed:() {
                storage.deleteAll();
                Navigator.pushReplacement(context, MaterialPageRoute(builder: (BuildContext context) => MyApp()));
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

    void secTest() async {

      var host;

      if(kIsWeb){
        host = '127.0.0.1';
      }
      else{
        host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1'; 
        log('not web');
      }

        var url = Uri.parse('http://$host:8080/sec');
        Map<String,String> tokenMap = await storage.readAll();
        String tokenString = '${tokenMap['token']}';
        
        print('tokenString>>>'+tokenString);

        log('http://$host:8080/sec');
        var response = await http.get(url,
          headers: {
            HttpHeaders.authorizationHeader: tokenString,
          },
        );

        print('response.statusCode : ${response.statusCode}');
        print('response.body : ${response.body}');
        
    
  }

  return Scaffold(
    appBar: AppBar(
      title: Text(widget.title),
    ),
    body: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          const Text(
            '카운트 추가',
          ),
          Text(
            '$_counter',
            style: Theme.of(context).textTheme.headline4,
          ),
          ElevatedButton(
            onPressed: widget.testAjax,
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
            child: Text('sec test')
          ),
          FutureBuilder(
            future: fetch(),
            builder: (BuildContext context, AsyncSnapshot snapshot) {
              if(!snapshot.hasData){
                return Text('로그인체크');
              }else{
                return loginBtn;
              }
            }
          ),
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
