
import 'package:flutter/material.dart';

class TestPage extends StatefulWidget{
  @override
  State<TestPage> createState() => _TestPage();

}
class _TestPage extends State<TestPage>{

  @override
  Widget build(BuildContext context) {
    return(Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leadingWidth: 100,
        leading: Container(
            child: Expanded(
             child: Container(
              padding: EdgeInsets.fromLTRB(20, 15, 0, 0),
              child: Text(
                  "여행기",
                  style: TextStyle(
                    color: Colors.black54,
                    fontSize: 24,
                    fontWeight: FontWeight.w700
                  ),
                ),
             ),
            )
          ),
        shadowColor : Colors.transparent,
        actions: [
            IconButton(icon: Icon(Icons.login), onPressed: null),
            IconButton(icon: Icon(Icons.menu), onPressed: null),
        ],
      ),
      body: Container(
        child:Column(
          children: [
            Expanded(child: Container(
              child: Row(
                children: [
                  Flexible(child: Container(color:Colors.black12), flex:5),
                  Flexible(child: Container(color:Colors.black26), flex:5),
                ],
              ),
            )),
            Expanded(
              child:Container(
                child : Column(
                  children: [
                    Flexible(child: Container(color: Color(0xFFA7DE2F),), flex: 8,),
                    Flexible(
                      flex: 2,
                      child: Container(
                        child: ElevatedButton(
                            child: Center(
                              child: Text("돌아가기"),
                            ),
                          onPressed: () => Navigator.pop(context),
                        ),
                      ) 
                    )
                  ],
                ),
              )
            ),
          ],
        )
      ),
      bottomNavigationBar: BottomAppBar(
        child : Container(
          height: 50,
          child: Row(
            children: [
              Flexible(child: Container(color: Colors.indigo), flex: 3),
              Flexible(child: Container(color: Colors.purple), flex: 7),
              ],
          ),
        ),
        
      )
      
    ));
  }
}