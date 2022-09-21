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
        'loginPw': '${login_pw.text}',
        // 'name': '${name.text}',
        // 'nick': '${nick.text}',
        // 'mobile': '${mobile.text}',
        // 'email': '${email.text}',
        // 'birth': '${birth.text}',
        // 'gender': '${gender.text}',
        // 'agree': '${agree.text}'
        'name': '박소영',
        'nick': '소바리',
        'mobile': '010-0000-0000',
        'email': 'aaa@aaa.com',
        'birth': '1992-09-23',
        'gender': 'FEMALE',
        'agree': '1'
    };
    
    response = await http.post(
      url,
      headers: <String, String> {
          'Content-Type': 'application/json',
      },
      body: {
        'id' : '${login_id.text}',
      },
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
                    hintText: '아이디를 입력해주세요.'
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
                    hintText: '비밀번호를 입력해주세요.'
                ),
                controller: login_pw
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Name',
                    hintText: '이름 입력해주세요.'
                ),
                controller: name
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Nick',
                    hintText: '닉네임을 입력해주세요.'
                ),
                controller: nick
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Mobile',
                    hintText: '휴대폰 번호를 입력해주세요.'
                ),
                controller: mobile
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Email',
                    hintText: '이메일을 입력해주세요.'
                ),
                controller: email
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Birth',
                    hintText: '생년월일을 입력해주세요.'
                ),
                controller: birth
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Gender',
                    hintText: '성별을 입력해주세요.'
                ),
                controller: gender
              ),
            ),
            Padding(
              padding: EdgeInsets.only(
                  left: 15.0, right: 15.0, top: 5),
              //padding: EdgeInsets.symmetric(horizontal: 15),
              child: TextField(
                decoration: InputDecoration(
                    border: OutlineInputBorder(),
                    labelText: 'Agree',
                    hintText: '동의를 입력해주세요.'
                ),
                controller: agree
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
