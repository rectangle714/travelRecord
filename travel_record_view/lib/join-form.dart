import 'dart:convert';
import 'dart:developer';
import 'dart:io';
import 'package:http/http.dart' as http;

import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';

var response;
var host;
var url;
class JoinForm extends StatefulWidget {
  @override
  JoinFormState createState() => JoinFormState();
}

class JoinFormState extends State<JoinForm> {

  TextEditingController login_id = TextEditingController();
  TextEditingController login_pw = TextEditingController();
  TextEditingController name = TextEditingController();
  TextEditingController nick = TextEditingController();
  TextEditingController mobile = TextEditingController();
  TextEditingController email = TextEditingController();
  TextEditingController birth = TextEditingController();
  TextEditingController gender = TextEditingController();
  TextEditingController agree = TextEditingController();

  joinAction() async {
    
    if(kIsWeb){
      host = '127.0.0.1';
    }
    else{
      host = Platform.isAndroid ? '10.0.2.2' : '127.0.0.1'; 
    }

    url = Uri.parse('http://$host:8080/join');

    Map<String,String> body = {
        'loginId': '${login_id.text}',
        'loginPw': '${login_pw.text}'
    };
    
    response = await http.post(
      url,
      headers: <String, String> {
          'Content-Type': 'application/json',
      },
      body: json.encode(body),
    );

    
    print('response.statusCode : ${response.statusCode}');
    print('response.body : ${response.body}');
  }

  


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text("Join Us"),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: <Widget>[
            Padding(
              //padding: const EdgeInsets.only(left:15.0,right: 15.0,top:0,bottom: 0),
              padding: 
              EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'ID',
                    hintText: '???????????? ??????????????????.'
                ),
                controller: login_id,
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(

                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Password',
                    hintText: '??????????????? ??????????????????.'
                ),
                controller: login_pw
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(

                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Name',
                    hintText: '?????? ??????????????????.'
                ),
                controller: name
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(

                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Nick',
                    hintText: '???????????? ??????????????????.'
                ),
                controller: nick
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Mobile',
                    hintText: '????????? ????????? ??????????????????.'
                ),
                controller: mobile
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Email',
                    hintText: '???????????? ??????????????????.'
                ),
                controller: email
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Birth',
                    hintText: '??????????????? ??????????????????.'
                ),
                controller: birth
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Gender',
                    hintText: '????????? ??????????????????.'
                ),
                controller: gender
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                obscureText: true,
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Agree',
                    hintText: '????????? ??????????????????.'
                ),
                controller: gender
              ),
            ),
            Container(
              height: 50,
              width: 250,
              margin: EdgeInsets.only(
                  top: 10, bottom: 20),
              decoration: BoxDecoration(
                  color: Colors.blue, borderRadius: BorderRadius.circular(20)),
                  child: ElevatedButton(
                    onPressed: joinAction,
                    child: Text(
                      'Save',
                      style: TextStyle(color: Colors.white, fontSize: 25),
                    ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
