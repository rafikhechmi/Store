var express = require('express');
var app = express();
var MongoClient = require('mongodb').MongoClient;
var url = 'mongodb://localhost/store_db';
var str = "";
var sep =",";
var a="[";
var b="]";

var i=0;
var j=0;
var session = require('express-session');
var cookieParser=require('cookieparser')
app.use(session({secret : "eduxlab"}));





function getcats(req, res)
    {


        MongoClient.connect(url, function(err, db)
       {
            var cursor = db.collection('categories').find();		

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{
 res.send("["+str+"]");
str=""
i=0;
req.session.destroy();



}


            });
			db.close();

//res.writeHead(200, {"Content-Type": "application/json"});

          	      //  res.send("["+str+"]");

        })

    }
app.route('/categories').get(getcats);


function getapps(req, res)
    {delete req.session;

        MongoClient.connect(url, function(err, db)
       {
            var cursor = db.collection('applications').find();	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{
 res.send("["+str+"]");
str=""
i=0;


{




 
}





}
            });
          	       
        })
    }



app.route('/applications').get(getapps);


app.get('/:my_param',function (req,res){

 MongoClient.connect(url, function(err, db)
       {
		var json= JSON.stringify(req.params);

            var cursor = db.collection('applications').find({cat_id:req.params.my_param});	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{

 res.send("["+str+"]");
str=""
i=0;

}
            });
		db.close();
        })
})

app.get('/appinfo/:my_param',function (req,res){

 MongoClient.connect(url, function(err, db)
       {
		var json= JSON.stringify(req.params);

            var cursor = db.collection('app_info').find({app_id:req.params.my_param});	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{

 res.send("["+str+"]");
str=""
i=0;

}
            });
		db.close();
        })
})


app.get('/app_by_id/:my_param',function (req,res){

 MongoClient.connect(url, function(err, db)
       {
		var json= JSON.stringify(req.params);

            var cursor = db.collection('applications').find({app_id:req.params.my_param});	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{

 res.send("["+str+"]");
str=""
i=0;

}
            });
		db.close();
        })
})





app.get('/screenshots/:my_param',function (req,res){

 MongoClient.connect(url, function(err, db)
       {
		var json= JSON.stringify(req.params);

            var cursor = db.collection('screen_shots').find({app_id:req.params.my_param});	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{

 res.send("["+str+"]");
str=""
i=0;

}
            });
		db.close();
        })
})




app.get('/comments/:my_param',function (req,res){

 MongoClient.connect(url, function(err, db)
       {
		var json= JSON.stringify(req.params);

            var cursor = db.collection('comments').find({app_id:req.params.my_param});	

            cursor.each(function(err, item) {
               		if (item!=null)
			{
				if (i!=0)
				{
					str=str+","
				}
				i=1;
				str=str+JSON.stringify(item);
			}
else
{

 res.send("["+str+"]");
str=""
i=0;

}
            });
		db.close();
        })
})

















var server = app.listen(3000, function() {}); 
console.log('Server Is Running on port 3000');

