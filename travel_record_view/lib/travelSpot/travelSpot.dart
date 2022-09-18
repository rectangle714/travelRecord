import 'dart:convert';
import 'dart:developer';
import 'dart:io' show Platform;
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../design/travelSpotTheme.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

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
                                decoration: BoxDecoration(
                                  borderRadius: const BorderRadius.all(
                                      Radius.circular(16.0)),
                                  boxShadow: <BoxShadow>[
                                    BoxShadow(
                                      color: Colors.grey.withOpacity(0.6),
                                      offset: const Offset(4, 4),
                                      blurRadius: 16,
                                    ),
                                  ],
                                ),
                                child: ClipRRect(
                                  borderRadius: const BorderRadius.all(
                                      Radius.circular(16.0)),
                                  child: Stack(
                                    children: <Widget>[
                                      Column(
                                        children: <Widget>[
                                          AspectRatio(
                                            aspectRatio: 2,
                                            child: Image.network(
                                                spotList[index]['firstimage'] ??
                                                    'lib/asstes/noImage.jpg',
                                                errorBuilder:
                                                    ((BuildContext context,
                                                        Object error,
                                                        stackTrace) {
                                              return Image.asset(
                                                'lib/asstes/noImage.jpg',
                                                fit: BoxFit.cover,
                                              );
                                            })),
                                          ),
                                          Container(
                                            color: travelSpotTheme
                                                .buildLightTheme()
                                                .backgroundColor,
                                            child: Row(
                                              mainAxisAlignment:
                                                  MainAxisAlignment.center,
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.start,
                                              children: <Widget>[
                                                Expanded(
                                                  child: Container(
                                                    child: Padding(
                                                      padding:
                                                          const EdgeInsets.only(
                                                              left: 16,
                                                              top: 8,
                                                              bottom: 8),
                                                      child: Column(
                                                        mainAxisAlignment:
                                                            MainAxisAlignment
                                                                .center,
                                                        crossAxisAlignment:
                                                            CrossAxisAlignment
                                                                .start,
                                                        children: <Widget>[
                                                          Text(
                                                            spotList[index]
                                                                ['title'],
                                                            textAlign:
                                                                TextAlign.left,
                                                            style: TextStyle(
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w600,
                                                              fontSize: 22,
                                                            ),
                                                          ),
                                                          Row(
                                                            crossAxisAlignment:
                                                                CrossAxisAlignment
                                                                    .center,
                                                            mainAxisAlignment:
                                                                MainAxisAlignment
                                                                    .start,
                                                            children: <Widget>[
                                                              const SizedBox(
                                                                width: 4,
                                                              ),
                                                              Icon(
                                                                FontAwesomeIcons
                                                                    .locationDot,
                                                                size: 12,
                                                                color: travelSpotTheme
                                                                    .buildLightTheme()
                                                                    .primaryColor,
                                                              ),
                                                              Expanded(
                                                                child: Text(
                                                                  spotList[index]
                                                                          [
                                                                          'addr1'] ??
                                                                      '' +
                                                                          spotList[index]
                                                                              [
                                                                              'addr12'] ??
                                                                      '',
                                                                  overflow:
                                                                      TextOverflow
                                                                          .ellipsis,
                                                                  style: TextStyle(
                                                                      fontSize:
                                                                          14,
                                                                      color: Colors
                                                                          .grey
                                                                          .withOpacity(
                                                                              0.8)),
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                          Padding(
                                                            padding:
                                                                const EdgeInsets
                                                                        .only(
                                                                    top: 4),
                                                            child: Row(
                                                              children: <
                                                                  Widget>[],
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Padding(
                                                  padding:
                                                      const EdgeInsets.only(
                                                          right: 16, top: 8),
                                                  child: Column(
                                                    mainAxisAlignment:
                                                        MainAxisAlignment
                                                            .center,
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment.end,
                                                    children: <Widget>[],
                                                  ),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ],
                                      ),
                                      Positioned(
                                        top: 8,
                                        right: 8,
                                        child: Material(
                                          color: Colors.transparent,
                                          child: InkWell(
                                            borderRadius:
                                                const BorderRadius.all(
                                              Radius.circular(32.0),
                                            ),
                                            onTap: () {},
                                            child: Padding(
                                              padding:
                                                  const EdgeInsets.all(8.0),
                                              child: Icon(
                                                Icons.favorite_border,
                                                color: travelSpotTheme
                                                    .buildLightTheme()
                                                    .primaryColor,
                                              ),
                                            ),
                                          ),
                                        ),
                                      )
                                    ],
                                  ),
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
