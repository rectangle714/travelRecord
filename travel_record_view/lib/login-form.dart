import 'dart:convert';
import 'dart:developer';
import 'dart:io';
import 'package:http/http.dart' as http;

import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

class LoginDemo extends StatefulWidget {
  @override
  _LoginDemoState createState() => _LoginDemoState();
}

class _LoginDemoState extends State<LoginDemo> {

  TextEditingController user_id = TextEditingController();
  TextEditingController user_pw = TextEditingController();

  printLoginInfo(){
    print('${user_id.text}');
    print('${user_pw.text}');
  }

  loginAction() async {
    var host;
    if(kIsWeb){
      host = '127.0.0.1';
    }
    else{
      host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1'; 
      log('not web');
    }

    var url = Uri.parse('http://$host:8080/login');
    Map<String,String> body = {
        'id': '${user_id.text}',
        'pw': '${user_pw.text}'
    };

    log('http://$host:8080/login');
    var response = await http.post(
      url,
      headers: <String, String> {
          'Content-Type': 'application/json',
      },
      body: json.encode(body),
    );
    print('${user_id.text}');
    print('${user_pw.text}');
    print('response.statusCode : ${response.statusCode}');
    print('response.body : ${response.body}');
}


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text("Login Page"),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.only(top: 60.0),
              child: Center(
                child: Container(
                    width: 200,
                    height: 80,
                    /*decoration: BoxDecoration(
                        color: Colors.red,
                        borderRadius: BorderRadius.circular(50.0)),*/
                    child: Text("Travel Record!",style: TextStyle(color: Colors.blue, fontSize: 30),textAlign: TextAlign.center,)
                    ),
              ),
            ),
            Padding(
              //padding: const EdgeInsets.only(left:15.0,right: 15.0,top:0,bottom: 0),
              padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'ID',
                    hintText: '아이디를 입력해주세요.'
                ),
                controller: user_id,
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 15, bottom: 0),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(

                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Password',
                    hintText: '비밀번호를 입력해주세요.'
                ),
                controller: user_pw
              ),
            ),
            FlatButton(
              onPressed: (){
                //TODO FORGOT PASSWORD SCREEN GOES HERE
              },
              child: Text(
                'Forgot Password',
                style: TextStyle(color: Colors.blue, fontSize: 15),
              ),
            ),
            Container(
              height: 50,
              width: 250,
              decoration: BoxDecoration(
                  color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                  child: FlatButton(
                    // onPressed: () {
                    //   Navigator.push(
                    //       context, MaterialPageRoute(builder: (_) => HomePage())
                    //   );
                    // },
                    onPressed: loginAction,
                    child: Text(
                      'Login',
                      style: TextStyle(color: Colors.white, fontSize: 25),
                    ),
              ),
            ),
            SizedBox(
              height: 130,
            ),
            Text('New User? Create Account')
          ],
        ),
      ),
    );
  }
}



