var express = require('express');
const fileUpload = require('express-fileupload');
const fs = require('fs');
var app = express();

var ip = "http://54.211.158.145:2358";

app.use(express.static("public"));
app.use(express.json());
app.use(fileUpload());

app.get("/", (req, res) => {
    res.sendFile(__dirname + "/status.html");
});
app.get("/status", (req, res) => {
    res.sendFile(__dirname + "/status.html");
});
app.get("/submit", function (req, res) {
    res.sendFile(__dirname + "/submit.html");
});
app.get("/score", function (req, res) {
    res.sendFile(__dirname + "/score.html");
});


app.post("/submitProgram", async (req, res) => {
    console.log("submitting...")
    var problemName = req.body.problemName;
    var lang = req.body.languageID;
    var content = req.body.program;
    content = content.replaceAll(problemName, "Main");
    var time = req.body.time;
    var userId = req.body.userId;

    const path = __dirname + "/locals/data/" + problemName;
    var tl = 5;

    var files = fs.readdirSync(path);

    files = files.filter(name => {
        return name.endsWith(".in");
    });

    var tokens = []

    for(var id in files){
        var name = files[id];
        console.log("running... " + name);
    
        var fileName = name.substring(0, name.indexOf("."));
        var inputFile = name;
        var outputFile = fileName + ".out";
        var input = fs.readFileSync(path + "/" + inputFile, "utf8");
        var output = fs.readFileSync(path + "/" + outputFile, "utf8");
                
        var send = await fetch(ip + "/submissions?base64_encoded=false", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                source_code: content,
                language_id: lang,
                stdin: input,
                expected_output: output,
                cpu_time_limit: tl
            })
            // eslint-disable-next-line no-loop-func
        });
        
        var response = await send.json();
        if (!response.hasOwnProperty("token")) {
            res.sendFile(__dirname + "/status.html");
            return;
        }
        tokens.push(response.token);
    }
    
    console.log(tokens);

    await fetch("https://parseapi.back4app.com/classes/submission", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-Parse-Application-Id": "ydpBQ9tdLUa7OkXsfHdXxhvLio5gltRSsKYYFqeX",
            "X-Parse-REST-API-Key": "z0bblcIvW9Ltit6bJbHxegUGnMYV1w29CziDD4SI"
        },
        body: JSON.stringify({
            User: userId,
            tokens: tokens,
            time: time,
            verdict: "",
            problem: problemName
        })
    });
    
    res.send("dones");
});


app.listen(3000, function () {
    console.log("Server is running on http://localhost:3000")
});